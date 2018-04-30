//package com.kelfan.textfiler;
//
//public class LineParser {
//    private String text;
//    private String[] strlist;
//    private DataList dataList;
//
//    public LineParser(String inStr) {
//        this.text = inStr;
//    }
//
//    public static LineParser set(String inStr) {
//        LineParser lineParser = new LineParser(inStr);
//        return lineParser;
//    }
//
//    public LineParser split() {
//        this.strlist = this.text.split("\n");
//        return this;
//    }
//
//    public LineParser toData(){
//        dataList = new DataList();
//        for (String s: this.strlist){
//            DataObject dataObject = new DataObject();
//        }
//        return this;
//    }
//
//    public LineParser(){
//        dataList = new DataList();
//        dataList.add(new DataObject());
//    }
//
//
//
//}
