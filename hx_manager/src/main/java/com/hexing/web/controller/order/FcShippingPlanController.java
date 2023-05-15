package com.hexing.web.controller.order;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.enums.BusinessType;
import com.hexing.system.domain.FcCustomerConsignment;
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

    /**
     * 新增发货计划
     *
     * @param fcShippingPlan
     * @return
     */
    //    @SaCheckPermission("order:shippingPlan:add")
    @PostMapping("/saveFcShippingPlan")
    public R<Void> saveFcShippingPlan(@Validated @RequestBody FcShippingPlan fcShippingPlan) {
        return shippingPlanService.saveFcShippingPlan(fcShippingPlan);
    }

    /**
     * 发货计划列表
     *
     * @param fcShippingPlan
     * @param pageQuery
     * @return
     */
    //    @SaCheckPermission("order:shippingPlan:list")
    @GetMapping("/list")
    public TableDataInfo<FcShippingPlan> list(FcShippingPlan fcShippingPlan, PageQuery pageQuery) {
        return shippingPlanService.list(fcShippingPlan, pageQuery);
    }

    /**
     * 发货计划详情
     *
     * @param id
     * @return
     */
    //    @SaCheckPermission("order:shippingPlan:detail")
    @GetMapping("/getShippingPlanDetail")
    public R<FcShippingPlan> getShippingPlanDetail(Long id) {
        return shippingPlanService.getShippingPlanDetail(id);
    }

    /**
     * 编辑发货计划
     *
     * @param fcShippingPlan
     * @return
     */
//    @SaCheckPermission("order:shippingPlan:updateFcShippingPlan")
    @Log(title = "编辑发货计划", businessType = BusinessType.UPDATE)
    @PostMapping("/updateFcShippingPlan")
    public R<Void> updateFcShippingPlan(@Validated @RequestBody FcShippingPlan fcShippingPlan) {
        return shippingPlanService.updateFcShippingPlan(fcShippingPlan);
    }

    /**
     * 通知单证专员
     *
     * @param fcShippingPlan
     * @return
     */
    //    @SaCheckPermission("order:shippingPlan:noticeDocumentSpecialist")
    @PostMapping("/noticeDocumentSpecialist")
    public R<Void> noticeDocumentSpecialist(@RequestBody FcShippingPlan fcShippingPlan) {
        return shippingPlanService.noticeDocumentSpecialist(fcShippingPlan.getId());
    }

    /**
     * 报关完成
     *
     * @param fcShippingPlan
     * @return
     */
    //    @SaCheckPermission("order:shippingPlan:reportCustomsCompleted")
    @PostMapping("/reportCustomsCompleted")
    public R<Void> reportCustomsCompleted(@RequestBody FcShippingPlan fcShippingPlan) {
        return shippingPlanService.reportCustomsCompleted(fcShippingPlan.getId());
    }

    /**
     * 发货计划同步sap
     *
     * @param fcShippingPlan
     * @return
     */
    //    @SaCheckPermission("order:shippingPlan:shippingPlanSyncSap")
    @PostMapping("/shippingPlanSyncSap")
    public R<String> shippingPlanSyncSap(@RequestBody FcShippingPlan fcShippingPlan) {
        return shippingPlanService.shippingPlanSyncSap(fcShippingPlan.getId());
    }


}
