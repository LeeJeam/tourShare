package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.mapper.UcFeedQuestionMapper;
import com.xmyy.circle.model.UcFeedQuestion;
import com.xmyy.circle.service.UcFeedQuestionService;
import com.xmyy.circle.vo.UcFeedQuestionParam;
import com.xmyy.common.EnumConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.ibase4j.core.Constants;
import top.ibase4j.core.base.BaseServiceImpl;
import org.springframework.cache.annotation.CacheConfig;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;

/**
 * 反馈问题答疑  服务实现类
 *
 * @author yeyu
 */
@Service(interfaceClass = UcFeedQuestionService.class)
@CacheConfig(cacheNames = "UcFeedQuestion")
public class UcFeedQuestionServiceImpl extends BaseServiceImpl<UcFeedQuestion, UcFeedQuestionMapper> implements UcFeedQuestionService {

    @Resource
    private UcFeedQuestionMapper ucFeedQuestionMapper;

    /**
     * 查询反馈答疑列表
     *
     * @param question
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = Constants.CACHE_NAMESPACE + "queryFeedQuestionList")
    @Override
    public Object queryFeedQuestionList(String question) {
        EntityWrapper<UcFeedQuestion> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        if (!StringUtils.isEmpty(question)) {
            ew.andNew();
            ew.like("question", question);
            ew.or().like("answer", question);
        }
        ew.orderAsc(Arrays.asList(new String[]{"sort"}));
        return ucFeedQuestionMapper.selectList(ew);
    }

    /**
     * 新增反馈答疑
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
    public Object addFeedQuestion(UcFeedQuestionParam params, Long uid) {
        UcFeedQuestion ucFeedQuestion = new UcFeedQuestion();
        ucFeedQuestion.setCreateBy(uid);
        ucFeedQuestion.setCreateTime(new Date());
        ucFeedQuestion.setQuestion(params.getQuestion());
        ucFeedQuestion.setAnswer(params.getAnswer());
        ucFeedQuestion.setSort(params.getSort());
        return ucFeedQuestionMapper.insert(ucFeedQuestion);
    }

    /**
     * 更新反馈答疑
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
    public Object updateFeedQuestion(UcFeedQuestionParam params, Long uid) {
        UcFeedQuestion ucFeedQuestion = new UcFeedQuestion();
        ucFeedQuestion.setId(params.getId());
        ucFeedQuestion.setUpdateBy(uid);
        ucFeedQuestion.setCreateTime(new Date());
        ucFeedQuestion.setUpdateTime(new Date());
        ucFeedQuestion.setQuestion(params.getQuestion());
        ucFeedQuestion.setAnswer(params.getAnswer());
        ucFeedQuestion.setSort(params.getSort());
        return ucFeedQuestionMapper.updateById(ucFeedQuestion);
    }

    /**
     * 删除反馈的问题
     *
     * @param Id
     * @return
     */
    @Override
    public Object deleteFeedQuestion(Long Id, Long userId) {
        UcFeedQuestion ucFeedQuestion = new UcFeedQuestion();
        ucFeedQuestion.setId(Id);
        ucFeedQuestion.setUpdateBy(userId);
        ucFeedQuestion.setUpdateTime(new Date());
        ucFeedQuestion.setEnable(EnumConstants.YesOrNo.NO.getValue());
        return ucFeedQuestionMapper.updateById(ucFeedQuestion);
    }
}
