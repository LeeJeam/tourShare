package com.xmyy.scheduler.product;

import com.xmyy.product.model.DgCurrencyExchange;
import com.xmyy.product.service.DgCurrencyExchangeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Simon
 */
@Service
@EnableScheduling
public class CurrencyExchangeTask {
    private final Logger logger = LogManager.getLogger();

    @Resource
    private DgCurrencyExchangeService dgCurrencyExchangeService;

    /**
     * 汇率更新
     */
    @Scheduled(cron = "${ProductTask.getCurrencyExchange}")
    public void updateExchange() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("汇率更新 start ============>" + sdf.format(new Date()) + "<============");
        try {
            List<DgCurrencyExchange> exchangeList = dgCurrencyExchangeService.getExchangeList();
            for (DgCurrencyExchange currencyExchange : exchangeList) {
                String foreignCurrencyCode = currencyExchange.getForeignCurrencyCode();
                Double exchangeResult = dgCurrencyExchangeService.getExchangeResult("CNY", foreignCurrencyCode);
                if (exchangeResult != null) {
                    currencyExchange.setRate(exchangeResult);
                    dgCurrencyExchangeService.update(currencyExchange);
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.info("汇率更新出错", e);
        }
        logger.debug("汇率更新 end ============>" + sdf.format(new Date()) + "<============");
    }

}
