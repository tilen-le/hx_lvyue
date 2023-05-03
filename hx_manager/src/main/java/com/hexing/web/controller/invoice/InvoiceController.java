package com.hexing.web.controller.invoice;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.R;
import com.hexing.system.domain.FcOrderInvoice;
import com.hexing.system.service.IFcOrderInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {

    private final IFcOrderInvoiceService iFcOrderInvoiceService;

    @SaCheckPermission("invoice:list:add")
    @PostMapping("/add")
    public R<Void> add(@RequestBody FcOrderInvoice fcOrderInvoice) {
        return toAjax(iFcOrderInvoiceService.saveFcOrderInvoice(fcOrderInvoice));
    }


}
