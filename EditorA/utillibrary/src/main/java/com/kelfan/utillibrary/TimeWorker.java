package com.kelfan.utillibrary;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 17/01/2018.
 */

public class TimeWorker {

    public static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getDatetime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getNextWeekday(int weekday) {
        Calendar now = Calendar.getInstance();
        int cWeekday = now.get(Calendar.DAY_OF_WEEK);
        int difference = weekday - cWeekday;
        if (difference <= 0) {
            difference += 7;
        }
        now.add(Calendar.DAY_OF_YEAR, difference);
        Date date = now.getTime();
        String format = new SimpleDateFormat(UtilConstant.DAYWEEKFORMAT).format(date);
        return format;
    }


    public static String getNextDay(int days) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, days);
        Date date = now.getTime();
        String format = new SimpleDateFormat(UtilConstant.DAYWEEKFORMAT).format(date);
        return format;
    }

    public static String getLocalTime(){
        return new Date().toString();
    }

}
