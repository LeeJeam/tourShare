package com.xmyy.search.listener;

import com.xmyy.search.service.ProductImportService;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * ES同步商品信息
 */
@Component("productMessageListener")
public class ProductMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Resource
    private ProductImportService productImportService;

    @Override
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        productImportService.importByProductId(Long.parseLong(textMessage.getText()));
    }
}