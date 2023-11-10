package com.example.cslocalspringdemo.cs.mapper;

import com.example.cslocalspringdemo.cs.entity.CsLocalDemoDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
public interface CsLocalDemoDetailMapper extends BaseMapper<CsLocalDemoDetail> {

    CsLocalDemoDetail getListByCsLocalDemoId(Long csLocalDemoId);

}
