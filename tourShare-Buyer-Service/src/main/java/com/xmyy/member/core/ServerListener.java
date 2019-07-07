package com.xmyy.member.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerListener implements ServletContextListener {
	private final Logger logger = LogManager.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent contextEvent) {
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		logger.info("=================================");
		logger.info("系统[Buyer-Service]启动完成!!!", contextEvent.getServletContext().getServletContextName());
		logger.info("=================================");
	}
}