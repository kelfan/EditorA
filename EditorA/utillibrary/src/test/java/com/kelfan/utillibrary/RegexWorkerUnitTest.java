package com.kelfan.utillibrary;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RegexWorkerUnitTest {
    @Test
    public void addition_isCorExampleUnitTestrect() {
        assertEquals(4, 2 + 2);
        String i = "test/t/t: hello";
        assertEquals("/t/t: hello", RegexWorker.signToEnd(i, "/"));

    }

    @Test
    public void getAllSet() {
        String s = "# 待做/ 周一 phd meeting \n" +
                "\n" +
                "# 待做/安卓/ 每月循化\n" +
                "\n" +
                "# 13424\n" +
                "\n" +
                "# 待做/ 测试 \n" +
                "\n" +
                "# 别的/别的/别的/ 测试\n";
        Set<String> l = RegexWorker.matchAllSet(s, "(# )(.*)/");
        l.add("# 全部/");
        String out = l.toString();
        assertEquals("[# 待做/, # 待做/安卓/, # 别的/别的/别的/, # 全部/]", out);
    }
}