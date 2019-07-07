package com.xmyy.search.dao;

import com.xmyy.search.vo.ProductData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * Created by Simon on 2018/7/20.
 */
public interface ProductDao {

    int countValidProducts(@Param("lastIndexTime") Date lastIndexTime);

    List<ProductData> findValidProducts(@Param("lastIndexTime") Date lastIndexTime, RowBounds rowBounds);

}
