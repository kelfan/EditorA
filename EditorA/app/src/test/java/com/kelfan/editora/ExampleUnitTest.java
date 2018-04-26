package com.kelfan.editora;

import android.support.test.espresso.core.internal.deps.protobuf.Internal;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        String text = "1/2/3";
        if (text.contains("/")) {
            String[] txtList = text.split("/");
            String result = txtList[txtList.length-1];
            System.out.print(result);
        }

    }
}