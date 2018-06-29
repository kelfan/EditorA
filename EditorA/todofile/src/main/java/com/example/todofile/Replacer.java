package com.example.todofile;

import com.kelfan.utillibrary.TimeWorker;

import java.util.Calendar;

public class Replacer {

    public static String replaceText(String s) {
        // all string command codes can change to Constant files for change or setting
        s = s.replace(".rq", TimeWorker.getDate()); // get Current time
        s = s.replace(".dt", TimeWorker.getDatetime()); // get Current datetime
        s = s.replace(".mon", TimeWorker.getNextWeekday(Calendar.MONDAY));
        s = s.replace(".tue", TimeWorker.getNextWeekday(Calendar.TUESDAY));
        s = s.replace(".wed", TimeWorker.getNextWeekday(Calendar.WEDNESDAY));
        s = s.replace(".thu", TimeWorker.getNextWeekday(Calendar.THURSDAY));
        s = s.replace(".fri", TimeWorker.getNextWeekday(Calendar.FRIDAY));
        s = s.replace(".sat", TimeWorker.getNextWeekday(Calendar.SATURDAY));
        s = s.replace(".sun", TimeWorker.getNextWeekday(Calendar.SUNDAY));
        s = s.replace(".nmon", TimeWorker.getNextWeekday(7 + Calendar.MONDAY));
        s = s.replace(".ntue", TimeWorker.getNextWeekday(7 + Calendar.TUESDAY));
        s = s.replace(".nwed", TimeWorker.getNextWeekday(7 + Calendar.WEDNESDAY));
        s = s.replace(".nthu", TimeWorker.getNextWeekday(7 + Calendar.THURSDAY));
        s = s.replace(".nfri", TimeWorker.getNextWeekday(7 + Calendar.FRIDAY));
        s = s.replace(".nsat", TimeWorker.getNextWeekday(7 + Calendar.SATURDAY));
        s = s.replace(".nsun", TimeWorker.getNextWeekday(7 + Calendar.SUNDAY));
        s = s.replace(".nnmon", TimeWorker.getNextWeekday(14 + Calendar.MONDAY));
        s = s.replace(".nntue", TimeWorker.getNextWeekday(14 + Calendar.TUESDAY));
        s = s.replace(".nnwed", TimeWorker.getNextWeekday(14 + Calendar.WEDNESDAY));
        s = s.replace(".nnthu", TimeWorker.getNextWeekday(14 + Calendar.THURSDAY));
        s = s.replace(".nnfri", TimeWorker.getNextWeekday(14 + Calendar.FRIDAY));
        s = s.replace(".nnsat", TimeWorker.getNextWeekday(14 + Calendar.SATURDAY));
        s = s.replace(".nnsun", TimeWorker.getNextWeekday(14 + Calendar.SUNDAY));
        s = s.replace(".dqt", TimeWorker.getNextDay(-3)); //大前天
        s = s.replace(".qt", TimeWorker.getNextDay(-2)); //前天
        s = s.replace(".zt", TimeWorker.getNextDay(-1)); // 昨天
        s = s.replace(".jt", TimeWorker.getNextDay(0)); // 今天
        s = s.replace(".mt", TimeWorker.getNextDay(1)); //明天
        s = s.replace(".ht", TimeWorker.getNextDay(2)); //后天
        s = s.replace(".dht", TimeWorker.getNextDay(3)); //大后天
        s = s.replace(".nsz", TimeWorker.getNextDay(28)); // 下四周
        return s;
    }

}
