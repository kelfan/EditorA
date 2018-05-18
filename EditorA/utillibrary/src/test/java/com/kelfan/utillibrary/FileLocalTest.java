package com.kelfan.utillibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FileLocalTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        String s = "F:/test/test/abc.txt";
        FileLocal f = FileLocal.set(s);
        f.addPostfix("_archive");
        f.checkFile();
        f.saveFile("test", FileLocal.MODE_APPEND);
        boolean b = f.rename("test2");
        assertEquals(4, 2 + 2);
    }
}