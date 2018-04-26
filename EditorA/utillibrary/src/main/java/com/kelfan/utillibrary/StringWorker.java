package com.kelfan.utillibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 1/01/2018.
 */

public class StringWorker {
    /**
     * transform a String into a list based on
     *
     * @param inString  A String
     * @param delimiter The delimiter for splitting text
     * @return a list
     */
    public static ArrayList<String> stringToList(String inString, String delimiter) {
        return new ArrayList<>(Arrays.asList(inString.split(delimiter)));
    }

    public static ArrayList<String> stringToListByLine(String inString) {
        return stringToList(inString, "\n");
    }

    public static String stringSortByLine(String inString, boolean desc) {
        ArrayList<String> list = stringToListByLine(inString);
        if (!desc) {
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        } else {
            Collections.sort(list, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        }
        return listToStringByLine(list);
    }

    /**
     * transform List of String into a single String
     *
     * @param inList list of String
     * @return a single String
     */
    public static String listToString(ArrayList<String> inList, String delimiter) {
        String listString = "";
        for (String s : inList) {
            listString += s + delimiter;
        }
        return listString;
    }

    public static String listToStringByLine(ArrayList<String> inList) {
        return listToString(inList, "\n");
    }

    public static String replaceByLine(String inStr, String supersede, String replace) {
        return inStr.replace(supersede, replace);
    }

    public static long countLetter(String inStr, String letter) {
        return (inStr.length() - inStr.replace(letter, "").length()) / letter.length();
    }

    public static String getLast2end(String inStr, String delimiter) {
        if (inStr.contains(delimiter)) {
            if (delimiter.equals(".")) {
                delimiter = "[.]";
            }
            String[] txtList = inStr.split(delimiter);
            String result = txtList[txtList.length - 1];
            return result;
        }
        return inStr;
    }

    /**
     * get String between to sign like get 345 from 12345678 between 2 and 6
     *
     * @param inStr
     * @param preSign
     * @param posSign
     * @return
     */
    public static String getBetween(String inStr, String preSign, String posSign) {
        int preInt = inStr.indexOf(preSign) + preSign.length();
        int posInt = inStr.indexOf(posSign, preInt);
        if (preInt <= posInt) {
            return inStr.substring(preInt, posInt);
        } else {
            return inStr.substring(preInt);
        }
    }

    public static String list2str(String[] inVar, String delimiter) {
        StringBuilder result = new StringBuilder();
        if (inVar.length > 0) {
            if ((inVar.length == 1 & inVar[0].equals(""))) {
                return result.toString();
            }
            for (String s : inVar) {
                result.append(s).append(delimiter);
            }
        }
        return result.toString();
    }

    public static List<String> getPatternList(String inStr, String patternIn) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(patternIn).matcher(inStr);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    public static List<String> getTokenList(String inStr) {
        return getPatternList(inStr, "[^(a-zA-Z0-9\\\\u4e00-\\\\u9fa5)]*[(a-zA-Z0-9\\\\u4e00-\\\\u9fa5)]+[^(a-zA-Z0-9\\\\u4e00-\\\\u9fa5)]*");
    }

    public static String replaceBetween(String inStr, String replace, String preSign, String posSign) {
        String regex = preSign + ".*" + posSign;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inStr);
        return matcher.replaceAll(preSign + replace + posSign);
    }
}
