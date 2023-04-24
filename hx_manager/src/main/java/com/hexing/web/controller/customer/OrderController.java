package com.hexing.web.controller.customer;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final IOrderService orderService;


    @SaCheckPermission("order:all:list")
    @GetMapping("/list")
    public TableDataInfo<FcOrder> list(FcOrder fcOrder, PageQuery pageQuery) {
        return orderService.listOrders(fcOrder, pageQuery);
    }

    @SaCheckPermission("order:detail:check")
    @PostMapping("/detail")
    public R<FcOrder> getDetailOrder(FcOrder fcOrder) {
        return R.ok(orderService.getOrderDetailById(fcOrder.getId()));
    }


}
