package com.hexing.customer.controller;

import cn.hutool.core.collection.CollUtil;
import com.hexing.common.pojo.CommonResult;
import com.hexing.common.pojo.PageResult;
import com.hexing.customer.entity.FcCustomer;
import com.hexing.customer.entity.vo.FcCustomerVo;
import com.hexing.customer.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.hexing.common.pojo.CommonResult.success;

/**
 * @author firerock_tech
 */
@RestController
@RequestMapping("/system/customer")
@Validated
@RequiredArgsConstructor
public class CustomerController {


    private final ICustomerService iCustomerService;

    @GetMapping("/page")
    @Operation(summary = "获得用户分页列表")
    public CommonResult<PageResult<FcCustomer>> getUserPage(@Valid FcCustomerVo reqVO) {
        // 获得用户分页列表
        PageResult<FcCustomer> pageResult = iCustomerService.listUserPage(reqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal())); // 返回空
        }
        return success(new PageResult<>(pageResult.getList(), pageResult.getTotal()));
    }
}
