package com.example.cslocalspringdemo.cs.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.cslocalspringdemo.common.Util.StringUtil;
import com.example.cslocalspringdemo.common.page.ConventPage;
import com.example.cslocalspringdemo.cs.dto.LmBaseUserDto;
import com.example.cslocalspringdemo.cs.entity.LmBaseUser;
import com.example.cslocalspringdemo.cs.mapper.LmBaseUserMapper;
import com.example.cslocalspringdemo.cs.service.LmBaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cslocalspringdemo.cs.vo.LmBaseUserVo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
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
public class LmBaseUserServiceImpl extends ServiceImpl<LmBaseUserMapper, LmBaseUser> implements LmBaseUserService {

    @Override
    public IPage<LmBaseUserVo> queryPageList(LmBaseUserDto lmBaseUserDto) {
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), LmBaseUserVo.class);
        Wrapper<LmBaseUserVo> wrapper = Wrappers.<LmBaseUserVo>query().lambda()
                .eq(!StringUtil.isEmpty(lmBaseUserDto.getUserName()), LmBaseUserVo::getUserName, lmBaseUserDto.getUserName());
        return this.baseMapper.queryPageList(ConventPage.getPage(lmBaseUserDto), wrapper);
    }

    @Override
    public LmBaseUser getUserPwd(String userName) {
        return baseMapper.getUserPwd(userName);
    }
}
