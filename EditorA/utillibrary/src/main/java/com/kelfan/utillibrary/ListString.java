package com.kelfan.utillibrary;

import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListString implements List<String> {

    private String text;
    private List<String> strList;
    private String pattern = "([^\n\r]+[\n\r]*)";
    private String delimiter = "";

    public ListString(String inStr) {
        this.text = inStr;
        getLineList(inStr);
    }

    public ListString(String inStr, String inPattern) {
        this.text = inStr;
        getPatternList(inPattern);
    }

    public ListString setDelimiter(String delimiter){
        this.pattern = String.format("([^(%s)]+[%s]*)", delimiter, delimiter);
        this.delimiter = delimiter;
        return this;
    }

    public ListString setDelimiterWithoutDelimiter(String delimiter){
        this.pattern = String.format("([^%s]+(?=[%s]*))", delimiter, delimiter);
        this.delimiter = delimiter;
        return this;
    }

    public ListString setSeparateSign(String separator){
        this.pattern = String.format("([^%s]+[%s]*)", separator, separator);
        this.delimiter = separator;
        return this;
    }

    public ListString sortItem(){
        Collections.sort(strList);
        return this;
    }

    public ListString setPattern(String pattern) {
        getPatternList(pattern);

        return this;
    }

    public List<String> getStrList() {
        return strList;
    }

    public ListString(List<String> strList) {
        this.text = strList.toString();
        this.strList = strList;
        this.pattern = "";
    }

    public ListString(String[] strList) {
        this.text = strList.toString();
        this.strList = Arrays.asList(strList);
        this.pattern = "";
    }

    public String conbine(String delimiter){
        String out = "";
        for (String s: strList){
            out += s + delimiter;
        }
        return out;
    }

    public static ListString set(String inStr) {
        return new ListString(inStr);
    }

    public static ListString set(List<String> inList) {
        return new ListString(inList);
    }

    public static ListString set(String[] inList){
        return new ListString(inList);
    }

    public static ListString set(String inStr, String inPattern) {
        return new ListString(inStr, inPattern);
    }

    Boolean contain(String[] list, String item) {
        return Arrays.asList(list).contains(item);
    }

    public String toString(String delimiter) {
        StringBuilder out = new StringBuilder();
        for (String item : this.strList) {
            out.append(item).append(delimiter);
        }
        out = new StringBuilder(out.substring(0, out.length() - delimiter.length()));
        return out.toString();
    }

    public ListString getPatternList() {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(this.pattern).matcher(this.text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        this.strList = allMatches;
        return this;
    }

    public ListString getPatternList(String patternIn) {
        this.pattern = patternIn;
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(this.pattern).matcher(this.text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        this.strList = allMatches;
        return this;
    }

    public ListString getSplitList(){
        this.strList = new ArrayList<String>(Arrays.asList(this.text.split(this.delimiter)));
        this.strList.removeAll(Arrays.asList(null, "", "\n"));
        return this;
    }

    public ListString getTokenList(String inStr) {
        getPatternList("[^(a-zA-Z0-9\\\\u4e00-\\\\u9fa5)]*[(a-zA-Z0-9\\\\u4e00-\\\\u9fa5_)]+[^(a-zA-Z0-9\\\\u4e00-\\\\u9fa5)]*");
        return this;
    }

    public ListString getLineList(String inStr) {
        getPatternList("([^\n\r]+[\n\r]*)");
        return this;
    }

    public StringLocal getItem(int i){
        return StringLocal.set(this.strList.get(i));
    }

    @Override
    public String toString() {
        return toString(this.delimiter);
    }

    @Override
    public int size() {
        return strList.size();
    }

    @Override
    public boolean isEmpty() {
        return strList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return strList.contains(o);
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return strList.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return strList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return strList.toArray(ts);
    }

    @Override
    public boolean add(String s) {
        return strList.add(s);
    }

    @Override
    public boolean remove(Object o) {
        return strList.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return strList.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends String> collection) {
        return strList.addAll(collection);
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
        return strList.addAll(collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return strList.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return strList.retainAll(collection);
    }

    @Override
    public void clear() {
        strList.clear();
    }

    @Override
    public String get(int i) {
        return strList.get(i);
    }

    @Override
    public String set(int i, String s) {
        return strList.set(i, s);
    }

    @Override
    public void add(int i, String s) {
        strList.add(i, s);
    }

    @Override
    public String remove(int i) {
        return strList.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return strList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return strList.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<String> listIterator() {
        return strList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<String> listIterator(int i) {
        return strList.listIterator(i);
    }

    @NonNull
    @Override
    public List<String> subList(int i, int i1) {
        return strList.subList(i, i1);
    }
}
