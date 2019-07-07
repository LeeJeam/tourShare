package com.xmyy.product.dao;

import com.xmyy.product.dto.ApiTourImgDto;
import com.xmyy.product.model.DgTourSite;
import com.xmyy.product.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 行程DAO
 *
 * @author wangzejun
 */
public interface DgTourDao {

    /**
     * 查询匹配的行程列表
     * @param params
     * @return
     */
    List<ApiTourInfoResult> matchPageListByMacthCondtion(ApiTourMatchParam params);

    /**
     * 查询匹配的行程数
     * @param params
     * @return
     */
    Long countByMacthCondtion(ApiTourMatchParam params);

    /**
     * 买家端 更多行程查询
     * 只查行程表 （排序 按智能，行程时间）
     * @param params
     * @return
     */
    List<ApiTourImgDto> listPageForBuyer1(ApiTourListForBuyerParam params);

    /**
     * 买家端 更多行程查询
     * 只查行程表 （排序 按智能，行程时间）
     * @param params
     * @return
     */
    Long countTourForBuyer1(ApiTourListForBuyerParam params);

    /**
     * 买家端 更多行程查询
     * 注意：行程表关联买手表 需要改造成 买手 背包客公共表
     * @param params
     * @return
     */
    List<ApiTourImgDto> listPageForBuyer2(ApiTourListForBuyerParam params);

    /**
     * 买家端 更多行程查询
     * 注意：行程表关联买手表 需要改造成 买手 背包客公共表
     * @param params
     * @return
     */
    Long countTourForBuyer2(ApiTourListForBuyerParam params);

    /**
     * 查询买手或背包最新行程
     * @param createBy
     * @param isPacker
     * @return
     */
    List<ApiTourInfoResult> listRecentForSeller(@Param("createBy") Long createBy, @Param("isPacker") Integer isPacker);

    /**
     * 查询买手或背包历史行程
     * @param createBy
     * @param isPacker
     * @return
     */
    List<ApiTourInfoResult> listHistoryForSeller(@Param("createBy") Long createBy, @Param("isPacker") Integer isPacker);

    /**
     * 查询买家端首页轮播行程
     * @param params
     * @return
     */
    List<ApiTourImgDto> listIndexForBuyer(ApiTourInBuyerIndexParam params);

    /**
     * 统计买家端首页轮播行程
     * @param params
     * @return
     */
    Long countIndexForBuyer(ApiTourInBuyerIndexParam params);

    /**
     * 行程签到，更新当前到达国家
     * @param tourSite
     * @return
     */
    Integer updateCurSiteByTourSite(DgTourSite tourSite);

    /**
     * 背包客首页，查询最新行程（正在进行的行程）
     * @param memberId
     * @return
     */
    ApiTourInfoResult listRunningTour(@Param("createBy") Long memberId);

    /**
     * 买家端，推荐买手-背包客，查询背包客最新行程
     * @param memberIds
     * @return
     */
    List<ApiTourInfoResult> queryNewTourByMemberIds(@Param("memberIds") List<Long> memberIds);

    /**
     * 查询行程站点的出发时间是今天的行程
     * @return
     */
    List<ApiTourTodayResult> queryAllTodayTours();


    //============================管理端DAO start=============================
    /**
     * 管理端-买手-查询行程
     * @param params
     * @return
     */
    List<ManageTourDetailResult> queryForBuyer(ManageTourQueryParam params);

    /**
     * 管理端-买手-统计行程
     * @param params
     * @return
     */
    int countForBuyer(ManageTourQueryParam params);

    /**
     * 按行程目的地统计
     * @param params
     * @return
     */
    List<ManageTourDestRegionCountResult> countForDestRegion(ManageTourQueryParam params);

    /**
     * 查询行程验证列表数据
     * @param params
     * @return
     */
    List<ManageTourValidateDetailResult> queryForValidate(ManageTourGetValidateParam params);

    /**
     * 统计行程验证查询总记录数
     * @param params
     * @return
     */
    int countForValidate(ManageTourGetValidateParam params);

    /**
     * 查询行程验证统计数
     * @param params
     * @return
     */
    List<Map<String, Object>> queryValidateCount(ManageTourGetValidateParam params);

    /**
     * 查询前5条置顶行程信息
     * @return
     */
    List<ManageTourTopResult> queryForTop();
    //============================管理端DAO end=============================
}
