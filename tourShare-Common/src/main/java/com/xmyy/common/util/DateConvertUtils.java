package com.xmyy.common.util;

import java.time.*;
import java.util.Date;

/**
 * Created by zlp on 2016/4/5.
 */
public class DateConvertUtils {

    /**
     * Calls {@link #asLocalDate(Date, ZoneId)} with the system default time zone.
     */
    public static LocalDate asLocalDate(Date date) {
        return asLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * Creates {@link LocalDate} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static LocalDate asLocalDate(Date date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Date)
            return ((java.sql.Date) date).toLocalDate();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
    }

    /**
     * Calls {@link #asLocalDateTime(Date, ZoneId)} with the system default time zone.
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return asLocalDateTime(date, ZoneId.systemDefault());
    }

    /**
     * Creates {@link LocalDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static LocalDateTime asLocalDateTime(Date date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Timestamp)
            return ((java.sql.Timestamp) date).toLocalDateTime();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
    }

    /**
     * Calls {@link #asUtilDate(Object, ZoneId)} with the system default time zone.
     */
    public static Date asUtilDate(Object date) {
        return asUtilDate(date, ZoneId.systemDefault());
    }

    /**
     * Creates a {@link Date} from various date objects. Is null-safe. Currently supports:<ul>
     * <li>{@link Date}
     * <li>{@link java.sql.Date}
     * <li>{@link java.sql.Timestamp}
     * <li>{@link LocalDate}
     * <li>{@link LocalDateTime}
     * <li>{@link ZonedDateTime}
     * <li>{@link Instant}
     * </ul>
     *
     * @param zone Time zone, used only if the input object is LocalDate or LocalDateTime.
     *
     * @return {@link Date} (exactly this class, not a subclass, such as java.sql.Date)
     */
    public static Date asUtilDate(Object date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Date || date instanceof java.sql.Timestamp)
            return new Date(((Date) date).getTime());
        if (date instanceof Date)
            return (Date) date;
        if (date instanceof LocalDate)
            return Date.from(((LocalDate) date).atStartOfDay(zone).toInstant());
        if (date instanceof LocalDateTime)
            return Date.from(((LocalDateTime) date).atZone(zone).toInstant());
        if (date instanceof ZonedDateTime)
            return Date.from(((ZonedDateTime) date).toInstant());
        if (date instanceof Instant)
            return Date.from((Instant) date);

        throw new UnsupportedOperationException("Don't know hot to convert " + date.getClass().getName() + " to java.util.Date");
    }

    /**
     * Creates an {@link Instant} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static Instant asInstant(Date date) {
        if (date == null)
            return null;
        else
            return Instant.ofEpochMilli(date.getTime());
    }

    /**
     * Calls {@link #asZonedDateTime(Date, ZoneId)} with the system default time zone.
     */
    public static ZonedDateTime asZonedDateTime(Date date) {
        return asZonedDateTime(date, ZoneId.systemDefault());
    }

    /**
     * Creates {@link ZonedDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static ZonedDateTime asZonedDateTime(Date date, ZoneId zone) {
        if (date == null)
            return null;
        else
            return asInstant(date).atZone(zone);
    }
}
