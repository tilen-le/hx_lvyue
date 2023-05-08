package com.hexing.system.service.impl;

import com.hexing.system.domain.FcShippingPlanReportInfo;
import com.hexing.system.service.IFcShippingPlanReportInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author firerock_tech
 */
@Service
public class FcShippingPlanReportInfoServiceImpl implements IFcShippingPlanReportInfoService {


    @Override
    public int saveFcShippingPlanReportInfo(FcShippingPlanReportInfo fcShippingPlanReportInfo) {
        return 0;
    }

    @Override
    public int updateFcShippingPlanReportInfo(List<FcShippingPlanReportInfo> ifs) {
        return 0;
    }

    @Override
    public List<FcShippingPlanReportInfo> listFcShippingPlanReportInfoByPlanId(String id) {
        return null;
    }
}
