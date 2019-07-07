package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.mapper.UcFeedbackMapper;
import com.xmyy.circle.model.UcFeedback;
import com.xmyy.circle.service.UcFeedbackService;
import com.xmyy.circle.vo.UcFeedbackPageParam;
import com.xmyy.circle.vo.UcFeedbackParam;
import com.xmyy.common.EnumConstants;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.Constants;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 意见反馈  服务实现类
 *
 * @author yeyu
 */
@Service(interfaceClass = UcFeedbackService.class)
@CacheConfig(cacheNames = "UcFeedback")
public class UcFeedbackServiceImpl extends BaseServiceImpl<UcFeedback, UcFeedbackMapper> implements UcFeedbackService {

    @Resource
    private UcFeedbackMapper ucFeedbackMapper;

    /**
     * 查询反馈内容集合
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = Constants.CACHE_NAMESPACE + "queryAll")
    @Override
    public Object queryAll(UcFeedbackPageParam param) {
        EntityWrapper<UcFeedback> ew = new EntityWrapper();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
        List<UcFeedback> resultList = ucFeedbackMapper.selectPage(rb, ew);
        //以分页方式返回
        Pagination<UcFeedback> page = new Pagination<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        //获取关注总条数
        page.setTotal(ucFeedbackMapper.selectCount(ew));
        page.setRecords(resultList);
        return page;
    }

    /**
     * 新增反馈内容
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Object addFeedBack(UcFeedbackParam param, Long uid) {
        UcFeedback ucFeedback = new UcFeedback();
        ucFeedback.setCreateBy(uid);
        ucFeedback.setCreateTime(new Date());
        ucFeedback.setQuestionType(1);
        ucFeedback.setContent(param.getContent());
        return ucFeedbackMapper.insert(ucFeedback);
    }

    @Override
    @Transactional
    public Object updateFeedBack(UcFeedbackParam param, Long uid) {
        UcFeedback ucFeedback = new UcFeedback();
        ucFeedback.setId(param.getId());
        ucFeedback.setUpdateBy(uid);
        ucFeedback.setCreateTime(new Date());
        ucFeedback.setUpdateTime(new Date());
        ucFeedback.setQuestionType(1);
        ucFeedback.setContent(param.getContent());
        return ucFeedbackMapper.updateById(ucFeedback);
    }

    /**
     * 删除反馈内容
     *
     * @param Id
     * @return
     */
    @Override
    @Transactional
    public Object delFeedBack(Long Id, Long userId) {
        UcFeedback ucFeedback = new UcFeedback();
        ucFeedback.setEnable(EnumConstants.YesOrNo.NO.getValue());
        ucFeedback.setUpdateTime(new Date());
        ucFeedback.setUpdateBy(userId);
        ucFeedback.setId(Id);
        return ucFeedbackMapper.updateById(ucFeedback);
    }
}
