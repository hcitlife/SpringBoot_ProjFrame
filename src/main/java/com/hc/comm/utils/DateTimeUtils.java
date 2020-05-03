package com.hc.comm.utils;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class DateTimeUtils {
    /**
     * 考虑港股和美股 采用GMT-1时区来确定报表日 即T日的报表包含北京时间T日9时至T+1日9时的数据
     */
    public static final ZoneId TIMEZONE_GMT_1 = ZoneId.of("GMT-1");
    public static final ZoneId TIMEZONE_EST = ZoneId.of("US/Eastern");
    public static final ZoneId TIMEZONE_GMT8 = ZoneId.of("GMT+8");

    /**
     * 常用时间转换格式
     */
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_NO_GAP_FORMAT = "yyyyMMdd";
    public static final String DATE_GAP_FORMAT = "yyyy-MM-dd";
    public static final String TIME_HH_MM_FORMAT = "HHmm";

    public static final Map<String, DateTimeFormatter> DATE_TIME_FORMAT_MAP = new Hashtable<String, DateTimeFormatter>() {
        {
            put(TIME_FORMAT, DateTimeFormatter.ofPattern(TIME_FORMAT));
            put(DATE_NO_GAP_FORMAT, DateTimeFormatter.ofPattern(DATE_NO_GAP_FORMAT));
            put(DATE_GAP_FORMAT, DateTimeFormatter.ofPattern(DATE_GAP_FORMAT));
            put(TIME_HH_MM_FORMAT, DateTimeFormatter.ofPattern(TIME_HH_MM_FORMAT));
        }
    };

    /**
     * 根据format的格式获取相应的DateTimeFormatter对象
     *
     * @param format 时间转换格式字符串
     * @return
     */
    public static DateTimeFormatter getDateTimeFormatter(String format) {
        if (DATE_TIME_FORMAT_MAP.containsKey(format)) {
            return DATE_TIME_FORMAT_MAP.get(format);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            DATE_TIME_FORMAT_MAP.put(format, formatter);
            return formatter;
        }
    }

    /**
     * 获取当前日期的开始时间
     *
     * @param zoneId 时间偏移量
     * @return
     */
    public static LocalDateTime todayStart(ZoneId zoneId) {
        return startOfDay(0, zoneId);
    }

    /**
     * 获取当前的ZoneDateTime
     *
     * @param zoneId 时区偏移量
     * @return
     */
    public static ZonedDateTime now(ZoneId zoneId) {
        return ZonedDateTime.now(zoneId);
    }

    /**
     * 获取当前日期的开始时间ZonedDateTime
     *
     * @param date   日期
     * @param zoneId 时区偏移量
     * @return
     */
    public static ZonedDateTime localDateToZoneDateTime(LocalDate date, ZoneId zoneId) {
        return date.atStartOfDay(zoneId);
    }

    /**
     * 获取当前日期的开始时间
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime startOfDay(ZonedDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.DAYS).toLocalDateTime();
    }

    /**
     * 获取今天后的指定天数的开始时间
     *
     * @param plusDays 当前多少天后
     * @param zoneId   时区偏移量
     * @return
     */
    public static LocalDateTime startOfDay(int plusDays, ZoneId zoneId) {
        return startOfDay(now(zoneId).plusDays(plusDays));
    }

    /**
     * 获取指定日期的后几个工作日的时间LocalDate
     *
     * @param date 指定日期
     * @param days 工作日数
     * @return
     */
    public static LocalDate plusWeekdays(LocalDate date, int days) {
        if (days == 0) {
            return date;
        }
        if (Math.abs(days) > 50) {
            throw new IllegalArgumentException("days must be less than 50");
        }
        int i = 0;
        int delta = days > 0 ? 1 : -1;
        while (i < Math.abs(days)) {
            date = date.plusDays(delta);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                i += 1;
            }
        }
        return date;
    }

    /**
     * 获取指定日期的后几个工作日的时间ZoneDateTime
     *
     * @param date
     * @param days
     * @return
     */
    public static ZonedDateTime plusWeekdays(ZonedDateTime date, int days) {
        return plusWeekdays(date.toLocalDate(), days).atStartOfDay(date.getZone());
    }

    /**
     * 获取当前月份的第一天的时间ZoneDateTime
     *
     * @param zoneId
     * @return
     */
    public static ZonedDateTime firstDayOfMonth(ZoneId zoneId) {
        return now(zoneId).withDayOfMonth(1);
    }


    /**
     * 将Date转成指定时区的Date
     *
     * @param date
     * @return
     */
    public static Date dateToDate(Date date, ZoneId zoneId) {
        LocalDateTime dt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return toDate(ZonedDateTime.of(dt, zoneId));
    }

    /**
     * 将LocalDate转成Date
     *
     * @param date
     * @return
     */
    public static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * ZonedDateTime 转换成Date
     *
     * @param dateTime
     * @return
     */
    public static Date toDate(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    /**
     * String 转换成 Date
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String date, String format, ZoneId zoneId) throws ParseException {
        DateTimeFormatter formatter = getDateTimeFormatter(format).withZone(zoneId);
        Instant instant = Instant.from(formatter.parse(date));
        return Date.from(instant);
    }

    /**
     * 将Date转成相应的时区的localDate
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * 将Instant转成指定时区偏移量的localDate
     *
     * @param instant
     * @param zoneId
     * @return
     */
    public static LocalDate toLocalDate(Instant instant, ZoneId zoneId) {
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 将Instant转换成指定时区偏移量的localDateTime
     *
     * @param instant
     * @param zoneId
     * @return
     */
    public static LocalDateTime toLocalDateTime(Instant instant, ZoneId zoneId) {
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将Instant转成系统默认时区偏移量的LocalDateTime
     *
     * @param instant
     * @return
     */
    public static LocalDateTime toLocalDateTime(Instant instant) {
        return toLocalDateTime(instant, ZoneId.systemDefault());
    }

    /**
     * 将ZoneDateTime 转成 指定时区偏移量的LocalDateTime
     *
     * @param zonedDateTime 时间
     * @param zoneId        指定时区偏移量
     * @return
     */
    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return zonedDateTime.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * 将ZoneDateTime 转成 LocalDateTime
     *
     * @param zonedDateTime
     * @return
     */
    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * String 转成 ZoneDateTime
     * 需要类似 yyyy-MM-dd HH:mm:ss 需要日期和时间信息完整信息
     *
     * @param date
     * @param format
     * @param zoneId
     * @return
     */
    public static ZonedDateTime stringToZoneDateTime(String date, String format, ZoneId zoneId) {
        DateTimeFormatter formatter = getDateTimeFormatter(format).withZone(zoneId);
        return ZonedDateTime.parse(date, formatter);
    }


    /**
     * 将时间戳long转成ZonedDateTime
     *
     * @param timeStamp
     * @param zoneId
     * @return
     */
    public static ZonedDateTime longToZoneDateTime(long timeStamp, ZoneId zoneId) {
        return ZonedDateTime.from(Instant.ofEpochMilli(timeStamp).atZone(zoneId));
    }

    /**
     * 两个时区的zoneDateTime相互转换
     *
     * @param zonedDateTime 需要转换的如期
     * @param zoneId        转换成的ZoneDateTime的时区偏移量
     * @return
     */
    public static ZonedDateTime zonedDateTimeToZoneDateTime(ZonedDateTime zonedDateTime, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(zonedDateTime.toInstant(), zoneId);
    }

    /**
     * Date 转成 指定时区偏移量的ZoneDateTime
     *
     * @param date
     * @param zoneId
     * @return
     */
    public static ZonedDateTime toZonedDateTime(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId);
    }

    /**
     * LocaldateTime 转成 指定时区偏移量的ZonedDateTime
     *
     * @param localDateTime 本地时间
     * @param zoneId        转成ZonedDateTime的时区偏移量
     * @return
     */
    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId);
    }

    /**
     * Date装换成String
     *
     * @param date   时间
     * @param format 转化格式
     * @return
     */
    public static String dateToString(Date date, String format, ZoneId zoneId) {
        DateTimeFormatter formatter = getDateTimeFormatter(format).withZone(zoneId);
        return formatter.format(date.toInstant());
    }

    /**
     * ZoneDateTime 转换成 String
     *
     * @param dateTime
     * @param zoneId   localDateTime所属时区
     * @return
     */
    public static String zoneDateTimeToString(ZonedDateTime dateTime, String format, ZoneId zoneId) {
        DateTimeFormatter formatter = getDateTimeFormatter(format).withZone(zoneId);
        return dateTime.format(formatter);
    }

    /**
     * LocalDateTime 转成 String
     *
     * @param localDateTime
     * @param format
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = getDateTimeFormatter(format);
        return localDateTime.format(formatter);
    }

    /**
     * 将ZonedDateTime转成时间戳long
     *
     * @return
     * @parm zonedDateTime
     */
    public static long zoneDateTimeToLong(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant().toEpochMilli();
    }

    /**
     * 将LocalDateTime转成时间戳long
     *
     * @param localDateTime
     * @param zoneId
     * @return
     */
    public static long toLong(LocalDateTime localDateTime, ZoneId zoneId) {
        return zoneDateTimeToLong(localDateTime.atZone(zoneId));
    }

    /**
     * java.util.Date与java.sql.Date之间相互转换
     */
    public void date2Date() {
        java.util.Date uDate = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        uDate = sDate;
    }

}
