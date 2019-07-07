package com.xmyy.manage.dao;

import com.xmyy.manage.model.WsSensitive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SensitiveDao {

    int insertSensitiveBatch(@Param(value = "records") List<WsSensitive> its);
}
