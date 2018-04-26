package com.kelfan.textfiler;

import com.kelfan.utillibrary.ListString;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.andro2id.com/tools/testing">Testing documentation</a>
 */
public class DataSetUnitTest {
    @Test
    public void addition_isCorrect() {
        String  a = "<levels># \t</levels>\n# 12\n \td1\n\t\td2\n# 13\n\td1";
        Configer c = new Configer(a);
        if (c.getAttributes().get(Configer.parseMode)[0].toUpperCase().equals("signOnRight")){
//            String[] strList = a.split(c.getAttributes().get(Configer.delimiters)[0]);
            ListString strList = ListString.set(a);
            String[] levels = c.getLevels();
            TextData textData = new TextData();
            textData.setData(strList.get(0));
            textData.setSigns(levels);
            int z = 0;
        }else{

        }
        assertEquals(4, 2 + 2);
    }
}