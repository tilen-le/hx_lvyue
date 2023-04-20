package com.hexing.system.service;

import com.hexing.system.domain.FcCustomerConsignment;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcCustomerConsignmentService {

    int saveFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment);


    int updateFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment);

    List<FcCustomerConsignment> listFcCustomerConsignment(String cusId);

    int removeFcCustomerConsignment(Long id);

}
