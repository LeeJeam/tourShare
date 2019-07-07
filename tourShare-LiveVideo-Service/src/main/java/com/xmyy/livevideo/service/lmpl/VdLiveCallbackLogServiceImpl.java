package com.xmyy.livevideo.service.lmpl;

import com.xmyy.livevideo.mapper.VdLiveCallbackLogMapper;
import com.xmyy.livevideo.model.VdLiveCallbackLog;
import com.xmyy.livevideo.serivce.VdLiveCallbackLogService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

/**
 * 直播回调记录  服务实现类
 *
 * @author simon
 */
@com.alibaba.dubbo.config.annotation.Service(interfaceClass = VdLiveCallbackLogService.class)
@CacheConfig(cacheNames = "VdLiveCallbackLog")
public class VdLiveCallbackLogServiceImpl extends BaseServiceImpl<VdLiveCallbackLog, VdLiveCallbackLogMapper> implements VdLiveCallbackLogService {

}
