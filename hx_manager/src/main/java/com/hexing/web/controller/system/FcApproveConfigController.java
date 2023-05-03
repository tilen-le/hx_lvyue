package com.hexing.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.enums.BusinessType;
import com.hexing.system.domain.FcApproveConfig;
import com.hexing.system.service.IFcApproveConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/approveConfig")
public class FcApproveConfigController extends BaseController {
    private final IFcApproveConfigService approveConfigService;
    @SaCheckPermission("system:approve:list")
    @GetMapping("/list")
    public TableDataInfo<FcApproveConfig> list(FcApproveConfig fcApproveConfig, PageQuery pageQuery) {
        return approveConfigService.listApproveConfig(fcApproveConfig, pageQuery);
    }
    @Log(title = "审批配置新增", businessType = BusinessType.INSERT)
    @SaCheckPermission("approve:config:add")
    @PostMapping
    public R<Void> add(@Validated @RequestBody FcApproveConfig fcApproveConfig) {
        return toAjax(approveConfigService.saveFcApproveConfig(fcApproveConfig));
    }
    @Log(title = "审批配置修改", businessType = BusinessType.UPDATE)
    @SaCheckPermission("approve:config:update")
    @PutMapping
    public R<Void> update(@Validated @RequestBody FcApproveConfig fcApproveConfig) {
        return toAjax(approveConfigService.updateFcApproveConfig(fcApproveConfig));
    }

    @Log(title = "审批配置删除", businessType = BusinessType.UPDATE)
    @SaCheckPermission("approve:config:remove")
    @PostMapping("/remove")
    public R<Void> remove(@Validated @RequestBody FcApproveConfig fcApproveConfig) {
        return toAjax(approveConfigService.removeFcApproveConfig(fcApproveConfig.getId()));
    }

}
