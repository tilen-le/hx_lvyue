package com.hexing.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.enums.BusinessType;
import com.hexing.system.domain.FcSaleBank;
import com.hexing.system.service.IFcSaleBankService;
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
@RequestMapping("/system/saleBank")
public class FcSaleBankController extends BaseController {

    private final IFcSaleBankService fcSaleBankService;


    /**
     * 获取卖方银行列表
     *
     * @param fcSaleBank
     * @param pageQuery
     * @return
     */
    @SaCheckPermission("system:bank:list")
    @GetMapping("/list")
    public TableDataInfo<FcSaleBank> list(FcSaleBank fcSaleBank, PageQuery pageQuery) {
        return fcSaleBankService.listFcSaleBank(fcSaleBank, pageQuery);
    }

    @Log(title = "卖方银行新增", businessType = BusinessType.INSERT)
    @SaCheckPermission("system:bank:add")
    @PostMapping
    public R<Void> add(@Validated @RequestBody FcSaleBank fcSaleBank) {
        return toAjax(fcSaleBankService.saveFcSaleBank(fcSaleBank));
    }

    @Log(title = "卖方银行修改", businessType = BusinessType.UPDATE)
    @SaCheckPermission("system:bank:update")
    @PutMapping
    public R<Void> update(@Validated @RequestBody FcSaleBank fcSaleBank) {
        return toAjax(fcSaleBankService.updateFcSaleBank(fcSaleBank));
    }

    @Log(title = "卖方银行删除", businessType = BusinessType.UPDATE)
    @SaCheckPermission("system:bank:remove")
    @DeleteMapping
    public R<Void> remove(@Validated @RequestBody FcSaleBank fcSaleBank) {
        return toAjax(fcSaleBankService.removeFcSaleBank(fcSaleBank));
    }

    @Log(title = "卖方银行可用获取", businessType = BusinessType.UPDATE)
    @SaCheckPermission("system:bank:list")
    @GetMapping("/available")
    public R<List<FcSaleBank>> getAvailable() {
        return R.ok(fcSaleBankService.availableBank());
    }


}
