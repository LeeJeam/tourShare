package com.xmyy.demand.dao;

import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.vo.StatisticResult;
import top.ibase4j.core.base.BaseMapper;

import java.util.List;

public interface DgDemandOrderDao extends BaseMapper<DgDemandOrder> {

    /**
     * 统计
     * @return
     */
    List<StatisticResult> getStatisticByTag();
}
