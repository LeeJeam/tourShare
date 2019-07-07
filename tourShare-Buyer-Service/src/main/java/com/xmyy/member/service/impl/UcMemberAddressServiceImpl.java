package com.xmyy.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.common.EnumConstants;
import com.xmyy.member.mapper.UcMemberAddressMapper;
import com.xmyy.member.model.UcMemberAddress;
import com.xmyy.member.service.UcMemberAddressService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import java.util.List;

/**
 * 我的收货地址  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcMemberAddressService.class)
@CacheConfig(cacheNames = "UcMemberAddress")
public class UcMemberAddressServiceImpl extends BaseServiceImpl<UcMemberAddress, UcMemberAddressMapper> implements UcMemberAddressService {

    @Override
    @Transactional(readOnly = true)
    public List<UcMemberAddress> list(Long currUser) {

        //封装查询参数
        EntityWrapper<UcMemberAddress> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("buyer_id_", currUser);
        ew.orderDesc(Lists.newArrayList("is_default", "create_time"));

        return this.mapper.selectList(ew);
    }
}
