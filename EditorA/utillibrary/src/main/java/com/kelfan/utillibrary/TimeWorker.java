package com.kelfan.utillibrary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 17/01/2018.
 */

public class TimeWorker {
    public static final String DAYWEEKFORMAT = "MM-dd EEE";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd_Z";


    public static Date getNow(){
        return new Date();
    }

    public static String getDate(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getDatetime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getNextWeekdayStr(int weekday) {
        Date date = getNextWeekday(weekday);
        String format = new SimpleDateFormat(DAYWEEKFORMAT).format(date);
        return format;
    }

    public static String getNextWeekdayStr(int weekday, String format) {
        Date date = getNextWeekday(weekday);
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getNextWeekday(int weekday) {
        Calendar now = Calendar.getInstance();
        int cWeekday = now.get(Calendar.DAY_OF_WEEK);
        int difference = weekday - cWeekday;
        if (difference <= 0) {
            difference += 7;
        }
        now.add(Calendar.DAY_OF_YEAR, difference);
        return now.getTime();
    }

    public static String getNextDayStr(int days) {
        Date date = getNextDay(days);
        String format = new SimpleDateFormat(DAYWEEKFORMAT).format(date);
        return format;
    }

    public static String getNextDayStr(int days, String format) {
        Date date = getNextDay(days);
        return  new SimpleDateFormat(format).format(date);
    }

    public static Date getNextDay(int days) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, days);
        return now.getTime();
    }

    public static String getLocalTime() {
        return new Date().toString();
    }

    public static String formatDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

}
