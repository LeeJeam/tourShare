package com.xmyy.circle.service;

import com.xmyy.circle.model.DgEverydayRadio;
import com.xmyy.circle.vo.EveryDayRadioParam;
import top.ibase4j.core.base.BaseService;

/**
 * 今日提醒  服务类接口
 *
 * @author yeyu
 * @since 2018-07-03
 */
public interface DgEverydayRadioService extends BaseService<DgEverydayRadio> {

	/**
	 * 添加今日提醒
	 * @param everyDayRadioParam
	 * @return
	 */
	public Object addEveryDayRadio(EveryDayRadioParam everyDayRadioParam);

	/**
	 * 获取所有今日提醒消息
	 * @param memberId
	 * @param memberType
	 * @return
	 */
	public Object queryEveryDayRadioList(Long memberId, Integer memberType);
}