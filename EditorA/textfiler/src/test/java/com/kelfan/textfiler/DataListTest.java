package com.kelfan.textfiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.andro2id.com/tools/testing">Testing documentation</a>
 */
public class DataListTest {
    @Test
    public void addition_isCorrect() {
        String  a = "# title1\ncontent1:text1\n\tsub1\n\tsub2";
        DataList d = DataList.set(a).auto();
        assertEquals(4, 2 + 2);
    }
}