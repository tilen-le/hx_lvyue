package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcPayment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author firerock_tech
 */
public interface FcPaymentMapper extends BaseMapperPlus<FcPaymentMapper, FcPayment,FcPayment> {
    @Select("SELECT MAX(id) FROM fc_payment")
    Long selectMaxid();

    Page<FcPayment> listFcPayment(@Param("page") Page<FcPayment> page, @Param("payment") FcPayment payment);
}
