package com.hexing.system.service;

import com.hexing.system.domain.FcApproveRecord;

/**
 * @author firerock_tech
 */
public interface IFcApproveRecordService {
    /**
     * 保存审批记录
     * @param FcApproveRecord
     * @return
     */
    int saveFcApproveRecord(FcApproveRecord FcApproveRecord);


    /**
     * 修改审批记录
     * @param FcApproveRecord
     * @return
     */
    int updateFcApproveRecord(FcApproveRecord FcApproveRecord);


    /**
     * 获取审批记录
     * @param appId
     * @return
     */
    FcApproveRecord getFcApproveRecord(String appId);
}
