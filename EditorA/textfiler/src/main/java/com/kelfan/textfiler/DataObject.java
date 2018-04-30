package com.kelfan.textfiler;

import com.kelfan.utillibrary.StringLocal;
import com.kelfan.utillibrary.TimeWorker;
import com.kelfan.utillibrary.Xmler;

public class DataObject {

    private String data;
    private int level = 0;
    private String createTime = "createTime";
    private String modifyTime = "modifyTime";
    private String upLevel = "#";
    private String lowerLevel = "\t";


    public static DataObject set(String inStr){
        DataObject dataObject = new DataObject();
        dataObject.setData(inStr);
        return dataObject;
    }

    public DataObject setData(String data) {
        this.data = data;
        getLevel();
        return this;
    }

    private DataObject getLevel(){
        StringLocal d = StringLocal.set(data);
        if (d.countLetter(upLevel)>0){
            level += d.countLetter(upLevel);
        }else if (d.countLetter(lowerLevel)> 0 ){
            level -= d.countLetter(lowerLevel);
        }
        return this;
    }

    public DataObject changeContent(String newContent){
        String out = "";
        if (data.contains("<" + createTime +">")) {
            out = Xmler.set(data, createTime).setXmlContent();
        }
        data = newContent + out;
        return this;
    }

    public String getContent(){
        return Xmler.set(Xmler.set(data, createTime).getRemain(), modifyTime).getRemain();
    }

    @Override
    public String toString() {
        String out = data;
        if (!out.contains("<" + createTime + ">")){
            out = Xmler.set(out, createTime).setContent(TimeWorker.getLocalTime()).toString();
        }
        return Xmler.set(out, modifyTime).setContent(TimeWorker.getLocalTime()).toString();
    }
}
