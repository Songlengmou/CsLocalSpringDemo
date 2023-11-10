package com.example.cslocalspringdemo.cs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cslocalspringdemo.cs.dto.CsLocalDemoDto;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemo;
import com.example.cslocalspringdemo.cs.vo.CsLocalDemoVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
public interface CsLocalDemoService extends IService<CsLocalDemo> {

    IPage<CsLocalDemoVo> queryPageList(CsLocalDemoDto csUserDto);

    CsLocalDemoVo getListById(Long csLocalDemoId);

}
