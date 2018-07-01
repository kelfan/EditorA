package com.kelfan.utillibrary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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

    public static Set<String> matchAllSet(String text, String pattern) {
        Set<String> allMatches = new LinkedHashSet<String>();;
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


    public static String dropFirstMatch(String text, String pattern){
        List<String> matches = matchAll(text, pattern);
        return text.replace(matches.get(0), "");
    }

}
