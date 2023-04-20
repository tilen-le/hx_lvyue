package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.form.CustomerForm;

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

    FcCustomer getCustomerById(String id);

    TableDataInfo<FcCustomer> listCustomer(FcCustomer fcCustomer, PageQuery pageQuery);
}
