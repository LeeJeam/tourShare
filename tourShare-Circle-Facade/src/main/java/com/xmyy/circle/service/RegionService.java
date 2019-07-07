package com.xmyy.circle.service;

import com.xmyy.circle.model.DgRegion;
import com.xmyy.circle.vo.ApiRegionFlagResult;
import com.xmyy.circle.vo.RegionTreeListParam;
import top.ibase4j.core.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * DG_REGION 地区表  服务类
 * </p>
 *
 * @author wangzejun
 * @since 2018-06-04
 */
public interface RegionService extends BaseService<DgRegion> {

    Object getTreeList(RegionTreeListParam params);

    Map<String,String> getFlagMap();

    List<ApiRegionFlagResult> getRegionList();
}