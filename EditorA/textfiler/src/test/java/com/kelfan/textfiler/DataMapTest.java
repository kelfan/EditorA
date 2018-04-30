package com.kelfan.textfiler;

import com.kelfan.utillibrary.ListString;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.andro2id.com/tools/testing">Testing documentation</a>
 */
public class DataMapTest {
    @Test
    public void addition_isCorrect() {
        String  a = "# title1\ncontent1:text1\n\tsub1\n\tsub2";
        ListString l  = ListString.set(a).getPatternList();
        int level = 0;

        int z = 0;
    }
}