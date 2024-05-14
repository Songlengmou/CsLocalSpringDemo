package com.example.cslocalspringdemo.common.redis;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisConfigEntity redisConfigEntity;

    /**
     * 装配 redis的数据源
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate redisTemplate() {
        return createRedisTemplate(redisConfigEntity.getRedisHost(), redisConfigEntity.getRedisPort(), redisConfigEntity.getRedisPass(), redisConfigEntity.getRedisDb());
    }

    /**
     * 创建 RedisTemplate
     */
    public StringRedisTemplate createRedisTemplate(String host, int port, String password, int database) {
        // 基本配置
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfigEntity.getMaxActive());
        poolConfig.setMaxIdle(redisConfigEntity.getMaxIdle());
        poolConfig.setMinIdle(redisConfigEntity.getMinIdle());
        poolConfig.setMaxWaitMillis(redisConfigEntity.getMaxWait());
        poolConfig.setBlockWhenExhausted(true);
        if (ObjectUtils.isNotEmpty(password)) {
            jedisConnectionFactory.setPassword(password);
        }
        if (ObjectUtils.isNotEmpty(database)) {
            jedisConnectionFactory.setDatabase(database);
        }
        jedisConnectionFactory.setPoolConfig(poolConfig);
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setTimeout(redisConfigEntity.getTimeout());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);

        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }
}
