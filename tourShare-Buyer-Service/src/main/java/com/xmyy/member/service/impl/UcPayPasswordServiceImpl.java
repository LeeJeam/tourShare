package com.xmyy.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.member.mapper.UcPayPasswordMapper;
import com.xmyy.member.model.UcPayPassword;
import com.xmyy.member.service.UcPayPasswordService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 用户支付密码  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = UcPayPasswordService.class)
@CacheConfig(cacheNames = "UcPayPassword")
public class UcPayPasswordServiceImpl extends BaseServiceImpl<UcPayPassword, UcPayPasswordMapper> implements UcPayPasswordService {

}
