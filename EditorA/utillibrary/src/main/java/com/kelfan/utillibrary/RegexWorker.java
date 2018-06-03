package com.kelfan.utillibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexWorker {

    /**
     * get all string match patter
     * @param text
     * @param pattern
     * @return
     */
    public static List<String> matchAll(String text, String pattern) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }


    public static String signToEnd(String text, String sign){
        String pattern =  "[".concat(sign).concat("][\\s\\S]*");
        return matchAll(text,  pattern).get(0);
    }


}
