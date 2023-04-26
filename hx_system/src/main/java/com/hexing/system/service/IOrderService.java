package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.form.OrderForm;

import java.util.List;

/**
 * @author firerock_tech
 */
public interface IOrderService {

    /**
     * 保存推送订单
     * @param orders
     * @return
     */
    int saveOrder(Object orders);


    /**
     * 接收订单数据格式转换处理
     * @param orderForm
     */
    void handleOrderStruct(OrderForm orderForm);


    /**
     * 获取订单列表
     * @param fcOrder
     * @param pageQuery
     * @return
     */
    TableDataInfo<FcOrder> listOrders(FcOrder fcOrder, PageQuery pageQuery);


    /**
     * 获取订单详情
     * @param id
     * @return
     */
    FcOrder getOrderDetailById(Long id);


    /**
     * 通过客户编号获取客户对应订单
     * @param code
     * @return
     */
    List<FcOrder> getOrdersByCusId(String code);
}
