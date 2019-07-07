package com.xmyy.common.util;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TimeCounter {
	public static final String DATE_TIME_MILLS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static TimeCounter create(Object target){
		TimeCounter t = new TimeCounter(target);
		return t;
	}
	public static TimeCounter start(Object target){
		TimeCounter t = new TimeCounter(target);
		t.start();
		return t;
	}

	final private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Object target;
	private Date start;
	private Date stop;
	private long costTime;
	private StringBuilder message = new StringBuilder();

	public TimeCounter(Object target) {
		this.target = target;
	}
	
	public Date start() {
		long start = System.currentTimeMillis();
		this.start = new Date(start);
		return this.start;
	}

	public TimeCounter startIt() {
		start();
		return this;
	}
	

	
	public Date restart() {
		return restart(target);
	}
	
	public Date restart(Object target) {
		logger.info("restart time counter...");
		this.message = new StringBuilder();
		this.target = target;
		return start();
	}

	public Date stop() {
		long stopMills = System.currentTimeMillis();
		this.stop = new Date(stopMills);
		this.costTime = this.stop.getTime() - this.start.getTime();
		message.append(this.target)
				.append("---> cost time[").append(this.costTime).append(" (millis), ").append(this.costTime / 1000).append(" (second)]")
				.append(", start time[").append(formatDate(start))
				.append("], stop time[").append(formatDate(stop))
				.append("]");
		logger.info(message.toString());
		return this.stop;
	}
	
	private String formatDate(Date date){
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(DATE_TIME_MILLS);
	}
	public long getCostTime() {
		return costTime;
	}
	
}
