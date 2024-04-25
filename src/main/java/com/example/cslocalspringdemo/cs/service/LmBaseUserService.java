package com.example.cslocalspringdemo.cs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.cslocalspringdemo.cs.dto.LmBaseUserDto;
import com.example.cslocalspringdemo.cs.entity.LmBaseUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cslocalspringdemo.cs.vo.LmBaseUserVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
public interface LmBaseUserService extends IService<LmBaseUser> {

    IPage<LmBaseUserVo> queryPageList(LmBaseUserDto lmBaseUserDto);

    LmBaseUser getUserPwd(String userName);
}
