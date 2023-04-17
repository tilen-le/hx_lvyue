package com.hexing.pay.api.order;

import com.hexing.pay.api.order.dto.PayOrderCreateReqDTO;
import com.hexing.pay.api.order.dto.PayOrderRespDTO;
import com.hexing.pay.convert.order.PayOrderConvert;
import com.hexing.pay.dal.dataobject.order.PayOrderDO;
import com.hexing.pay.service.order.PayOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 支付单 API 实现类
 *
 * @author firerock_tech
 */
@Service
public class PayOrderApiImpl implements PayOrderApi {

    @Resource
    private PayOrderService payOrderService;

    @Override
    public Long createOrder(PayOrderCreateReqDTO reqDTO) {
        return payOrderService.createPayOrder(reqDTO);
    }

    @Override
    public PayOrderRespDTO getOrder(Long id) {
        PayOrderDO order = payOrderService.getOrder(id);
        return PayOrderConvert.INSTANCE.convert2(order);
    }

}
