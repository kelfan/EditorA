package com.kelfan.utillibrary;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timer {
    public static final String DAYWEEKFORMAT = "MM-dd EEE";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd_Z";

    Date date;
    String format;

    Timer() {
        date = new Date();
        format = "yyyy-MM-dd HH:mm:ss";
    }

    public static Timer set() {
        return new Timer();
    }

    public Timer withFormat(String format) {
        this.format = format;
        return this;
    }

    public Timer nextDays(int days) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, days);
        this.date = now.getTime();
        return this;
    }

    public Timer nextWeekdays(int weekday) {
        Calendar now = Calendar.getInstance();
        int cWeekday = now.get(Calendar.DAY_OF_WEEK);
        int difference = weekday - cWeekday;
        if (difference <= 0) {
            difference += 7;
        }
        now.add(Calendar.DAY_OF_YEAR, difference);
        this.date = now.getTime();
        return this;
    }

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getDatetime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getLocalTime() {
        return new Date().toString();
    }

    public String toString(){
        return new SimpleDateFormat(this.format).format(this.date);
    }
}
