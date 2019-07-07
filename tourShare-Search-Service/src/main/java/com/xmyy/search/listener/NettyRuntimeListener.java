package com.xmyy.search.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NettyRuntimeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
