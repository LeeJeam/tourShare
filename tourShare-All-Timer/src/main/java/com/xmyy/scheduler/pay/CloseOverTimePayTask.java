package com.xmyy.scheduler.pay;

import com.xmyy.pay.service.DgPayLogService;
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
public class CloseOverTimePayTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private DgPayLogService payLogService;

    /**
     * 超过支付时限的“未支付”交易记录，更新为“关闭”
     */
    @Scheduled(cron = "${CloseOverTimePayTask.closeOverTimePay}")
    public void CloseOverTimePay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("关闭超时“未支付”的支付记录 start ============>" + sdf.format(new Date()) + "<============");
        try {
            payLogService.closeOverTimePay();
        } catch (Exception e) {
            logger.info("关闭超时“未支付”的支付记录出错", e);
        }
        logger.debug("关闭超时“未支付”的支付记录 end ============>" + sdf.format(new Date()) + "<============");
    }
}
