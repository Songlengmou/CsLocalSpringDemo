package com.example.cslocalspringdemo.cs.controller;

import com.example.cslocalspringdemo.common.redis.RedisConfigEntity;
import com.example.cslocalspringdemo.common.redis.RedisUtil;
import com.example.cslocalspringdemo.common.result.Response;
import com.example.cslocalspringdemo.cs.entity.SystemConfig;
import com.example.cslocalspringdemo.cs.service.LmBaseUserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/auth/lmBaseUserDetail")
@Api(tags = "用户明细")
public class LmBaseUserDetailController {

    private final static String REDIS_KEY_PREFIX = "system:config:";

    private final static String REDIS_KEY_PREFIX_ALL = "system:config:*";

    @Resource
    private LmBaseUserDetailService lmBaseUserDetailService;

    @Resource
    private RedisConfigEntity redisConfigEntity;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 新增
     */
    @PostMapping("/addRedis")
    @ApiOperation(value = "新增redis", notes = "")
    public Response addRedis(@RequestBody SystemConfig systemConfig) {
        checkArgument(!Objects.isNull(systemConfig), "缺少必传参数");
        if (redisConfigEntity.isEnabled()) {
            redisUtil.set(REDIS_KEY_PREFIX + systemConfig.getConfigKey(), systemConfig.getConfigValue());
        }
        return Response.ok("新增redis成功");
    }

    /**
     * 根据key获取value
     */
    @GetMapping("/getSystemConfig/{configKey}")
    @ApiOperation(value = "redis根据key获取value", notes = "")
    public Response getSystemConfig(@PathVariable String configKey) {
        checkArgument(!Objects.isNull(configKey), "缺少redisKey");
        if (redisConfigEntity.isEnabled()) {
            String configValue = (String) redisUtil.get(REDIS_KEY_PREFIX + configKey);
            return Response.ok(configValue, "获取redisValue成功");
        }
        return Response.notOk("获取redisValue失败");
    }

    /**
     * 获取所有对应的key和value
     */
    @GetMapping("/getSystemConfigAll")
    @ApiOperation(value = "redis获取所有对应的key和value", notes = "")
    public Response getSystemConfigAll() {
        if (redisConfigEntity.isEnabled()) {
            Jedis jedis = new Jedis(redisConfigEntity.getRedisHost(), redisConfigEntity.getRedisPort());
            jedis.auth(redisConfigEntity.getRedisPass());
            jedis.ping();
            Set<String> keys = jedis.keys(REDIS_KEY_PREFIX_ALL);
            List<SystemConfig> systemConfigs = new ArrayList<>();
            for (String key : keys) {
                SystemConfig systemConfig = new SystemConfig();
                String value = jedis.get(key);
                String[] parts = key.split(":");
                String resultKey = parts[parts.length - 1];
                systemConfig.setConfigKey(resultKey);
                systemConfig.setConfigValue(value);
                systemConfigs.add(systemConfig);
            }
            jedis.close();
            return Response.ok(systemConfigs, "获取redis成功");
        }
        return Response.ok("获取redis失败");
    }
}
