package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.manage.dao.AdminSessionDao;
import com.xmyy.manage.mapper.AdminSessionMapper;
import com.xmyy.manage.model.AdminSession;
import com.xmyy.manage.service.AdminSessionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.Constants;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后台登录日志  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = AdminSessionService.class)
@CacheConfig(cacheNames = "AdminSession")
public class AdminSessionServiceImpl extends BaseServiceImpl<AdminSession, AdminSessionMapper> implements AdminSessionService {

    @Resource
    private AdminSessionDao adminSessionDao;

    @CachePut
    @Transactional
    public AdminSession update(AdminSession record) {
        if (record != null && record.getId() == null) {
            record.setUpdateTime(new Date());
            Long id = adminSessionDao.queryBySessionId(record.getSessionId());
            if (id != null) {
                mapper.updateById(record);
            } else {
                record.setCreateTime(new Date());
                mapper.insert(record);
            }
        } else {
            mapper.updateById(record);
        }
        return record;
    }

    // 系统触发,由系统自动管理缓存
    public void deleteBySessionId(final AdminSession adminSession) {
        if (adminSession != null) {
            adminSessionDao.deleteBySessionId(adminSession.getSessionId());
        }
    }

    public List<String> querySessionIdByAccount(AdminSession sysSession) {
        if (sysSession != null) {
            return adminSessionDao.querySessionIdByAccount(sysSession.getAccount());
        }
        return null;
    }

    //
    public void cleanExpiredSessions() {
        Map<String, Object> columnMap = InstanceUtil.newHashMap();
        List<AdminSession> sessions = queryList(columnMap);
        for (AdminSession adminSession : sessions) {
            if (adminSession != null) {
                logger.info("检查SESSION : {}", adminSession.getSessionId());
                if (!CacheUtil.getCache().exists(Constants.REDIS_SHIRO_SESSION + adminSession.getSessionId())) {
                    logger.info("移除SESSION : {}", adminSession.getSessionId());
                    delete(adminSession.getId());
                }
            }
        }
    }
}
