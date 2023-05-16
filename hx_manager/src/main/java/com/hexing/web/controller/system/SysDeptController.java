package com.hexing.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import com.hexing.common.annotation.Log;
import com.hexing.common.constant.UserConstants;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.domain.entity.SysDept;
import com.hexing.common.enums.BusinessType;
import com.hexing.common.utils.StringUtils;
import com.hexing.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author firerock_tech
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    private final ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list")
    public R<List<SysDept>> list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return R.ok(depts);
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list/exclude/{deptId}")
    public R<List<SysDept>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        depts.removeIf(d -> d.getDeptId().equals(deptId)
            || StringUtils.splitList(d.getAncestors()).contains(Convert.toStr(deptId)));
        return R.ok(depts);
    }

    /**
     * 根据部门编号获取详细信息
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping(value = "/{deptId}")
    public R<SysDept> getInfo(@PathVariable Long deptId) {
        deptService.checkDeptDataScope(deptId);
        return R.ok(deptService.selectDeptById(deptId));
    }

    /**
     * 新增部门
     */
    @SaCheckPermission("system:dept:add")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysDept dept) {
        if (!deptService.checkDeptNameUnique(dept)) {
            return R.fail("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @SaCheckPermission("system:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysDept dept) {
        Long deptId = dept.getDeptId();
        deptService.checkDeptDataScope(deptId);
        if (!deptService.checkDeptNameUnique(dept)) {
            return R.fail("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(deptId)) {
            return R.fail("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
            && deptService.selectNormalChildrenDeptById(deptId) > 0) {
            return R.fail("该部门包含未停用的子部门！");
        }
        return toAjax(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:remove")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public R<Void> remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return R.warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return R.warn("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        return toAjax(deptService.deleteDeptById(deptId));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public R<Map<String, Object>> roleDeptTreeselect(@PathVariable("roleId") Long roleId)
    {
        Map<String, Object> ajax = new HashMap<>();
        List<SysDept> depts = deptService.selectDeptList(new SysDept());
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.buildDeptTreeSelect(depts));
        return R.ok(ajax);
    }

}
