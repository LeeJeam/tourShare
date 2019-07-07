package com.xmyy.member.service;

import com.xmyy.member.model.UcMemberLog;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * uc_member_log用户修改日志  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-17
 */
public interface UcMemberLogService extends BaseService<UcMemberLog> {
    public void saveLog(String action, Long memberId, Integer memberType, String content, Long userId);
}