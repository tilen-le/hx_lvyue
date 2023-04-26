package com.hexing.system.service.impl;

import com.hexing.system.domain.FcShippingPlanReportInfo;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcShippingPlanReportInfoService {

    /**
     * 保存实际业务报关信息
     *
     * @param fcShippingPlanReportInfo
     * @return
     */
    int saveFcShippingPlanReportInfo(FcShippingPlanReportInfo fcShippingPlanReportInfo);

    /**
     * 批量修改报关业务信息
     *
     * @param ifs
     * @return
     */
    int updateFcShippingPlanReportInfo(List<FcShippingPlanReportInfo> ifs);


    /**
     * 获取计划报关信息
     *
     * @param id
     * @return
     */
    List<FcShippingPlanReportInfo> listFcShippingPlanReportInfoByPlanId(String id);
}
