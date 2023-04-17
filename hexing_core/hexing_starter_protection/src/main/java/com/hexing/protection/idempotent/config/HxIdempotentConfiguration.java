package com.hexing.protection.idempotent.config;

import com.hexing.protection.idempotent.core.aop.IdempotentAspect;
import com.hexing.protection.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.hexing.protection.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.hexing.protection.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.hexing.protection.idempotent.core.redis.IdempotentRedisDAO;
import com.hexing.redis.config.HxRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = HxRedisAutoConfiguration.class)
public class HxIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
