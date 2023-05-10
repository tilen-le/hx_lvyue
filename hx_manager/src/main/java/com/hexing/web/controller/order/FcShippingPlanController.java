package com.hexing.web.controller.order;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.R;
import com.hexing.system.domain.FcShippingPlan;
import com.hexing.system.service.IFcShippingPlanService;
import com.hexing.system.service.impl.FcShippingPlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
