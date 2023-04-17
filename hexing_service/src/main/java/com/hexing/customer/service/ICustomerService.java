package com.hexing.customer.service;

import com.hexing.common.pojo.PageResult;
import com.hexing.customer.entity.FcCustomer;
import com.hexing.customer.entity.vo.FcCustomerVo;


/**
 * @author firerock_tech
 */
public interface ICustomerService {


    /**
     * 新增客户
     * @param fcCustomer
     * @return
     */
    int saveCustomer(FcCustomer fcCustomer);

    /**
     * 修改客户
     * @param fcCustomer
     * @return
     */
    int updateCustomer(FcCustomer fcCustomer);


    /**
     * 获取客户
     * @param fcCustomer
     * @return
     */
    PageResult<FcCustomer> listUserPage(FcCustomerVo fcCustomer);





}
