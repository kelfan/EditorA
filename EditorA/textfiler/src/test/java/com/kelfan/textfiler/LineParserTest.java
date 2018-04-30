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
public class LineParserTest {
    @Test
    public void addition_isCorrect() {
        String  a = "## t2 \n#t1\n content\n\tsub1\n\t\tsub2";
//        LineParser lineParser = LineParser.set(a).split();
//        LineParser l = new LineParser();
        assertEquals(4, 2 + 2);
    }
}