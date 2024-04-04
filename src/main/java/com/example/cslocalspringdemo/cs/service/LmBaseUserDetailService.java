package com.example.cslocalspringdemo.cs.service;

import com.example.cslocalspringdemo.cs.entity.LmBaseUserDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
public interface LmBaseUserDetailService extends IService<LmBaseUserDetail> {

    LmBaseUserDetail getListByLmBaseUserId(Long lmBaseUserId);
}
