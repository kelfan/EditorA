package com.kelfan.textfiler;

import com.kelfan.utillibrary.ListWorker;
import com.kelfan.utillibrary.StringWorker;

import java.util.HashMap;
import java.util.Map;

public class Configer {

    public static String configSplit = "configSplit";
    public static String delimiters = "delimiters";
    public static String levels = "levels";
    public static String records = "records";
    public static String parseMode = "parseMode";

    private HashMap<String, String[]> attributes = new HashMap<String, String[]>();


    public String replaceStr(String inStr) {
        for (Map.Entry<String, String[]> entry : attributes.entrySet()) {
            if (entry.getValue().length > 0) {
                String keySign = getPreFormat(entry.getKey());
                if (inStr.contains(keySign)) {
                    String posSign = getPosFormat(entry.getKey());
                    String content = ListWorker.list2str(entry.getValue(), this.attributes.get(configSplit)[0]);
                    content = content.replaceAll("\n", "\\\\\\\\n");
                    inStr = StringWorker.replaceBetween(inStr, content, keySign, posSign);
                }
            }
        }
        return inStr;
    }

    @Override
    public String toString() {
        String out = "";
        for (Map.Entry<String, String[]> entry : attributes.entrySet()) {
            if (entry.getValue().length > 0) {
                String txt = setXmlContent(ListWorker.list2str(entry.getValue(), this.attributes.get(configSplit)[0]), entry.getKey());
                out += txt;
            }
        }

        return out;
    }

    public Configer() {
        attributes.put(configSplit, new String[]{" "});
        attributes.put(delimiters, new String[]{"\n"});
        attributes.put(levels, new String[]{});
        attributes.put(records, new String[]{"false"});
        attributes.put(parseMode, new String[]{"txt"});
    }

    public Configer(String inStr) {
        this();
        this.withText(inStr);
    }

    public static Configer set(String inStr){
        return new Configer(inStr);
    }

    public Configer withText(String inStr) {
        for (Map.Entry<String, String[]> entry : attributes.entrySet()) {
            String sign = getPreFormat(entry.getKey());
            if (inStr.contains(sign)) {
                String content = getXmlContent(inStr, entry.getKey());
                if (entry.getKey().equals(configSplit)) {
                    entry.setValue(new String[]{content});
                } else {
                    entry.setValue(content.split(attributes.get(configSplit)[0]));
                }
            }
        }
        return this;
    }

    public String[] getLevels(){
        return attributes.get(levels);
    }

    public String[] getDelimiters(){
        return attributes.get(delimiters);
    }

    public String getDelimiters(int n){
        return attributes.get(delimiters)[n-1];
    }

    public String getConfigSplit(){
        return attributes.get(configSplit)[0];
    }


    public static String getPreFormat(String indicator) {
        return String.format("<%s>", indicator);
    }

    public static String getPosFormat(String indicator) {
        return String.format("</%s>\n", indicator);
    }

    public static String getXmlContent(String textIn, String indicator) {
        String pre = getPreFormat(indicator);
        String pos = getPosFormat(indicator);
        if (textIn.contains(pre)) {
            return StringWorker.getBetween(textIn, pre, pos).replaceAll("\\\\n", "\n");
        }
        return "";
    }

    public static String setXmlContent(String textIn, String indicator) {
        String pre = getPreFormat(indicator);
        String pos = getPosFormat(indicator);
        textIn = textIn.replaceAll("\n", "\\\\n");
        return pre.concat(textIn).concat(pos);
    }


    public Configer(HashMap<String, String[]> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String, String[]> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String[]> attributes) {
        this.attributes = attributes;
    }
}
