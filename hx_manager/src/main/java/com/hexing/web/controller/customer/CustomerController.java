package com.hexing.web.controller.customer;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.domain.entity.SysUser;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.enums.BusinessType;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.domain.FcCustomer;
import com.hexing.system.domain.FcCustomerConsignment;
import com.hexing.system.service.IFcCustomerConsignmentService;
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


    /**
     * 获取客户列表
     *
     * @param fcCustomer
     * @param pageQuery
     * @return
     */
    @SaCheckPermission(" customer:all:list")
    @GetMapping("/list")
    public TableDataInfo<FcCustomer> list(FcCustomer fcCustomer, PageQuery pageQuery) {
        return iFcCustomerService.listCustomer(fcCustomer, pageQuery);
    }


    @SaCheckPermission(" customer:all:list")
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

    @SaCheckPermission("customer:address:add")
    @Log(title = "修改客户收货地址", businessType = BusinessType.UPDATE)
    @PutMapping("/updateAddress")
    public R<Void> updateAddress(@Validated @RequestBody FcCustomerConsignment fcCustomerConsignment) {
        return toAjax(iFcCustomerConsignmentService.updateFcCustomerConsignment(fcCustomerConsignment));
    }

    @SaCheckPermission("customer:address:remove")
    @Log(title = "删除客户收货地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteAddress")
    public R<Void> deleteAddress(@Validated @RequestBody FcCustomerConsignment fcCustomerConsignment) {
        return toAjax(iFcCustomerConsignmentService.removeFcCustomerConsignment(fcCustomerConsignment.getId()));
    }


}
