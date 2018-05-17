package com.kelfan.utillibrary;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ListStringTest {
    @Test
    public void Constructor() {
        // listString.set(list)
        String a = "1_2,3,4";
        String[] alist = a.split(",");
        ListString la = ListString.set(alist);
        String b = la.toString();
        assertEquals("1_234", b);

        //set(str)
        la = ListString.set(a);
        b = la.toString();
        assertEquals(a , b );
        assertEquals(1, la.size());

        a = "1,2,3\n4,5,6\n\n";
        la = ListString.set(a);
        b = la.toString();
        assertEquals(a, b);
        assertEquals(2, la.size());

        for (String i: la){
            b=i;
        }

        int z = 0 ;
    }

    @Test
    public void getLines() {
        String a = "1_2,3,4\n56\n78";
        String[] alist = a.split(",");
        ListString la = ListString.set(a).setDelimiterWithoutDelimiter("\n").getPatternList();

        int z = 9;
    }
}