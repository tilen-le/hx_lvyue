package com.hexing.web.controller.customer;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.enums.BusinessType;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.FcCustomerConsignment;
import com.hexing.system.domain.FcCustomerInvoice;
import com.hexing.system.domain.FcOrder;
import com.hexing.system.service.IFcCustomerConsignmentService;
import com.hexing.system.service.IFcCustomerInvoiceService;
import com.hexing.system.service.IFcCustomerService;
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
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    private final IFcCustomerService iFcCustomerService;

    private final IFcCustomerConsignmentService iFcCustomerConsignmentService;

    private final IFcCustomerInvoiceService iFcCustomerInvoiceService;


    /**
     * 获取客户列表
     * A00
     *
     * @param fcCustomer
     * @param pageQuery
     * @return
     */
    @SaCheckPermission(" customer:list:query")
    @GetMapping("/list")
    public TableDataInfo<FcCustomer> list(FcCustomer fcCustomer, PageQuery pageQuery) {
        return iFcCustomerService.listCustomer(fcCustomer, pageQuery);
    }

    @SaCheckPermission(" customer:all:list")
    @GetMapping("/all")
    public R<List<FcCustomer>> all(FcCustomer fcCustomer) {
        return R.ok(iFcCustomerService.allCustomer(fcCustomer));
    }

    /**
     * 客户详情
     * A01
     *
     * @param id
     * @return
     */
    @SaCheckPermission(" customer:list:detail")
    @GetMapping("/detail/{id}")
    public R<FcCustomer> detail(@PathVariable("id") String id) {
        return R.ok(iFcCustomerService.getCustomerById(id));
    }

    @SaCheckPermission("customer:address:add")
    @Log(title = "新增客户收货地址", businessType = BusinessType.INSERT)
    @PostMapping("/addAddress")
    public R<Void> addAddress(@Validated @RequestBody FcCustomerConsignment fcCustomerConsignment) {
        return toAjax(iFcCustomerConsignmentService.saveFcCustomerConsignment(fcCustomerConsignment));
    }

    @SaCheckPermission("customer:address:list")
    @PostMapping("/getAddress")
    public R<List<FcCustomerConsignment>> getAddress(@RequestBody FcCustomerConsignment fcCustomerConsignment) {
        return R.ok(iFcCustomerConsignmentService.listFcCustomerConsignment(fcCustomerConsignment.getCustomerId()));
    }

    @SaCheckPermission("customer:address:list")
    @PostMapping("/getAddressByCode")
    public R<List<FcCustomerConsignment>> getAddressByCode(@RequestBody FcCustomer fcCustomer) {
        return R.ok(iFcCustomerConsignmentService.listFcCustomerConsignmentByCode(fcCustomer.getCode()));
    }

    /**
     * 创建/修改客户收货信息
     * A03
     *
     * @param fcCustomerConsignment
     * @return
     */
    @SaCheckPermission("customer:address:update")
    @Log(title = "修改客户收货地址", businessType = BusinessType.UPDATE)
    @PutMapping("/updateAddress")
    public R<Void> updateAddress(@Validated @RequestBody FcCustomerConsignment fcCustomerConsignment) {
        return toAjax(iFcCustomerConsignmentService.updateFcCustomerConsignment(fcCustomerConsignment));
    }

    /**
     * 删除客户收货地址
     * A05
     *
     * @param fcCustomerConsignment
     * @return
     */
    @SaCheckPermission("customer:address:remove")
    @Log(title = "删除客户收货地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteAddress")
    public R<Void> deleteAddress(@Validated @RequestBody FcCustomerConsignment fcCustomerConsignment) {
        return toAjax(iFcCustomerConsignmentService.removeFcCustomerConsignment(fcCustomerConsignment.getId()));
    }

    /**
     * 新增客户开户行信息
     * A04
     *
     * @param FcCustomerInvoice
     * @return
     */
    @SaCheckPermission("customer:openbank:add")
    @Log(title = "新增客户开户行信息", businessType = BusinessType.INSERT)
    @PostMapping("/addOpenBank")
    public R<Void> addOpenBank(@Validated @RequestBody FcCustomerInvoice FcCustomerInvoice) {
        return toAjax(iFcCustomerInvoiceService.saveFcCustomerInvoice(FcCustomerInvoice));
    }

    /**
     * 修改开户行信息
     * A04
     *
     * @param fcCustomerInvoice
     * @return
     */
    @SaCheckPermission("customer:openbank:update")
    @Log(title = "修改开户行信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updateOpenBank")
    public R<Void> updateOpenBank(@Validated @RequestBody FcCustomerInvoice fcCustomerInvoice) {
        return toAjax(iFcCustomerInvoiceService.updateFcCustomerInvoice(fcCustomerInvoice));
    }

    @SaCheckPermission("customer:openbank:list")
    @Log(title = "获取开户行信息", businessType = BusinessType.EXPORT)
    @PostMapping("/getOpenBank")
    public R<List<FcCustomerInvoice>> getOpenBank(@RequestBody FcCustomerInvoice fcCustomerInvoice) {
        return R.ok(iFcCustomerInvoiceService.listFcCustomerInvoice(fcCustomerInvoice.getCustomerId()));
    }

    /**
     * 删除开户行信息
     * A06
     *
     * @param fcCustomerInvoice
     * @return
     */
    @SaCheckPermission("customer:openbank:remove")
    @Log(title = "删除开户行信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeOpenBank")
    public R<Void> removeOpenBank(@Validated @RequestBody FcCustomerInvoice fcCustomerInvoice) {
        return toAjax(iFcCustomerInvoiceService.removeFcCustomerInvoice(fcCustomerInvoice));
    }

    @SaCheckPermission("customer:openbank:list")
    @Log(title = "获取开户行信息", businessType = BusinessType.EXPORT)
    @PostMapping("/getOpenBankByBe")
    public R<List<FcCustomerInvoice>> getOpenBankByBe(@RequestBody FcCustomer fcCustomer ) {
        return R.ok(iFcCustomerInvoiceService.getOpenBankByBillee(fcCustomer.getCode()));
    }


}
