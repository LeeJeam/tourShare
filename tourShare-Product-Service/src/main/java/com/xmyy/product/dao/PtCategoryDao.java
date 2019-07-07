package com.xmyy.product.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 类目DAO
 *
 * @author simon
 */
public interface PtCategoryDao {

    List<Long> selectIdByKeyword(@Param("keyword") String keyword);

}