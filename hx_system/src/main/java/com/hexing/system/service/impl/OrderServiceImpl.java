package com.hexing.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hexing.common.utils.JsonUtils;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.form.CustomerList;
import com.hexing.system.domain.form.OrderForm;
import com.hexing.system.domain.form.ResultForm;
import com.hexing.system.mapper.FcOrderMapper;
import com.hexing.system.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author firerock_tech
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    private final FcOrderMapper baseMapper;

    @Override
    public int saveOrder(Object orders) {
        if (ObjectUtil.isNotNull(orders)) {
            Type type = new TypeToken<ResultForm<List<OrderForm>>>() {
            }.getType();
            ResultForm<List<OrderForm>> orderForm = new Gson().fromJson(JsonUtils.toJsonString(orders), type);
            if (ObjectUtil.isNotNull(orderForm)) {
                orderForm.getData().forEach(this::handleOrderStruct);
            }
        }
        return 0;
    }

    @Override
    public void handleOrderStruct(OrderForm orderForm) {
        LambdaQueryWrapper<FcOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FcOrder::getOrderNumber, orderForm.getVbeln());
        FcOrder existOrder = baseMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNotNull(existOrder)) {
            existOrder.setContarctId(Long.valueOf(orderForm.getVbelnRe()));
            existOrder.setOrderNumber(orderForm.getVbeln());
            existOrder.setOrderTitle(orderForm.getVbelnT());
            existOrder.setCurrency(orderForm.getWerks());
            existOrder.setSoldToParty(orderForm.getKunnrSp());

        }
    }
}
