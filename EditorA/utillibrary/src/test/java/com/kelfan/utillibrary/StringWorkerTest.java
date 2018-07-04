package com.kelfan.utillibrary;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringWorkerTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        String s = "1232142134";
        String[] l = s.split("\n");
        assert StringWorker.contain(s, "123", "\n");
        assert StringWorker.contain(s, "123 214", null);
        assert StringWorker.contain(s, "123", " ");
        assert StringWorker.contain(s, "", " ");
        assertEquals(4, 2 + 2);
    }

    @Test
    public void insert() {
        String s = "abcefg";
        assertEquals("abcdddefg", StringWorker.insert("ddd", 3, s));
    }
}