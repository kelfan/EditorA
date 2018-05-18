package com.kelfan.utillibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLocal {

    private String inStr = "";
    private String pattern = "";

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
        int preInt = inStr.indexOf(preSign) + preSign.length();
        int posInt = inStr.indexOf(posSign, preInt);
        return inStr.substring(preInt, posInt);
    }

    public StringLocal getRemain(){
        return getRemain(this.pattern);
    }

    public StringLocal getRemain(String pattern){
        this.pattern = pattern;
        List<String> all = getPatterns(pattern);
        if (all.size()>0){
            String tmp = inStr;
            String tmp2 = all.get(0);
            return StringLocal.set(tmp.replace(tmp2, ""));
        }else{
            return StringLocal.set(inStr);
        }
    }

    public StringLocal getPattern(String pattern){
        this.pattern = pattern;
        List<String> all = getPatterns(pattern);
        if (all.size() > 0 ){
            return StringLocal.set(all.get(0));
        }else{
            return StringLocal.set("");
        }
    }

    public List<String> getPatterns(String pattern){
        this.pattern = pattern;
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern).matcher(this.inStr);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
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
