package com.xmyy.demand.service;

import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.vo.*;
import top.ibase4j.core.base.BaseService;

/**
 * 需求后台管理  服务接口
 */
public interface DgDemandOrderManageService extends BaseService<DgDemandOrder> {

    /**
     * 后端获取需求详情
     * @param id
     * @return
     */
    Object getDetailByBack(Long id);

    /**
     * 按标签统计需求数量
     * @return
     */
    Object getStatisticByTag();

    /**
     * 需求库列表
     * @param params
     * @return
     */
    Object listLibrary(DemandLibListManageParam params);

    /**
     * 分页查询需求列表
     * @param params
     * @return
     */
    Object listByPage(DemandListManageParam params);
}