package com.example.todofile;

import android.view.View;

import com.kelfan.utillibrary.ColorWorker;
import com.kelfan.utillibrary.LunarSolarConverter;
import com.kelfan.utillibrary.TimeWorker;

import java.util.Date;

public class StringParser {
    public static Date parseLunar(String text) {
        try {
            String[] strings = text.split("-");
            if (strings.length == 2) {
                int month = Integer.parseInt(strings[0]);
                int day = Integer.parseInt(strings[1]);
                return LunarSolarConverter.lunar2solar(TimeWorker.currentYear(), month, day);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static Date parseRepeatY(String text) {
        try {
            String[] strings = text.split("-");
            if (strings.length == 2) {
                int month = Integer.parseInt(strings[0]);
                int day = Integer.parseInt(strings[1]);
                return TimeWorker.int2date(TimeWorker.currentYear(), month, day);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
