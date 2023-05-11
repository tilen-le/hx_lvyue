package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcShippingPlan;

/**
 * @author firerock_tech
 */
public interface IFcShippingPlanService {


    /**
     * 保存发货计划单
     *
     * @param fcShippingPlan
     * @return
     */
    R<Void> saveFcShippingPlan(FcShippingPlan fcShippingPlan);

    /**
     * 发货计划单详情
     * @param id
     * @return
     */
    FcShippingPlan getDetailId(String id);

    /**
     * 获取发货计划单列表
     *
     * @param fcShippingPlan
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcShippingPlan> listFcShippingPlan(FcShippingPlan fcShippingPlan, PageQuery pageQuery);

    TableDataInfo<FcShippingPlan> list(FcShippingPlan fcShippingPlan, PageQuery pageQuery);


    R<FcShippingPlan> getShippingPlanDetail(Long id);

}
