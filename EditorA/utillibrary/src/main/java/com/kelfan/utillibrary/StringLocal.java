package com.kelfan.utillibrary;

public class StringLocal {

    private String inStr = "";

    public String getInStr() {
        return inStr;
    }

    public void setInStr(String inStr) {
        this.inStr = inStr;
    }

    public StringLocal(String inStr) {

        this.inStr = inStr;
    }

    public String getBetween(String preSign, String posSign){
        int preInt = inStr.indexOf(preSign);
        int posInt = inStr.indexOf(posSign, preInt);
        return inStr.substring(preInt, posInt);
    }


}
