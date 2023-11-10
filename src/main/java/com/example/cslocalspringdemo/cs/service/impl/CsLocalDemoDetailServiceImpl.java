package com.example.cslocalspringdemo.cs.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemoDetail;
import com.example.cslocalspringdemo.cs.mapper.CsLocalDemoDetailMapper;
import com.example.cslocalspringdemo.cs.service.CsLocalDemoDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
@Service
@DS("cs_local_spring_demo")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CsLocalDemoDetailServiceImpl extends ServiceImpl<CsLocalDemoDetailMapper, CsLocalDemoDetail> implements CsLocalDemoDetailService {

    @Override
    public CsLocalDemoDetail getListByCsLocalDemoId(Long csLocalDemoId) {
        return this.baseMapper.getListByCsLocalDemoId(csLocalDemoId);
    }
}
