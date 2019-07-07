package com.xmyy.product.dao;

import com.xmyy.product.dto.ProductStatisticDto;
import com.xmyy.product.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 商品DAO
 *
 * @author simon
 */
public interface PtProductDao {

    List<ApiProductSearchListResult> selectIdByKeyword(@Param("keyword") String keyword, @Param("categoryIds") List<Long> categoryIds,
                                                       @Param("brandIds") List<Long> brandIds, @Param("productIds") List<Long> productIds);

    Integer getProductPageCount(ApiProductListParam params);

    List<ApiProductListResult> getProductPageList(ApiProductListParam params, RowBounds rowBounds);

    List<ProductStatisticDto> getStatisticByCategory();

    Integer queryProductListCount(ManageProductListParam params);

    List<ManageProductListResult> queryProductListPage(ManageProductListParam params, RowBounds rowBounds);

    Integer stopProductsByTourId(@Param("tourId") Long tourId,@Param("isSale")Integer isSale);

    Integer increaseVideoClick(@Param("id") Long id);

    Integer queryManagePresellProductCount(ManagePresellProductParam params);

    List<ManagePresellProductResult> queryManagePresellProductList(ManagePresellProductParam params, RowBounds rowBounds);
}