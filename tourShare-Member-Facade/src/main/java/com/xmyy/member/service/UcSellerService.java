package com.xmyy.member.service;

import com.xmyy.common.vo.MemberInfo;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 买手  服务接口
 *
 */
public interface UcSellerService extends BaseService<UcSeller> {

    /**
     * 买手注册
     * @param param
     * @return
     */
    public Object add(SellerAddParam param);

    /**
     * 买手找回/重置密码
     * @param param
     * @return
     */
    public Object resetpwd(ModifyPwdParam param);

    /**
     * 买手密码登录
     * @param user
     * @return
     */
    public Object login(LoginParam user);

    /**
     * 修改个人信息
     * @param params
     * @return
     */
    Object updateInfo(MemberUpdateParam params);

    /**
     * 修改个人手机号
     * @param currUser
     * @param newMobile
     * @param smsCode
     * @return
     */
    Object updateMobile(Long currUser, String newMobile, String smsCode);

    /**
     * 按买手ID或昵称搜索买手列表
     * @param param
     * @return
     */
    Object querySellerListByName(SellerPageParam param);

    /**
     * 推荐买手列表
     * @param param
     * @return
     */
    Pagination<SellerPageResult> topList(SellerPageParam param);

    /**
     * 根据ids查询买手列表
     * @param ids
     * @return
     */
    List<UcSeller> querySellerList(List<Long> ids);

    /**
     * 获取用户信息
     * @param memberId
     * @param memberType
     * @return
     */
    MemberInfo getMemberInfo(Long memberId, Integer memberType);


    /**
     * 获取多个用户信息
     * @param memberIds
     * @param memberType
     * @return key 是memberId,value是MemberInfo
     */
    Map<String,MemberInfo> getManyMemberInfos(List<Long> memberIds, Integer memberType);


    /**
     * 批量查询买手的信息
     */
    List<MemberInfo> getManySellerInfos(List<Long> sellerIds);

    /**
     * 获取所有买手信息
     * @return
     */
    Object getSellerList();

    /**
     * 后台获取用户信息
     * @param currUser
     * @return
     */
    MemberInfoResult getInfo(Long currUser);

    /**
     * 用户是否完成个人信息设置
     * @param id 用户ID
     * @param memberType 用户类型
     * @return 0未完全设置，1已全部设置
     */
    Integer isFinishSetInfo(Long id, Integer memberType);
}