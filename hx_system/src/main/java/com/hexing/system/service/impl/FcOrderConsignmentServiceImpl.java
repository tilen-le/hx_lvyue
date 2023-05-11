package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.*;
import com.hexing.system.domain.bo.SysOssCons;
import com.hexing.system.mapper.*;
import com.hexing.system.service.IFcApproveService;
import com.hexing.system.service.IFcOrderConsignmentService;
import com.hexing.system.service.IOrderService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final FcApproveRecordMapper fcApproveRecordMapper;

    private final FcApproveConfigMapper approveConfigMapper;

    private final HttpKit httpKit;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        log.error(JsonUtils.toJsonString(fcOrderConsignment));
        LambdaQueryWrapper<FcOrderConsignment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrderConsignment::getOrderId, fcOrderConsignment.getOrderId());
        List<FcOrderConsignmentDetail> products = fcOrderConsignment.getProducts();
        if (CollectionUtils.isNotEmpty(products)) {
            String amount = getConsignmentAmount(products);
            fcOrderConsignment.setConsignmentAmount(amount);
        }
        //附件处理
        FcOssRelevance relevance = fcOssRelevanceMapper.selectOne(new LambdaQueryWrapper<FcOssRelevance>().eq(FcOssRelevance::getMainId, fcOrderConsignment.getId()).eq(FcOssRelevance::getType, 1).orderByDesc(FcOssRelevance::getVersion).last("limit 1"));
        Integer version = Objects.isNull(relevance) ? 1 : relevance.getVersion() + 1;
        fcOrderConsignment.setCurrentVersion(version);
        fcOrderConsignment.setConsigneeId(fcOrderConsignment.getAddressId());
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
                if (!Objects.equals(fcOrderConsignment.getApprovalStatus(), 3)) {
                    FcOrderProduct product = fcOrderProductMapper.selectById(productId);
                    double v = Double.parseDouble(product.getNotSentNum()) - Double.parseDouble( item.getProductNum());
                    product.setNotSentNum(String.valueOf(v));
                    fcOrderProductMapper.updateById(product);
                }
            });
        }
        //审批信息
        if (Objects.equals(0,fcOrderConsignment.getApprovalStatus()) ){
            handleApprove(fcOrderConsignment);
        }
        return result;
    }

    public String getConsignmentAmount(List<FcOrderConsignmentDetail> consignmentDetails) {
        BigDecimal sum = BigDecimal.ZERO;
        consignmentDetails.forEach(temp -> {
            FcOrderProduct product = fcOrderProductMapper.selectById(temp.getOrderProductId());
            if (ObjectUtil.isNull(product)) {
                return;
            }
            String unitPrice = product.getUnitPrice();
            if (StringUtils.isNotEmpty(unitPrice) && temp.getProductNum() != null) {
                BigDecimal decimal = new BigDecimal(unitPrice).multiply(new BigDecimal(temp.getProductNum()));
                sum.add(decimal);
            }

        });
        return sum.toPlainString();
    }

    @Override
    public int updateFcOrderConsignment(FcOrderConsignment fcOrderConsignment) {
        FcOrderConsignment consignment = baseMapper.selectById(fcOrderConsignment.getId());
        if (Objects.isNull(consignment)) {
            throw new ServiceException("该发货单不存在");
        }
        if (consignment.getApprovalStatus() != 3) {
            throw new ServiceException("只允许修改草稿状态的发货单");
        }

        //todo
        int result = baseMapper.updateById(fcOrderConsignment);
        //附件处理
        handleOssFile(fcOrderConsignment);
        if (result > 0) {
            fcOrderConsignment.getProducts().forEach(item -> {
                Long productId = item.getId();
                item.setConsignmentId(fcOrderConsignment.getId().toString());
                item.setOrderProductId(productId);
                item.setId(null);
                fcOrderConsignmentDetailMapper.updateById(item);
                //库存锁定
                FcOrderProduct product = fcOrderProductMapper.selectById(productId);
                double v = Double.parseDouble(product.getNotSentNum()) - Double.parseDouble( item.getProductNum());
                product.setNotSentNum(String.valueOf(v));
                fcOrderProductMapper.updateById(product);
            });
        }

        return result;
    }

    private void handleOssFile(FcOrderConsignment fcOrderConsignment) {
        List<SysOssCons> files = fcOrderConsignment.getFiles();
        if (CollectionUtils.isNotEmpty(files)) {
            List<String> fileIds =files.stream().map(SysOssCons::getOssId).collect(Collectors.toList());
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
        Page<FcOrderConsignment> page = baseMapper.listFcOrder(pageQuery.build(), fcOrderConsignment);
        return TableDataInfo.build(page);
    }

    @Override
    public Map<String, Object> getDetailById(Long id) {
        Map<String, Object> result = new HashMap<>();
        FcOrderConsignment consignment = baseMapper.selectById(id);
        if (Objects.isNull(consignment)){
            throw new ServiceException("未找到此发货单信息");
        }
        List<FcOrderConsignmentDetail> details = fcOrderConsignmentDetailMapper.selectList(new LambdaQueryWrapper<FcOrderConsignmentDetail>().eq(FcOrderConsignmentDetail::getConsignmentId, consignment.getId()));
        details.forEach(item -> {
            FcOrderProduct orderProduct = fcOrderProductMapper.selectById(item.getOrderProductId());
            item.setOrderProduct(orderProduct);
        });
        FcOrder fcOrder = fcOrderMapper.selectById(consignment.getOrderId());
        FcContract fcContract = orderService.getContact(fcOrder.getContractNumber());
        FcCustomerConsignment customerConsignment = fcCustomerConsignmentMapper.selectById(consignment.getConsigneeId());

        //附件
        List<FcOssRelevance> ossRelevanceList = fcOssRelevanceMapper.selectList(new LambdaQueryWrapper<FcOssRelevance>()
                .eq(FcOssRelevance::getType, 1)
                .eq(FcOssRelevance::getMainId, id)
                .eq(FcOssRelevance::getVersion, consignment.getCurrentVersion()));
        List<SysOss> ossList;
        if (CollectionUtils.isNotEmpty(ossRelevanceList)) {
            List<Long> idList = ossRelevanceList.stream().map(FcOssRelevance::getOssId).collect(Collectors.toList());
            ossList = sysOssMapper.selectBatchIds(idList);
        }else {
            ossList = new ArrayList<>();
        }

        boolean hasConsApprove = hasConsApprove(consignment);

        result.put("ossList", ossList);
        result.put("consignment", consignment);
        result.put("order", Objects.nonNull(fcOrder)?fcOrder:new FcOrder());
        result.put("contract", Objects.nonNull(fcContract) ? fcContract :new FcContract() );
        result.put("customerConsignment", customerConsignment);
        result.put("products", details);
        result.put("hasConsApprove",hasConsApprove);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(FcOrderConsignment consignment) {
        Integer status = consignment.getApprovalStatus();
        baseMapper.update(null,new LambdaUpdateWrapper<FcOrderConsignment>().eq(FcOrderConsignment::getApprovalStatus,status));
        FcApprove fcApprove = getFcApprove(consignment.getId());
        fcApprove.setStatus(status);
        fcApproveMapper.updateById(fcApprove);
        FcApproveRecord approveRecord = new FcApproveRecord();
        approveRecord.setApproveId(fcApprove.getId());
        approveRecord.setResult(String.valueOf(status));
        approveRecord.setOptUser(LoginHelper.getUsername());
        fcApproveRecordMapper.insert(approveRecord);
    }

    private void handleApprove(FcOrderConsignment fcOrderConsignment) {
        FcApprove fcApprove = getFcApprove(fcOrderConsignment.getId());
        if (Objects.isNull(fcApprove)) {
            fcApprove = new FcApprove();
        }
            fcApprove.setTitle("发货审批" + fcOrderConsignment.getConsigmentNumber());
            fcApprove.setType(1);
            fcApprove.setOriginator(Objects.requireNonNull(LoginHelper.getUserId()).toString());
            fcApprove.setStatus(0);
            FcOrder order = fcOrderMapper.selectById(fcOrderConsignment.getOrderId());
            FcApproveConfig approveConfig = approveConfigMapper.selectOne(new LambdaQueryWrapper<FcApproveConfig>()
                    .eq(FcApproveConfig::getFactory, order.getFactory())
                    .eq(FcApproveConfig::getSaleDept, order.getMarketingDepartment()));
            fcApprove.setCurrentNode(approveConfig.getStoreKeeper());
            fcApprove.setRequestTime(new Date());
            fcApprove.setMainId(fcOrderConsignment.getId());
            fcApproveMapper.insertOrUpdate(fcApprove);
    }

    public FcApprove getFcApprove(Long consignmentId){
        return fcApproveMapper.selectOne(new LambdaQueryWrapper<FcApprove>()
                .eq(FcApprove::getMainId, consignmentId)
                .eq(FcApprove::getType, 1));

    }

    public boolean hasConsApprove(FcOrderConsignment fcOrderConsignment){
        String username = LoginHelper.getUsername();
        Integer approvalStatus = fcOrderConsignment.getApprovalStatus();
        if (Objects.equals(0,approvalStatus)){
            FcApprove fcApprove = fcApproveMapper.selectOne(new LambdaQueryWrapper<FcApprove>()
                    .eq(FcApprove::getMainId, fcOrderConsignment.getId())
                    .eq(FcApprove::getType, 1)
                    .eq(FcApprove::getCurrentNode, username));

            return Objects.nonNull(fcApprove);
        }else {
            return false;
        }
    }



    /**
     * 创建发货单同步sap
     * @param fcOrderConsignment
     */
    private void submitSapConsigment(FcOrderConsignment fcOrderConsignment) {
        if (fcOrderConsignment.getProducts() == null) {
            throw new ServiceException("发货明细不能为空");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_FHD");
        List<FcOrderConsignmentDetail> details = fcOrderConsignment.getProducts();
        List<Object> data = new ArrayList<>(details.size());
        LambdaQueryWrapper<FcOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(FcOrder::getId, fcOrderConsignment.getOrderId());
        FcOrder order = fcOrderMapper.selectOne(orderWrapper);
        for (FcOrderConsignmentDetail info : details) {
            FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectById(info.getOrderProductId());
            FcCustomerConsignment consignment = fcCustomerConsignmentMapper.selectById(fcOrderConsignment.getConsigneeId());
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
            item.put("LTCADD_RE", consignment.getAddress() + " " + consignment.getLocation());
            item.put("LTCNAME_RE", consignment.getName());
            item.put("LTCTEL_RE", consignment.getPhone());

            item.put("LTCPR", info.getTechnicalRequirement());
            item.put("RESWK", order.getFactory());
            item.put("VSTEL", order.getFactory());
            item.put("LGORT", "3001");

            data.add(item);
        }
        params.put("data", data);
        httpKit.postData(params);
    }

    private Wrapper<FcOrderConsignment> buildQueryWrapper(FcOrderConsignment fcOrderConsignment) {
        QueryWrapper<FcOrderConsignment> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0").eq(ObjectUtils.isNotNull(fcOrderConsignment.getOrderId()), "order_id", fcOrderConsignment.getOrderId());
        return wrapper;
    }

}
