package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.form.CustomerForm;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IFcCustomerService {

    /**
     * 更新保存客户
     * @param fcCustomer
     * @return
     */
    int saveCustomer(CustomerForm fcCustomer);

    /**
     * 获取客户详情
     * @param id
     * @return
     */
    FcCustomer getCustomerById(String id);


    /**
     * 获取客户列表
     *
     * @param fcCustomer
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcCustomer> listCustomer(FcCustomer fcCustomer, PageQuery pageQuery);

    List<FcCustomer> allCustomer(FcCustomer fcCustomer);


    /**
     * 只查询 id 和 name
     *
     * @return
     */
    List<FcCustomer> getCustomerlist(FcCustomer fcCustomer);
}
