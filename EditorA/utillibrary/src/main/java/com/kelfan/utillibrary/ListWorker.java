package com.kelfan.utillibrary;

import java.util.Arrays;

/**
 * Created by Administrator on 19/03/2018.
 */

public class ListWorker {
    Boolean contain(String[] list, String item) {
        return Arrays.asList(list).contains(item);
    }

    public static String list2str(String[] list, String delimiter){
        String out = "";
        for (String item: list){
            out += item + delimiter;
        }
        out = out.substring(0, out.length()-delimiter.length());
        return out;
    }
}
