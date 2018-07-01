package com.kelfan.utillibrary;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LunarSolarConverterTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        Solar solar = new Solar();
        solar.solarYear = 2018;
        solar.solarMonth = 7;
        solar.solarDay = 1;
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        assertEquals(23, lunar.lunarDay + lunar.lunarMonth);

        Date date = LunarSolarConverter.lunar2solar(2018, 5, 18);
        assertEquals("2018-07-01", TimeWorker.formatDate("yyyy-MM-dd", date));
    }
}