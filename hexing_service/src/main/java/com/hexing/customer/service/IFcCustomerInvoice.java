package com.hexing.customer.service;

import com.hexing.customer.entity.FcCustomerInvoice;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcCustomerInvoice {

    int saveFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice);

    int updateFcCustomerInvoice(FcCustomerInvoice fcCustomerInvoice);


    int removeFcCustomerInvoice(String id);


    List<FcCustomerInvoice> listFcCustomerInvoice(String customerId);
}
