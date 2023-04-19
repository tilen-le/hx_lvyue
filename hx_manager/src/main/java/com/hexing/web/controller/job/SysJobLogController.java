package com.hexing.web.controller.job;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hexing.common.annotation.Log;
import com.hexing.common.core.controller.BaseController;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.R;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.enums.BusinessType;
import com.hexing.job.entity.SysJobLog;
import com.hexing.job.service.ISysJobLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 调度日志操作处理
 *
 * @author ruoyi
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {
    private final ISysJobLogService jobLogService;

    /**
     * 查询定时任务调度日志列表
     */
    @SaCheckPermission("monitor:job:list")
    @GetMapping("/list")
    public TableDataInfo<SysJobLog> list(SysJobLog sysJobLog, PageQuery pageQuery) {
        return jobLogService.selectJobLogList(sysJobLog,pageQuery);
    }

    /**
     * 导出定时任务调度日志列表
     */
    @SaCheckPermission("monitor:job:export")
    @Log(title = "任务调度日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysJobLog sysJobLog) {
    }

    /**
     * 根据调度编号获取详细信息
     */
    @SaCheckPermission("monitor:job:query")
    @GetMapping(value = "/{jobLogId}")
    public R<SysJobLog> getInfo(@PathVariable Long jobLogId) {
        return R.ok(jobLogService.selectJobLogById(jobLogId));
    }


    /**
     * 删除定时任务调度日志
     */
    @SaCheckPermission("monitor:job:remove")
    @Log(title = "定时任务调度日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jobLogIds}")
    public R<Void> remove(@PathVariable Long[] jobLogIds) {
        return toAjax(jobLogService.deleteJobLogByIds(jobLogIds));
    }

    /**
     * 清空定时任务调度日志
     */
    @SaCheckPermission("monitor:job:remove")
    @Log(title = "调度日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public R<Void> clean() {
        jobLogService.cleanJobLog();
        return toAjax(0);
    }
}
