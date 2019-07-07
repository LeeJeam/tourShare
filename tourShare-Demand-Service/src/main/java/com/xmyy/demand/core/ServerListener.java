package com.xmyy.demand.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
* @author wangmd
* @Description 监听器
* @Date 10:49 2018/6/22
**/
public class ServerListener implements ServletContextListener {
	protected final Logger logger = LogManager.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent contextEvent) {
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		logger.info("=================================");
		logger.info("系统[Demand-Service]启动完成!!!", contextEvent.getServletContext().getServletContextName());
		logger.info("=================================");
	}
}