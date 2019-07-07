package com.xmyy.product.service;

import com.xmyy.product.model.DgTour;
import com.xmyy.product.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 行程  服务接口
 *
 * @author wangzejun
 */
public interface TourService extends BaseService<DgTour> {

    /**
     * 发行程
     *
     * @param param
     * @param memberId
     * @return
     */
    Object add(ApiTourAddParam param, Long memberId);

    /**
     * 删除行程
     *
     * @param id
     * @param memberId
     * @return
     */
    Object delete(Long id, Long memberId);

    /**
     * 买手/背包客 我的行程列表
     *
     * @param createBy
     * @param isPacker
     * @return
     */
    Pagination<ApiTourInfoResult> listForSeller(Long createBy, Integer isPacker);

    /**
     * 买家端首页，更多行程列表
     *
     * @param params
     * @return
     */
    Pagination<ApiTourInBuyerIndexResult> listPageForBuyer(ApiTourListForBuyerParam params);

    /**
     * 买家端首页轮播行程列表
     *
     * @param params
     * @return
     */
    Pagination<ApiTourInBuyerIndexResult> listIndexForBuyer(ApiTourInBuyerIndexParam params);

    /**
     * 背包客个人主页，进行中的行程
     *
     * @param memberId 背包客ID
     * @return
     */
    ApiTourInfoInPackerIndexResult getTourInfoForPackerIndex(Long memberId);

    /**
     * 买家端-推荐买手-背包客，获取背包客的最新行程信息
     * @param memberIds
     * @return
     */
    Map<String, ApiTourInfoResult> queryNewTourByMemberIds(List<Long> memberIds);

    /**
     * 行程详情
     *
     * @param params
     * @return
     */
    Object getDetail(ApiTourDetailParam params);

    /**
     * 记录上传登机牌的信息
     *
     * @param param
     * @return
     */
    Object recordCheck(ApiTourUploadParam param);

    /**
     * 开启直播/关闭直播时，更新行程直播的状态
     *
     * @param params
     */
    void updateLiveStatus(ApiTourLiveParam params);

    /**
     * 行程下架
     *
     * @param id
     * @param createBy
     * @return
     */
    Object stop(Long id, Long createBy);

    /**
     * 根据id查询行程，商品详情使用
     *
     * @param id
     * @return
     */
    ApiTourShortInfoResult getTourById(Long id);

    /**
     * 查询匹配的行程列表（开直播/接需求/发预售）
     *
     * @param params
     * @return
     */
    Pagination<ApiTourInfoResult> getMatchTourList(ApiTourMatchParam params);

    /**
     * 查询行程首站出行时间24(港澳台)/36(其他) 小时内，没有上传登机牌的行程ID
     *
     * @param memberId 用户ID
     * @param isPacker 是否背包客
     * @return 行程ID列表
     */
    List<Long> queryTourIdsNoCheckPic(Long memberId, Integer isPacker);

    /**
     * 行程签到提醒消息推送
     */
    void pushTodayToursInfo();

    /**
     * 到期“进行中”的行程，更新为“已完成”
     */
    void finishTour();

    /**
     * 设置未提交登机牌、行程开始时间超过36小时的行程失效
     */
    void abolishTour();

    /**
     * 需求完成，更新行程需求统计
     * @param tourId 行程ID
     */
    void updateDemandCountInTour(Long tourId);

    /**
     * 订单完成，更新行程订单统计
     * @param tourId
     * @param payMoney
     */
    void updateOrderCountInTour(Long tourId, BigDecimal payMoney);
}