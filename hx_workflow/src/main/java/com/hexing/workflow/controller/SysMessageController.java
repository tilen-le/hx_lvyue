package com.hexing.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.hexing.common.annotation.Log;
import com.hexing.common.annotation.RepeatSubmit;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.core.validate.AddGroup;
import com.hexing.common.core.validate.EditGroup;
import com.hexing.common.core.validate.QueryGroup;
import com.hexing.common.enums.BusinessType;
import com.hexing.common.utils.poi.ExcelUtil;
import com.hexing.workflow.domain.bo.SysMessageBo;
import com.hexing.workflow.domain.vo.SysMessageVo;
import com.hexing.workflow.service.ISysMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 消息通知Controller
 *
 * @author firerock_tech
 * @date 2022-06-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/message")
public class SysMessageController extends BaseController {

    private final ISysMessageService iSysMessageService;

    /**
     * 查询消息通知列表
     */
    @SaCheckPermission("workflow:message:list")
    @GetMapping("/list")
    public TableDataInfo<SysMessageVo> list(@Validated(QueryGroup.class) SysMessageBo bo, PageQuery pageQuery) {
        return iSysMessageService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询消息通知列表
     */
    @SaIgnore
    @GetMapping("/waitingMessage")
    public TableDataInfo<SysMessageVo> message() {
        return iSysMessageService.queryPage();
    }

    /**
     * 导出消息通知列表
     */
    @SaCheckPermission("workflow:message:export")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated SysMessageBo bo, HttpServletResponse response) {
        List<SysMessageVo> list = iSysMessageService.queryList(bo);
        ExcelUtil.exportExcel(list, "消息通知", SysMessageVo.class, response);
    }

    /**
     * 获取消息通知详细信息
     */
    @SaCheckPermission("workflow:message:query")
    @GetMapping("/{id}")
    public R<SysMessageVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable("id") Long id) {
        return R.ok(iSysMessageService.queryById(id));
    }

    /**
     * 新增消息通知
     */
    @SaCheckPermission("workflow:message:add")
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysMessageBo bo) {
        return toAjax(iSysMessageService.sendMessage(bo) ? 1 : 0);
    }

    /**
     * 修改消息通知
     */
    @SaCheckPermission("workflow:message:edit")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysMessageBo bo) {
        return toAjax(iSysMessageService.updateMessage(bo) ? 1 : 0);
    }

    /**
     * 删除消息通知
     */
    @SaCheckPermission("workflow:message:remove")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iSysMessageService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
    /**
     * @Description:  阅读消息
     * @param: id
     * @return: com.ruoyi.common.core.domain.R<java.lang.Void>
     * @author: firerock_tech
     * @Date: 2022/6/19 17:08
     */
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @GetMapping("/readMessage/{id}")
    public R<Void> readMessage(@PathVariable Long id) {
        return toAjax(iSysMessageService.readMessage(id) ? 1 : 0);
    }

    /**
     * @Description:  批量阅读消息
     * @return: com.ruoyi.common.core.domain.R<java.lang.Void>
     * @author: firerock_tech
     * @Date: 2022/6/19 17:08
     */
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @GetMapping("/batchReadMessage")
    public R<Void> batchReadMessage() {
        return toAjax(iSysMessageService.batchReadMessage() ? 1 : 0);
    }
}
