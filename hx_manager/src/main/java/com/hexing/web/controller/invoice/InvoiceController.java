package com.hexing.web.controller.invoice;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.system.domain.FcOrderConsignment;
import com.hexing.system.domain.FcOrderInvoice;
import com.hexing.system.service.IFcOrderInvoiceService;
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
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {

    private final IFcOrderInvoiceService iFcOrderInvoiceService;

    /**
     * 保存/创建开票单
     * A26
     *
     * @param fcOrderInvoice
     * @return
     */
    @SaCheckPermission("invoice:list:add")
    @PostMapping("/add")
    public R<Void> add(@RequestBody FcOrderInvoice fcOrderInvoice) {
        return toAjax(iFcOrderInvoiceService.saveFcOrderInvoice(fcOrderInvoice));
    }

    /**
     * 查询开票单列表
     * A23
     *
     * @param fcOrderInvoice
     * @param pageQuery
     * @return
     */
    @SaCheckPermission("invoice:list:add")
    @GetMapping("/list")
    public TableDataInfo<FcOrderInvoice> list(FcOrderInvoice fcOrderInvoice, PageQuery pageQuery) {
        return iFcOrderInvoiceService.listFcOrderInvoice(fcOrderInvoice, pageQuery);
    }

    @SaCheckPermission("invoice:list:add")
    @GetMapping("/detail")
    public R<Map<String,Object>> detail( FcOrderInvoice fcOrderInvoice) {
        return R.ok(iFcOrderInvoiceService.getDetailById(fcOrderInvoice.getId()));
    }


}
