package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.domain.entity.SysUser;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.exception.ServiceException;
import com.hexing.common.utils.CodeGenerate;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.*;
import com.hexing.system.domain.bo.SysOssCons;
import com.hexing.system.domain.form.FcShippingPlanReportInfoVo;
import com.hexing.system.mapper.*;
import com.hexing.system.service.*;
import com.hexing.system.utils.HttpKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author firerock_tech
 */
@Service
@Slf4j
public class FcShippingPlanServiceImpl implements IFcShippingPlanService {

    @Resource
    private FcShippingPlanMapper planMapper;

    @Resource
    private FcShippingPlanReportInfoMapper planReportInfoMapper;

    @Resource
    private FcShippingPlanFinancialAccountingMapper planFinancialAccountingMapper;

    @Resource
    private CodeGenerate codeGenerate;

    @Resource
    private FcOrderProductMapper fcOrderProductMapper;

    @Resource
    private FcOrderMapper fcOrderMapper;

    @Resource
    private HttpKit httpKit;

    @Resource
    private FcOssRelevanceMapper fcOssRelevanceMapper;

    @Resource
    private SysOssMapper sysOssMapper;

    @Resource
    private IFcCustomerService customerService;

    @Resource
    private IFcCustomerConsignmentService customerConsignmentService;

    @Resource
    private ISysUserService sysUserService;


    @Override
    public R<Void> saveFcShippingPlan(FcShippingPlan fcShippingPlan) {
        //校验实际业务报关信息
        List<FcShippingPlanReportInfo> reportIfs = fcShippingPlan.getReportList();
        if (reportIfs.isEmpty()) {
            return R.fail("实际业务报关信息不能为空");
        }
        //校验SAP财务核算收入
        List<FcShippingPlanFinancialAccounting> accountIfs = fcShippingPlan.getFinancialList();
        if (accountIfs.isEmpty()) {
            return R.fail("SAP财务核算收入不能为空");
        }
        List<Long> orderIds = new ArrayList<>();
        //校验每个行明细的报关数量 逻辑：<=在途数量 <=报关剩余数量
        for (FcShippingPlanFinancialAccounting accountIf : accountIfs) {
            //报关数量
            BigDecimal reportNum = new BigDecimal(accountIf.getReportCustomsNum());
            //获取订单明细在途数量
            String orderProductId = accountIf.getOrderProductId();

            FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectById(orderProductId);
            BigDecimal inTransitNum = new BigDecimal(fcOrderProduct.getInTransitNum());
            if (reportNum.compareTo(inTransitNum) == 1) {
                return R.fail("订单明细编号:" + accountIf.getSapMaterialCode() + "不能大于在途数量");
            }
            //获取订单数量
            BigDecimal num = new BigDecimal(fcOrderProduct.getNum());
            if (reportNum.compareTo(num) == 1) {
                return R.fail("订单明细编号:" + accountIf.getSapMaterialCode() + "不能大于订单数量");
            }
            //获取剩余报关数量
            LambdaQueryWrapper<FcShippingPlanFinancialAccounting> financialWrapper = new LambdaQueryWrapper<>();
            financialWrapper.eq(FcShippingPlanFinancialAccounting::getOrderProductId, accountIf.getOrderProductId())
                    .orderByAsc(FcShippingPlanFinancialAccounting::getId)
                    .last("limit 1");
            FcShippingPlanFinancialAccounting lastFinancial = planFinancialAccountingMapper.selectOne(financialWrapper);
            if (ObjectUtil.isNotNull(lastFinancial)) {
                BigDecimal reportCustomsResidueNum = new BigDecimal(lastFinancial.getReportCustomsResidueNum());
                if (reportNum.compareTo(reportCustomsResidueNum) == 1) {
                    return R.fail("订单明细编号:" + accountIf.getSapMaterialCode() + "不能大于报关剩余数量");
                }
            }
            orderIds.add(fcOrderProduct.getOrderId());
        }
        //校验所有订单的客户是否为同一个
        boolean checkSoldToPartyCd = checkSoldToPartyCd(orderIds);
        if (!checkSoldToPartyCd) {
            return R.fail("发货计划只能为同一个客户的订单");
        }

        fcShippingPlan.setPlanCode(codeGenerate.genShippingPlanCode());
        //是否通知单证专员
        fcShippingPlan.setIsNoticeDocumentSpecialist("0");
        //未同步sap
        fcShippingPlan.setSyncSapSuccess("0");
        //报关
        fcShippingPlan.setReportCustomsComplted("0");
        //是否已通知单证专员
        fcShippingPlan.setAlreadyNoticeDocumentSpecialist("0");
        //附件版本
        List<SysOssCons> file = fcShippingPlan.getFile();
        if (CollectionUtils.isNotEmpty(file)) {
            fcShippingPlan.setCurrentVersion(1);
        } else {
            fcShippingPlan.setCurrentVersion(0);
        }
        planMapper.insert(fcShippingPlan);
        //附件处理
        handleOssFile(fcShippingPlan);
        for (FcShippingPlanReportInfo reportIf : reportIfs) {
            reportIf.setShippingPlanId(fcShippingPlan.getId());
        }
        planReportInfoMapper.insertBatch(reportIfs);
        for (FcShippingPlanFinancialAccounting accountIf : accountIfs) {
            accountIf.setShippingPlanId(fcShippingPlan.getId());
        }
        planFinancialAccountingMapper.insertBatch(accountIfs);
        return R.ok();
    }

    @Override
    public FcShippingPlan getDetailId(String id) {
        return null;
    }

    @Override
    public TableDataInfo<FcShippingPlan> listFcShippingPlan(FcShippingPlan fcShippingPlan, PageQuery pageQuery) {
        return null;
    }

    @Override
    public TableDataInfo<FcShippingPlan> list(FcShippingPlan fcShippingPlan, PageQuery pageQuery) {
        Page<FcShippingPlan> page = planMapper.selectPageList(pageQuery.build(), fcShippingPlan);
        List<FcShippingPlan> records = page.getRecords();
        records.forEach(record -> {
            //收货方
            String consignee = record.getConsignee();
            FcCustomer customerConsignee = customerService.getCustomerById(consignee);
            record.setConsigneeName(customerConsignee.getName());
            //通知方
            String notifyId = record.getNotifyId();
            if (StringUtils.isEmpty(notifyId)) {
                record.setNotifyName("");
            } else {
                FcCustomer customerNotify = customerService.getCustomerById(notifyId);
                record.setNotifyName(customerNotify.getName());
            }

        });
        return TableDataInfo.build(page);
    }


    @Override
    public R<FcShippingPlan> getShippingPlanDetail(Long id) {
        FcShippingPlan fcShippingPlan = planMapper.selectVoById(id);
        if (ObjectUtil.isNull(fcShippingPlan)) {
            return R.fail("未查询到该发货计划详情");
        }
        //收货方
        String consignee = fcShippingPlan.getConsignee();
        FcCustomer customerConsignee = customerService.getCustomerById(consignee);
        fcShippingPlan.setConsigneeName(customerConsignee.getName());
        //通知方
        String notifyId = fcShippingPlan.getNotifyId();
        if (StringUtils.isNotEmpty(notifyId)) {
            FcCustomer customerNotify = customerService.getCustomerById(notifyId);
            fcShippingPlan.setNotifyName(customerNotify.getName());
        }
        //客户
        String customerId = fcShippingPlan.getCustomerId();
        FcCustomer customer = customerService.getCustomerById(customerId);
        fcShippingPlan.setCustomerName(customer.getName());
        //客户联系人
        String customerContact = fcShippingPlan.getCustomerContact();
        FcCustomerConsignment customerConsignment = customerConsignmentService.getById(Long.valueOf(customerContact));
        fcShippingPlan.setCustomerContactName(customerConsignment.getName());
        //单证专员
        String documentSpecialist = fcShippingPlan.getDocumentSpecialist();
        if (StringUtils.isNotEmpty(documentSpecialist)) {
            SysUser documentSpecia = sysUserService.selectUserByUserName(documentSpecialist);
            fcShippingPlan.setDocumentSpecialistName(documentSpecia.getNickName());
        }
        //财务人员
        String financialStaff = fcShippingPlan.getFinancialStaff();
        if (StringUtils.isNotEmpty(financialStaff)) {
            SysUser financialStaffUser = sysUserService.selectUserByUserName(financialStaff);
            fcShippingPlan.setFinancialStaffName(financialStaffUser.getNickName());
        }
        //附件
        List<FcOssRelevance> ossRelevanceList = fcOssRelevanceMapper.selectList(new LambdaQueryWrapper<FcOssRelevance>()
                .eq(FcOssRelevance::getType, 3)
                .eq(FcOssRelevance::getMainId, id)
                .eq(FcOssRelevance::getVersion, fcShippingPlan.getCurrentVersion()));
        List<SysOss> ossList;
        if (CollectionUtils.isNotEmpty(ossRelevanceList)) {
            List<Long> idList = ossRelevanceList.stream().map(FcOssRelevance::getOssId).collect(Collectors.toList());
            ossList = sysOssMapper.selectBatchIds(idList);
        } else {
            ossList = new ArrayList<>();
        }
        fcShippingPlan.setSysOssList(ossList);
        //查询报关信息
        LambdaQueryWrapper<FcShippingPlanReportInfo> reportInfowrapper = new LambdaQueryWrapper<>();
        reportInfowrapper.eq(FcShippingPlanReportInfo::getShippingPlanId, fcShippingPlan.getId());
        List<FcShippingPlanReportInfo> reportInfoList = planReportInfoMapper.selectList(reportInfowrapper);
        fcShippingPlan.setReportList(reportInfoList);
        //sap核算
        LambdaQueryWrapper<FcShippingPlanFinancialAccounting> financialwrapper = new LambdaQueryWrapper<>();
        financialwrapper.eq(FcShippingPlanFinancialAccounting::getShippingPlanId, fcShippingPlan.getId());
        List<FcShippingPlanFinancialAccounting> financialAccountingList = planFinancialAccountingMapper.selectList(financialwrapper);
        if (financialAccountingList.size() > 0) {
            financialAccountingList.forEach(financialAccounting -> {
                //关联订单和产品信息
                String orderProductId = financialAccounting.getOrderProductId();
                FcOrderProduct product = fcOrderProductMapper.selectOne(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getId, Long.valueOf(orderProductId)));
                Long orderId = product.getOrderId();
                LambdaQueryWrapper<FcOrder> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(FcOrder::getId, orderId);
                FcOrder fcOrder = fcOrderMapper.selectVoOne(wrapper);
                financialAccounting.setOrderNumber(fcOrder.getOrderNumber());
                financialAccounting.setOrderTitle(fcOrder.getOrderTitle());
                financialAccounting.setSapDetailNumber(product.getSapDetailNumber());
                financialAccounting.setProductName(product.getProductName());
                financialAccounting.setProductNumber(product.getProductNumber());
                financialAccounting.setProductModel(product.getProductModel());
                //订单数量
                financialAccounting.setNum(product.getNum());
            });
        }
        fcShippingPlan.setFinancialList(financialAccountingList);
        return R.ok(fcShippingPlan);
    }

    @Override
    public R<Void> updateFcShippingPlan(FcShippingPlan fcShippingPlan) {
        //校验实际业务报关信息
        List<FcShippingPlanReportInfo> reportIfs = fcShippingPlan.getReportList();
        if (reportIfs.isEmpty()) {
            return R.fail("实际业务报关信息不能为空");
        }
        //校验SAP财务核算收入
        List<FcShippingPlanFinancialAccounting> accountIfs = fcShippingPlan.getFinancialList();
        if (accountIfs.isEmpty()) {
            return R.fail("SAP财务核算收入不能为空");
        }
        List<Long> orderIds = new ArrayList<>();
        //校验每个行明细的报关数量 逻辑：<=在途数量 <=报关剩余数量
        for (FcShippingPlanFinancialAccounting accountIf : accountIfs) {
            //报关数量
            BigDecimal reportNum = new BigDecimal(accountIf.getReportCustomsNum());
            //获取订单明细在途数量
            String productId = accountIf.getOrderProductId();
            FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectById(productId);
            BigDecimal inTransitNum = new BigDecimal(fcOrderProduct.getInTransitNum());
            if (reportNum.compareTo(inTransitNum) == 1) {
                return R.fail("订单明细编号:" + accountIf.getSapMaterialCode() + "不能大于在途数量");
            }
            //获取订单数量
            BigDecimal num = new BigDecimal(fcOrderProduct.getNum());
            if (reportNum.compareTo(num) == 1) {
                return R.fail("订单明细编号:" + accountIf.getSapMaterialCode() + "不能大于订单数量");
            }
            //获取剩余报关数量
            LambdaQueryWrapper<FcShippingPlanFinancialAccounting> financialWrapper = new LambdaQueryWrapper<>();
            financialWrapper.eq(FcShippingPlanFinancialAccounting::getOrderProductId, accountIf.getOrderProductId())
                    .orderByAsc(FcShippingPlanFinancialAccounting::getId)
                    .last("limit 1");
            FcShippingPlanFinancialAccounting lastFinancial = planFinancialAccountingMapper.selectOne(financialWrapper);
            if (ObjectUtil.isNotNull(lastFinancial)) {
                BigDecimal reportCustomsResidueNum = new BigDecimal(lastFinancial.getReportCustomsResidueNum());
                if (reportNum.compareTo(reportCustomsResidueNum) == 1) {
                    return R.fail("订单明细编号:" + accountIf.getSapMaterialCode() + "不能大于报关剩余数量");
                }
            }
            orderIds.add(fcOrderProduct.getOrderId());

        }
        //校验所有订单的客户是否为同一个
        boolean checkSoldToPartyCd = checkSoldToPartyCd(orderIds);
        if (!checkSoldToPartyCd) {
            return R.fail("发货计划只能为同一个客户的订单");
        }
        //附件处理
        FcOssRelevance relevance = fcOssRelevanceMapper.selectOne(new LambdaQueryWrapper<FcOssRelevance>().eq(FcOssRelevance::getMainId, fcShippingPlan.getId()).eq(FcOssRelevance::getType, 1).orderByDesc(FcOssRelevance::getVersion).last("limit 1"));
        Integer version = Objects.isNull(relevance) ? 1 : relevance.getVersion() + 1;
        fcShippingPlan.setCurrentVersion(version);
        handleOssFile(fcShippingPlan);
        //更新发货计划信息
        planMapper.updateById(fcShippingPlan);
        //删除该发货计划的报关信息列表
        LambdaQueryWrapper<FcShippingPlanReportInfo> reportInfoWrapper = new LambdaQueryWrapper<>();
        reportInfoWrapper.eq(FcShippingPlanReportInfo::getShippingPlanId, fcShippingPlan.getId());
        planReportInfoMapper.delete(reportInfoWrapper);
        //删除该发货计划的财务核算列表
        LambdaQueryWrapper<FcShippingPlanFinancialAccounting> financialWrapper = new LambdaQueryWrapper<>();
        financialWrapper.eq(FcShippingPlanFinancialAccounting::getShippingPlanId, fcShippingPlan.getId());
        planFinancialAccountingMapper.delete(financialWrapper);
        //添加报关信息列表
        for (FcShippingPlanReportInfo reportIf : reportIfs) {
            reportIf.setShippingPlanId(fcShippingPlan.getId());
        }
        planReportInfoMapper.insertBatch(reportIfs);
        for (FcShippingPlanFinancialAccounting accountIf : accountIfs) {
            accountIf.setShippingPlanId(fcShippingPlan.getId());
        }
        planFinancialAccountingMapper.insertBatch(accountIfs);
        return R.ok();
    }

    @Override
    public R<Void> noticeDocumentSpecialist(Long id) {
        FcShippingPlan fcShippingPlan = planMapper.selectVoById(id);
        if (ObjectUtil.isNull(fcShippingPlan)) {
            return R.fail("未查询到该发货计划详情");
        }
        //判断该计划是否已报关完成
        String reportCustomsCompleted = fcShippingPlan.getReportCustomsComplted();
        if ("1".equals(reportCustomsCompleted)) {
            return R.fail("报关已完成的发货计划不支持该操作");
        }
        //TODO  通知单证专员 message
        fcShippingPlan.setAlreadyNoticeDocumentSpecialist("1");
        planMapper.updateById(fcShippingPlan);
        return R.ok();
    }

    @Override
    public R<Void> reportCustomsCompleted(Long id) {
        FcShippingPlan fcShippingPlan = planMapper.selectVoById(id);
        if (ObjectUtil.isNull(fcShippingPlan)) {
            return R.fail("未查询到该发货计划详情");
        }
        //判断该计划是否已报关完成
        String reportCustomsCompleted = fcShippingPlan.getReportCustomsComplted();
        if ("1".equals(reportCustomsCompleted)) {
            return R.fail("报关已完成的发货计划不支持该操作");
        }
        //判断是否上传附件
        Integer currentVersion = fcShippingPlan.getCurrentVersion();
        if (currentVersion == 0) {
            return R.fail("报关完成需上传附件");
        }
        //TODO  通知财务专员 message
        fcShippingPlan.setReportCustomsComplted("1");
        planMapper.updateById(fcShippingPlan);
        return R.ok();
    }

    @Override
    public R<String> shippingPlanSyncSap(Long id) {
        FcShippingPlan fcShippingPlan = planMapper.selectVoById(id);
        if (ObjectUtil.isNull(fcShippingPlan)) {
            return R.fail("未查询到该发货计划详情");
        }
        //判断该计划是否已报关完成
        String reportCustomsCompleted = fcShippingPlan.getReportCustomsComplted();
        if (!"1".equals(reportCustomsCompleted)) {
            return R.fail("报关未完成的发货计划不支持该操作");
        }
        //c查询该盘点计划下需同步sap的核算列表
        LambdaQueryWrapper<FcShippingPlanFinancialAccounting> financialWrapper = new LambdaQueryWrapper<>();
        financialWrapper.eq(FcShippingPlanFinancialAccounting::getShippingPlanId, fcShippingPlan.getId())
                .eq(FcShippingPlanFinancialAccounting::getSapSyncFlag, "1");
        List<FcShippingPlanFinancialAccounting> financialAccountings = planFinancialAccountingMapper.selectList(financialWrapper);

        Map<String, Object> params = new HashMap<>();
        params.put("interfaceCode", "ZLVY_JHKP");
        List<Object> data = new ArrayList<>(financialAccountings.size());

        for (FcShippingPlanFinancialAccounting info : financialAccountings) {
            String orderProductId = info.getOrderProductId();
            FcOrderProduct fcOrderProduct = fcOrderProductMapper.selectOne(new LambdaQueryWrapper<FcOrderProduct>().eq(FcOrderProduct::getId, Long.valueOf(orderProductId)));
            Long orderId = fcOrderProduct.getOrderId();
            LambdaQueryWrapper<FcOrder> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(FcOrder::getId, orderId);
            FcOrder order = fcOrderMapper.selectVoOne(wrapper);
            Map<String, Object> item = new HashMap<>();
            //订单编号
            item.put("VBELN_VA", order.getOrderNumber());
            //订单行项目号
            item.put("POSNR_VA", fcOrderProduct.getSapDetailNumber());
            //执行类型
//            item.put("CREATE_TYPE", "C");
            //目标数量（采用销售单位）=报关数量
            item.put("ZMENG", info.getReportCustomsNum());
            item.put("KUNNR_BP", order.getSoldToPartyCd());
            item.put("NETPR_ZFOB", fcOrderProduct.getUnitPrice());
            item.put("ITEXT1", "普票");
            item.put("ITEXT3", "");
            item.put("ITEXT4", "");
            item.put("ITEXT5", "");
            item.put("ITEXT6", fcShippingPlan.getContactInformation());
            item.put("ITEXT7", "");
            item.put("CREATE_TYPE", "C");
            item.put("VSTEL", order.getFactory());
            item.put("LGORT", "3007");
            data.add(item);
        }
        params.put("data", data);
        log.info("======发货计划同步sap请求报文={}", params.toString());
        String body = httpKit.postData(params);
        log.info("======发货计划同步sap返回报文={}", body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject dataJO = jsonObject.getJSONObject("data");
        String vl = dataJO.getString("vl");
        String vf = dataJO.getString("vf");
        String message = dataJO.getString("ev_message");
        if (StringUtils.isEmpty(vl) || StringUtils.isEmpty(vf)) {
            throw new ServiceException(message);
        }
        //更新同步SAP是否成功为是
        fcShippingPlan.setSyncSapSuccess("1");
        planMapper.updateById(fcShippingPlan);
        return R.ok();
    }

    private void handleOssFile(FcShippingPlan shippingPlan) {
        List<SysOssCons> files = shippingPlan.getFile();
        if (CollectionUtils.isNotEmpty(files)) {
            List<String> fileIds = files.stream().map(SysOssCons::getOssId).collect(Collectors.toList());
            fileIds.forEach(temp -> {
                FcOssRelevance fcOssRelevance = new FcOssRelevance();
                fcOssRelevance.setOssId(Long.parseLong(temp));
                fcOssRelevance.setMainId(shippingPlan.getId());
                fcOssRelevance.setType(3);
                fcOssRelevance.setVersion(shippingPlan.getCurrentVersion());
                fcOssRelevanceMapper.insert(fcOssRelevance);
            });
        }
    }

    private boolean checkSoldToPartyCd(List<Long> orderIds) {
        if (CollectionUtils.isEmpty(orderIds)) {
            return false;
        }
        LambdaQueryWrapper<FcOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(FcOrder::getId, orderIds);
        List<FcOrder> fcOrders = fcOrderMapper.selectList(wrapper);
        long count = fcOrders.stream().map(FcOrder::getSoldToPartyCd).distinct().count();
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }


}
