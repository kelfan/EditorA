package com.kelfan.editfiler;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class reflectVariableTest {
    @Test
    public void addition_isCorrect() throws NoSuchFieldException, IllegalAccessException {

        reflectObject r = new reflectObject();
        Field i = r.getClass().getField("v1");
        System.out.print(i.get(r));
        assertEquals(4, 2 + 2);
    }
}