package com.kelfan.utillibrary;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ListHandlerTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        String[] a = "1,2,3,4".split(",");
        String b= ListWorker.list2str(a, ";");

        assertEquals(b, "1;2;3;4");

        String  c = "1_2, 3_4, 5, 6";


    }
}