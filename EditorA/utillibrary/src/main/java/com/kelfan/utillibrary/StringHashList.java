package com.kelfan.utillibrary;

import java.util.LinkedHashMap;
import java.util.Map;

public class StringHashList {
    LinkedHashMap<Long, String> listStr = new LinkedHashMap<>();
    Object[] positionList;

    public StringHashList sync() {
        positionList = listStr.keySet().toArray();
        return this;
    }

    public static StringHashList set(String[] strings) {
        return new StringHashList().putAll(strings);
    }

    public static Long doHash(String text) {
        return (long) (text + Math.random()).hashCode();
    }

    public StringHashList put(Long key, String value) {
        listStr.put(key, value);
        sync();
        return this;
    }

    public StringHashList add(String s) {
        listStr.put(doHash(s), s);
        sync();
        return this;
    }

    public String get(int position) {
        return listStr.get(positionList[position]);
    }

    public int size() {
        return listStr.size();
    }

    public StringHashList putAll(String[] strings) {
        for (String s : strings) {
            listStr.put(doHash(s), s);
        }
        sync();
        return this;
    }


    public StringHashList copy() {
        StringHashList tmp = new StringHashList();
        for (Map.Entry<Long, String> map : listStr.entrySet()) {
            tmp.put(map.getKey(), map.getValue());
        }
        return tmp;
    }

    public StringHashList subContain(String filter) {
        StringHashList tmp = new StringHashList();
        for (Map.Entry<Long, String> map : listStr.entrySet()) {
            String value = map.getValue();
            if (value.contains(filter)) {
                tmp.put(map.getKey(), map.getValue());
            }
        }
        return tmp;
    }

    public String combine(String delimiter) {
        String out = "";
        for (Map.Entry<Long, String> map : listStr.entrySet()) {
            String value = map.getValue();
            out += value + delimiter;
        }
        out = out.substring(0, out.length() - delimiter.length());
        return out;
    }
}
