package com.xmyy.product.dao;

import com.xmyy.product.dto.ApiAttrDto;
import com.xmyy.product.dto.PtSeriesAttrDto;
import com.xmyy.product.vo.ManageSeriesParam;
import com.xmyy.product.vo.ManagerPtSeriesListResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SeriesAttr DAO
 *
 * @author simon
 */
public interface PtSeriesAttrDao{

    List<ApiAttrDto> getSeriesAttr(@Param("seriesId") Long seriesId);

    List<ManagerPtSeriesListResult> queryManagerPtSeriesList(ManageSeriesParam params);

    Integer countManagerPtSeries(ManageSeriesParam param);

    List<PtSeriesAttrDto> getSeriesAttrList(@Param("seriesIds") List<Long> seriesIds);

    Integer countSeriesTotal();
}