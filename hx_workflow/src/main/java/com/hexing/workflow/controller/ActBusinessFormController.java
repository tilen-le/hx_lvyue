package com.hexing.workflow.controller;

import java.util.List;
import java.util.Arrays;

import com.hexing.workflow.domain.bo.ActBusinessFormBo;
import com.hexing.workflow.domain.vo.ActBusinessFormVo;
import com.hexing.workflow.service.IActBusinessFormService;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.hexing.common.annotation.RepeatSubmit;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.validate.AddGroup;
import com.hexing.common.core.validate.EditGroup;
import com.hexing.common.enums.BusinessType;
import com.hexing.common.utils.poi.ExcelUtil;
import com.hexing.common.core.page.TableDataInfo;

/**
 * 业务表单Controller
 *
 * @author firerock_tech
 * @date 2022-08-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/businessForm")
public class ActBusinessFormController extends BaseController {

    private final IActBusinessFormService iActBusinessFormService;

    /**
     * 查询业务表单列表
     */
    @SaCheckPermission("workflow:businessForm:list")
    @GetMapping("/list")
    public TableDataInfo<ActBusinessFormVo> list(ActBusinessFormBo bo, PageQuery pageQuery) {
        return iActBusinessFormService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出业务表单列表
     */
    @SaCheckPermission("workflow:businessForm:export")
    @Log(title = "业务表单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ActBusinessFormBo bo, HttpServletResponse response) {
        List<ActBusinessFormVo> list = iActBusinessFormService.queryList(bo);
        ExcelUtil.exportExcel(list, "业务表单", ActBusinessFormVo.class, response);
    }

    /**
     * 获取业务表单详细信息
     */
    @SaCheckPermission("workflow:businessForm:query")
    @GetMapping("/{id}")
    public R<ActBusinessFormVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable("id") Long id) {
        return R.ok(iActBusinessFormService.queryById(id));
    }

    /**
     * 新增业务表单
     */
    @SaCheckPermission("workflow:businessForm:add")
    @Log(title = "业务表单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<ActBusinessFormVo> add(@Validated(AddGroup.class) @RequestBody ActBusinessFormBo bo) {
        return R.ok(iActBusinessFormService.insertByBo(bo));
    }

    /**
     * 修改业务表单
     */
    @SaCheckPermission("workflow:businessForm:edit")
    @Log(title = "业务表单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<ActBusinessFormVo> edit(@Validated(EditGroup.class) @RequestBody ActBusinessFormBo bo) {
        return R.ok(iActBusinessFormService.updateByBo(bo));
    }

    /**
     * 删除业务表单
     */
    @SaCheckPermission("workflow:businessForm:remove")
    @Log(title = "业务表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")  @PathVariable Long[] ids) {
        return toAjax(Boolean.TRUE.equals(iActBusinessFormService.deleteWithValidByIds(Arrays.asList(ids))) ? 1 : 0);
    }
}
