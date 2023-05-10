package com.hexing.system.service.impl;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcShippingPlan;
import com.hexing.system.domain.FcShippingPlanFinancialAccounting;
import com.hexing.system.domain.FcShippingPlanReportInfo;
import com.hexing.system.mapper.FcShippingPlanMapper;
import com.hexing.system.service.IFcShippingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author firerock_tech
 */
@Service
public class FcShippingPlanServiceImpl implements IFcShippingPlanService {

    @Resource
    private FcShippingPlanMapper planMapper;

    @Override
    public R<Void> saveFcShippingPlan(FcShippingPlan fcShippingPlan) {
        //校验实际业务报关信息
        List<FcShippingPlanReportInfo> reportIfs = fcShippingPlan.getReportIfs();
        if (reportIfs.isEmpty()) {
            return R.fail("实际业务报关信息不能为空");
        }
        //校验SAP财务核算收入
        List<FcShippingPlanFinancialAccounting> accountIfs = fcShippingPlan.getAccountIfs();
        if (accountIfs.isEmpty()) {
            return R.fail("SAP财务核算收入不能为空");
        }

//        accountIfs.stream().map(FcShippingPlanFinancialAccounting)


        planMapper.insert(fcShippingPlan);
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
}
