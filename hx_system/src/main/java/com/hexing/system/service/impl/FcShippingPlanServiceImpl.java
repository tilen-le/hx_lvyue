package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.utils.CodeGenerate;
import com.hexing.system.domain.FcShippingPlan;
import com.hexing.system.domain.FcShippingPlanFinancialAccounting;
import com.hexing.system.domain.FcShippingPlanReportInfo;
import com.hexing.system.domain.form.FcShippingPlanReportInfoVo;
import com.hexing.system.mapper.FcShippingPlanFinancialAccountingMapper;
import com.hexing.system.mapper.FcShippingPlanMapper;
import com.hexing.system.mapper.FcShippingPlanReportInfoMapper;
import com.hexing.system.service.IFcShippingPlanService;
import com.hexing.system.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author firerock_tech
 */
@Service
public class FcShippingPlanServiceImpl implements IFcShippingPlanService {

    @Resource
    private FcShippingPlanMapper planMapper;

    @Resource
    private FcShippingPlanReportInfoMapper planReportInfoMapper;

    @Resource
    private FcShippingPlanFinancialAccountingMapper planFinancialAccountingMapper;

    @Resource
    private CodeGenerate codeGenerate;

    @Autowired
    private IOrderService orderService;

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
        fcShippingPlan.setPlanCode(codeGenerate.genShippingPlanCode());
        planMapper.insert(fcShippingPlan);
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
        return TableDataInfo.build(page);
    }


    @Override
    public R<FcShippingPlan> getShippingPlanDetail(Long id) {
        FcShippingPlan fcShippingPlan = planMapper.selectVoById(id);
        if (ObjectUtil.isNull(fcShippingPlan)) {
            return R.fail("为查询到该发货计划详情");
        }
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
            List<String> productIds = financialAccountingList.stream().map(FcShippingPlanFinancialAccounting::getOrderProductId).collect(Collectors.toList());
            R<List<FcShippingPlanReportInfoVo>> orderAndProduct = orderService.getOrderAndProduct(productIds);


        }


        return null;
    }
}
