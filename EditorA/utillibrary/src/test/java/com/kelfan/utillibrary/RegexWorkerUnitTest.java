package com.kelfan.utillibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegexWorkerUnitTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        assertEquals(4, 2 + 2);
        String i = "test/t/t: hello";
        assertEquals("/t/t: hello", RegexWorker.signToEnd(i, "/"));

    }
}