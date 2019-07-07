package com.xmyy.scheduler.demand;

import com.xmyy.demand.service.DgDemandOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author AnCheng
 */
@Service
@EnableScheduling
public class CloseDemandTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private DgDemandOrderService dgDemandOrderService;

    /**
     * 超过收货时间，且没有形成订单的需求，更新为“已失效”
     */
    @Scheduled(cron = "${CloseDemandTask.closeDeliveryTimeOutDemand}")
    public void deliveryTimeOutCancel() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("更新过期需求为“已失效” start ============>" + sdf.format(new Date()) + "<============");
        try {
            dgDemandOrderService.closeDeliveryTimeOutDemand();
        } catch (Exception e) {
            logger.info("更新过期需求为“已失效”出错", e);
        }
        logger.debug("更新过期需求为“已失效” end ============>" + sdf.format(new Date()) + "<============");
    }

}
