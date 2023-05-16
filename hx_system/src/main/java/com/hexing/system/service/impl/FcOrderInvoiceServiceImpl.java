package com.hexing.system.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.core.service.DictService;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.helper.LoginHelper;
import com.hexing.common.utils.JsonUtils;
import com.hexing.system.domain.*;
import com.hexing.system.domain.bo.SysOssCons;
import com.hexing.system.domain.form.OrderForm;
import com.hexing.system.domain.form.SapJhkpForm;
import com.hexing.system.mapper.*;
import com.hexing.system.service.IFcApproveConfigService;
import com.hexing.system.service.IFcApproveService;
import com.hexing.system.service.IFcOrderInvoiceService;
import com.hexing.system.service.ISysOssService;
import com.hexing.system.utils.HttpKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author firerock_tech
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FcOrderInvoiceServiceImpl implements IFcOrderInvoiceService {

    @Resource
    private FcOrderInvoiceMapper baseMapper;
    @Resource
    private FcOrderMapper fcOrderMapper;

    @Resource
    private FcOssRelevanceMapper fcOssRelevanceMapper;

    @Resource
    private FcOrderProductMapper productMapper;

    @Resource
    private IFcApproveService fcApproveService;

    @Resource
    private FcApproveMapper approveMapper;

    @Resource
    private FcSaleBankMapper saleBankMapper;

    @Resource
    private FcCustomerMapper fcCustomerMapper;

    @Resource
    private FcCustomerConsignmentMapper fcCustomerConsignmentMapper;

    @Resource
    private FcOrderInvoiceDetailMapper fcOrderInvoiceDetailMapper;

    private final IFcApproveConfigService approveConfigService;

    private final FcApproveRecordMapper fcApproveRecordMapper;
    private final HttpKit httpKit;

    private final FcCustomerInvoiceMapper customerInvoiceMapper;

    private final DictService dictService;
    private final SysOssMapper sysOssMapper;

    private final ISysOssService ossService;

    @Resource
    @Lazy
    private SyncService syncService;

    public String generateOrderCode() {
        // 获取最新的订单ID
        Long latestId = baseMapper.selectMaxid();
        Long sequence = (latestId != null) ? latestId + 1 : 1;
        String formattedSequence = String.format("%05d", sequence); // 格式化为6位数字
        return "IN-" + formattedSequence;
    }

    @Override
    public int saveFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {
        log.error(JsonUtils.toJsonString(fcOrderInvoice));
        if (Objects.equals(fcOrderInvoice.getApprovalStatus(),0) || Objects.equals(fcOrderInvoice.getApprovalStatus(),3)){
            fcOrderInvoice.setInvoiceNumber(generateOrderCode());
        }
        Integer version = ossService.getVersion(fcOrderInvoice.getId(), 2);
        fcOrderInvoice.setCurrentVersion(version);
        int result = baseMapper.insert(fcOrderInvoice);
        //附件处理
        handleOssFile(fcOrderInvoice);
        if (result > 0) {
            for (FcOrderInvoiceDetail detail : fcOrderInvoice.getProductList()) {
                String productId = detail.getOrderProductId();
                FcOrderProduct product = productMapper.selectById(productId);
                //累计开票限制
                String sumInTransitNum = baseMapper.selectSumInTransitNum(fcOrderInvoice.getOrderId(),productId);
                Double v =Double.parseDouble(product.getInTransitNum()) -  Double.parseDouble(sumInTransitNum) ;
                if (Double.valueOf(detail.getAppliedQuantity()).compareTo(v) > 0) {
                    throw new ServiceException("可开票数量不足");
                }
                detail.setInvoiceId(fcOrderInvoice.getId());
                fcOrderInvoiceDetailMapper.insert(detail);
            }
        }
        //审批信息
        if (Objects.equals(0, fcOrderInvoice.getApprovalStatus())) {
            handleApprove(fcOrderInvoice);
        }
        return result;
    }

    @Override
    public int updateFcOrderInvoice(FcOrderInvoice fcOrderInvoice) {
        log.error(JsonUtils.toJsonString(fcOrderInvoice));
        FcOrderInvoice orderInvoice = baseMapper.selectById(fcOrderInvoice.getId());
        if (Objects.isNull(orderInvoice)) {
            throw new ServiceException("该开票单不存在");
        }
        Integer status = fcOrderInvoice.getApprovalStatus();
        if (orderInvoice.getApprovalStatus() != 3 && orderInvoice.getApprovalStatus() != 2) {
            if (Objects.equals(0,status)||Objects.equals(3,status)){

            }else {
                throw new ServiceException("当前审批状态的开票单不允许修改");
            }
        }
        Integer version = ossService.getVersion(fcOrderInvoice.getId(), 2);
        fcOrderInvoice.setCurrentVersion(version);
        int result = baseMapper.updateById(fcOrderInvoice);
        handleOssFile(fcOrderInvoice);
        if (result > 0) {
            List<FcOrderInvoiceDetail> productList = fcOrderInvoice.getProductList();
            if (CollectionUtils.isNotEmpty(productList))
            fcOrderInvoiceDetailMapper.updateBatchById(productList);
        }
        //审批信息
        if (Objects.equals(0, fcOrderInvoice.getApprovalStatus())) {
            handleApprove(fcOrderInvoice);
        }

        return result;
    }

//    private String getInvoiceAmount(List<FcOrderInvoiceDetail> productList) {
//        BigDecimal sum = BigDecimal.ZERO;
//        for (int i = 0; i < productList.size(); i++) {
//            FcOrderInvoiceDetail invoiceDetail = productList.get(i);
//            FcOrderProduct product = productMapper.selectById(invoiceDetail.getOrderProductId());
//            if (ObjectUtil.isNull(product)) {
//                continue;
//            }
//            String unitPrice = product.getUnitPrice();
//            if (StringUtils.isNotEmpty(unitPrice) && StringUtils.isNotEmpty(invoiceDetail.getAppliedQuantity())) {
//                BigDecimal decimal = new BigDecimal(unitPrice).multiply(new BigDecimal(invoiceDetail.getAppliedQuantity()));
//                sum = sum.add(decimal);
//            }
//        }
//        return sum.toPlainString();
//    }

    private void handleOssFile(FcOrderInvoice fcOrderInvoice) {
        List<SysOssCons> files = fcOrderInvoice.getFiles();
        if (CollectionUtils.isNotEmpty(files)) {
            List<String> fileIds = files.stream().map(SysOssCons::getOssId).collect(Collectors.toList());
            fileIds.forEach(temp -> {
                FcOssRelevance fcOssRelevance = new FcOssRelevance();
                fcOssRelevance.setOssId(Long.parseLong(temp));
                fcOssRelevance.setMainId(fcOrderInvoice.getId());
                fcOssRelevance.setType(2);
                fcOssRelevance.setVersion(fcOrderInvoice.getCurrentVersion());
                fcOssRelevanceMapper.insert(fcOssRelevance);
            });
        }
    }

    private void handleApprove(FcOrderInvoice fcOrderInvoice) {
        FcApprove fcApprove = fcApproveService.getFcApprove(fcOrderInvoice.getId(), 2);
        if (Objects.isNull(fcApprove)) {
            fcApprove = new FcApprove();
        }
        fcApprove.setTitle("开票审批" + fcOrderInvoice.getInvoiceNumber());
        fcApprove.setType(2);
        fcApprove.setOriginator(Objects.requireNonNull(LoginHelper.getUserId()).toString());
        fcApprove.setStatus(0);
        FcOrder order = fcOrderMapper.selectById(fcOrderInvoice.getOrderId());
        FcApproveConfig approveConfig = approveConfigService.getFcApproveConfig(order);
        if (Objects.isNull(approveConfig)) {
            throw new ServiceException("未找到对应审批人，请维护审批配置");
        }
        fcApprove.setCurrentNode(approveConfig.getBookKeeper());
        fcApprove.setRequestTime(new Date());
        fcApprove.setMainId(fcOrderInvoice.getId());
        fcApproveService.saveFcApprove(fcApprove);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(FcOrderInvoice fcOrderInvoice) {
        Integer status = fcOrderInvoice.getApprovalStatus();
        //撤销审批
        baseMapper.update(null, new LambdaUpdateWrapper<FcOrderInvoice>().set(FcOrderInvoice::getApprovalStatus, status).eq(FcOrderInvoice::getId, fcOrderInvoice.getId()));
        if (Objects.equals(1, status) || Objects.equals(2, status)) {
            FcApprove fcApprove = fcApproveService.getFcApprove(fcOrderInvoice.getId(), 2);
            fcApprove.setStatus(status);
            fcApproveService.updateFcApprove(fcApprove);
            FcApproveRecord approveRecord = new FcApproveRecord();
            approveRecord.setApproveId(fcApprove.getId());
            approveRecord.setResult(String.valueOf(status));
            approveRecord.setOptUser(LoginHelper.getUsername());
            fcApproveRecordMapper.insert(approveRecord);
            //若审批通过
            if (Objects.equals(1, status)) {
                //创建开票同步SAP
                String result = submitSapInvoice(fcOrderInvoice);
                log.error(result);
            }
        }
    }

    /**
     * *{
     * *     "interfaceCode": "ZLVY_JHKP",
     * *     "data": [{
     * *         "VBELN_VA": "PN23.04005",
     * *         "POSNR_VA": "10",
     * *         "ZMENG": "1",
     * *         "KUNNR_BP": "0080000000",
     * *         "NETPR_ZFOB": "55",
     * *         "ITEXT1": "普票",
     * *         "ITEXT3": "",
     * *         "ITEXT4": "个",
     * *         "ITEXT5": "",
     * *         "ITEXT6": "杭州市碧江区(18868899351)",
     * *         "ITEXT7": "滨江支行",
     * *         "CREATE_TYPE":"C",
     * *         "LGORT":"3007",
     * *         "VSTEL":"1007"
     * *     }]
     * * }
     *
     * @param fcOrderInvoice
     * @return
     */
    @Async
    public String submitSapInvoice(FcOrderInvoice fcOrderInvoice) {
        Long invoiceId = fcOrderInvoice.getId();
        List<FcOrderInvoiceDetail> details = fcOrderInvoiceDetailMapper.selectList(new LambdaQueryWrapper<FcOrderInvoiceDetail>().eq(FcOrderInvoiceDetail::getInvoiceId, invoiceId));
        if (CollectionUtils.isEmpty(details)) {
            throw new ServiceException("开票明细不能为空");
        }
        fcOrderInvoice = baseMapper.selectById(fcOrderInvoice.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_JHKP");
        List<Object> data = new ArrayList<>(details.size());
        LambdaQueryWrapper<FcOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(FcOrder::getId, fcOrderInvoice.getOrderId());
        FcOrder order = fcOrderMapper.selectOne(orderWrapper);
        List<OrderForm> forms = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            FcOrderInvoiceDetail info = details.get(i);
            if (Objects.isNull( info.getAppliedQuantity()) || Objects.equals(Double.parseDouble(info.getAppliedQuantity()),0.0)){
                continue;
            }
            FcOrderProduct fcOrderProduct = productMapper.selectById(info.getOrderProductId());
            FcCustomerConsignment fcCustomerConsignment = fcCustomerConsignmentMapper.selectById(fcOrderInvoice.getConsignmentId());
            Map<String, Object> item = new HashMap<>();
            String invoiceType = dictService.getDictLabel("invoice_type", fcOrderInvoice.getInvoiceType().toString());
            OrderForm orderForm = new OrderForm();
            orderForm.setMatnr(fcOrderProduct.getProductNumber());
            orderForm.setWerks(order.getFactory());
            orderForm.setVbeln(order.getOrderNumber());
            orderForm.setPosnr(fcOrderProduct.getSapDetailNumber());
            orderForm.setOrderProduct(fcOrderProduct);
            forms.add(orderForm);
            FcCustomerInvoice customerInvoice = customerInvoiceMapper.selectById(fcOrderInvoice.getOpeningBank());
            item.put("VBELN_VA", order.getOrderNumber());
            item.put("POSNR_VA", fcOrderProduct.getSapDetailNumber());
            item.put("ZMENG", info.getAppliedQuantity());
            item.put("KUNNR_BP", fcOrderInvoice.getConsigneeId());
            item.put("NETPR_ZFOB", info.getInvoicingUnitPriceWithTax());
            item.put("CREATE_TYPE", "C");
            item.put("ITEXT1", invoiceType);
            item.put("ITEXT3", info.getCustomerSpecName());
            item.put("ITEXT4", info.getUnit());
            item.put("ITEXT5", "");
            item.put("ITEXT6", fcCustomerConsignment.getLocation() + fcCustomerConsignment.getAddress() + "(" + fcCustomerConsignment.getPhone() + ")");
            item.put("ITEXT7", customerInvoice.getOpeningBank() + "(" + customerInvoice.getAccount() + ")");
            item.put("LGORT", "3007");
            item.put("VSTEL", order.getFactory());
            data.add(item);
        }
        params.put("data", data);
        log.error("param{}", JSON.toJSONString(params));
        String result = httpKit.postData(params);
        JSONObject resultObj = JSONObject.parseObject(result);
        log.error("result{}", JSON.toJSONString(params));
        SapJhkpForm jhkpForm = resultObj.getObject("data", SapJhkpForm.class);
        if ("S".equals(jhkpForm.getEv_type())) {
            //更新在途库存信息
            for (OrderForm orderForm : forms) {
                syncService.getStore(orderForm);
            }
            baseMapper.update(null, new LambdaUpdateWrapper<FcOrderInvoice>()
                    .eq(FcOrderInvoice::getOrderId, fcOrderInvoice.getOrderId())
                    .set(FcOrderInvoice::getSyncSapStatus, 1)
                    .set(FcOrderInvoice::getSyncSapVoucher, jhkpForm.getVf())
                    .set(FcOrderInvoice::getSyncSapTime, new Date())
            );
        } else if ("E".equals(jhkpForm.getEv_type())) {
            throw new ServiceException(jhkpForm.getEv_message());
        }
        return result;
    }




    @Override
    public TableDataInfo<FcOrderInvoice> listFcOrderInvoice(FcOrderInvoice fcOrderInvoice, PageQuery pageQuery) {
        Page<FcOrderInvoice> page = baseMapper.listPageInvoice(pageQuery.build(), fcOrderInvoice);
        return TableDataInfo.build(page);
    }

    private Wrapper<FcOrderInvoice> buildQueryWrapper(FcOrderInvoice fcOrderInvoice) {
        QueryWrapper<FcOrderInvoice> wrapper = Wrappers.query();
        wrapper.eq("deleted", "0")
                .like(StringUtils.isNotEmpty(fcOrderInvoice.getInvoiceNumber()), "invoice_number", fcOrderInvoice.getInvoiceNumber())
                .eq(ObjectUtils.isNotNull(fcOrderInvoice.getInvoiceType()), "invoice_type", fcOrderInvoice.getInvoiceType())
                .eq(ObjectUtils.isNotNull(fcOrderInvoice.getApprovalStatus()), "approval_status", fcOrderInvoice.getApprovalStatus());
        return wrapper;
    }

    @Override
    public Map<String, Object> getDetailById(Long id) {
        Map<String, Object> result = new HashMap<>();
        FcOrderInvoice fcOrderInvoice = baseMapper.selectById(id);
        String consigneeId = fcOrderInvoice.getConsigneeId();
        FcOrder fcOrder = fcOrderMapper.selectById(fcOrderInvoice.getOrderId());
        FcCustomer fcCustomer = fcCustomerMapper.selectOne(new LambdaQueryWrapper<FcCustomer>()
                .eq(FcCustomer::getCode, consigneeId));
        fcOrderInvoice.setCustomer(fcOrder.getBillee());
        fcOrderInvoice.setOrderTitle(fcOrder.getOrderTitle());
        fcOrderInvoice.setFactory(fcOrder.getFactory());
        fcOrderInvoice.setOrderNumber(fcOrder.getOrderNumber());
        if (fcCustomer != null) {
            fcOrderInvoice.setConsigneeName(fcCustomer.getName());
        }
        FcSaleBank fcSaleBank = saleBankMapper.selectById(fcOrderInvoice.getSaleBank());
        if (Objects.nonNull(fcSaleBank)) {
            fcOrderInvoice.setSaleBankName(fcSaleBank.getBankName());
        }
        //收票方
        FcCustomerInvoice fcCustomerInvoice = customerInvoiceMapper.selectById(fcOrderInvoice.getOpeningBank());
        List<FcOrderInvoiceDetail> fcOrderInvoiceDetails = fcOrderInvoiceDetailMapper.selectList(new LambdaUpdateWrapper<FcOrderInvoiceDetail>()
                .eq(FcOrderInvoiceDetail::getInvoiceId, id));
        if (CollectionUtils.isEmpty(fcOrderInvoiceDetails)){
            fcOrderInvoiceDetails = new ArrayList<>();
        }else {
            fcOrderInvoiceDetails.forEach(item -> {
                FcOrderProduct product = productMapper.selectById(item.getOrderProductId());
                item.setProduct(product==null?new FcOrderProduct():product);
            });
        }
        //附件
        List<FcOssRelevance> ossRelevanceList = fcOssRelevanceMapper.selectList(new LambdaQueryWrapper<FcOssRelevance>()
                .eq(FcOssRelevance::getType, 2)
                .eq(FcOssRelevance::getMainId, id)
                .eq(FcOssRelevance::getVersion, fcOrderInvoice.getCurrentVersion()));
        List<SysOss> ossList;
        if (CollectionUtils.isNotEmpty(ossRelevanceList)) {
            List<Long> idList = ossRelevanceList.stream().map(FcOssRelevance::getOssId).collect(Collectors.toList());
            ossList = sysOssMapper.selectBatchIds(idList);
        } else {
            ossList = new ArrayList<>();
        }

        boolean hasConsApprove = hasConsApprove(fcOrderInvoice);
        FcCustomerConsignment fcCustomerConsignment = fcCustomerConsignmentMapper.selectById(fcOrderInvoice.getConsignmentId());
        result.put("fcOrderInvoice", fcOrderInvoice);
        result.put("fcOrderInvoiceDetail", fcOrderInvoiceDetails);
        result.put("fcCustomerConsignment", fcCustomerConsignment != null ? fcCustomerConsignment : new FcCustomerConsignment());
        result.put("fcCustomerInvoice", fcCustomerInvoice != null ? fcCustomerInvoice : new FcCustomerInvoice());
        result.put("ossList", ossList);
        result.put("hasConsApprove", hasConsApprove);
        return result;
    }

    private boolean hasConsApprove(FcOrderInvoice fcOrderInvoice) {
        if (LoginHelper.isAdmin()) {
            return true;
        }
        String username = LoginHelper.getUsername();
        Integer approvalStatus = fcOrderInvoice.getApprovalStatus();
        if (Objects.equals(0, approvalStatus)) {
            FcApprove fcApprove = approveMapper.selectOne(new LambdaQueryWrapper<FcApprove>()
                    .eq(FcApprove::getMainId, fcOrderInvoice.getId())
                    .eq(FcApprove::getType, 2)
                    .eq(FcApprove::getCurrentNode, username));
            return Objects.nonNull(fcApprove);
        } else {
            return false;
        }
    }


}
