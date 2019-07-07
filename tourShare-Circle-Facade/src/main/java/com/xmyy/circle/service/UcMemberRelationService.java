package com.xmyy.circle.service;

import com.xmyy.circle.model.UcMemberRelation;
import com.xmyy.circle.vo.UcMemberRelationPageParam;
import com.xmyy.circle.vo.UcMemberRelationParam;
import top.ibase4j.core.base.BaseService;

/**
 * 关注  服务接口
 *
 * @author AnCheng
 */
public interface UcMemberRelationService extends BaseService<UcMemberRelation> {

    /**
     * 关注买手
     * @param buyerId
     * @param params
     * @return
     */
    Object addMember(Long buyerId, UcMemberRelationParam params);

    /**
     * 取消关注
     * @param toMemberRelationId
     * @param memberId
     * @param toMemberType
     * @return
     */
    Object delMember(Long toMemberRelationId,Long memberId,Integer toMemberType);

    /**
     * 查询关注列表
     * @param param
     * @param memberId 买家Id
     * @return
     */
    Object getFocusList(UcMemberRelationPageParam param, Long memberId);

    /**
     * 获取关注数
     * @param merberId
     * @return
     */
    Integer countFocusByMerberId(Long merberId);

    /**
     * 获取被关注数，粉丝数
     * @param toMerberId
     * @return
     */
    Integer countFansByToMerberId(Long toMerberId);

    /**
     * 关注所有买手集合
     * @param params
     * @return
     */
    Object getSellerFocusList(UcMemberRelationPageParam params);

    /**
     * 获取关注的买家列表
     * @param params
     * @return
     */
    Object getBuyerFocusList(UcMemberRelationPageParam params);

    /**
     * merberId 是否关注 toMemberId
     * @param merberId
     * @param toMemberId
     * @return
     */
    Boolean isFollowedByMember(Long merberId, Long toMemberId);

    /**
     * 查询关注列表，新接口
     * @param buyerId
     * @param current
     * @param size
     * @return
     */
    Object getFocusList2(Long buyerId, Integer current, Integer size);

    /**
     * 查询指定过需求的买手列表
     * @param buyerId
     * @param current
     * @param size
     * @return
     */
    Object listSelected(Long buyerId, Integer current, Integer size);
}