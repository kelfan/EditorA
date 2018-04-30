package com.kelfan.textfiler;

import java.util.HashMap;

public class DataMap {
    private HashMap dataMap;

    private static String content = "content";
    private String level = "level";
    private String importance = "importance";
    private String recordTime = "recordTime";
    private String updateTime = "updateTime";

    public DataMap() {
        dataMap = new HashMap();
    }

    public static DataMap set(String text){
        DataMap dMap = new DataMap();
        dMap.put(content, text);
        return dMap;
    }

    public DataMap put(String key, Object value) {
        dataMap.put(key, value);
        return this;
    }
}
