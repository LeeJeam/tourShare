package com.xmyy.circle.service;

import com.xmyy.circle.model.UcFeedback;
import com.xmyy.circle.vo.UcFeedbackPageParam;
import com.xmyy.circle.vo.UcFeedbackParam;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * 意见反馈  服务类
 * </p>
 *
 * @author yeyu
 * @since 2018-06-12
 */
public interface UcFeedbackService extends BaseService<UcFeedback> {
	public Object queryAll(UcFeedbackPageParam param);
    public Object addFeedBack(UcFeedbackParam param, Long uid);
    public Object updateFeedBack(UcFeedbackParam param, Long uid);
    public Object delFeedBack(Long Id,Long userId);
}