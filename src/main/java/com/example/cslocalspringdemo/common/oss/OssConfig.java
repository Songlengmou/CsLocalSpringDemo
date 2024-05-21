package com.example.cslocalspringdemo.common.oss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云·OSS配置
 */
@Configuration
public class OssConfig {

    @Value("${alioss.endpoint}")
    private String endpoint;
    @Value("${alioss.accessKey}")
    private String accessKeyId;
    @Value("${alioss.secretKey}")
    private String accessKeySecret;
    @Value("${alioss.bucketname}")
    private String bucketName;

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil() {
        return new AliOssUtil(endpoint, accessKeyId, accessKeySecret, bucketName);
    }
}
