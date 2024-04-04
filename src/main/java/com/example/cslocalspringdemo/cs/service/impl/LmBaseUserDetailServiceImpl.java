package com.example.cslocalspringdemo.cs.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.cslocalspringdemo.cs.entity.LmBaseUserDetail;
import com.example.cslocalspringdemo.cs.mapper.LmBaseUserDetailMapper;
import com.example.cslocalspringdemo.cs.service.LmBaseUserDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
@Service
@DS("auth")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LmBaseUserDetailServiceImpl extends ServiceImpl<LmBaseUserDetailMapper, LmBaseUserDetail> implements LmBaseUserDetailService {

    @Override
    public LmBaseUserDetail getListByLmBaseUserId(Long lmBaseUserId) {
        return this.baseMapper.getListByLmBaseUserId(lmBaseUserId);
    }
}
