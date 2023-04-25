package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.form.PaymentForm;

/**
 * @author firerock_tech
 */
public interface IFcPaymentService {

    /**
     * 保存回款单
     *
     * @param paymentForm
     * @return
     */
    int saveFcPayment(PaymentForm paymentForm);


    /**
     * 发起认领
     * @param fcPaymentClaim
     * @return
     */
    int claimFcPayment(FcPaymentClaim fcPaymentClaim);


    /**
     * 获取回款单详情
     *
     * @param id
     * @return
     */
    FcPayment getDetailById(Long id);


    /**
     * 回款单列表
     *
     * @param fcPayment
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcPayment> listFcPayment(FcPayment fcPayment, PageQuery pageQuery);
}
