package com.example.cslocalspringdemo.cs.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.cslocalspringdemo.common.Util.BeanUtil;
import com.example.cslocalspringdemo.common.Util.StringUtil;
import com.example.cslocalspringdemo.common.page.ConventPage;
import com.example.cslocalspringdemo.common.page.PageOutput;
import com.example.cslocalspringdemo.common.result.Response;
import com.example.cslocalspringdemo.cs.dto.LmBaseUserDto;
import com.example.cslocalspringdemo.cs.entity.LmBaseUser;
import com.example.cslocalspringdemo.cs.entity.LmBaseUserDetail;
import com.example.cslocalspringdemo.cs.service.LmBaseUserDetailService;
import com.example.cslocalspringdemo.cs.service.LmBaseUserService;
import com.example.cslocalspringdemo.cs.vo.LmBaseUserDetailVo;
import com.example.cslocalspringdemo.cs.vo.LmBaseUserVo;
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
 * @since 2024-04-03
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/auth/lmBaseUser")
public class LmBaseUserController {

    @Resource
    private LmBaseUserService lmBaseUserService;

    @Resource
    private LmBaseUserDetailService lmBaseUserDetailService;

    /**
     * 新增
     */
    @PostMapping("/addUser")
    @ApiOperation(value = "新增", notes = "")
    private Response<Object> addCsData(@RequestBody LmBaseUserDto lmBaseUserDto) {
        checkArgument(!Objects.isNull(lmBaseUserDto), "缺少必传参数");
        boolean result;
        LmBaseUser lmBaseUser = BeanUtil.copy(lmBaseUserDto, LmBaseUser.class);
        lmBaseUser.setModifyTime(LocalDateTime.now());
        lmBaseUser.setCreateId(4);
        lmBaseUser.setCreateName("李四");
        result = lmBaseUserService.save(lmBaseUser);
        if (!result) {
            return Response.notOk(500, "操作失败");
        }
        //detail
        if (!isNull(lmBaseUser.getId())) {
            LmBaseUserDetail lmBaseUserDetail = new LmBaseUserDetail();
            lmBaseUserDetail.setCreateId(4);
            lmBaseUserDetail.setCreateName("李四");
            lmBaseUserDetail.setBaseUserId(Math.toIntExact(lmBaseUser.getId()));
            result = lmBaseUserDetailService.save(lmBaseUserDetail);
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
    @ApiOperation(value = "分页 * 接口", notes = "Counter")
    public Response<PageOutput<LmBaseUserVo>> getPageList(LmBaseUserDto lmBaseUserDto) {
        IPage<LmBaseUserVo> pageList = lmBaseUserService.queryPageList(lmBaseUserDto);
        List<LmBaseUserVo> lmBaseUserVos = BeanUtil.copyList(pageList.getRecords(), LmBaseUserVo.class);
        if (pageList.getTotal() > 0) {
            lmBaseUserVos.forEach(x -> {
                x.setAddress("HZ");
            });
        }
        return Response.ok(ConventPage.getPageOutput(pageList.getTotal(), lmBaseUserVos));
    }

    /**
     * 明细
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "明细", notes = "")
    public Response<LmBaseUserVo> getLmBaseUserById(@PathVariable Long id) {
        checkArgument(!Objects.isNull(id), "id不能为空");
        LmBaseUser lmBaseUserServiceById = lmBaseUserService.getById(id);
        LmBaseUserVo lmBaseUserVo = BeanUtil.copy(lmBaseUserServiceById, LmBaseUserVo.class);
        //detail
        Wrapper<LmBaseUserDetail> wrapper = Wrappers.<LmBaseUserDetail>query().lambda()
                .eq(LmBaseUserDetail::getBaseUserId, lmBaseUserServiceById.getId());
        LmBaseUserDetail detailServiceOne = lmBaseUserDetailService.getOne(wrapper);
        LmBaseUserDetailVo lmBaseUserDetailVo = BeanUtil.copy(detailServiceOne, LmBaseUserDetailVo.class);
        lmBaseUserVo.setLmBaseUserDetailVo(lmBaseUserDetailVo);
        return Response.ok(lmBaseUserVo);
    }

    /**
     * 修改
     */
    @PostMapping("/updateUser")
    @ApiOperation(value = "修改", notes = "")
    private Response<Object> updateUser(@RequestBody LmBaseUserDto lmBaseUserDto) {
        checkArgument(!Objects.isNull(lmBaseUserDto), "缺少必传参数");
        LmBaseUser byIdUser = lmBaseUserService.getById(lmBaseUserDto.getId());
        boolean result;
        LmBaseUser lmBaseUser;
        LmBaseUserDetail lmBaseUserDetail;
        if (!StringUtil.isEmpty(byIdUser)) {
            lmBaseUser = BeanUtil.copy(lmBaseUserDto, LmBaseUser.class);
            lmBaseUser.setModifyTime(LocalDateTime.now());
            lmBaseUser.setModifyId(5);
            lmBaseUser.setModifyName("王五");
            result = lmBaseUserService.updateById(lmBaseUser);
            //detail
            if (!isNull(lmBaseUser.getId())) {
                lmBaseUserDetail = lmBaseUserDetailService.getListByLmBaseUserId(lmBaseUser.getId());
                lmBaseUserDetail.setModifyTime(LocalDateTime.now());
                lmBaseUserDetail.setModifyId(5);
                lmBaseUserDetail.setModifyName("王五");
                result = lmBaseUserDetailService.updateById(lmBaseUserDetail);
            }
        } else {
            lmBaseUser = BeanUtil.copy(lmBaseUserDto, LmBaseUser.class);
            lmBaseUser.setModifyTime(LocalDateTime.now());
            lmBaseUser.setCreateId(6);
            lmBaseUser.setCreateName("赵六");
            lmBaseUser.setModifyTime(LocalDateTime.now());
            lmBaseUser.setModifyId(6);
            lmBaseUser.setModifyName("赵六");
            lmBaseUser.setUserName(lmBaseUserDto.getUserName());
            lmBaseUser.setUserAge(lmBaseUserDto.getUserAge());
            lmBaseUser.setUserLevel(lmBaseUserDto.getUserLevel());
            result = lmBaseUserService.save(lmBaseUser);
            //detail
            if (!isNull(lmBaseUser.getId())) {
                lmBaseUserDetail = new LmBaseUserDetail();
                lmBaseUserDetail.setCreateId(6);
                lmBaseUserDetail.setCreateName("赵六");
                lmBaseUserDetail.setModifyTime(LocalDateTime.now());
                lmBaseUserDetail.setModifyId(6);
                lmBaseUserDetail.setModifyName("赵六");
                lmBaseUserDetail.setBaseUserId(Math.toIntExact(lmBaseUser.getId()));
                result = lmBaseUserDetailService.save(lmBaseUserDetail);
            }
        }
        if (result) {
            return Response.ok("修改成功");
        }
        return Response.notOk(500, "修改失败");
    }

    /**
     * 真实删除
     */
    @DeleteMapping("/delUser/{id}")
    @ApiOperation(value = "真实删除", notes = "")
    public Response<Object> delUser(@PathVariable Long id) {
        LmBaseUser byIdLmBaseUser = lmBaseUserService.getById(id);
        if (lmBaseUserService.removeById(byIdLmBaseUser)) {
            return Response.ok("删除成功");
        }
        return Response.notOk(500, "删除失败");
    }

    /**
     * 是否启用
     */
    @PutMapping("/isEnabledUser/{id}")
    @ApiOperation(value = "是否启用", notes = "")
    public Response<Object> isEnabledUser(@PathVariable Long id) {
        LmBaseUser byIdLmBaseUser = lmBaseUserService.getById(id);
        byIdLmBaseUser.setIsEnabled(1);
        byIdLmBaseUser.setModifyTime(LocalDateTime.now());
        byIdLmBaseUser.setModifyId(7);
        byIdLmBaseUser.setModifyName("钱七");
        if (lmBaseUserService.updateById(byIdLmBaseUser)) {
            return Response.ok("启用成功");
        }
        return Response.notOk(500, "启用失败");
    }
}
