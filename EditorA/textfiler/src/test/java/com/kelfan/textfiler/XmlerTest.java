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
public class XmlerTest {
    @Test
    public void addition_isCorrect() {
        String  a = "<levels># \t</levels>\n# 12\n \td1\n\t\td2\n# 13\n\td1";
        Xmler xmler = Xmler.set(a, "levels").setContent("new levels");
        String s = xmler.toString();
        assertEquals("<levels>new levels</levels>\n# 12\n \td1\n\t\td2\n# 13\n\td1", s);

        String b = "test2";
        Xmler x2 = Xmler.set(b, "create").setContent("2018-4-28");
        s = x2.toString();
        assertEquals("test2<create>2018-4-28</create>", s);
    }
}