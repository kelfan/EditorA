package com.kelfan.utillibrary;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StringHashTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {

        StringHash stringHash = new StringHash();
        stringHash.put(1, "a");
        stringHash.put(2, "f");
        stringHash.put(3, "c");
        stringHash.sort();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(1, "a");
        linkedHashMap.put(3, "c");
        linkedHashMap.put(2, "f");
        assertEquals(linkedHashMap, stringHash);
    }
}