package com.example.cslocalspringdemo.cs.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemo;
import com.example.cslocalspringdemo.cs.vo.CsLocalDemoVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
public interface CsLocalDemoMapper extends BaseMapper<CsLocalDemo> {

    <E extends IPage<CsLocalDemoVo>> E queryPageList(E page, @Param("ew") Wrapper<CsLocalDemoVo> queryWrapper);

    CsLocalDemoVo getListById(Long csLocalDemoId);
}
