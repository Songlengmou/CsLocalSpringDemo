package com.example.cslocalspringdemo.cs.mapper;

import com.example.cslocalspringdemo.cs.entity.LmBaseUserDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
public interface LmBaseUserDetailMapper extends BaseMapper<LmBaseUserDetail> {

    LmBaseUserDetail getListByLmBaseUserId(Long lmBaseUserId);
}
