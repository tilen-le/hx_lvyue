package com.hexing.demo.controller;

import java.util.List;
import java.util.Arrays;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.core.domain.R;
import com.hexing.demo.domain.bo.BsLeaveBo;
import com.hexing.demo.domain.vo.BsLeaveVo;
import com.hexing.demo.service.IBsLeaveService;
import com.hexing.demo.domain.BsLeave;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.hexing.common.annotation.RepeatSubmit;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.validate.AddGroup;
import com.hexing.common.core.validate.EditGroup;
import com.hexing.common.core.validate.QueryGroup;
import com.hexing.common.enums.BusinessType;
import com.hexing.common.utils.poi.ExcelUtil;
import com.hexing.common.core.page.TableDataInfo;

/**
 * 请假业务Controller
 *
 * @author firerock_tech
 * @date 2022-01-11
 */
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/demo/leave")
public class BsLeaveController extends BaseController {

    private final IBsLeaveService iBsLeaveService;

    /**
     * 查询请假业务列表
     */
    @SaCheckPermission("demo:leave:list")
    @GetMapping("/list")
    public TableDataInfo<BsLeaveVo> list(@Validated(QueryGroup.class) BsLeaveBo bo, PageQuery pageQuery) {
        return iBsLeaveService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出请假业务列表
     */
    @SaCheckPermission("demo:leave:export")
    @Log(title = "请假业务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated BsLeaveBo bo, HttpServletResponse response) {
        List<BsLeaveVo> list = iBsLeaveService.queryList(bo);
        ExcelUtil.exportExcel(list, "请假业务", BsLeaveVo.class, response);
    }

    /**
     * 获取请假业务详细信息
     */
    @SaCheckPermission("demo:leave:query")
    @GetMapping("/{id}")
    public R<BsLeaveVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable("id") String id) {
        return R.ok(iBsLeaveService.queryById(id));
    }

    /**
     * 新增请假业务
     */
    @SaCheckPermission("demo:leave:add")
    @Log(title = "请假业务", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<BsLeave> add(@Validated(AddGroup.class) @RequestBody BsLeaveBo bo) {
        return R.ok(iBsLeaveService.insertByBo(bo));
    }

    /**
     * 修改请假业务
     */
    @SaCheckPermission("demo:leave:edit")
    @Log(title = "请假业务", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<BsLeave> edit(@Validated(EditGroup.class) @RequestBody BsLeaveBo bo) {
        return R.ok(iBsLeaveService.updateByBo(bo));
    }

    /**
     * 删除请假业务
     */
    @SaCheckPermission("demo:leave:remove")
    @Log(title = "请假业务" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable String[] ids) {
        return toAjax(iBsLeaveService.deleteWithValidByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
