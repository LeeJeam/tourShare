package com.xmyy.search.dao;

import com.xmyy.search.vo.PackerData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface PackerDao {

    int countValidPackers(@Param("lastIndexTime") Date lastIndexTime);

    List<PackerData> findValidPackers(@Param("lastIndexTime") Date lastIndexTime, RowBounds rowBounds);
}
