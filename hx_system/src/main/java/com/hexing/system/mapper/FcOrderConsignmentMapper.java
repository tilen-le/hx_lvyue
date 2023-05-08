package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcApproveConfig;
import com.hexing.system.domain.FcOrderConsignment;
import org.apache.ibatis.annotations.Param;

/**
 * @author firerock_tech
 */
public interface FcOrderConsignmentMapper extends BaseMapperPlus<FcOrderConsignmentMapper, FcOrderConsignment,FcOrderConsignment> {

    Page<FcOrderConsignment> listFcOrder(@Param("page") Page<FcOrderConsignment> page, @Param("config") FcOrderConsignment order);

}
