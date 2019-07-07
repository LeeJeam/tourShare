package com.xmyy.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.member.mapper.UcMemberLoginMapper;
import com.xmyy.member.model.UcMemberLogin;
import com.xmyy.member.service.UcMemberLoginService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.KickOutTokenUtil;
import top.ibase4j.core.util.TokenUtil;

import java.util.Date;
import java.util.List;

/**
 * 登录日志  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcMemberLoginService.class)
@CacheConfig(cacheNames = "UcMemberLogin")
public class UcMemberLoginServiceImpl extends BaseServiceImpl<UcMemberLogin, UcMemberLoginMapper> implements UcMemberLoginService {

    @Override
    @Transactional
    public UcMemberLogin saveLogin(UcMemberLogin login) {
        login.setIsLogout(EnumConstants.YesOrNo.NO.getValue());
        Date now = new Date();
        login.setLastActiveTime(now);
        login.setLoginTime(now);
        login.setState(EnumConstants.State.NORMAL.value());

        super.update(login);
        return login;
    }

    @Override
    @Transactional
    public void logout(String token, Integer memberType) {
        EntityWrapper<UcMemberLogin> ew = new EntityWrapper<>();
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("member_type", EnumConstants.MemberType.valueOf(memberType).getValue());
        ew.eq("token", token);

        UcMemberLogin login = new UcMemberLogin();
        login.setState(EnumConstants.State.DELETED.value());
        login.setEnable(EnumConstants.YesOrNo.NO.getValue());
        login.setIsLogout(EnumConstants.YesOrNo.YES.getValue());

        //List<UcMemberLogin> ucMemberLogins = memberLoginService.queryList(login);
        mapper.update(login, ew);
    }

    @Override
    @Transactional
    public void kickOut(Long memberId, Integer memberType, String sessId) {
        EntityWrapper<UcMemberLogin> ew = new EntityWrapper<>();
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("member_type", EnumConstants.MemberType.valueOf(memberType).getValue());
        ew.eq("member_id", memberId);

        List<UcMemberLogin> ucMemberLogins = mapper.selectList(ew);
        if (CollectionUtils.isNotEmpty(ucMemberLogins)) {
            ucMemberLogins.parallelStream().forEach(login -> {

                if (!login.getToken().equals(sessId)) {
                    TokenUtil.delToken(login.getToken()); //删除redis中有效token

                    KickOutTokenUtil.setKickOutTokenInfo(login.getToken(), login.getId() + ""); //redis记录踢出token

                    login.setState(EnumConstants.State.DELETED.value());
                    login.setEnable(EnumConstants.YesOrNo.NO.getValue());
                    super.update(login);
                }
            });
        }
    }
}
