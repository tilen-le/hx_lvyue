package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface FcCustomerMapper extends BaseMapperPlus<FcCustomerMapper, FcCustomer, FcCustomer> {


    List<FcCustomer> getCustomerlist(@Param("param") FcCustomer fcCustomer);

    Page<FcCustomer> listCustomer(@Param("page") Page<Object> build,@Param("param") FcCustomer fcCustomer);
}
