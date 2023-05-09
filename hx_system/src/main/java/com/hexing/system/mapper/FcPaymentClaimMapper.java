package com.hexing.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.core.mapper.BaseMapperPlus;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.vo.PaymentClaimVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface FcPaymentClaimMapper extends BaseMapperPlus<FcPaymentClaimMapper, FcPaymentClaim,FcPaymentClaim> {


    Page<FcPaymentClaim> selectPageList(@Param("page") Page<FcPaymentClaim> page, @Param("info") FcPaymentClaim fcPaymentClaim);


    FcPaymentClaim getDetailById(@Param("id") String id);

    List<PaymentClaimVO> selectPaymentClaimByOrder(@Param("id") Long id);
}
