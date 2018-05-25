package com.kelfan.utillibrary;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringHash extends LinkedHashMap<Integer, String> {

    private int MODE_SORT = 1;
    private int MODE_REVERSE = 2;
    private int MODE_SORT_REVERSE = 3;

    private StringHash change(int mode) {
        // comparator for sort
        Comparator<Map.Entry<Integer, String>> valueComparator = new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        };

        // transform map into list
        List<Map.Entry<Integer, String>> list = new ArrayList<Map.Entry<Integer, String>>(this.entrySet());

        // sort list
        if (mode == MODE_SORT) {
            Collections.sort(list, valueComparator);
            Collections.reverse(list);
        }
        if (mode == MODE_REVERSE) {
            Collections.reverse(list);
        }
        if (mode == MODE_SORT_REVERSE) {
            Collections.sort(list, valueComparator);
        }

        // transform back into LinkedHashMap
        this.clear();
        for (Map.Entry<Integer, String> map : list) {
            this.put(map.getKey(), map.getValue());
        }

        return this;
    }

    public StringHash sort() {
        return change(MODE_SORT);
    }

    public StringHash reverse() {
        return change(MODE_REVERSE);
    }

    public StringHash sort_reverse() {
        return change(MODE_SORT_REVERSE);
    }

    public StringHash putIn(Integer key, String value) {
        super.put(key, value);
        return this;
    }

    public StringHash putIn(String value) {
        super.put(getKeyHash(value), value);
        return this;
    }

    public StringHash putAll(Collection<? extends String> collection) {
        for (String s : collection) {
            this.putIn(s);
        }
        return this;
    }

    public StringHash putAll(StringHash stringHash){
        for (int i: stringHash.keySet()){
            this.putIn(i, stringHash.get(i));
        }
        return this;
    }

    public StringHash copy(StringHash stringHash){
        this.clear();
        this.putAll(stringHash);
        return this;
    }

    public StringHash copy(){
        StringHash stringHash = new StringHash();
        for (int i: this.keySet()){
            stringHash.putIn(i, this.get(i));
        }
        return  stringHash;
    }

    public StringHash removeAll(Collection<? extends String> collection) {
        StringHash stringHash = new StringHash();
        keyLoop:
        for (int key : this.keySet()) {
            String value = this.get(key);
            for (String s : collection) {
                if (value.equals(s)) {
                    continue keyLoop;
                }
            }
            stringHash.putIn(key, value);
        }
        this.copy(stringHash);
        return this;
    }

    public StringHash retainAll(Collection<? extends String> collection) {
        StringHash stringHash = new StringHash();
        keyLoop:
        for (int key : this.keySet()) {
            String value = this.get(key);
            for (String s : collection) {
                if (value.equals(s)) {
                    stringHash.putIn(key, value);
                    continue keyLoop;
                }
            }
        }
        this.copy(stringHash);
        return this;
    }

    public StringHash filter(String s) {
        StringHash stringHash = new StringHash();
        for (int key : keySet()) {
            String value = this.get(key);
            if (value.contains(s)) {
                stringHash.putIn(key, value);
            }
        }
        return stringHash;
    }

    public StringHash add(int i, String s) {
        StringHash stringHash = new StringHash();
        int counter = 0;
        for (int key : keySet()) {
            if (counter == i) {
                stringHash.putIn(s);
            } else {
                stringHash.putIn(key, this.get(key));
            }
            counter += 1;
        }
        if (counter < i) {
            for (int j = 0; j < i - counter - 1; j++) {
                stringHash.putIn("");
            }
            stringHash.putIn(s);
        }
        this.copy(stringHash);
        return this;
    }

    public String set(int i, String s) {
        StringHash stringHash = new StringHash();
        String out = "";
        int counter = 0;
        for (int key : this.keySet()) {
            if (counter == i) {
                out = this.get(key);
                stringHash.putIn(key, s);
            } else {
                stringHash.putIn(key, this.get(key));
            }
            counter += 1;
        }
        this.copy(stringHash);
        return out ;
    }

    public static StringHash set(List<String> strings) {
        StringHash stringHash = new StringHash();
        for (String s : strings) {
            int key = getKeyHash(s);
            stringHash.putIn(key, s);
        }
        return stringHash;
    }

    public static StringHash set(String[] strings) {
        StringHash stringHash = new StringHash();
        for (String s : strings) {
            int key = getKeyHash(s);
            stringHash.putIn(key, s);
        }
        return stringHash;
    }

    public String getItem(int position) {
        int i = 0;
        for (String s : this.values()) {
            if (position == i) {
                return s;
            }
            i++;
        }
        return null;
    }

    public static int getKeyHash(String s) {
        return (int) Math.round(System.currentTimeMillis() + Math.random() + s.hashCode());
    }

    @Override
    public String toString() {
        String out = "";
        for (String v : values()) {
            out += v;
        }
        return out;
    }


    public int indexOf(Object o) {
        int i = 0;
        for (String s : this.values()) {
            if (s.equals(o)) {
                return i;
            }
            i++;
        }
        return -1;
    }


    public int lastIndexOf(Object o) {
        int i = this.size();
        List<String> list = new ArrayList<>(this.values());
        Collections.reverse(list);
        for (String s : list) {
            if (s.equals(o)) {
                return i;
            }
            i--;
        }
        return -1;
    }

}
