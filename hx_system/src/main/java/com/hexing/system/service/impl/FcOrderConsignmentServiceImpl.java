package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.entity.SysUser;
import com.hexing.common.core.domain.model.LoginUser;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.*;
import com.hexing.system.domain.bo.SysOssCons;
import com.hexing.system.domain.form.OrderForm;
import com.hexing.system.domain.form.SapFhdForm;
import com.hexing.system.mapper.*;
import com.hexing.system.service.*;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderConsignmentServiceImpl implements IFcOrderConsignmentService {

    private final FcOrderConsignmentMapper baseMapper;

    private final FcOrderMapper fcOrderMapper;

    private final FcOssRelevanceMapper fcOssRelevanceMapper;

    private final FcOrderProductMapper fcOrderProductMapper;

    private final IOrderService orderService;

    private final FcCustomerConsignmentMapper fcCustomerConsignmentMapper;

    private final FcOrderConsignmentDetailMapper fcOrderConsignmentDetailMapper;

    private final SysOssMapper sysOssMapper;

    private final FcApproveMapper fcApproveMapper;
    @Resource
    private IFcApproveService fcApproveService;

    private final FcApproveRecordMapper fcApproveRecordMapper;

    private final IFcApproveConfigService fcApproveConfigService;

    private final SysUserMapper sysUserMapper;

    private final ISysOssService ossService;

    private final HttpKit httpKit;

    @Resource
    @Lazy
    private SyncService syncService;

    public String generateInvoiceCode() {
        // 获取最新的订单ID
        Long latestId = baseMapper.selectMaxid();
        Long sequence = (latestId != null) ? latestId + 1 : 1;

        String formattedSequence = String.format("%05d", sequence); // 格式化为6位数字
        return "DG-" + formattedSequence;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        log.error(JsonUtils.toJsonString(fcOrderConsignment));
        List<FcOrderConsignmentDetail> products = fcOrderConsignment.getProducts();
        if (CollectionUtils.isNotEmpty(products)) {
            String amount = getConsignmentAmount(products);
            fcOrderConsignment.setConsignmentAmount(amount);
        }
        //附件处理
        Integer version = ossService.getVersion(fcOrderConsignment.getId(), 1);
        fcOrderConsignment.setCurrentVersion(version);
        //待审批才创建发货单号
        if (Objects.equals(fcOrderConsignment.getApprovalStatus(), 0)) {
            fcOrderConsignment.setConsigmentNumber(generateInvoiceCode());
        }
        //审批信息
        if (Objects.equals(0, fcOrderConsignment.getApprovalStatus())) {
            handleApprove(fcOrderConsignment);
        }
        int result = baseMapper.insert(fcOrderConsignment);
        handleOssFile(fcOrderConsignment);
        if (result > 0) {
            products.forEach(item -> {
                Long productId = item.getId();
                item.setConsignmentId(fcOrderConsignment.getId().toString());
                item.setOrderProductId(productId);
                item.setId(null);
                fcOrderConsignmentDetailMapper.insert(item);
                //库存锁定
                FcOrderProduct product = fcOrderProductMapper.selectById(productId);
                if (item.getProductNum().compareTo(product.getInStorageNum()) > 0) {
                    throw new ServiceException("行项目" + product.getSapDetailNumber() + "库存不足");
                }
                double v = Double.parseDouble(product.getNotSentNum()) - Double.parseDouble(item.getProductNum());
                product.setNotSentNum(String.valueOf(v));
                fcOrderProductMapper.updateById(product);
            });
        }

        return result;
    }

    @Override
    public int updateFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        FcOrderConsignment consignment = baseMapper.selectById(fcOrderConsignment.getId());
        if (Objects.isNull(consignment)) {
            throw new ServiceException("该发货单不存在");
        }
        Integer status = fcOrderConsignment.getApprovalStatus();
        if (consignment.getApprovalStatus() != 3 && consignment.getApprovalStatus() != 2) {
            if (Objects.equals(0, status) || Objects.equals(3, status)) {
            } else {
                throw new ServiceException("当前审批状态的发货单不允许修改");
            }

        }
        int result = baseMapper.updateById(fcOrderConsignment);
        //附件处理
        handleOssFile(fcOrderConsignment);
        //明细信息
        if (result > 0) {
            fcOrderConsignment.getProducts().forEach(item -> {
                fcOrderConsignmentDetailMapper.updateById(item);
                //库存锁定
                if (!Objects.equals(fcOrderConsignment.getApprovalStatus(), 3)) {
                    FcOrderProduct product = fcOrderProductMapper.selectById(item.getOrderProductId());
                    double v = Double.parseDouble(product.getNotSentNum()) - Double.parseDouble(item.getProductNum());
                    product.setNotSentNum(String.valueOf(v));
                    fcOrderProductMapper.updateById(product);
                }
            });
        }
        return result;
    }

    public String getConsignmentAmount(List<FcOrderConsignmentDetail> consignmentDetails) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < consignmentDetails.size(); i++) {
            FcOrderConsignmentDetail temp = consignmentDetails.get(i);
            FcOrderProduct product = fcOrderProductMapper.selectById(temp.getId());
            if (ObjectUtil.isNull(product)) {
                continue;
            }
            String unitPrice = product.getUnitPrice();
            if (StringUtils.isNotEmpty(unitPrice) && StringUtils.isNotEmpty(temp.getProductNum())) {
                BigDecimal decimal = new BigDecimal(unitPrice).multiply(new BigDecimal(temp.getProductNum()));
                sum = sum.add(decimal);
            }
        }
        return sum.toPlainString();
    }


    private void handleOssFile(FcOrderConsignment fcOrderConsignment) {
        List<SysOssCons> files = fcOrderConsignment.getFiles();
        if (CollectionUtils.isNotEmpty(files)) {
            List<String> fileIds = files.stream().map(SysOssCons::getOssId).collect(Collectors.toList());
            fileIds.forEach(temp -> {
                FcOssRelevance fcOssRelevance = new FcOssRelevance();
                fcOssRelevance.setOssId(Long.parseLong(temp));
                fcOssRelevance.setMainId(fcOrderConsignment.getId());
                fcOssRelevance.setType(1);
                fcOssRelevance.setVersion(fcOrderConsignment.getCurrentVersion());
                fcOssRelevanceMapper.insert(fcOssRelevance);
            });
        }
    }

    @Override
    public TableDataInfo<FcOrderConsignment> listFcOrderConsignment(FcOrderConsignment fcOrderConsignment, PageQuery pageQuery) {
        String orderNumber = fcOrderConsignment.getOrderNumber();
        if (Objects.nonNull(orderNumber)) {
            fcOrderConsignment.setOrderNumber(orderNumber.toUpperCase());
        }
        Page<FcOrderConsignment> page = baseMapper.listFcOrder(pageQuery.build(), fcOrderConsignment);
        return TableDataInfo.build(page);
    }

    @Override
    public Map<String, Object> getDetailById(Long id) {
        Map<String, Object> result = new HashMap<>();
        FcOrderConsignment consignment = baseMapper.selectById(id);
        if (Objects.isNull(consignment)) {
            throw new ServiceException("未找到此发货单信息");
        }
        List<FcOrderConsignmentDetail> details = fcOrderConsignmentDetailMapper.selectList(new LambdaQueryWrapper<FcOrderConsignmentDetail>().eq(FcOrderConsignmentDetail::getConsignmentId, consignment.getId()));
        List<FcOrderProduct> productList = new ArrayList<>();
        details.forEach(item -> {
            FcOrderProduct orderProduct = fcOrderProductMapper.selectById(item.getOrderProductId());
            item.setOrderProduct(orderProduct);
            productList.add(orderProduct);
        });
        syncService.syncStore(productList);
        FcOrder fcOrder = fcOrderMapper.selectById(consignment.getOrderId());
        FcContract fcContract = orderService.getContact(fcOrder.getContractNumber());
        FcCustomerConsignment customerConsignment = fcCustomerConsignmentMapper.selectById(consignment.getAddressId());
        //附件
        List<FcOssRelevance> ossRelevanceList = fcOssRelevanceMapper.selectList(new LambdaQueryWrapper<FcOssRelevance>()
                .eq(FcOssRelevance::getType, 1)
                .eq(FcOssRelevance::getMainId, id)
                .eq(FcOssRelevance::getVersion, consignment.getCurrentVersion()));
        List<SysOss> ossList;
        if (CollectionUtils.isNotEmpty(ossRelevanceList)) {
            List<Long> idList = ossRelevanceList.stream().map(FcOssRelevance::getOssId).collect(Collectors.toList());
            ossList = sysOssMapper.selectBatchIds(idList);
        } else {
            ossList = new ArrayList<>();
        }
        FcApproveConfig fcApproveConfig = fcApproveConfigService.getFcApproveConfig(fcOrder);
        if (Objects.nonNull(fcApproveConfig)) {
            Long keeperId = fcApproveConfig.getStoreKeeper();
            SysUser sysUser = sysUserMapper.selectUserById(keeperId);
            consignment.setStoreKeeper(sysUser.getUserName() + "(" + sysUser.getNickName() + ")");
        }
        boolean hasConsApprove = hasConsApprove(consignment);
        result.put("ossList", ossList);
        result.put("consignment", consignment);
        result.put("order", fcOrder);
        result.put("contract", Objects.nonNull(fcContract) ? fcContract : new FcContract());
        result.put("customerConsignment", Objects.nonNull(customerConsignment) ? customerConsignment : new FcCustomerConsignment());
        result.put("products", details);
        result.put("hasConsApprove", hasConsApprove);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(FcOrderConsignment consignment) {
        Integer status = consignment.getApprovalStatus();
        //撤销审批
        baseMapper.update(null, new LambdaUpdateWrapper<FcOrderConsignment>().set(FcOrderConsignment::getApprovalStatus, status).eq(FcOrderConsignment::getId, consignment.getId()));
        if (Objects.equals(1, status) || Objects.equals(2, status)) {
            FcApprove fcApprove = fcApproveService.getFcApprove(consignment.getId(), 1);
            fcApprove.setStatus(status);
            fcApproveMapper.updateById(fcApprove);
            FcApproveRecord approveRecord = new FcApproveRecord();
            approveRecord.setApproveId(fcApprove.getId());
            approveRecord.setResult(String.valueOf(status));
            approveRecord.setOptUser(LoginHelper.getUsername());
            fcApproveRecordMapper.insert(approveRecord);
            //若审批通过
            if (Objects.equals(1, status)) {
                //创建发货同步SAP
                String result = submitSapConsigment(consignment);
                log.error(result);
            }
        }
    }


    private void handleApprove(FcOrderConsignment fcOrderConsignment) {
        FcApprove fcApprove = fcApproveService.getFcApprove(fcOrderConsignment.getId(), 1);
        if (Objects.isNull(fcApprove)) {
            fcApprove = new FcApprove();
        }
        fcApprove.setTitle("发货审批" + fcOrderConsignment.getConsigmentNumber());
        fcApprove.setType(1);
        fcApprove.setOriginator(Objects.requireNonNull(LoginHelper.getUserId()).toString());
        fcApprove.setStatus(0);
        FcOrder order = fcOrderMapper.selectById(fcOrderConsignment.getOrderId());
        FcApproveConfig approveConfig = fcApproveConfigService.getFcApproveConfig(order);
        if (Objects.isNull(approveConfig)) {
            throw new ServiceException("未找到对应审批人，请维护审批配置");
        }
        fcApprove.setCurrentNode(approveConfig.getStoreKeeper());
        fcApprove.setRequestTime(new Date());
        fcApprove.setMainId(fcOrderConsignment.getId());
        fcApproveMapper.insertOrUpdate(fcApprove);
    }


    public boolean hasConsApprove(FcOrderConsignment fcOrderConsignment) {
        if (LoginHelper.isAdmin()) {
            return true;
        }
        LoginUser loginUser = LoginHelper.getLoginUser();
        Integer approvalStatus = fcOrderConsignment.getApprovalStatus();
        if (Objects.equals(0, approvalStatus)) {
            FcApprove fcApprove = fcApproveMapper.selectOne(new LambdaQueryWrapper<FcApprove>()
                    .eq(FcApprove::getMainId, fcOrderConsignment.getId())
                    .eq(FcApprove::getType, 1)
                    .eq(FcApprove::getCurrentNode, loginUser.getUsername()));
            return Objects.nonNull(fcApprove);
        } else {
            return false;
        }
    }


    /**
     * 创建发货单同步sap
     *
     * @param fcOrderConsignment
     */
    @Async
    public String submitSapConsigment(FcOrderConsignment fcOrderConsignment) {
        Long consignmentId = fcOrderConsignment.getId();
        List<FcOrderConsignmentDetail> details = fcOrderConsignmentDetailMapper.selectList(new LambdaQueryWrapper<FcOrderConsignmentDetail>().eq(FcOrderConsignmentDetail::getConsignmentId, consignmentId));
        if (CollectionUtils.isEmpty(details)) {
            throw new ServiceException("发货明细不能为空");
        }
        fcOrderConsignment = baseMapper.selectById(consignmentId);
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_FHD");
        List<Object> data = new ArrayList<>(details.size());
        LambdaQueryWrapper<FcOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(FcOrder::getId, fcOrderConsignment.getOrderId());
        FcOrder order = fcOrderMapper.selectOne(orderWrapper);
        List<OrderForm> forms = new ArrayList<>();
        for (FcOrderConsignmentDetail info : details) {
            FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectById(info.getOrderProductId());
            FcCustomerConsignment consignment = fcCustomerConsignmentMapper.selectById(fcOrderConsignment.getAddressId());
            OrderForm orderForm = new OrderForm();
            orderForm.setMatnr(fcOrderProduct.getProductNumber());
            orderForm.setWerks(order.getFactory());
            orderForm.setVbeln(order.getOrderNumber());
            orderForm.setPosnr(fcOrderProduct.getSapDetailNumber());
            orderForm.setOrderProduct(fcOrderProduct);
            forms.add(orderForm);
            Map<String, Object> item = new HashMap<>();
            item.put("KDAUF", order.getOrderNumber());
            item.put("KDPOS", fcOrderProduct.getSapDetailNumber());
            item.put("EMATN", fcOrderProduct.getProductNumber());
            item.put("MENGE", info.getProductNum());
            item.put("CREATE_TYPE", "C");
            item.put("VBELN", order.getOrderNumber());
            item.put("POSNR", fcOrderProduct.getSapDetailNumber());
            item.put("LTCYN_D", fcOrderConsignment.getIsReserveSend());
            item.put("LTCITEM", fcOrderConsignment.getRemark());
            item.put("LTCADD_RE", consignment.getLocation() + consignment.getAddress() + " " + consignment.getLocation());
            item.put("LTCNAME_RE", consignment.getName());
            item.put("LTCTEL_RE", consignment.getPhone());

            item.put("LTCPR", info.getTechnicalRequirement());
            item.put("RESWK", order.getFactory());
            item.put("VSTEL", order.getFactory());
            item.put("LGORT", "3001");

            data.add(item);
        }
        Map<String, Object> item01 = new HashMap<>(1);
        item01.put("ITEM_01", data);
        params.put("data", item01);
        log.error(JSON.toJSONString(params));
        String result = httpKit.postData(params);
        SapFhdForm fhdForm = JSONObject.parseObject(result, SapFhdForm.class);
        if ("S".equals(fhdForm.getTypes())) {
            //更新库存信息 释放库存
            for (OrderForm orderForm : forms) {
                syncService.getStore(orderForm);
            }
            baseMapper.update(null, new LambdaUpdateWrapper<FcOrderConsignment>()
                    .eq(FcOrderConsignment::getId, consignmentId)
                    .set(FcOrderConsignment::getSyncSapStatus, 1)
                    .set(FcOrderConsignment::getSyncSapVoucher, fhdForm.getVbeln())
                    .set(FcOrderConsignment::getSyncSapTime, new Date()));
        } else if ("E".equals(fhdForm.getTypes())) {
            throw new ServiceException(fhdForm.getZmsg());
        }
        return result;
    }

    private Wrapper<FcOrderConsignment> buildQueryWrapper(FcOrderConsignment fcOrderConsignment) {
        QueryWrapper<FcOrderConsignment> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0").eq(ObjectUtils.isNotNull(fcOrderConsignment.getOrderId()), "order_id", fcOrderConsignment.getOrderId());
        return wrapper;
    }

}
