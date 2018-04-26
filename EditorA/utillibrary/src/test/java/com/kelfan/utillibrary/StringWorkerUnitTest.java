package com.kelfan.utillibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringWorkerUnitTest {
    @Test
    public void getBetween() {
        String a = "12345678";
        String b = StringWorker.getBetween(a, "3", "7");
        assertEquals("456", b);
        String c = StringWorker.getBetween(a, "3", "9");
        assertEquals("45678", c);
        String d = StringWorker.getBetween(a, "9", "4");
        assertEquals("123", d);
        String e = StringWorker.getBetween(a, "8", "4");
        assertEquals("", e);
        String f = StringWorker.getBetween(a, "6", "3");
        assertEquals("78", f);
        String g = StringWorker.getBetween(a, "5", "6");
        assertEquals("", g);
        int z = 0;

    }

    @Test
    public void replaceBetween() {
        String a = "<1>aba;'as@#$@#$</1>asadf";
        String s = StringWorker.replaceBetween(a, "replace", "<1>", "</1>");
        assertEquals("<1>replace</1>asadf", s);
        int z = 0;
    }
}