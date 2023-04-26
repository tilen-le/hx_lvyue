package com.hexing.system.service;

import com.hexing.system.domain.FcShippingPlanFinancialAccounting;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcShippingPlanAccountingService {

    /**
     * 保存sap财务核算收入
     *
     * @param fcShippingPlanFinancialAccounting
     * @return
     */
    int saveFcShippingPlanFinancialAccounting(FcShippingPlanFinancialAccounting fcShippingPlanFinancialAccounting);

    /**
     * 修改sap财务核算收入
     *
     * @param ifs
     * @return
     */
    int updateFcShippingPlanAccounting(List<FcShippingPlanFinancialAccounting> ifs);

    /**
     * 获取sap核算收入
     *
     * @param id
     * @return
     */
    List<FcShippingPlanFinancialAccounting> listAccountingByPlanId(String id);

}
