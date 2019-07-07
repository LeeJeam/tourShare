package com.xmyy.scheduler.circle;

import com.xmyy.circle.service.UcDynamicCircleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zlp
 */
@Service
@EnableScheduling
public class CircleDynamicTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private UcDynamicCircleService circleService;

    /**
     * 取消“过期”的动态置顶
     */
    @Scheduled(cron = "${CircleDynamicTask.cancelTopOverTime}")
    public void cancelTopOverTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("取消“过期”动态置顶 start ============>" + sdf.format(new Date()) + "<============");
        try {
            circleService.cancelTopOverTime();
        } catch (Exception e) {
            logger.info("取消“过期”动态置顶出错：", e);
        }
        logger.debug("取消“过期”动态置顶 end ============>" + sdf.format(new Date()) + "<============");
    }
}
