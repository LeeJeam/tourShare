package com.xmyy.circle.service;

import com.xmyy.circle.model.DgSysMessage;
import com.xmyy.circle.vo.DgSysMessageNotReadParam;
import com.xmyy.circle.vo.DgSysMessagePageParam;
import com.xmyy.circle.vo.DgSysMessageParam;
import com.xmyy.circle.vo.DgSysMessageReadParam;
import top.ibase4j.core.base.BaseService;

/**
 * 系统消息  服务接口
 */
public interface DgSysMessageService extends BaseService<DgSysMessage> {

	/**
	 * 添加系统消息
	 * @param param
	 * @param memberId
	 * @return
	 */
	Object addSysMessage(DgSysMessageParam param, Long memberId);

	/**
	 * 查询系统消息列表
	 * @param param
	 * @return
	 */
	Object list(Long memberId, DgSysMessagePageParam param);

	/**
	 * 获取未阅读的系统消息总数
	 * @param params
	 * @return
	 */
	Object countNotReadMessage(Long memberId, DgSysMessageNotReadParam params);

	/**
	 * 根据消息ID获取系统消息详情
	 * @param
	 * @return
	 */
	Object detail(Long memberId, DgSysMessageReadParam param);

}