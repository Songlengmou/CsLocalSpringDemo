package com.example.cslocalspringdemo.cs.controller;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/csLocalSpringDemo/csLocalDemoDetail")
@Api(tags = "用户明细")
public class CsLocalDemoDetailController {

}

