package com.hexing.web.controller.order;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.domain.FcOrderConsignment;
import com.hexing.system.domain.FcShippingPlan;
import com.hexing.system.service.IFcShippingPlanService;
import com.hexing.system.service.impl.FcShippingPlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/shippingPlan")
public class FcShippingPlanController extends BaseController {


    @Autowired
    private IFcShippingPlanService shippingPlanService;


    //    @SaCheckPermission("order:shippingPlan:add")
    @PostMapping("/saveFcShippingPlan")
    public R<Void> saveFcShippingPlan(@Validated @RequestBody FcShippingPlan fcShippingPlan) {
        return shippingPlanService.saveFcShippingPlan(fcShippingPlan);
    }

    //    @SaCheckPermission("delivery:all:list")
    @GetMapping("/list")
    public TableDataInfo<FcShippingPlan> list(FcShippingPlan fcShippingPlan, PageQuery pageQuery) {
        return shippingPlanService.list(fcShippingPlan, pageQuery);
    }

    //    @SaCheckPermission("order:detail:check")
    @PostMapping("/getShippingPlanDetail")
    public R<FcShippingPlan> getShippingPlanDetail(FcShippingPlan shippingPlan) {
        return shippingPlanService.getShippingPlanDetail(shippingPlan.getId());
    }


}
