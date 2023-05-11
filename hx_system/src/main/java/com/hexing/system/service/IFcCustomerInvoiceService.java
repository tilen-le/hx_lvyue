package com.hexing.system.service;

import com.hexing.system.domain.FcCustomerInvoice;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcCustomerInvoiceService {


    /**
     * 新增开户行信息
     *
     * @param fcCustomerInvoice
     * @return
     */
    int saveFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice);

    /**
     * 修改开户行信息
     *
     * @param fcCustomerInvoice
     * @return
     */
    int updateFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice);

    /**
     * 获取客户开户行信息
     *
     * @param userId
     * @return
     */
    List<FcCustomerInvoice> listFcCustomerInvoice(Long userId);

    /**
     * 通过收票方获取开户行信息
     *
     * @param code
     * @return
     */
    List<FcCustomerInvoice> getOpenBankByBillee(String code);

    /**
     * 删除开户行信息
     *
     * @param fcCustomerInvoice
     * @return
     */
    int removeFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice);
}
