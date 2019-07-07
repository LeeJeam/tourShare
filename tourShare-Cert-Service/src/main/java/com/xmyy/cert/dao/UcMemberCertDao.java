package com.xmyy.cert.dao;

import com.xmyy.cert.vo.MemberCertCountParam;

import java.util.List;
import java.util.Map;

public interface UcMemberCertDao {

    List<Map<String, String>> getResultCount(MemberCertCountParam param);

}