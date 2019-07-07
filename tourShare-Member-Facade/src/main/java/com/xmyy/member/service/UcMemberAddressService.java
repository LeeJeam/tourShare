package com.xmyy.member.service;

import com.xmyy.member.model.UcMemberAddress;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * uc_member_address 我的收货地址  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-17
 */
public interface UcMemberAddressService extends BaseService<UcMemberAddress> {

    List<UcMemberAddress> list(Long currUser);
}