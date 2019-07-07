package top.ibase4j.core.support.mq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.support.email.Email;
import top.ibase4j.core.util.EmailUtil;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * 发送邮件队列
 * 
 * @author ShenHuaJie
 * @version 2016年6月14日 上午11:00:53
 */
public class SendEmailListener implements MessageListener {
	private static Logger logger = LoggerFactory.getLogger(SendEmailListener.class);

	public void onMessage(Message message) {
		try {
			Email email = (Email) ((ObjectMessage) message).getObject();
			logger.info("将发送邮件至：" + email.getSendTo());
			EmailUtil.sendEmail(email);
		} catch (JMSException e) {
			logger.error("邮件发送错误：{}", e);
		}
	}
}
