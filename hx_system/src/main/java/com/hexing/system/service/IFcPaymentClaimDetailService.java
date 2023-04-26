package com.hexing.system.service;

import com.hexing.system.domain.FcPaymentClaimDetail;

import java.util.List;


/**
 * @author firerock_tech
 */
public interface IFcPaymentClaimDetailService {

    /**
     * 保存认领明细
     * @param fcPaymentClaimDetail
     * @return
     */
    int saveFcPaymentClaimDetail(FcPaymentClaimDetail fcPaymentClaimDetail);


    /**
     * 删除明细
     * @param id
     * @return
     */
    int removeFcPaymentClaimDetail(Long id);


    /**
     * 获取认领单明细
     * @param id
     * @return
     */
    List<FcPaymentClaimDetail> listClaimDetail(Long id);
}
