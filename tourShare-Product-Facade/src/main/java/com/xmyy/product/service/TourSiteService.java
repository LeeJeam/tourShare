package com.xmyy.product.service;

import com.xmyy.product.dto.ApiTourSiteDto;
import com.xmyy.product.model.DgTourSite;
import com.xmyy.product.vo.ApiTourDetailParam;
import com.xmyy.product.vo.ApiTourSiteSignInParam;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * 行程站点  服务接口
 * @author wangzejun
 */
public interface TourSiteService extends BaseService<DgTourSite> {

    /**
     * 根据行程ID查询行程站点信息
     * @param tourId
     * @return
     */
    List<ApiTourSiteDto> queryTourSiteInfoList(Long tourId);

    /**
     * 查询行程站点列表、站点卡片，用于行程详情接口
     * @param params
     * @return
     */
    List<ApiTourSiteDto> queryTourSiteAndCardInfoList(ApiTourDetailParam params);

    /**
     * 行程签到
     * @param locationInfo
     * @return
     */
    Object signTourSite(ApiTourSiteSignInParam locationInfo);

    /**
     * 逻辑删除行程相关的行程站点
     * @param tourId 行程ID
     */
    void deleteTourSitesByTourId(Long tourId);
}