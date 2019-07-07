package com.xmyy.scheduler.order;

import com.xmyy.order.service.DgOrderService;
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
public class CloseOverTimeOrderTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private DgOrderService orderService;

    /**
     * "待支付"订单超过支付时限，更新为“关闭交易”
     */
    @Scheduled(cron = "${CloseOverTimeOrderTask.closeOverTimeOrder}")
    public void closeOutOfPayTimeOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("关闭超时“待支付”订单 start ============>" + sdf.format(new Date()) + "<============");
        try {
            orderService.closeOverTimeOrder();
        } catch (Exception e) {
            logger.info("关闭超时“待支付”订单出错", e);
        }
        logger.debug("关闭超时“待支付”订单 end ============>" + sdf.format(new Date()) + "<============");
    }
}
