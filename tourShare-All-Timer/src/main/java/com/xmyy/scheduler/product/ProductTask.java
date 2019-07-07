package com.xmyy.scheduler.product;

import com.xmyy.product.service.PtProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Simon
 */
@Service
@EnableScheduling
public class ProductTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private PtProductService ptProductService;

    /**
     * 取消“过期”的商品置顶
     */
    @Scheduled(cron = "${ProductTask.cancelTopOverTime}")
    public void cancelTopOverTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("取消“过期”的商品置顶 start ============>" + sdf.format(new Date()) + "<============");
        try {
            ptProductService.cancelTopOverTime();
        } catch (Exception e) {
            logger.info("取消“过期”的商品置顶出错", e);
        }
        logger.debug("取消“过期”的商品置顶 end ============>" + sdf.format(new Date()) + "<============");
    }

}
