package com.xmyy.search.dao;

import com.xmyy.search.vo.SellerData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface SellerDao {

    int countValidSellers(@Param("lastIndexTime") Date lastIndexTime);

    List<SellerData> findValidSellers(@Param("lastIndexTime") Date lastIndexTime, RowBounds rowBounds);
}
