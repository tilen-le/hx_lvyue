package com.hexing.file.config;

import com.hexing.file.core.client.FileClientFactory;
import com.hexing.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author firerock_tech
 */
@AutoConfiguration
public class HxFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
