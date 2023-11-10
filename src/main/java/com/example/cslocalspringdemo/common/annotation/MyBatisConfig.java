package com.example.cslocalspringdemo.common.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Song
 * @Desc: 扫描的mapper包路径
 * @Data: 2023/11/9
 */
@Configuration
@MapperScan("com.example.cslocalspringdemo.**.mapper")
public class MyBatisConfig {

}
