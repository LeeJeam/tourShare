package com.xmyy.scheduler.pay;

import com.xmyy.common.EnumConstants;
import com.xmyy.pay.service.DgPayLogService;
import com.xmyy.pay.service.ManageCheckAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author AnCheng
 */
@Service
@EnableScheduling
public class DownCheckFileTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private ManageCheckAccountService manageCheckAccountService;

    /**
     * 下载解析每日对账文件
     */
    @Scheduled(cron = "${DownCheckFileTask.downCheckFile}")
    public void downCheckFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        logger.debug("下载解析每日对账文件 start ============>" + sdf.format(new Date()) + "<============");
        try {
            manageCheckAccountService.getCheckAccountFile(sdf1.format(calendar.getTime()), EnumConstants.FileType.DETAIL.getValue());
            manageCheckAccountService.getCheckAccountFile(sdf1.format(calendar.getTime()), EnumConstants.FileType.SUMMARY.getValue());
        } catch (Exception e) {
            logger.info("下载解析每日对账文件出错", e);
        }
        logger.debug("下载解析每日对账文件 end ============>" + sdf.format(new Date()) + "<============");
    }
}
