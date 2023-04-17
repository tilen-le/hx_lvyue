package com.hexing.sms.config;

import com.hexing.sms.core.client.SmsClientFactory;
import com.hexing.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author firerock_tech
 */
@AutoConfiguration
public class HxSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
