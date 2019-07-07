package com.xmyy.product.dao;

import com.xmyy.product.dto.SeriesStatisticDto;

import java.util.List;

/**
 * Series DAO
 *
 * @author simon
 */
public interface PtSeriesDao {

    List<SeriesStatisticDto> getStatisticByCategory();

}