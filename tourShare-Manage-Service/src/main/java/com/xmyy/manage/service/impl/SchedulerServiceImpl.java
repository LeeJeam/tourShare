package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.manage.service.SchedulerService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.support.scheduler.TaskScheduled;
import top.ibase4j.model.TaskFireLog;

import java.util.List;
import java.util.Map;

/**
 * 定时任务 服务实现类
 *
 * @author
 */
@Service(interfaceClass = SchedulerService.class)
@CacheConfig(cacheNames = "Scheduler")
public class SchedulerServiceImpl implements SchedulerService {

    @Override
    public List<TaskScheduled> getAllTaskDetail() {
        return null;
    }

    @Override
    public void execTask(TaskScheduled taskScheduler) {

    }

    @Override
    public void openTask(TaskScheduled taskScheduler) {

    }

    @Override
    public void closeTask(TaskScheduled taskScheduler) {

    }

    @Override
    public void delTask(TaskScheduled taskScheduler) {

    }

    @Override
    public TaskFireLog updateLog(TaskFireLog record) {
        return null;
    }

    @Override
    public TaskFireLog getFireLogById(Long id) {
        return null;
    }

    @Override
    public Object queryLog(Map<String, Object> params) {
        return null;
    }

    @Override
    public void updateTask(TaskScheduled taskScheduled) {

    }
}
