package com.kelfan.textfiler;

import com.kelfan.utillibrary.TimeWorker;

import org.junit.Test;

import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.andro2id.com/tools/testing">Testing documentation</a>
 */
public class DataObjectTest {
    @Test
    public void addition_isCorrect() {
        // first put in data
        String  a = "# t1";
        DataObject dataObject = DataObject.set(a);
        String out = dataObject.toString();
        assertEquals("# t1<createTime>Sun Apr 29 23:45:56 AEST 2018</createTime><modifyTime>Sun Apr 29 23:45:56 AEST 2018</modifyTime>".length(), out.length());
        String c= dataObject.getContent();
        assertEquals(a, c);
        // change content
        DataObject d = DataObject.set("# t1<createTime>Sun Apr 29 23:45:56 AEST 2018</createTime><modifyTime>Sun Apr 29 23:45:56 AEST 2018</modifyTime>");
        d.changeContent("# t2");
        out = d.toString();
        assertEquals("# t2<createTime>Sun Apr 29 23:45:56 AEST 2018</createTime><modifyTime>Mon Apr 30 00:08:58 AEST 2018</modifyTime>".substring(0, 58), out.substring(0, 58));
        c=d.getContent();
        assertEquals("# t2", c);
    }
}