package com.hexing.pay.api.refund;

import com.hexing.pay.api.refund.dto.PayRefundCreateReqDTO;
import com.hexing.pay.api.refund.dto.PayRefundRespDTO;
import com.hexing.pay.service.refund.PayRefundService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 退款单 API 实现类
 *
 * @author firerock_tech
 */
@Service
@Validated
public class PayRefundApiImpl implements PayRefundApi {

    @Resource
    private PayRefundService payRefundService;

    @Override
    public Long createPayRefund(PayRefundCreateReqDTO reqDTO) {
        return payRefundService.createPayRefund(reqDTO);
    }

    @Override
    public PayRefundRespDTO getPayRefund(Long id) {
        // TODO 芋艿：暂未实现
        return null;
    }

}
