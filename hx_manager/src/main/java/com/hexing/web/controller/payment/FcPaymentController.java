package com.hexing.web.controller.payment;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcPayment;
import com.hexing.system.domain.FcPaymentClaim;
import com.hexing.system.domain.FcPaymentClaimDetail;
import com.hexing.system.service.IFcPaymentClaimDetailService;
import com.hexing.system.service.IFcPaymentClaimService;
import com.hexing.system.service.IFcPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class FcPaymentController extends BaseController {

    private final IFcPaymentService iFcPaymentService;
    private final IFcPaymentClaimService fcPaymentClaimService;
    private final IFcPaymentClaimDetailService iFcPaymentClaimDetailService;

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

    @SaCheckPermission("payment:claim:add")
    @PostMapping("/addClaim")
    public R<Void> addClaim(@RequestBody FcPaymentClaim fcPaymentClaim) {
        return toAjax(fcPaymentClaimService.saveFcPaymentClaim(fcPaymentClaim));
    }

    @SaCheckPermission("claim:all:list")
    @GetMapping("/claim/list")
    public TableDataInfo<FcPaymentClaim> list(FcPaymentClaim fcPaymentClaim, PageQuery pageQuery) {
        return fcPaymentClaimService.listFcPaymentClaim(fcPaymentClaim, pageQuery);
    }
    @SaCheckPermission("claim:list:cancel")
    @PostMapping("/claim/cancel")
    public R<String> claimCancel(@RequestBody FcPaymentClaim fcPaymentClaim) {
        String result=fcPaymentClaimService.updateFcPaymentClaim(fcPaymentClaim);
        return R.ok(result);
    }

    @SaCheckPermission("claim:all:list")
    @GetMapping("/claim/all/list")
    public TableDataInfo<FcPaymentClaim> claimList(FcPaymentClaim fcPaymentClaim, PageQuery pageQuery) {
        return fcPaymentClaimService.listClaimPage(fcPaymentClaim, pageQuery);
    }

    @SaCheckPermission("claim:all:list")
    @PostMapping("/claim/detail")
    public R<FcPaymentClaim> claimDetail(@RequestBody FcPaymentClaim fcPaymentClaim) {
        return R.ok(fcPaymentClaimService.getClaimDetail(fcPaymentClaim.getId().toString()));
    }


    @SaCheckPermission("claim:all:list")
    @PostMapping("/claim/detail/list")
    public R<List<FcPaymentClaimDetail>> claimDetailList(@RequestBody FcPaymentClaim fcPaymentClaim) {
        return R.ok(iFcPaymentClaimDetailService.listClaimDetail(fcPaymentClaim.getId()));
    }





}
