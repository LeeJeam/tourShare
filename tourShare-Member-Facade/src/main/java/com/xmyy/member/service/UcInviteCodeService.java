package com.xmyy.member.service;

import com.xmyy.member.model.UcInviteCode;
import com.xmyy.member.vo.InviteCodeGenerateParams;
import com.xmyy.member.vo.InviteCodePageParam;
import com.xmyy.member.vo.InviteCodePageResult;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * <p>
 * uc_invite_code 邀请码  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-05-17
 */
public interface UcInviteCodeService extends BaseService<UcInviteCode> {
    /**
     * 批量生成邀请码
     * @param code
     * @param count
     * @return
     */
   public List<UcInviteCode> exportExcel(UcInviteCode code, Integer count);

    /**
     * 单账号生成
     * @param params
     * @return
     */
   public Object batchSend(InviteCodeGenerateParams params);

    /**
     * 查询邀请码
     * @param param
     * @return
     */
    public Pagination<InviteCodePageResult> list(InviteCodePageParam param);

    /**
     * 失效“过期”的邀请码
     */
    void cancelInviteCodeOverTime();

    /**
     * 根据编号获取有效的邀请码
     */
    public UcInviteCode findByNo(String no);
}