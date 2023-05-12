package com.hexing.system.service;

import com.hexing.system.domain.FcCustomerConsignment;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcCustomerConsignmentService {


    /**
     * 新增客户收货信息
     * @param fcCustomerConsignment
     * @return
     */
    int saveFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment);

    /**
     * 修改客户收货信息
     * @param fcCustomerConsignment
     * @return
     */
    int updateFcCustomerConsignment(FcCustomerConsignment fcCustomerConsignment);

    /**
     * 获取客户收货信息
     * @param cusId
     * @return
     */
    List<FcCustomerConsignment> listFcCustomerConsignment(String cusId);

    /**
     * 通过客户code获取客户收货信息
     * @param code
     * @return
     */
    List<FcCustomerConsignment> listFcCustomerConsignmentByCode(String code);


    /**
     * 删除指定客户收货信息
     *
     * @param id
     * @return
     */
    int removeFcCustomerConsignment(Long id);

    FcCustomerConsignment getById(Long id);

}
