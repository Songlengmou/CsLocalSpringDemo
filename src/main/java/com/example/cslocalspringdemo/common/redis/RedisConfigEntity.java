package com.example.cslocalspringdemo.common.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description RedisConfigBean配置
 * @Author Song
 * @Date 2024/3/5 18:18
 */
@Data
@Component
public class RedisConfigEntity {

    @Value("${redis.enabled:false}")
    private boolean enabled;

    @Value("${redis.host:}")
    private String redisHost;

    @Value("${redis.port:0}")
    private int redisPort;

    @Value("${redis.password:}")
    private String redisPass;

    @Value("${redis.database:0}")
    private int redisDb;

    @Value("${redis.timeout:0}")
    private int timeout;

    @Value("${redis.pool.minIdle:0}")
    private int minIdle;

    @Value("${redis.pool.maxIdle:0}")
    private int maxIdle;

    @Value("${redis.pool.maxActive:0}")
    private int maxActive;

    @Value("${redis.pool.maxWait:0}")
    private int maxWait;
}
