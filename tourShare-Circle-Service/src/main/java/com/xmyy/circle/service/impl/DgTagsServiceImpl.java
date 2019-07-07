package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.mapper.DgTagsMapper;
import com.xmyy.circle.model.DgTags;
import com.xmyy.circle.service.DgTagsService;
import com.xmyy.common.EnumConstants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标签  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = DgTagsService.class)
@CacheConfig(cacheNames = "DgTags")
public class DgTagsServiceImpl extends BaseServiceImpl<DgTags, DgTagsMapper> implements DgTagsService {

    @Override
    @Transactional(readOnly = true)
    public List<String> tagList() {
        EntityWrapper<DgTags> ew = new EntityWrapper();
        ew.and("is_recommend", EnumConstants.YesOrNo.YES.getValue());
        ew.and("state", EnumConstants.State.NORMAL.value());
        ew.and("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("sort"));

        List<DgTags> lists = mapper.selectList(ew);
        List<String> tags = lists.stream().map(o -> o.getTagName()).collect(Collectors.toCollection(ArrayList::new));

        return tags;
    }
}
