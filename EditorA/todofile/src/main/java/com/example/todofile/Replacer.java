package com.example.todofile;

import com.kelfan.utillibrary.TimeWorker;

import java.util.Calendar;

public class Replacer {

    public static final String DATE_FORMAT = "yyyy-MM-dd_Z";

    public static String replaceText(String s) {
        // all string command codes can change to Constant files for change or setting
        s = s.replace(".rq", TimeWorker.getDate()); // get Current time
        s = s.replace(".dt", TimeWorker.getDatetime()); // get Current datetime
        s = s.replace(".mon", TimeWorker.getNextWeekdayStr(Calendar.MONDAY));
        s = s.replace(".tue", TimeWorker.getNextWeekdayStr(Calendar.TUESDAY));
        s = s.replace(".wed", TimeWorker.getNextWeekdayStr(Calendar.WEDNESDAY));
        s = s.replace(".thu", TimeWorker.getNextWeekdayStr(Calendar.THURSDAY));
        s = s.replace(".fri", TimeWorker.getNextWeekdayStr(Calendar.FRIDAY));
        s = s.replace(".sat", TimeWorker.getNextWeekdayStr(Calendar.SATURDAY));
        s = s.replace(".sun", TimeWorker.getNextWeekdayStr(Calendar.SUNDAY));
        s = s.replace(".nmon", TimeWorker.getNextWeekdayStr(7 + Calendar.MONDAY));
        s = s.replace(".ntue", TimeWorker.getNextWeekdayStr(7 + Calendar.TUESDAY));
        s = s.replace(".nwed", TimeWorker.getNextWeekdayStr(7 + Calendar.WEDNESDAY));
        s = s.replace(".nthu", TimeWorker.getNextWeekdayStr(7 + Calendar.THURSDAY));
        s = s.replace(".nfri", TimeWorker.getNextWeekdayStr(7 + Calendar.FRIDAY));
        s = s.replace(".nsat", TimeWorker.getNextWeekdayStr(7 + Calendar.SATURDAY));
        s = s.replace(".nsun", TimeWorker.getNextWeekdayStr(7 + Calendar.SUNDAY));
        s = s.replace(".nnmon", TimeWorker.getNextWeekdayStr(14 + Calendar.MONDAY));
        s = s.replace(".nntue", TimeWorker.getNextWeekdayStr(14 + Calendar.TUESDAY));
        s = s.replace(".nnwed", TimeWorker.getNextWeekdayStr(14 + Calendar.WEDNESDAY));
        s = s.replace(".nnthu", TimeWorker.getNextWeekdayStr(14 + Calendar.THURSDAY));
        s = s.replace(".nnfri", TimeWorker.getNextWeekdayStr(14 + Calendar.FRIDAY));
        s = s.replace(".nnsat", TimeWorker.getNextWeekdayStr(14 + Calendar.SATURDAY));
        s = s.replace(".nnsun", TimeWorker.getNextWeekdayStr(14 + Calendar.SUNDAY));
        s = s.replace(".dqt", TimeWorker.getNextDayStr(-3)); //大前天
        s = s.replace(".qt", TimeWorker.getNextDayStr(-2)); //前天
        s = s.replace(".zt", TimeWorker.getNextDayStr(-1)); // 昨天
        s = s.replace(".jt", TimeWorker.getNextDayStr(0)); // 今天
        s = s.replace(".mt", TimeWorker.getNextDayStr(1)); //明天
        s = s.replace(".ht", TimeWorker.getNextDayStr(2)); //后天
        s = s.replace(".dht", TimeWorker.getNextDayStr(3)); //大后天
        s = s.replace(".nsz", TimeWorker.getNextDayStr(28)); // 下四周
        return s;
    }

}
