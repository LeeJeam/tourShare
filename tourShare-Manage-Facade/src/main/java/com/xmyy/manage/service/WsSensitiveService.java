package com.xmyy.manage.service;

import com.xmyy.manage.model.WsSensitive;
import top.ibase4j.core.base.BaseService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 敏感词  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-06-27
 */
public interface WsSensitiveService extends BaseService<WsSensitive> {

    /**
     * 检查敏感词并返回
     * @param content
     * @return
     */
    Set<String> findSensitives(String content);

    /**
     * 导入敏感词；文件格式为：txt，excel；一行一个敏感词；excel第一行为标题不导入
     * @param
     * @return
     */
    Object importWord(String fileName, WsSensitive sensitive, byte[] inputStream);

    /**
     * 剔除敏感字
     * @param words
     */
    void removeSensitive(List<String> words);
}