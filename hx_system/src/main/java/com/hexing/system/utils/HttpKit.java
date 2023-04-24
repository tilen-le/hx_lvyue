package com.hexing.system.utils;

import cn.zhxu.okhttps.HTTP;
import cn.zhxu.okhttps.OkHttps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author firerock_tech
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpKit {

    @Value("${hexing.task.domain}")
    private String host;


    public String getData(String params) {
        HTTP http = HTTP.builder().config((OkHttpClient.Builder builder) -> {
            builder.connectTimeout(60, TimeUnit.SECONDS);
            builder.writeTimeout(60, TimeUnit.SECONDS);
            builder.readTimeout(60, TimeUnit.SECONDS);
        }).build();
        return http.async(host)
                .addBodyPara("interfaceCode", params)
                .post().getResult().getBody().toString();
    }



}
