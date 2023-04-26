package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcPaymentClaim;

/**
 * @author firerock_tech
 */
public interface IFcPaymentClaimService {

    /**
     * 保存认领单
     *
     * @param fcPaymentClaim
     * @return
     */
    int saveFcPaymentClaim(FcPaymentClaim fcPaymentClaim);

    /**
     * 修改认领单
     *
     * @param fcPaymentClaim
     * @return
     */
    String updateFcPaymentClaim(FcPaymentClaim fcPaymentClaim);

    /**
     * 认领单列表
     *
     * @param claim
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcPaymentClaim> listFcPaymentClaim(FcPaymentClaim claim, PageQuery pageQuery);


    /**
     * 获取所有认领单
     *
     * @param claim
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcPaymentClaim> listClaimPage(FcPaymentClaim claim, PageQuery pageQuery);


    /**
     * 获取认领单详情
     *
     * @param id
     * @return
     */
    FcPaymentClaim getClaimDetail(String id);


}
