package com.xmyy.common.util;

import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * 注意几种时间类型的使用：
 * Instant 它代表的是时间戳，比如2016-04-05T02:20:13.592Z
 * LocalDate 它表示的是不带时间的日期，比如2016-04-05
 * LocalTime – 它表示的是不带日期的时间，比如14:15:30
 * LocalDateTime – 它包含了时间与日期，不过没有带时区的偏移量，比如2016-04-05 14:15:30
 * ZonedDateTime – 这是一个带时区的完整时间，它根据UTC/格林威治时间来进行时区调整
 */
public class DateUtils {

    public static final String Pattern_Date_Only = "yyyy-MM-dd";
    public static final String Pattern_Date_Time = "yyyy-MM-dd HH:mm:ss";
    public static final String Pattern_DATE_TIME_MILLS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Pattern_DATE_SHORT_TIME = "yyyy-MM-dd HH:mm";
    public static final String Pattern_Time_Only = "HH:mm:ss";
    public static final String Pattern_SHORT_TIME_ONLY = "HH:mm";
    public static final String Pattern_DateOnly = "yyyyMMdd";
    public static final String Pattern_DateTime = "yyyyMMddHHmmss";
    public static final String Pattern_TimeOnly = "HHmmss";

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, Pattern_Date_Only);
    }

    /**
     * 格式化不带时间的日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (!Optional.ofNullable(date).isPresent()) {
            return null;
        }
        LocalDate localDate = DateConvertUtils.asLocalDate(date);

        return formatDate(localDate, pattern);

    }

    /**
     * 格式化不带时间的日期
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String formatDate(LocalDate localDate, String pattern) {
        Assert.notNull(localDate);
        if (!Optional.ofNullable(localDate).isPresent()) {
            return null;
        }

        return localDate.format(
                DateTimeFormatter.ofPattern(
                        Optional.ofNullable(pattern).orElse(Pattern_Date_Only)));

    }

    /**
     * 格式化完整日期,带时分秒
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDateTime(date, Pattern_Date_Time);
    }

    /**
     * 格式化完整日期,带时分秒
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateTime(Date date, String pattern) {

        Assert.notNull(date);

        if (!Optional.ofNullable(date).isPresent()) {
            return null;
        }
        LocalDateTime localDateTime = DateConvertUtils.asLocalDateTime(date);

        return formatDateTime(localDateTime, pattern);
    }

    /**
     * 格式化完整日期,带时分秒
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String formatDateTime(LocalDateTime localDateTime, String pattern) {

        if (!Optional.ofNullable(localDateTime).isPresent()) {
            return null;
        }

        return localDateTime.format(
                DateTimeFormatter.ofPattern(
                        Optional.ofNullable(pattern).orElse(Pattern_Date_Time)));
    }


    /**
     * 解析字符串，生成完整日期，带时分秒
     *
     * @param dateStr
     * @return
     */
    public static Date parseDateTime(String dateStr) {
        return parseDateTime(dateStr, Pattern_Date_Time);
    }

    /**
     * 解析字符串，生成完整日期，带时分秒
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDateTime(String dateStr, String pattern) {

        Assert.notNull(dateStr);

        if (!Optional.ofNullable(dateStr).isPresent()) {
            return null;
        }

        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(
                    Optional.ofNullable(pattern).orElse(Pattern_Date_Time)));
            return DateConvertUtils.asUtilDate(localDateTime);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析字符串，只生成日期，不带时分秒
     *
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, Pattern_Date_Only);
    }

    /**
     * 解析字符串，只生成日期，不带时分秒
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseDate(String dateStr, String pattern) {

        Assert.notNull(dateStr);

        if (!Optional.ofNullable(dateStr).isPresent()) {
            return null;
        }

        try {
            LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(Optional.ofNullable(pattern).orElse(Pattern_Date_Time)));
            return DateConvertUtils.asUtilDate(localDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 当日时间取整，年月日
     *
     * @param date
     * @return
     */
    public static Date getDateStart(Date date) {
        Assert.notNull(date, "date can not be null");
        return parseDate(formatDateTime(date, Pattern_Date_Only));
    }

    /**
     * 当日时间取末，含时分秒
     *
     * @param date
     * @return
     */
    public static Date getDateEnd(Date date) {
        Assert.notNull(date, "date can not be null");
        return DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDateTime(
                DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDate(date).plusDays(1))).minusNanos(1));
    }

    /**
     * 返回date加N天的时间
     *
     * @param date
     * @param numb 增加天数
     * @return
     */
    public static Date addDay(Date date, int numb) {
        Assert.notNull(date, "date can not be null");
        return DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDateTime(date).plusDays(numb));
    }

    /**
     * 返回date减N天的时间
     *
     * @param date
     * @param numb 减少天数
     * @return
     */
    public static Date reduceDay(Date date, int numb) {
        Assert.notNull(date, "date can not be null");
        return DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDateTime(date).minusDays(numb));
    }

    /**
     * 返回date加N小时的时间
     *
     * @param date
     * @param numb 增加小时数
     * @return
     */
    public static Date addHour(Date date, int numb) {
        Assert.notNull(date, "date can not be null");
        return DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDateTime(date).plusHours(numb));
    }

    /**
     * 返回date减N小时的时间
     *
     * @param date
     * @param numb 减少小时数
     * @return
     */
    public static Date reduceHour(Date date, int numb) {
        Assert.notNull(date, "date can not be null");
        return DateConvertUtils.asUtilDate(DateConvertUtils.asLocalDateTime(date).minusHours(numb));
    }

    /**
     * 判断当前时间是否小于传进的时间值与时间差之和
     *
     * @param timeStr
     * @param date
     */
    public static boolean isOverRange(String timeStr, Date date) {
        if (timeStr == null || timeStr.trim().equals("") || date == null)
            return false;
        try {
            int time = Integer.parseInt(timeStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, time);
            Calendar nowCalendar = Calendar.getInstance();
            int result = nowCalendar.compareTo(calendar);
            if (result == -1 || result == 0) {// nowCalendar 比 calendar早,返回-1,nowCalendar 与 calendar相等,返回 0
                return true;
            } else if (result == 1) {// nowCalendar 比 calendar 晚,返回1
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 得到传入日期当前月份的第一天
     * 如：输入日期：2016-4-14 18:45:38  则返回日期：2016-4-1 0:00:00
     *
     * @param date
     * @return
     * @author wufengui
     */
    public static Date firstDayOfMonth(Date date) {
        Assert.notNull(date, "date can not be null");
        return firstDayOfSomeMonth(date, true, 0, true, false);
    }

    /**
     * 得到传入日期下一个月份的第一天
     * 如：输入日期：2016-4-14 18:45:38  则返回日期：2016-5-1 0:00:00
     *
     * @param date
     * @return
     * @author wufengui
     */
    public static Date firstDayOfNextMonth(Date date) {
        Assert.notNull(date, "date can not be null");
        return firstDayOfSomeMonth(date, true, 1, true, false);
    }

    /**
     * 得到传入日期当前月份的最后一天
     * 如：输入日期：2016-4-14 18:45:38  则返回日期：2016-4-30 0:00:00
     *
     * @param date public static Date lastDayOfMonth(Date date){
     *             Assert.notNull(date, "date can not be null");
     *             return firstDayOfSomeMonth(date, true, 0,false,false);
     *             }
     *             <p>
     *             /**
     *             得到传入日期当前月份的最后一天
     *             如：输入日期：2016-4-14 18:45:38  则返回日期：2016-4-30 23:59:59.999999999
     * @param date
     * @return
     */
    public static Date lastDayOfMonthWithTime(Date date) {
        Assert.notNull(date, "date can not be null");
        return firstDayOfSomeMonth(date, true, 0, false, true);
    }

    /**
     * 得到传入日期的  后面/前面 的  第一个/第几个 月份  的   第一天/最后一天
     *
     * @param date              待转换的Util.Date参数
     * @param isNext            true:是后面的月份； false:是前面的月份
     * @param num               后面num个月份/前面num个月份
     * @param isFirstDay        true:是月份的第一天;false: 月份的最后一天
     * @param isNeedLastDayTime 当isFirstDay=false时  isNeedLastDayTime=true时 表示 获得日期类型包含时分秒
     * @return
     */
    public static Date firstDayOfSomeMonth(Date date, boolean isNext, int num, boolean isFirstDay, boolean isNeedLastDayTime) {
        Assert.notNull(date, "date can not be null");
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zone);
        LocalDate localDate = isNext ? localDateTime.toLocalDate().plusMonths(num) : localDateTime.toLocalDate().minusMonths(num);
        LocalDate firstOrLastDay = localDate.with(isFirstDay ? TemporalAdjusters.firstDayOfMonth() : TemporalAdjusters.lastDayOfMonth());
        if (!isFirstDay && isNeedLastDayTime) {
            LocalDateTime localDateTimeTemp = firstOrLastDay.atTime(23, 59, 59, 999999999);
            Instant instant_LastDayWithTime = localDateTimeTemp.atZone(zone).toInstant();
            return Date.from(instant_LastDayWithTime);
        }
        Instant instant_fisrtOrLastDay = firstOrLastDay.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant_fisrtOrLastDay);
    }

    public static Date rollToLastSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date rollToFirstSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addHours(Calendar cal, int amount) {
        return add(cal, Calendar.HOUR_OF_DAY, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date add(Calendar c, int calendarField, int amount) {
        if (c == null) {
            throw new IllegalArgumentException("The Calendar must not be null");
        }
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 比较日期（只比较天）
     *
     * @param date1
     * @param date2
     * @return date1>date2 1; date1<date2 -1; date1=date2 0;
     */
    public static int compareDate(Date date1, Date date2) {
        try {
            Date dt1 = getDateStart(date1);
            Date dt2 = getDateStart(date2);
            return Long.compare(dt1.getTime(), dt2.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较日期（包括时分秒）
     *
     * @param date1
     * @param date2
     * @return date1>date2 1; date1<date2 -1; date1=date2 0;
     */
    public static int compareTime(Date date1, Date date2) {
        try {
            return Long.compare(date1.getTime(), date2.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 比较年份大小
     *
     * @param date1
     * @param date2
     * @return date1>date2 1; date1<date2 -1; date1=date2 0;
     */
    public static int compareYear(Date date1, Date date2) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            int year1 = c.get(Calendar.YEAR);
            c.setTime(date2);
            int year2 = c.get(Calendar.YEAR);
            return Integer.compare(year1, year2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取时间的小时数（24小时制）
     *
     * @param date
     * @return
     */
    public static Integer getHour(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取两个日期间的天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getDaysBetween(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);
        return getDaysBetween(c1, c2);
    }

    private static int getDaysBetween(java.util.Calendar c1, java.util.Calendar c2) {
        if (c1.after(c2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = c1;
            c1 = c2;
            c2 = swap;
        }
        int days = c2.get(java.util.Calendar.DAY_OF_YEAR) - c1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = c2.get(java.util.Calendar.YEAR);
        if (c1.get(java.util.Calendar.YEAR) != y2) {
            c1 = (java.util.Calendar) c1.clone();
            do {
                days += c1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                c1.add(java.util.Calendar.YEAR, 1);
            } while (c1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    }

    public static int diffDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }

    public static void main(String[] args) {
//        System.out.println(getDateStart(new Date()));
//        System.out.println(getDateEnd(new Date()));
//        System.out.println(getHour(new Date()));
//        System.out.println(addDay(getDateEnd(new Date()), 1));
//        System.out.println(getDateStart(new Date()));
//        System.out.println(compareYear(new Date(), new Date()));
//        System.out.println(formatDate(new Date(), "MM月dd日"));
        System.out.println(reduceHour(new Date(), 24));
    }
}
