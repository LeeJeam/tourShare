package com.xmyy.circle.dao;

import com.xmyy.circle.dto.AreaDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地区信息dao<br>
 *
 * @author wangzejun
 * @time 2018年 06月27日 16:15:33
 * @since 1.0.0
 */
public interface DgAreaDao {

    List<AreaDto> queryArea(@Param("parentCode") String parentCode, @Param("level") Integer level);
}