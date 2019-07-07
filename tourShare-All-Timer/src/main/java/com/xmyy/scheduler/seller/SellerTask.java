package com.xmyy.scheduler.seller;

import com.xmyy.member.service.UcInviteCodeService;
import com.xmyy.member.service.UcSellerManageService;
import com.xmyy.search.service.SellerImportService;
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
public class SellerTask {
    private final Logger logger = LogManager.getLogger();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private UcSellerManageService sellerManageService;
    @Resource
    private UcInviteCodeService codeService;
    @Resource
    private SellerImportService sellerService;

    /**
     * 取消“过期”的买手置顶
     */
    @Scheduled(cron = "${SellerTask.cancelTopOverTime}")
    public void cancelTopOverTime() {
        logger.debug("取消“过期”的买手置顶 start ============>" + sdf.format(new Date()) + "<============");
        try {
            sellerManageService.cancelTopOverTime();
        } catch (Exception e) {
            logger.info("取消“过期”的买手置顶出错", e);
        }
        logger.debug("取消“过期”的买手置顶 end ============>" + sdf.format(new Date()) + "<============");
    }

    /**
     * “过期”的邀请码更新为失效
     */
    @Scheduled(cron = "${SellerTask.cancelInviteCodeOverTime}")
    public void cancelInviteCodeOverTime() {
        logger.debug("“过期”的邀请码更新为失效 start ============>" + sdf.format(new Date()) + "<============");
        try {
            codeService.cancelInviteCodeOverTime();
        } catch (Exception e) {
            logger.info("“过期”的邀请码更新为失效出错", e);
        }
        logger.debug("“过期”的邀请码更新为失效 end ============>" + sdf.format(new Date()) + "<============");
    }

    /**
     * Elesticsearch增量更新买手数据
     */
    @Scheduled(cron = "${SellerTask.importIncrementDatas}")
    public void importIncrementDatas() {
        logger.debug("Elesticsearch增量更新买手数据 start ============>" + sdf.format(new Date()) + "<============");
        try {
            int importCount = sellerService.incrementImportSellersToIndex(-1);
            logger.debug("共导入[" + importCount + "]条数据！");

        } catch (Exception e) {
            logger.info("Elesticsearch增量更新买手数据出错", e);
        }
        logger.debug("Elesticsearch增量更新买手数据 end ============>" + sdf.format(new Date()) + "<============");
    }
}
