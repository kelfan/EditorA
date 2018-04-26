package com.kelfan.textfiler;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.andro2id.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String  a = "<1>aba;'as@#$@#$</1>asadf";
        String regex = "<1>.*</1>";
        Pattern pattern   =   Pattern.compile(regex);
        Matcher matcher   =   pattern.matcher(a);
        String s = matcher.replaceAll("<1>replace</1>");
        assertEquals(4, 2 + 2);
    }
}