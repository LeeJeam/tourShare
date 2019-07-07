package com.xmyy.circle.dao;

import com.xmyy.circle.vo.ApiRegionFlagListParam;
import com.xmyy.circle.vo.ApiRegionFlagResult;
import com.xmyy.circle.vo.RegionTreeListParam;
import com.xmyy.circle.vo.RegionTreeListResult;

import java.util.List;

/**
 * 地区dao<br>
 *
 * @author wangzejun
 * @time 2018年 06月21日 23:14:12
 * @since 1.0.0
 */
public interface DgRegionDao  {

    List<RegionTreeListResult> getTreeList(RegionTreeListParam param);

    List<ApiRegionFlagResult> getFlagList(ApiRegionFlagListParam params);
}