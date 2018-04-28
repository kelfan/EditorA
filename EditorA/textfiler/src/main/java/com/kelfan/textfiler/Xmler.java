package com.kelfan.textfiler;

import com.kelfan.utillibrary.StringWorker;

public class Xmler {
    private String text;
    private String indicator;
    private String content;
    private String remain;
    private boolean putTail = true;
    private boolean newLine = false;

    private Xmler(String text, String indicator) {
        this.text = text;
        this.indicator = indicator;
        getXmlContent();
        getRemain();
    }

    public static Xmler set(String text, String indicator) {
        return new Xmler(text, indicator);
    }

    public Xmler setContent(String content) {
        this.content = content;
        return this;
    }

    private String getPreFormat() {
        return String.format("<%s>", indicator);
    }

    private String getPosFormat() {
        return String.format("</%s>", indicator);
    }

    private Xmler getXmlContent() {
        String pre = getPreFormat();
        String pos = getPosFormat();
        content = "";
        if (text.contains(pre)) {
            content = StringWorker.getBetween(text, pre, pos).replaceAll("\\\\n", "\n");
        }
        return this;
    }

    public Xmler getRemain(){
        remain = text.replaceAll(getRegex(), "");
        return this;
    }

    private String setXmlContent() {
        String pre = getPreFormat();
        String pos = getPosFormat();
        content = content.replaceAll("\n", "\\\\n");
        return pre.concat(content).concat(pos);
    }

    private String getRegex() {
        return "<" + indicator + ">(.*)</" + indicator + ">";
    }

    @Override
    public String toString() {
        String out = "";
        if (text.contains(getPreFormat())) {
            out = text.replaceAll(getRegex(), setXmlContent());
        }else{
            String xml = setXmlContent();
            if (newLine){
                xml += "\n";
            }
            if (putTail){
                out = remain.concat(xml);
            }else{
                out = xml.concat(remain);
            }
        }
        return out;
    }
}
