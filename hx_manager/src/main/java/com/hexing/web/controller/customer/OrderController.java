package com.hexing.web.controller.customer;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSON;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.form.FcShippingPlanReportInfoVo;
import com.hexing.system.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final IOrderService orderService;

    /**
     * 订单列表
     * A10、A12
     *
     * @param fcOrder
     * @param pageQuery
     * @return
     */
//    @SaCheckPermission("order:list:query")
    @GetMapping("/list")
    public TableDataInfo<FcOrder> list(FcOrder fcOrder, PageQuery pageQuery) {
        return orderService.listOrders(fcOrder, pageQuery);
    }

//    @SaCheckPermission("order:detail:check")
    @PostMapping("/detail")
    public R<FcOrder> getDetailOrder(@RequestBody FcOrder fcOrder) {
        return R.ok(orderService.getOrderDetailById(fcOrder.getId()));
    }

    /**
     * 订单详情
     * A11  A18
     *
     * @param fcOrder
     * @return
     */
//    @SaCheckPermission("order:detail:check")
    @PostMapping("/detailMap")
    public R<Map<String, Object>> getDetailOrderMap(@RequestBody FcOrder fcOrder) {
        return R.ok(orderService.getOrderDetail(fcOrder.getId()));
    }

    @PostMapping("/getOrderByCusId")
    public R<List<FcOrder>> getOrderByCusId(@RequestBody FcOrder fcOrder) {
        return R.ok(orderService.getOrdersByCusId(fcOrder.getCusCode()));
    }

    /**
     * 根据订单名称或订单编号搜索订单
     *
     * @param orderNoOrName
     * @return
     */
    //    @SaCheckPermission("order:all:list")
    @GetMapping("/getOrderByNoOrName")
    public R<List<FcShippingPlanReportInfoVo>> getOrderByNoOrName(String orderNoOrName) {
        return orderService.getOrderByNoOrName(orderNoOrName);
    }

    /**
     * 根据productIds查询发货计划sap财务核算所需数据
     *
     * @param productIds
     * @return
     */
    @PostMapping("/getOrderAndProduct")
    public R<List<FcShippingPlanReportInfoVo>> getOrderAndProduct(@RequestBody List<String> productIds) {
        return orderService.getOrderAndProduct(productIds);
    }


}
