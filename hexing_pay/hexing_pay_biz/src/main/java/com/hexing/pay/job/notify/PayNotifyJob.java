package com.hexing.pay.job.notify;

import com.hexing.quartz.core.handler.JobHandler;
import com.hexing.pay.service.notify.PayNotifyService;
import com.hexing.tenant.core.job.TenantJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付通知 Job
 * 通过不断扫描待通知的 PayNotifyTaskDO 记录，回调业务线的回调接口
 *
 * @author firerock_tech
 */
@Component
@TenantJob // 多租户
@Slf4j
public class PayNotifyJob implements JobHandler {

    @Resource
    private PayNotifyService payNotifyCoreService;

    @Override
    public String execute(String param) throws Exception {
        int notifyCount = payNotifyCoreService.executeNotify();
        return String.format("执行支付通知 %s 个", notifyCount);
    }

}
