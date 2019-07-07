package com.xmyy.livevideo.mq;

import com.alibaba.fastjson.JSONObject;
import com.xmyy.livevideo.serivce.LiveVideoRoomService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author james
 */
@Component("userInfoMessageListener")
public class MyUserInfoMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Resource
    private LiveVideoRoomService liveVideoRoomService;

    @Override
    public void onMessage(TextMessage msg, Session session) throws JMSException {
        try {
            msg.acknowledge();
            String content = msg.getText();
            if (!StringUtils.isEmpty(content)) {
                JSONObject object = JSONObject.parseObject(content);
                JSONObject obj = object.getJSONObject("data");
                if (obj != null) {
                    String uuId = obj.get("uuId").toString();
                    String nickName = obj.get("nickName").toString();
                    String image = obj.get("image").toString();
                    liveVideoRoomService.setPortRait(uuId, nickName, image);
                }
            }
            System.out.println(Thread.currentThread().getName() + ": Consumer:我是消费者，" + content);
        } catch (JMSException e) {
            try {
                session.rollback();
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
