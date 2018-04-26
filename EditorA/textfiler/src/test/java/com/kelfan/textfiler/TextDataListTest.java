package com.kelfan.textfiler;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.andro2id.com/tools/testing">Testing documentation</a>
 */
public class TextDataListTest {
    @Test
    public void addition_isCorrect() {
        String  a = "<1>aba;'as@#$@#$</1>asadf";
        TextDataList textDataList = TextDataList.set(a);
        assertEquals(4, 2 + 2);
    }
}