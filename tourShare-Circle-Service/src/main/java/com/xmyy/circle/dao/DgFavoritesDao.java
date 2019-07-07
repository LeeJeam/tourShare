package com.xmyy.circle.dao;

import com.xmyy.circle.vo.MyFavoritesResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface DgFavoritesDao {

    List<MyFavoritesResult> findProductFavorites(@Param("m") Map<String, Object> map, RowBounds rb);

}
