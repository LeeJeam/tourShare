package com.xmyy.circle.service;

import com.xmyy.circle.model.DgTags;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 对象标签表  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-06-01
 */
public interface DgTagsService extends BaseService<DgTags> {

    /**
     * 标签列表
     * @return
     */
    List<String> tagList();
}