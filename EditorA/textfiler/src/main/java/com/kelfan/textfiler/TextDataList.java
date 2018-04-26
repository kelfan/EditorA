package com.kelfan.textfiler;

import com.kelfan.utillibrary.ListString;

import java.util.List;

public class TextDataList {

    private List<TextData> dataList;
    private ListString strList;
    private String text;
    private Configer configer;

    public TextDataList(String text) {
        this.text = text;
        this.configer = Configer.set(text);
        this.strList = ListString.set(text).getPatternList(configer.getDelimiters(1));
    }

    public static TextDataList set(String inStr) {
        return new TextDataList(inStr);
    }



}
