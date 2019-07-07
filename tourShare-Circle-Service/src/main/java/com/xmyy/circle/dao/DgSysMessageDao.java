package com.xmyy.circle.dao;

import com.xmyy.circle.model.DgSysMessageRead;

import java.util.List;

/**
 * 系统消息Dao
 *
 * @author yeyu
 * @since 2018-06-22
 */
public interface DgSysMessageDao {

    /**
     * 查询用户已读系统消息的消息ID列表
     * @param params
     * @return
     */
    List<Long> queryReadMsgList(DgSysMessageRead params);

}
