package com.example.cslocalspringdemo.cs.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cslocalspringdemo.common.Util.StringUtil;
import com.example.cslocalspringdemo.common.page.ConventPage;
import com.example.cslocalspringdemo.cs.dto.CsLocalDemoDto;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemo;
import com.example.cslocalspringdemo.cs.mapper.CsLocalDemoMapper;
import com.example.cslocalspringdemo.cs.service.CsLocalDemoService;
import com.example.cslocalspringdemo.cs.vo.CsLocalDemoVo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

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
public class CsLocalDemoServiceImpl extends ServiceImpl<CsLocalDemoMapper, CsLocalDemo> implements CsLocalDemoService {

    @Override
    public IPage<CsLocalDemoVo> queryPageList(CsLocalDemoDto csLocalDemoDto) {
//        List<String> storeList = new ArrayList<>();
//        if (!StringUtil.isEmpty(csLocalDemoDto.getStoreNo())) {
//            storeList = Arrays.asList(csLocalDemoDto.getStoreNo().split(","));
//        }

        String startDate = null, endDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        if (!isNull(csLocalDemoDto.getStartDate())) {
            startDate = csLocalDemoDto.getStartDate().format(formatter);
        }
        if (!isNull(csLocalDemoDto.getStartDate())) {
            endDate = csLocalDemoDto.getEndDate().format(formatter);
        }

        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""), CsLocalDemoVo.class);
        Wrapper<CsLocalDemoVo> wrapper = Wrappers.<CsLocalDemoVo>query().lambda()
                //获取所有的storeNo，在这里只是一个显示用法，之后根据自己项目情况编写
//                .in(!CollectionUtils.isEmpty(storeList), CsLocalDemoVo::getStoreNo, storeList)
                .eq(!StringUtil.isEmpty(csLocalDemoDto.getUserName()), CsLocalDemoVo::getUserName, csLocalDemoDto.getUserName())
                .like(!isNull(csLocalDemoDto.getUserAge()), CsLocalDemoVo::getUserAge, csLocalDemoDto.getUserAge())
                .ge(!StringUtil.isEmpty(startDate), CsLocalDemoVo::getStartDate, startDate)
                .le(!StringUtil.isEmpty(endDate), CsLocalDemoVo::getEndDate, endDate);
        return this.baseMapper.queryPageList(ConventPage.getPage(csLocalDemoDto), wrapper);
    }

    @Override
    public CsLocalDemoVo getListById(Long csLocalDemoId) {
        return this.baseMapper.getListById(csLocalDemoId);
    }
}
