package com.kelfan.textfiler;

public class TextData {

    private String data;
    private int level;
    private String recordsTime;
    private String modifyTime;

    public String[] getSigns() {
        return signs;
    }

    public void setSigns(String[] signs) {
        this.signs = signs;
    }

    private String[] signs;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRecordsTime() {
        return recordsTime;
    }

    public void setRecordsTime(String recordsTime) {
        this.recordsTime = recordsTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
