package com.kelfan.utillibrary;

public class AtSign {
    final String pre = "@";
    final String pos = " ";
    String sign = "";
    String content = "";
    String value = "";

    public static AtSign set(String content, String sign){
        AtSign atSign = new AtSign().withSign(sign).withContent(content).auto();
        return atSign;
    }

    public AtSign withSign(String sign){
        this.sign = sign;
        return this;
    }

    public AtSign withContent(String content){
        this.content = content;
        return this;
    }

    AtSign auto(){
        doValue();
        return this;
    }

    AtSign doValue() {
        String sign = pre + this.sign + "_";
        int start = content.indexOf(sign);
        if (!(start < 0)) {
            int end = content.indexOf(pos, start);
            if (end == -1) {
                end = content.length();
            }
            value = content.substring(start + sign.length(), end);
        }
        return this;
    }

    public String getValue() {
        return value;
    }

}
