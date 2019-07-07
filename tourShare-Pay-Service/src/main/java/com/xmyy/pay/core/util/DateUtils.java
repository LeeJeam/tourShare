package com.xmyy.pay.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    private static final String DATE_YYYYMMDD = "yyyy-MM-dd";
    private static final String DATE_YYMMDD_NOLINK = "yyyyMMdd";
    private static final String DATETIME_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String DATETIME_YYYYMMDD_HHMMSS_NOLINK = "yyyyMMddHHmmss";
    private static final String DATETIME_YYYYMMDD_HHMM = "yyyy-MM-dd HH:mm";
    private static final String TIME_HHMMSS = "HH:mm:ss";
    private static final String TIME_HHMM = "HH:mm";
    public static final long ONE_DAY_MILLIS = 24 * 60 * 60 * (long) 1000;

    public static String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(
                DATETIME_YYYYMMDD_HHMMSS);
        return formatter.format(cal.getTime());
    }

} 
 

