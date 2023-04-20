package com.hexing.system.service;

import com.hexing.system.domain.form.OrderForm;

/**
 * @author firerock_tech
 */
public interface IOrderService {

    int saveOrder(Object orders);


    void handleOrderStruct(OrderForm orderForm);
}
