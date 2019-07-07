package com.xmyy.scheduler.seller;

import com.xmyy.search.service.PackerImportService;
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
public class BuyerTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private PackerImportService packerImportService;

    /**
     * Elesticsearch增量更新背包客数据
     */
    @Scheduled(cron = "${PackerTask.importIncrementDatas}")
    public void importIncrementDatas() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("Elesticsearch增量更新背包客数据 start ============>" + sdf.format(new Date()) + "<============");
        try {
            int importCount = packerImportService.incrementImportPackersToIndex(-1);
            logger.debug("共导入[" + importCount + "]条数据！");
        } catch (Exception e) {
            logger.info("Elesticsearch增量更新背包客数据出错", e);
        }
        logger.debug("Elesticsearch增量更新背包客数据 end ============>" + sdf.format(new Date()) + "<============");
    }
}
