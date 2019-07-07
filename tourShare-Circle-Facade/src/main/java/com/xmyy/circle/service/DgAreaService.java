package com.xmyy.circle.service;

import com.xmyy.circle.model.DgArea;
import com.xmyy.circle.vo.ApiAreaJieDaoParam;
import com.xmyy.circle.vo.AreaJiedaoResult;
import com.xmyy.circle.vo.AreaTreeResult;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 *   地区服务类
 * </p>
 *
 * @author wangzejun
 * @since 2018-06-27
 */
public interface DgAreaService extends BaseService<DgArea> {

    /**
     * 省市区县
      * @return
     */
   List<AreaTreeResult> getAreaTree();

    /**
     * 获取街道信息
     */
    List<AreaJiedaoResult> getJieDaoInfo(ApiAreaJieDaoParam params);

}