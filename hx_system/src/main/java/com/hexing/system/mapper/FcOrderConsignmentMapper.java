package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcApproveConfig;
import com.hexing.system.domain.FcOrderConsignment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author firerock_tech
 */
public interface FcOrderConsignmentMapper extends BaseMapperPlus<FcOrderConsignmentMapper, FcOrderConsignment,FcOrderConsignment> {

    Page<FcOrderConsignment> listFcOrder(@Param("page") Page<FcOrderConsignment> page, @Param("param") FcOrderConsignment param);

    Double getConsignmentSum(@Param("orderId") Long orderId);

    @Select("SELECT MAX(id) FROM fc_order_consignment")
    Long selectMaxid();
}
