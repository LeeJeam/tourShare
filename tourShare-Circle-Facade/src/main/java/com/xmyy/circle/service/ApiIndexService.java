package com.xmyy.circle.service;

import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.vo.BuyerApiIndexResult;
import com.xmyy.circle.vo.BuyerIndexParam;
import top.ibase4j.core.base.BaseService;

public interface ApiIndexService extends BaseService<UcDynamicCircle> {

    /**
     * 买手端基于App预先加载基础信息
     * @return
     */
    Object sellerGet();

    /**
     * 买家端基于App预先加载基础信息
     * @return
     */
    Object get();

    /**
     * 买家首页列表
     * @param params
     * @return
     */
    BuyerApiIndexResult index(BuyerIndexParam params);

    /**
     * 买手端首页
     * @param id 买手ID
     * @return
     */
    Object sellerIndex(Long id);
}
