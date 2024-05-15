package com.example.cslocalspringdemo.cs.controller;

import com.example.cslocalspringdemo.common.result.Response;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemoDetail;
import com.example.cslocalspringdemo.cs.service.CsLocalDemoDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource
    private CsLocalDemoDetailService csLocalDemoDetailService;

    /**
     * 是否启用
     */
    @PutMapping("/isEnabledCsUserDetail/{id}")
    @ApiOperation(value = "用户明细是否启用", notes = "")
    public Response isEnabledCsUserDetail(@PathVariable Long id) {
        CsLocalDemoDetail csLocalDemoDetail = csLocalDemoDetailService.getById(id);
        csLocalDemoDetail.setIsEnabled(0);
        if (csLocalDemoDetailService.updateById(csLocalDemoDetail)) {
            return Response.ok("启用成功");
        }
        return Response.notOk("启用失败");
    }

}

