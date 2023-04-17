package com.hexing.customer.service;

import com.hexing.customer.entity.FcCustomerConsignment;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcCustomerConsignmentService {


    int saveFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment);

    int updateFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment);


    int removeFcCustomerConsignment(String id);

    List<FcCustomerConsignment> listFcCustomerConsignment(String customerId);


}
