package com.kelfan.utillibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringHashList {
    LinkedHashMap<Long, String> listStr = new LinkedHashMap<>();
    Object[] positionList;

    public StringHashList sync() {
        positionList = listStr.keySet().toArray();
        return this;
    }

    public StringHashList syncFromPosition() {
        LinkedHashMap<Long, String> tmp = new LinkedHashMap<>();
        for (Object l : positionList) {
            tmp.put((Long) l, listStr.get(l));
        }
        listStr = tmp;
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

    public String get(Long key) {
        return listStr.get(key);
    }

    public Long getKey(int position) {
        return (Long) positionList[position];
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

    public StringHashList notContain(String filter) {
        StringHashList tmp = new StringHashList();
        for (Map.Entry<Long, String> map : listStr.entrySet()) {
            String value = map.getValue();
            if (value.contains(filter)) {
                continue;
            }
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

    public ArrayList<Long> subContainKeys(String filter) {
        ArrayList<Long> tmp = new ArrayList<Long>();
        for (Map.Entry<Long, String> map : listStr.entrySet()) {
            String value = map.getValue();
            if (value.contains(filter)) {
                tmp.add(map.getKey());
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
        if (out.equals("")) {
            return out;
        }
        out = out.substring(0, out.length() - delimiter.length());
        return out;
    }

    public void swap(int i, int j) {
        Object tmp = positionList[i];
        positionList[i] = positionList[j];
        positionList[j] = tmp;
        syncFromPosition();
    }

    public void swapByKey(Long key1, Long key2) {
        int counter = 0;
        int i = -1, j = -1;
        for (Object p : positionList) {
            Long l = (Long) p;
            if (l == key1) {
                i = counter;
            }
            if (l == key2) {
                j = counter;
            }
            counter++;
        }
        if (i != -1 && j != -1) {
            swap(i, j);
        }
    }

    public void remove(int position) {
        listStr.remove(positionList[position]);
        sync();
    }

    public void removeByKey(Long index) {
//        if (listStr.containsKey(index)) {
        listStr.remove(index);
//        }
        sync();
    }

    public LinkedHashMap<Long, String> getValues() {
        return listStr;
    }

    public void sort() {
        ArrayList<String> strings = new ArrayList<>();
        for (String s : listStr.values()) {
            strings.add(s);
        }
        Collections.sort(strings);
        LinkedHashMap<Long, String> temp = new LinkedHashMap<>();
        for (String s : strings) {
            temp.put(doHash(s), s);
        }
        listStr = temp;
        sync();
    }

    public void sort(String sign) {
        int len = 10;
        ArrayList<String> strings = new ArrayList<>();
        for (String s : listStr.values()) {
            String prefix = AtSign.set(s, sign).getValue();
            len = prefix.length();
            if (len>0){
                prefix = String.format("%" + len + "s", prefix).replace(" ", "`");
                prefix = prefix.substring(0, len);
                strings.add(prefix + s);
            }
        }
        Collections.sort(strings);
        LinkedHashMap<Long, String> temp = new LinkedHashMap<>();
        for (String s : strings) {
            s = s.substring(len, s.length());
            temp.put(doHash(s), s);
        }
        listStr = temp;
        sync();
    }

    public void reverse() {
        ArrayList<Object> tmp = new ArrayList<>(Arrays.asList(positionList));
        Collections.reverse(tmp);
        positionList = tmp.toArray();
        syncFromPosition();
    }

}
