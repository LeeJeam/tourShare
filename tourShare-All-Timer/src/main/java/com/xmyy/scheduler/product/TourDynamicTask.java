package com.xmyy.scheduler.product;

import com.xmyy.product.service.ManageTourService;
import com.xmyy.product.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangzejun
 */
@Service
@EnableScheduling
public class TourDynamicTask {
    private final Logger logger = LogManager.getLogger();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private TourService tourService;
    @Resource
    private ManageTourService manageTourService;

    /**
     * 取消“过期”的行程置顶
     */
    @Scheduled(cron = "${TourDynamicTask.cancelTopOverTime}")
    public void cancelTopOverTime() {
        logger.debug("取消“过期”的行程置顶 start ============>" + sdf.format(new Date()) + "<============");
        try {
            manageTourService.cancelTopOverTime();
        } catch (Exception e) {
            logger.info("取消“过期”的行程置顶执行出错", e);
        }
        logger.debug("结束取消“过期”的行程置顶处理程序 end ============>" + sdf.format(new Date()) + "<============");
    }

    /**
     * 结束到期的行程
     */
    @Scheduled(cron = "${TourDynamicTask.finishTour}")
    public void finishTour() {
        logger.debug("结束到期的行程 start ============>" + sdf.format(new Date()) + "<============");
        try {
            tourService.finishTour();
        } catch (Exception e) {
            logger.info("结束到期的行程出错", e);
        }
        logger.debug("结束到期的行程 end ============>" + sdf.format(new Date()) + "<============");
    }

    /**
     * 未提交登机牌的行程失效
     */
    @Scheduled(cron = "${TourDynamicTask.abolishTour}")
    public void abolishTour() {
        logger.debug("未提交登机牌的行程失效 start ============>" + sdf.format(new Date()) + "<============");
        try {
            tourService.abolishTour();
        } catch (Exception e) {
            logger.info("未提交登机牌的行程失效出错", e);
        }
        logger.debug("未提交登机牌的行程失效 end ============>" + sdf.format(new Date()) + "<============");
    }

    /**
     * 行程签到消息推送
     */
    @Scheduled(cron = "${TourDynamicTask.recommendTourSignMessages}")
    public void recommendTourSignMessages() {
        logger.debug("行程签到消息推送 start ============>" + sdf.format(new Date()) + "<============");
        try {
            tourService.pushTodayToursInfo();
        } catch (Exception e) {
            logger.info("行程签到消息推送出错", e);
        }
        logger.debug("行程签到消息推送 end ============>" + sdf.format(new Date()) + "<============");
    }

}