package com.kelfan.utillibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AtSignTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        String s = "then test @test_123 11124 111";
        AtSign a = AtSign.set(s, "test");
        assertEquals("123", a.value);
    }
}