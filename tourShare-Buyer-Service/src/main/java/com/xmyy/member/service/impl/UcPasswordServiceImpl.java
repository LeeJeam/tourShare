package com.xmyy.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.member.mapper.UcPasswordMapper;
import com.xmyy.member.model.UcPassword;
import com.xmyy.member.service.UcPasswordService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 用户登录密码  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcPasswordService.class)
@CacheConfig(cacheNames = "UcPassword")
public class UcPasswordServiceImpl extends BaseServiceImpl<UcPassword, UcPasswordMapper> implements UcPasswordService {

}
