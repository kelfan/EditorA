package com.kelfan.utillibrary;

import org.junit.Test;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TimeWorkerTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        Date date = TimeWorker.getNextDay(3);
        String s = TimeWorker.formatDate("yyyy-MM-dd_Z", date);
        System.out.print(s);
//        assertEquals("2018-07-03_+1000", s);
    }
}