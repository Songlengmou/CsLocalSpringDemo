package com.example.cslocalspringdemo.cs.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.cslocalspringdemo.common.Util.BeanUtil;
import com.example.cslocalspringdemo.common.Util.StringUtil;
import com.example.cslocalspringdemo.common.page.ConventPage;
import com.example.cslocalspringdemo.common.page.PageOutput;
import com.example.cslocalspringdemo.common.result.Response;
import com.example.cslocalspringdemo.cs.dto.CsLocalDemoDto;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemo;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemoDetail;
import com.example.cslocalspringdemo.cs.mapper.CsLocalDemoDetailMapper;
import com.example.cslocalspringdemo.cs.service.CsLocalDemoDetailService;
import com.example.cslocalspringdemo.cs.service.CsLocalDemoService;
import com.example.cslocalspringdemo.cs.vo.CsLocalDemoDetailVo;
import com.example.cslocalspringdemo.cs.vo.CsLocalDemoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.isNull;

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
@RequestMapping("/csLocalSpringDemo/csLocalDemo")
@Api(tags = "用户")
public class CsLocalDemoController {

    @Resource
    private CsLocalDemoService csLocalDemoService;

    @Resource
    private CsLocalDemoDetailService csLocalDemoDetailService;

    @Resource
    private CsLocalDemoDetailMapper csLocalDemoDetailMapper;

    /**
     * 新增
     */
    @PostMapping("/addCsUser")
    @ApiOperation(value = "新增用户", notes = "")
    private Response addCsData(@RequestBody CsLocalDemoDto csLocalDemoDto) {
        checkArgument(!Objects.isNull(csLocalDemoDto), "缺少必传参数");
        //方法一：BeanUtil.copy
        boolean result;
        CsLocalDemo csLocalDemo = BeanUtil.copy(csLocalDemoDto, CsLocalDemo.class);
        csLocalDemo.setCreateTime(LocalDateTime.now());
        csLocalDemo.setModifyTime(LocalDateTime.now());
        csLocalDemo.setCreateName("创建人");
        csLocalDemo.setUserName(csLocalDemoDto.getUserName());
        csLocalDemo.setUserAge(csLocalDemoDto.getUserAge());
        result = csLocalDemoService.save(csLocalDemo);
        if (!result) {
            return Response.notOk(500, "操作失败");
        }
        //方法二：@Builder注解
        if (!isNull(csLocalDemo.getId())) {
            CsLocalDemoDetail csLocalDemoDetail = CsLocalDemoDetail.builder()
                    .createTime(LocalDateTime.now())
                    .createName("修改的新增人")
                    .modifyTime(LocalDateTime.now())
                    .levelName(csLocalDemoDto.getLevelName())
                    .csLocalDemoId(Math.toIntExact(csLocalDemo.getId()))
                    .isDeleted(0)
                    .isEnabled(1)
                    .build();
            result = csLocalDemoDetailService.save(csLocalDemoDetail);
            if (!result) {
                return Response.notOk(500, "操作失败");
            }
        }
        return Response.ok("操作成功");
    }

    /**
     * 查询
     */
    @GetMapping("/getPageList")
    @ApiOperation(value = "用户分页 * 接口", notes = "Counter")
    public Response<PageOutput<CsLocalDemoVo>> getPageList(CsLocalDemoDto csLocalDemoDto) {
        IPage<CsLocalDemoVo> pageList = csLocalDemoService.queryPageList(csLocalDemoDto);
        List<CsLocalDemoVo> csUserVos = BeanUtil.copyList(pageList.getRecords(), CsLocalDemoVo.class);
        if (pageList.getTotal() > 0) {
            csUserVos.forEach(x -> {
                x.setAddress("HZ");
            });
        }
        return Response.ok(ConventPage.getPageOutput(pageList.getTotal(), csUserVos));
    }

    /**
     * 明细
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "用户明细1", notes = "")
    public Response<CsLocalDemoVo> getBrokerById(@PathVariable Long id) {
        CsLocalDemoVo csUserVo = csLocalDemoService.getListById(id);
        return Response.ok(csUserVo);
    }

    @GetMapping("/detailTwo/{id}")
    @ApiOperation(value = "用户明细2", notes = "")
    public Response<CsLocalDemoVo> getBrokerTwoById(@PathVariable Long id) {
        checkArgument(!Objects.isNull(id), "id不能为空");
        CsLocalDemo csLocalDemoById = csLocalDemoService.getById(id);
        CsLocalDemoVo csLocalDemoVo = BeanUtil.copy(csLocalDemoById, CsLocalDemoVo.class);
        //detail
//        Wrapper<CsLocalDemoDetail> wrapper = Wrappers.<CsLocalDemoDetail>query().lambda()
//                .eq(CsLocalDemoDetail::getCsLocalDemoId, csLocalDemoById.getId());
        //或
        QueryWrapper<CsLocalDemoDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("cs_local_demo_id", csLocalDemoById.getId());

        //1.使用Mapper
        // 若单条数据则使用csLocalDemoDetailMapper.selectOne(wrapper),若多条数据则使用.selectList(wrapper)
//        List<CsLocalDemoDetail> csLocalDemoDetails = csLocalDemoDetailMapper.selectList(wrapper);
        //2.使用Service
        // 若单条数据则使用csLocalDemoDetailService.getOne(wrapper),若多条数据则使用.list(wrapper)
        List<CsLocalDemoDetail> csLocalDemoDetails = csLocalDemoDetailService.list(wrapper);
        List<CsLocalDemoDetailVo> csLocalDemoDetailVos = BeanUtil.copyList(csLocalDemoDetails, CsLocalDemoDetailVo.class);
        csLocalDemoVo.setCsLocalDemoDetailVos(csLocalDemoDetailVos);
        return Response.ok(csLocalDemoVo);
    }

    /**
     * 修改
     */
    @PostMapping("/updateCsUser")
    @ApiOperation(value = "用户修改", notes = "")
    private Response updateCsData(@RequestBody CsLocalDemoDto csLocalDemoDto) {
        checkArgument(!Objects.isNull(csLocalDemoDto), "缺少必传参数");
        CsLocalDemo byIdCsUser = csLocalDemoService.getById(csLocalDemoDto.getId());
        boolean result;
        CsLocalDemo csLocalDemo;
        CsLocalDemoDetail csLocalDemoDetail;
        if (!StringUtil.isEmpty(byIdCsUser)) {
            csLocalDemo = BeanUtil.copy(csLocalDemoDto, CsLocalDemo.class);
            csLocalDemo.setModifyTime(LocalDateTime.now());
            csLocalDemo.setCreateName("修改人");
            result = csLocalDemoService.updateById(csLocalDemo);
            //detail
            if (!isNull(csLocalDemo.getId())) {
                csLocalDemoDetail = csLocalDemoDetailService.getListByCsLocalDemoId(csLocalDemo.getId());
                csLocalDemoDetail.setModifyTime(LocalDateTime.now());
                csLocalDemoDetail.setModifyName("修改人detail");
                csLocalDemoDetail.setLevelName(csLocalDemoDto.getLevelName());
                result = csLocalDemoDetailService.updateById(csLocalDemoDetail);
            }
        } else {
            csLocalDemo = BeanUtil.copy(csLocalDemoDto, CsLocalDemo.class);
            csLocalDemo.setCreateTime(LocalDateTime.now());
            csLocalDemo.setModifyTime(LocalDateTime.now());
            csLocalDemo.setCreateName("修改的新增人");
            csLocalDemo.setUserName(csLocalDemoDto.getUserName());
            csLocalDemo.setUserAge(csLocalDemoDto.getUserAge());
            result = csLocalDemoService.save(csLocalDemo);
            //detail
            if (!isNull(csLocalDemo.getId())) {
                csLocalDemoDetail = CsLocalDemoDetail.builder()
                        .createTime(LocalDateTime.now())
                        .createName("修改的新增人detail")
                        .modifyTime(LocalDateTime.now())
                        .levelName(csLocalDemoDto.getLevelName())
                        .csLocalDemoId(Math.toIntExact(csLocalDemo.getId()))
                        .isDeleted(0)
                        .isEnabled(1)
                        .build();
                result = csLocalDemoDetailService.save(csLocalDemoDetail);
            }
        }
        if (result) {
            return Response.ok("修改成功");
        }
        return Response.notOk("修改失败");
    }

    /**
     * 真实删除
     */
    @DeleteMapping("/delCsUser/{id}")
    @ApiOperation(value = "真实删除用户", notes = "")
    public Response del(@PathVariable Long id) {
        CsLocalDemo csUser = csLocalDemoService.getById(id);
        if (csLocalDemoService.removeById(csUser)) {
            return Response.ok("删除成功");
        }
        return Response.notOk("删除失败");
    }

    /**
     * 是否启用
     */
    @PutMapping("/isEnabledCsUser/{id}")
    @ApiOperation(value = "用户是否启用", notes = "")
    public Response cancel(@PathVariable Long id) {
        CsLocalDemo csUser = csLocalDemoService.getById(id);
        csUser.setIsEnabled(0);
        if (csLocalDemoService.updateById(csUser)) {
            return Response.ok("启用成功");
        }
        return Response.notOk("启用失败");
    }
}

