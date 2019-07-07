package com.xmyy.product.dao;

import com.xmyy.product.vo.ApiBrandListResult;
import com.xmyy.product.vo.BrandListParam;
import com.xmyy.product.vo.BrandListResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 品牌DAO
 *
 * @author simon
 */
public interface PtBrandDao {

    List<Long> selectIdByKeyword(@Param("keyword") String keyword);

    List<BrandListResult> getBrandIdPageList(@Param("params") BrandListParam params, RowBounds rb);

    Integer getBrandIdPageCount(@Param("params") BrandListParam params);

    List<ApiBrandListResult> getSimpleBrandList(@Param("categoryId") Long categoryId, @Param("categoryId2") Long categoryId2, @Param("initial") String initial);

    List<ApiBrandListResult> getSimpleBrandAllList(@Param("initial") String initial);

}