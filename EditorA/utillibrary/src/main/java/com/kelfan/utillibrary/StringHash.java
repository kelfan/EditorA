package com.kelfan.utillibrary;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringHash extends LinkedHashMap<Integer, String>{

    int MODE_SORT = 1;
    int MODE_REVERSE = 2;
    int MODE_SORT_REVERSE =3;

    private StringHash change(int mode){
        // comparator for sort
        Comparator<Map.Entry<Integer, String>> valueComparator = new Comparator<Map.Entry<Integer,String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };

        // transform map into list
        List<Map.Entry<Integer, String>> list = new ArrayList<Map.Entry<Integer,String>>(this.entrySet());

        // sort list
        if (mode == MODE_SORT){
            Collections.sort(list,valueComparator);
            Collections.reverse(list);
        }
        if (mode ==MODE_REVERSE){
            Collections.reverse(list);
        }
        if (mode == MODE_SORT_REVERSE){
            Collections.sort(list,valueComparator);
        }

        // transform back into LinkedHashMap
        this.clear();
        for (Map.Entry<Integer, String> map:list){
            this.put(map.getKey(), map.getValue());
        }

        return this;
    }

    public StringHash sort(){
        return change(MODE_SORT);
    }

    public StringHash reverse(){
        return change(MODE_REVERSE);
    }

    public StringHash sort_reverse(){
        return change(MODE_SORT_REVERSE);
    }

    public StringHash putIn(Integer key, String value) {
        super.put(key, value);
        return this;
    }
}
