package com.example.cslocalspringdemo.cs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.cslocalspringdemo.cs.entity.LmBaseUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cslocalspringdemo.cs.vo.LmBaseUserVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
public interface LmBaseUserMapper extends BaseMapper<LmBaseUser> {

    <E extends IPage<LmBaseUserVo>> E queryPageList(E page, @Param("ew") Wrapper<LmBaseUserVo> queryWrapper);
}
