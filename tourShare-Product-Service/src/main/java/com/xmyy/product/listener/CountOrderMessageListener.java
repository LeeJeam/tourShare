package com.xmyy.product.listener;

import com.xmyy.product.service.TourService;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.math.BigDecimal;

/**
 * 订单完成，更新行程订单统计
 */
@Component("CountOrderMessageListener")
public class CountOrderMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Resource
    private TourService tourService;

    @Override
    @Transactional
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        String[] strs = textMessage.getText().split(",");
        Long tourId = Long.parseLong(strs[0]);
        BigDecimal payMoney = new BigDecimal(strs[1]);
        tourService.updateOrderCountInTour(tourId, payMoney);
    }
}