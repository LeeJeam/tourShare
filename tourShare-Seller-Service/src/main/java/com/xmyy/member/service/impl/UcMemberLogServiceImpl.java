package com.xmyy.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.Constants;
import com.xmyy.member.mapper.UcMemberLogMapper;
import com.xmyy.member.model.UcMemberLog;
import com.xmyy.member.service.UcMemberLogService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import java.util.Date;

/**
 * 用户修改个人信息日志 服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcMemberLogService.class)
@CacheConfig(cacheNames = "UcMemberLog")
public class UcMemberLogServiceImpl extends BaseServiceImpl<UcMemberLog, UcMemberLogMapper> implements UcMemberLogService {

    /**
     * @param action     记录名称
     * @param memberId   买手/买家
     * @param memberType 用户类型(1,买手，2买家)
     * @param content    记录详情
     * @param userId     后台操作员
     */
    @Transactional
    public void saveLog(String action, Long memberId, Integer memberType, String content, Long userId) {

        UcMemberLog e = new UcMemberLog();
        e.setAction(action);
        e.setContent(content);
        e.setCreateTime(new Date());
        e.setUpdateTime(new Date());
        e.setMemberId(memberId);
        e.setMemberType(memberType);

        if (userId == null) {
            e.setCreateBy(Constants.SYS_USER_ID);
        } else {
            e.setCreateBy(userId);
        }

        super.update(e);
    }
}
