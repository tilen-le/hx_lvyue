package com.hexing.web.controller.payment;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.service.IFcPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class FcPaymentController extends BaseController {

    private final IFcPaymentService iFcPaymentService;

    @SaCheckPermission("payment:all:list")
    @GetMapping("/list")
    public TableDataInfo<FcPayment> list(FcPayment fcPayment, PageQuery pageQuery) {
        return iFcPaymentService.listFcPayment(fcPayment, pageQuery);
    }

    @SaCheckPermission("payment:all:check")
    @PostMapping("/detail")
    public R<FcPayment> detail(@RequestBody FcPayment fcPayment) {
        return R.ok(iFcPaymentService.getDetailById(fcPayment.getId()));
    }

}
