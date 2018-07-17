package com.kelfan.utillibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StirngHashListTest {
    @Test
    public void test_reverse() {
        String s = "1,2,3,4";
        StringHashList sh = StringHashList.set(s.split(","));
        sh.reverse();
        assertEquals("4,3,2,1", sh.combine(","));
    }
}
