package com.xmyy.product.dao;

import com.xmyy.product.dto.ApiTourSiteDto;
import com.xmyy.product.vo.ApiTourSiteInfoResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行程站点DAO
 *
 * @author wangzejun
 */
public interface DgTourSiteDao {

    /**
     * 根据行程id查询行程站点列表，不查出发站
     *
     * @param tourId
     * @return
     */
    List<ApiTourSiteDto> queryTourSitesByTourId(@Param("tourId") Long tourId);

    /**
     * 根据行程id查询今日的行程站点信息，DgTourDao.xml的resultMap中使用，不要删除
     *
     * @param tourId
     * @return
     */
    List<ApiTourSiteInfoResult> queryTodayTourSiteInfosByTourId(@Param("id") Long tourId);

    /**
     * 根据行程id查询行程站点信息，DgTourDao.xml的resultMap中使用，不要删除
     *
     * @param tourId
     * @return
     */
    List<ApiTourSiteInfoResult> queryTourSiteInfosByTourId(@Param("id") Long tourId);

    /**
     * 根据行程站点id和行程id查询行程站点
     * 行程签到使用
     *
     * @param sort
     * @param tourId
     * @return
     */
    ApiTourSiteDto queryTourSiteBySortAndTourId(@Param("sort") Integer sort, @Param("tourId") Long tourId);
}
