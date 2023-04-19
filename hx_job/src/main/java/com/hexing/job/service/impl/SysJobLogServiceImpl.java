package com.hexing.job.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hexing.common.constant.UserConstants;
import com.hexing.common.core.domain.PageQuery;
import com.hexing.common.core.domain.entity.SysRole;
import com.hexing.common.core.page.TableDataInfo;
import com.hexing.common.utils.StringUtils;
import com.hexing.job.entity.SysJobLog;
import com.hexing.job.mapper.SysJobLogMapper;
import com.hexing.job.service.ISysJobLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author ruoyi
 */
@Service
@RequiredArgsConstructor
public class SysJobLogServiceImpl implements ISysJobLogService
{
    private final SysJobLogMapper jobLogMapper;

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public TableDataInfo<SysJobLog> selectJobLogList(SysJobLog jobLog, PageQuery pageQuery)
    {
        Page<SysJobLog> page=jobLogMapper.selectPage(pageQuery.build(),buildQueryWrapper(jobLog));
        return TableDataInfo.build(page);
    }
    private Wrapper<SysJobLog> buildQueryWrapper(SysJobLog jobLog) {
        Map<String, Object> params = jobLog.getParams();
        QueryWrapper<SysJobLog> wrapper = Wrappers.query();
        wrapper.like(ObjectUtil.isNotNull(jobLog.getJobName()), "job_name", jobLog.getJobName())
                .eq(StringUtils.isNotBlank(jobLog.getJobGroup()), "job_group", jobLog.getJobGroup())
                .eq(StringUtils.isNotBlank(jobLog.getStatus()), "status", jobLog.getStatus())
                .like(StringUtils.isNotBlank(jobLog.getInvokeTarget()), "invoke_target", jobLog.getInvokeTarget())
                .between(params.get("beginTime") != null && params.get("endTime") != null,
                        "r.create_time", params.get("beginTime"), params.get("endTime"));
        return wrapper;
    }
    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds)
    {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     *
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }
}
