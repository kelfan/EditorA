package com.kelfan.textfiler.depreciate;

import com.kelfan.utillibrary.StringWorker;

public class Configer_Depreciate {

    private String indicator_config = "config";
    private String indicator_delimiter = "delimiter";
    private String indicator_level = "level";
    private String[] split_config = {" "};

    private String[] delimiter = {"\n"};
    private String[] levels = {};
    private boolean records = true;
    private boolean signOnLeft = true;

    Configer_Depreciate() {
    }

    public boolean isRecords() {
        return records;
    }

    public void setRecords(boolean records) {
        this.records = records;
    }

    public Configer_Depreciate(String[] delimiter, String[] levels, boolean records) {

        this.delimiter = delimiter;
        this.levels = levels;
        this.records = records;
    }

    public Configer_Depreciate(boolean records) {

        this.records = records;
    }

    Configer_Depreciate(String inStr) {
        this.withTextIn(inStr);
    }

    public void setConfig(Object config, String indicator, String inStr){

        String sign = getPreFormat(indicator);
        if (inStr.contains(sign)) {
            String delimiterStr = getXmlContent(inStr, indicator);
            config =  delimiterStr.split(split_config[0]);
        }
    }

    public Configer_Depreciate withTextIn(String inStr){
        setConfig(this.split_config, indicator_config, inStr);
        setConfig(this.delimiter, indicator_delimiter, inStr);
//        this.split_config = getConfig(inStr, indicator_config);
//        if (this.split_config[0].equals("")){ this.split_config[0] = "";}
//        this.delimiter = getConfig(inStr, indicator_delimiter);
//        this.levels = getConfig(inStr, indicator_level);

        return this;
    }

    public Configer_Depreciate(String[] delimiter) {
        this.delimiter = delimiter;
    }

    public Configer_Depreciate(String[] delimiter, String[] levels) {
        this.delimiter = delimiter;
        this.levels = levels;
    }

    public String[] getDelimiter() {

        return delimiter;
    }

    public Configer_Depreciate setDelimiter(String[] delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public String[] getLevels() {
        return levels;
    }

    public Configer_Depreciate setLevels(String[] levels) {
        this.levels = levels;
        return this;
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

    @Override
    public String toString() {
        String delimiterStr = setXmlContent(StringWorker.list2str(delimiter, split_config[0]), indicator_delimiter);
        String levelStr = setXmlContent(StringWorker.list2str(levels, split_config[0]), indicator_level);
        return String.format("%s%s", delimiterStr, levelStr);
    }
}
