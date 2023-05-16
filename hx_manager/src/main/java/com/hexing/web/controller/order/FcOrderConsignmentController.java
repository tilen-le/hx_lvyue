package com.hexing.web.controller.order;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrderConsignment;
import com.hexing.system.service.IFcOrderConsignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/order/consignment")
public class FcOrderConsignmentController extends BaseController {

    private final IFcOrderConsignmentService iFcOrderConsignmentService;

    /**
     * 查询发货单列表
     * A16
     *
     * @param fcOrderConsignment
     * @param pageQuery
     * @return
     */
    @SaCheckPermission("delivery:all:list")
    @GetMapping("/list")
    public TableDataInfo<FcOrderConsignment> list(FcOrderConsignment fcOrderConsignment, PageQuery pageQuery) {
        return iFcOrderConsignmentService.listFcOrderConsignment(fcOrderConsignment, pageQuery);
    }

    /**
     * 保存/创建发货单
     * A19
     *
     * @param fcOrderConsignment
     * @return
     */
    @SaCheckPermission("delivery:plan:add")
    @PostMapping("/add")
    public R<Void> add(@RequestBody @Validated FcOrderConsignment fcOrderConsignment) {
        return toAjax(iFcOrderConsignmentService.saveFcOrderConsignment(fcOrderConsignment));
    }


    @SaCheckPermission("delivery:list:detail")
    @PostMapping("/check")
    public R<Map<String,Object>> detail(@RequestBody FcOrderConsignment fcOrderConsignment) {
        return R.ok(iFcOrderConsignmentService.getDetailById(fcOrderConsignment.getId()));
    }

    @SaCheckPermission("order:consignment:approve")
    @PostMapping("/approve")
    public R<Void> approve(@RequestBody FcOrderConsignment fcOrderConsignment) {
        iFcOrderConsignmentService.approve(fcOrderConsignment);
        return R.ok();
    }


    @SaCheckPermission("delivery:list:update")
    @PostMapping("/update")
    public R<Void> update(@RequestBody FcOrderConsignment fcOrderConsignment) {
        return toAjax(iFcOrderConsignmentService.updateFcOrderConsignment(fcOrderConsignment));
    }


}
