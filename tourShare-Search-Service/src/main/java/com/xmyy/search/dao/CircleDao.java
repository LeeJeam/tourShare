package com.xmyy.search.dao;

import com.xmyy.search.vo.CircleData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface CircleDao {

    int countValidCircles(@Param("lastIndexTime") Date lastIndexTime);

    List<CircleData> findValidCircles(@Param("lastIndexTime") Date lastIndexTime, RowBounds rowBounds);
}
