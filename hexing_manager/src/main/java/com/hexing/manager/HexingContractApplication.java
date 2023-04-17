package com.hexing.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 *
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 *
 * @author firerock_tech
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${hexing.info.base-package}
@SpringBootApplication(scanBasePackages = {"${hexing.info.base-package}.manager", "${hexing.info.base-package}.*"})
public class HexingContractApplication {

    public static void main(String[] args) {

        SpringApplication.run(HexingContractApplication.class, args);
//        new SpringApplicationBuilder(HexingContractApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

    }

}
