package com.xmyy.member.service;

import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * <p>
 * uc_buyer 买家  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-17
 */
public interface UcBuyerService extends BaseService<UcBuyer> {

    /**
     * 买家注册
     *
     * @param param
     * @return
     */
    Object add(BuyerAddParam param);

    /**
     * 买家找回/重置密码
     *
     * @param param
     * @return
     */
    Object resetpwd(ModifyPwdParam param);

    /**
     * 买家密码登录
     *
     * @param param
     * @return
     */
    Object login(LoginParam param);

    /**
     * 买家动态码登录
     *
     * @param user
     * @return
     */
    Object quicklogin(BuyerQuickLoginParam user);

    /**
     * 买家微信登录
     *
     * @param user
     * @return
     */
    Object wxlogin(WxLoginParam user);

    /**
     * 修改个人信息
     *
     * @param params
     * @return
     */
    Object updateInfo(MemberUpdateParam params);

    /**
     * 修改个人手机号
     *
     * @param buyerId
     * @param newMobile
     * @param smsCode
     * @return
     */
    Object updateMobile(Long buyerId, String newMobile, String smsCode);

    /**
     * 买家/背包客个人信息
     *
     * @param id
     * @param type 1买家；2背包客
     * @return
     */
    MemberInfoResult getInfo(Long id, Integer type);

    /**
     * 根据Ids查询所有数据
     *
     * @param ids
     * @return
     */
    List<UcBuyer> queryBuyerList(List<Long> ids);

    /**
     * 根据uuid获取买家
     *
     * @param uuid
     * @return
     */
    UcBuyer findByUuid(String uuid);

    /**
     * 根据用户类型获取用户信息
     *
     * @param memberType
     * @return
     */
    Object queryBuyerList(Integer memberType);

    /**
     * 推荐背包客列表
     *
     * @param param
     * @return
     */
    Pagination<SellerPageResult> topList(SellerPageParam param);
}