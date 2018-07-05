package com.kelfan.utillibrary;

public class AtSign {
    private final String pre = "@";
    private final String pos = " ";
    private String sign = "";
    private String content = "";
    String remain = "";
    String value = "";
    String separator = "_";


    public static AtSign set(String content, String sign) {
        AtSign atSign = new AtSign().withSign(sign).withContent(content).auto();
        return atSign;
    }

    public AtSign withSign(String sign) {
        this.sign = sign;
        return this;
    }

    public AtSign withContent(String content) {
        this.content = content;
        return this;
    }

    AtSign auto() {
        doValue();
        return this;
    }

    AtSign doValue() {
        String sign = pre + this.sign + separator;
        int start = content.indexOf(sign);
        if (!(start < 0)) {
            int end2 = content.indexOf("\n", start);
            int end = content.indexOf(pos, start);
            if (end > end2 && end2 != -1) {
                end = end2;
            }
            if (end == -1) {
                end = content.length();
            }
            value = content.substring(start + sign.length(), end);
        }
        remain = content.replace(sign + value + " ", "");
        if (remain.length() == content.length()) {
            remain = remain.replace(sign + value, "");
        }
        return this;
    }

    public AtSign updateValue(String text) {
        if (content.contains(pre + sign)) {
            content = content.replace(pre + sign + separator + value, pre + sign + separator + text);
        } else {
            content += pos + pre + sign + separator + text + pos;
        }
        value = text;
        return this;
    }

    public String getValue() {
        return value;
    }

    public String getRemain() {
        return remain;
    }

    @Override
    public String toString() {
        return content;
    }
}
