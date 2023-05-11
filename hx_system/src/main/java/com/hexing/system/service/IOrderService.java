package com.hexing.system.service;

import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcContract;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.form.FcShippingPlanReportInfoVo;
import com.hexing.system.domain.form.OrderForm;
import com.hexing.system.domain.form.ReciveOrderDTO;

import java.util.List;
import java.util.Map;

/**
 * @author firerock_tech
 */
public interface IOrderService {

    /**
     * 保存推送订单
     * @param orders
     * @return
     */
    int saveOrder(ReciveOrderDTO orders);

    FcContract getContact(String number);


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
     * 获取订单详情
     * @param id
     * @return
     */
    Map<String, Object> getOrderDetail(Long id);


    /**
     * 通过客户编号获取客户对应订单
     *
     * @param code
     * @return
     */
    List<FcOrder> getOrdersByCusId(String code);

    /**
     * 根据订单名称或订单编号搜索订单
     *
     * @return
     */
    R<List<FcShippingPlanReportInfoVo>> getOrderByNoOrName(String orderNoOrName);

    /**
     * 根据productId查询发货计划sap财务核算所需数据
     *
     * @param productIds
     * @return
     */
    R<List<FcShippingPlanReportInfoVo>> getOrderAndProduct(List<String> productIds);
}
