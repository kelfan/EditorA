package com.example.todofile;

import com.kelfan.utillibrary.TimeWorker;

import java.util.Calendar;

public class Replacer {

    public static final String TO_DATE = "yyyy-MM-dd_Z";
    public static final String DATE_FORMAT = "yyyy-MM-dd_Z EEE ";
    public static final String PREFIX = "@date_";

    public static String replaceTime(String s) {
        // all string command codes can change to Constant files for change or setting
        s = s.replace(".rq", PREFIX + TimeWorker.getDate(DATE_FORMAT)); // get Current time
        s = s.replace(".mon", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.MONDAY, DATE_FORMAT));
        s = s.replace(".tue", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.TUESDAY, DATE_FORMAT));
        s = s.replace(".wed", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.WEDNESDAY, DATE_FORMAT));
        s = s.replace(".thu", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.THURSDAY, DATE_FORMAT));
        s = s.replace(".fri", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.FRIDAY, DATE_FORMAT));
        s = s.replace(".sat", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.SATURDAY, DATE_FORMAT));
        s = s.replace(".sun", PREFIX + TimeWorker.getNextWeekdayStr(Calendar.SUNDAY, DATE_FORMAT));
        s = s.replace(".nmon", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.MONDAY, DATE_FORMAT));
        s = s.replace(".ntue", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.TUESDAY, DATE_FORMAT));
        s = s.replace(".nwed", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.WEDNESDAY, DATE_FORMAT));
        s = s.replace(".nthu", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.THURSDAY, DATE_FORMAT));
        s = s.replace(".nfri", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.FRIDAY, DATE_FORMAT));
        s = s.replace(".nsat", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.SATURDAY, DATE_FORMAT));
        s = s.replace(".nsun", PREFIX + TimeWorker.getNextWeekdayStr(7 + Calendar.SUNDAY, DATE_FORMAT));
        s = s.replace(".nnmon", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.MONDAY, DATE_FORMAT));
        s = s.replace(".nntue", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.TUESDAY, DATE_FORMAT));
        s = s.replace(".nnwed", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.WEDNESDAY, DATE_FORMAT));
        s = s.replace(".nnthu", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.THURSDAY, DATE_FORMAT));
        s = s.replace(".nnfri", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.FRIDAY, DATE_FORMAT));
        s = s.replace(".nnsat", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.SATURDAY, DATE_FORMAT));
        s = s.replace(".nnsun", PREFIX + TimeWorker.getNextWeekdayStr(14 + Calendar.SUNDAY, DATE_FORMAT));
        s = s.replace(".dqt", PREFIX + TimeWorker.getNextDayStr(-3, DATE_FORMAT)); //大前天
        s = s.replace(".qt", PREFIX + TimeWorker.getNextDayStr(-2, DATE_FORMAT)); //前天
        s = s.replace(".zt", PREFIX + TimeWorker.getNextDayStr(-1, DATE_FORMAT)); // 昨天
        s = s.replace(".jt", PREFIX + TimeWorker.getNextDayStr(0, DATE_FORMAT)); // 今天
        s = s.replace(".mt", PREFIX + TimeWorker.getNextDayStr(1, DATE_FORMAT)); //明天
        s = s.replace(".ht", PREFIX + TimeWorker.getNextDayStr(2, DATE_FORMAT)); //后天
        s = s.replace(".dht", PREFIX + TimeWorker.getNextDayStr(3, DATE_FORMAT)); //大后天
        s = s.replace(".nsz", PREFIX + TimeWorker.getNextDayStr(28, DATE_FORMAT)); // 下四周
        return s;
    }

}
