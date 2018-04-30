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

    public static StringLocal set(String inStr){
        return new StringLocal(inStr);
    }

    public String getBetween(String preSign, String posSign){
        int preInt = inStr.indexOf(preSign);
        int posInt = inStr.indexOf(posSign, preInt);
        return inStr.substring(preInt, posInt);
    }

    public int countLetter(String letter){
        return (inStr.length() - inStr.replace(letter, "").length()) / letter.length();
    }

    @Override
    public String toString() {
        return inStr;
    }

    public StringLocal concat(String inStr){
        this.inStr.concat(inStr);
        return this;
    }

    public boolean contain(String inStr){
        return this.inStr.contains(inStr);
    }

}
