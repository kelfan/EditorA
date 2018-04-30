package com.kelfan.editfiler;

import com.kelfan.utillibrary.StringLocal;

public class DataUnit {

    private String data;
    private int level = 0;
    private String upLevel = "#";
    private String lowerLevel = "\t";

    public static DataUnit set(String inStr){
        DataUnit data = new DataUnit();
        data.setData(inStr);
        return data;
    }

    public DataUnit setData(String data) {
        this.data = data;
        getLevel();
        return this;
    }

    private DataUnit getLevel(){
        StringLocal d = StringLocal.set(data);
        if (d.countLetter(upLevel)>0){
            level += d.countLetter(upLevel);
        }else if (d.countLetter(lowerLevel)> 0 ){
            level -= d.countLetter(lowerLevel);
        }
        return this;
    }

    public DataUnit changeContent(String newContent){
        data = newContent;
        return this;
    }

    public String getContent(){
        return data;
    }

    @Override
    public String toString() {
        return data;
    }

}
