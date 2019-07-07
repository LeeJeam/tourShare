package com.xmyy.product.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性DAO
 *
 * @author simon
 */
public interface PtAttrDao {

    List<Long> selectProductIdByKeyword(@Param("keyword") String keyword);

}