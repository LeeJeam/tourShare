package com.xmyy.product.listener;

import com.xmyy.product.service.TourService;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 需求完成，更新行程需求统计
 */
@Component("CountDemandMessageListener")
public class CountDemandMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Resource
    private TourService tourService;

    @Override
    @Transactional
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        tourService.updateDemandCountInTour(Long.parseLong(textMessage.getText()));
    }
}