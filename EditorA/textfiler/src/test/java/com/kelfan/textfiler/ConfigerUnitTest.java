package com.kelfan.textfiler;

import com.kelfan.textfiler.depreciate.Configer_Depreciate;
import com.kelfan.utillibrary.StringWorker;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ConfigerUnitTest {
    @Test
    public void setXmlContent() {
        String a = "test";
        String b = TextParser.setXmlContent(a, "Delimiter");
        String c = TextParser.getXmlContent(b, "Delimiter");
        assertEquals("<Delimiter>test</Delimiter>\n", b);
        assertEquals("test", c);
    }

//    @Test
//    public void getConfig() {
//        Configer_Depreciate configer = new Configer_Depreciate("\n , .");
//        String b = configer.toString();
//        assertEquals("<delimiter>\\n </delimiter>\n" +
//                "<level></level>\n", b);
//        configer = new Configer_Depreciate(b);
//        String c = configer.toString();
//        assertEquals(b, c);
//    }

    @Test
    public void getConfig() {

        String b = "<delimiters>\\n ;; >></delimiters>\nt1: a1 - xxxxxxx;\n t2: a2 - xxxxxxxxxxxx;\n\n\nt3: a3 - xxxxxxxxxxx\n";
        Configer configer = new Configer(b);
        List<String> list = StringWorker.getTokenList(b);
        configer.withText(b);
        String c = configer.toString();
        assertEquals("<records>True</records>\n" +
                "<parseMode>True</parseMode>\n" +
                "<delimiters>\\n ;; >></delimiters>\n" +
                "<configSplit> </configSplit>\n", c);
        configer.withText(c);
        // toString
        String d = configer.toString();
        assertEquals(c, d);

        //replace
        String f = "<delimiters># @ $</delimiters>\nt1: a1 - xxxxxxx;\n t2: a2 - xxxxxxxxxxxx;\n\n\nt3: a3 - xxxxxxxxxxx\n";
        String e = configer.replaceStr(b);
        boolean ch = e.equals(b);
        assertEquals(b, e);
        int i = 0;
    }

}