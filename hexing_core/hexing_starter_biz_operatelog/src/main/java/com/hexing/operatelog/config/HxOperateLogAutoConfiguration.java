package com.hexing.operatelog.config;

import com.hexing.operatelog.core.aop.OperateLogAspect;
import com.hexing.operatelog.core.service.OperateLogFrameworkService;
import com.hexing.operatelog.core.service.OperateLogFrameworkServiceImpl;
import com.hexing.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class HxOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
