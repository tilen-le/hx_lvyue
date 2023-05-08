package com.hexing.system.service.impl;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcShippingPlan;
import com.hexing.system.service.IFcShippingPlanService;
import org.springframework.stereotype.Service;

/**
 * @author firerock_tech
 */
@Service
public class FcShippingPlanServiceImpl implements IFcShippingPlanService {
    @Override
    public int saveFcShippingPlan(FcShippingPlan fcShippingPlan) {
        return 0;
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
