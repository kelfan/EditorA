package com.kelfan.editfiler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String s = "123 \n\n 1234\n";
        String pattern = "[\n]*[^\n]+";
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(s);
        while (m.find()) {
            allMatches.add(m.group());
        }

        assertEquals(4, 2 + 2);
    }
}