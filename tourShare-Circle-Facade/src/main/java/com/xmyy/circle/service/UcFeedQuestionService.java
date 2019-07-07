package com.xmyy.circle.service;

import com.xmyy.circle.model.UcFeedQuestion;
import com.xmyy.circle.vo.UcFeedQuestionParam;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * 反馈问题答疑  服务类
 * </p>
 *
 * @author yeyu
 * @since 2018-06-12
 */
public interface UcFeedQuestionService extends BaseService<UcFeedQuestion> {
	public Object queryFeedQuestionList(String question);
    public Object addFeedQuestion(UcFeedQuestionParam params, Long uid);
    public Object updateFeedQuestion(UcFeedQuestionParam params, Long uid);
    public Object deleteFeedQuestion(Long Id,Long userId);
}