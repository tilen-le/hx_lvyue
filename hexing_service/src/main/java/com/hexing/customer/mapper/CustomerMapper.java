package com.hexing.customer.mapper;

import com.hexing.common.pojo.PageResult;
import com.hexing.customer.entity.FcCustomer;
import com.hexing.customer.entity.vo.FcCustomerVo;
import com.hexing.database.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author firerock_tech
 */
@Mapper
public interface CustomerMapper extends BaseMapperX<FcCustomer> {

    PageResult<FcCustomer> selectPage(@Param("customer") FcCustomerVo customerVo);
}
