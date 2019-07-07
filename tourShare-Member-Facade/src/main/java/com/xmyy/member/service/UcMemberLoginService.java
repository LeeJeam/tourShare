package com.xmyy.member.service;

import com.xmyy.member.model.UcMemberLogin;
import top.ibase4j.core.base.BaseService;

/**
 * 登录日志  服务接口
 *
 * @author zlp
 */
public interface UcMemberLoginService extends BaseService<UcMemberLogin> {

    /**
     * 记录登陆日志
     * @param login
     * @return
     */
    UcMemberLogin saveLogin(UcMemberLogin login);

    /**
     * 提出用户
     * @param token
     * @param memberType
     */
    void logout(String token, Integer memberType);

    /**
     * 踢出
     * @param memberId
     * @param memberType
     * @param sessId
     */
    public void kickOut(Long memberId, Integer memberType, String sessId);
}