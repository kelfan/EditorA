package com.kelfan.utillibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static void swapItem(int i, int j, List... lists){
        for (List list: lists){
            Object tmp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, tmp);
        }
    }

}
