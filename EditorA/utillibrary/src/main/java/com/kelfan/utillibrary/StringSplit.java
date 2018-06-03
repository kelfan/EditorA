package com.kelfan.utillibrary;

import org.w3c.dom.Text;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StringSplit {

    String text;
    String delimiter="";
    String filter="";
    LinkedHashMap<Long, String> listStr = new LinkedHashMap<>();
    LinkedHashMap<Long, String> listFiltered = new LinkedHashMap<>();
    Object[] positionList;
    Object[] positionFiltered;

    public static StringSplit set(String text) {
        return new StringSplit().withText(text);
    }

    public StringSplit withText(String text) {
        this.text = text;
        return this;
    }

    public StringSplit withDeilimiter(String delimiter) {
        this.delimiter = delimiter;
        return this.doDelimiter().doFilter().syncPositions();
    }

    public StringSplit doDelimiter() {
        String[] list = this.text.split(delimiter);
        listStr = new LinkedHashMap<>();
        for (String s : list) {
            Long postion = doHash(s);
            listStr.put(postion, s);
        }
        return this;
    }

    public StringSplit withFilter(String filter){
        this.filter = filter;
        doFilter().syncPositions();
        return this;
    }

    public StringSplit doFilter(){
        String[] filterList = filter.split(" ");
        listFiltered = new LinkedHashMap<>();
        mapLoop:
        for (Map.Entry<Long, String> m : listStr.entrySet()){
            for (String s: filterList){
                if (!m.getValue().contains(s)){
                    continue mapLoop;
                }
            }
            listFiltered.put(m.getKey(), m.getValue());
        }
        return this;
    }

    public static Long doHash(String text) {
        return (long) (text + Math.random()).hashCode();
    }


    public StringSplit syncPositions(){
        positionList = listStr.keySet().toArray();
        positionFiltered = listFiltered.keySet().toArray();
        return this;
    }

    public String get(int position){
        return listStr.get(positionFiltered[position]);
    }

    public int size(){
        return listStr.size();
    }

    public int sizeFiltered(){
        return listFiltered.size();
    }

}