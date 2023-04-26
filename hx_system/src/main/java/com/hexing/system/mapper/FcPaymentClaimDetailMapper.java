package com.hexing.system.mapper;

import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcPaymentClaimDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface FcPaymentClaimDetailMapper extends BaseMapperPlus<FcPaymentClaimDetailMapper, FcPaymentClaimDetail,FcPaymentClaimDetail> {

    List<FcPaymentClaimDetail> listDetail(@Param("id") Long id);
}
