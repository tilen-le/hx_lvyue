package com.hexing.system.service;

import com.hexing.system.domain.FcApprove;

/**
 * @author firerock_tech
 */
public interface IFcApproveService {

    /**
     * 新增审批
     * @param fcApprove
     * @return
     */
    int saveFcApprove(FcApprove fcApprove);

    /**
     * 修改审批
     * @param fcApprove
     * @return
     */
    int updateFcApprove(FcApprove fcApprove);


    /**
     * 获取审批
     * @param mainId
     * @return
     */
    FcApprove getFcApprove(Long mainId,Integer type);
}
