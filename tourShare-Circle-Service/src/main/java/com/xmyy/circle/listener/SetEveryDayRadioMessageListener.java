package com.xmyy.circle.listener;

import com.xmyy.circle.service.DgEverydayRadioService;
import com.xmyy.circle.vo.EveryDayRadioParam;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * 发送今日提醒
 */
@Component("setEveryDayRadio")
public class SetEveryDayRadioMessageListener implements SessionAwareMessageListener<ObjectMessage> {

    @Resource
    private DgEverydayRadioService everydayRadioService;

    @Override
    public void onMessage(ObjectMessage textMessage, Session session) throws JMSException {
        EveryDayRadioParam everyDayRadioParam = (EveryDayRadioParam) textMessage.getObject();
        everydayRadioService.addEveryDayRadio(everyDayRadioParam);
    }

}
