package com.example.cslocalspringdemo.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cslocalspringdemo.cs.entity.CsLocalDemoDetail;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
public interface CsLocalDemoDetailService extends IService<CsLocalDemoDetail> {

    CsLocalDemoDetail getListByCsLocalDemoId(Long csLocalDemoId);
}
