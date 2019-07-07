package com.xmyy.member.dao;

import com.xmyy.member.vo.InviteCodePageParam;
import com.xmyy.member.vo.InviteCodePageResult;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 邀请码DAO
 *
 * @author zlp
 */
public interface InviteCodeDao {

    Integer countInviteCodePage(InviteCodePageParam param);

    List<InviteCodePageResult> listInviteCodePage(InviteCodePageParam param, RowBounds rowBounds);

}
