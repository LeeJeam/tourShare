package com.xmyy.manage.dao;

import java.util.List;

public interface AdminSessionDao {

    void deleteBySessionId(String sessionId);

    Long queryBySessionId(String sessionId);

    List<String> querySessionIdByAccount(String account);
}
