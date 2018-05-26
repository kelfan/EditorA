package com.kelfan.utillibrary;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListString implements List<String> {

    private String text;
    private StringHash strList = new StringHash();
    private String pattern = "([^\n\r]+[\n\r]*)";
    private String delimiter = "";
    private String filter = "";
    private StringHash filteredList = new StringHash();
    private List<Integer> positionList = new ArrayList<Integer>();
    private List<Integer> positionFilter = new ArrayList<Integer>();


    public ListString(){
        this.strList = new StringHash();
        this.strList = new StringHash();
        this.positionList = new ArrayList<Integer>();
        this.positionFilter = new ArrayList<Integer>();
    }

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

    public StringHash getFilteredList() {
        return filteredList;
    }

    public ListString sortItem(){
        if (strList.getItem(0).compareTo(strList.getItem(1))>0){
            strList.sort_reverse();
        }else{
            strList.sort();
        }
        syncPositionList();
        return this;
    }

    public ListString sortFilterItem(){
        if (filteredList.getItem(0).compareTo(filteredList.getItem(1))>0){
            filteredList.sort_reverse();
        }else{
            filteredList.sort();
        }
        sync();
        return this;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public ListString syncPositionList(){
        this.positionList = new ArrayList<>(strList.keySet());
        this.positionFilter = new ArrayList<>(filteredList.keySet());
        return this;
    }

    public ListString sync(){
        filter(this.filter);
        syncPositionList();
        return  this;
    }

    public ListString setPattern(String pattern) {
        getPatternList(pattern);
        return this;
    }

    public StringHash getStrList() {
        return strList;
    }

    public ListString filter(String text){
        this.filter = text;
        filteredList = new StringHash();
        for (int i:strList.keySet()){
            String value = strList.get(i);
            if (StringWorker.contain(value, text, " ")){
                filteredList.putIn(i, value);
            }
        }
        syncPositionList();
        return this;
    }

    public ListString(StringHash strList) {
        this.text = strList.toString();
        this.strList = strList;
        this.pattern = "";
        sync();
    }

    public ListString(List<String> strList) {
        this.text = strList.toString();
        this.strList = StringHash.set(strList);
        this.pattern = "";
        sync();
    }

    public ListString(String[] strList) {
        this.text = strList.toString();
        this.strList = StringHash.set(strList);
        this.pattern = "";
        sync();
    }

    public String conbine(String delimiter){
        String out = "";
        for (String s: strList.values()){
            out += s + delimiter;
        }
        return out;
    }

    public ListString copy(List<String> aList){
        strList  = StringHash.set(aList);
        sync();
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStrList(List<String> strList) {
        this.strList = StringHash.set(strList);
        sync();
    }

    public String getPattern() {
        return pattern;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setFilteredList(StringHash filteredList) {
        this.filteredList = filteredList;
        sync();
    }

    public ListString copy(ListString aList){
        this.text = aList.getText();
        this.strList  = aList.getStrList();
        this.filteredList =  aList.getFilteredList();
        this.delimiter  = aList.getDelimiter();
        this.pattern = aList.getPattern();
        sync();
        return this;
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
        for (String item : this.strList.values()) {
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
        this.strList = StringHash.set(allMatches);
        sync();
        return this;
    }

    public ListString getPatternList(String patternIn) {
        this.pattern = patternIn;
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(this.pattern).matcher(this.text);
        while (m.find()) {
            allMatches.add(m.group());
        }
        this.strList = StringHash.set(allMatches);
        sync();
        return this;
    }

    public ListString getSplitList(){
//        List<String> strings = Arrays.asList(this.text.split(this.delimiter));
//        strings.removeAll(Arrays.asList(null, "", "\n"));
//        this.strList = StringHash.set(strings);
        this.strList = StringHash.set(this.text.split(this.delimiter)).removeAll(Arrays.asList(null, "", "\n"));
        sync();
        return this;
    }

    public int getPosition(String text){
        for (int i = 0; i < this.strList.size(); i++) {
            if (this.strList.get(i).equals(text)){
                return i;
            }
        }
        return -1;
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
//        return StringLocal.set(this.strList.getItem(i));
        return StringLocal.set(this.strList.get(positionList.get(i)));
    }

    public StringLocal getFilterItem(int i){
        return StringLocal.set(this.filteredList.get(positionFilter.get(i)));
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
        return strList.values().contains(o);
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return strList.values().iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return strList.values().toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return strList.values().toArray(ts);
    }

    @Override
    public boolean add(String s) {
        strList.putIn(s);
        sync();
        return true;
    }

    @Override
    public boolean remove(Object o) {
        strList.remove(o);
        sync();
        return true;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return strList.values().containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends String> collection) {
        strList.putAll(collection);
        sync();
        return true;
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
        strList.putAll(collection);
        sync();
        return true;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        strList.removeAll((Collection<? extends String>) collection);
        sync();
        return true;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        strList.retainAll((Collection<? extends String>) collection);
        sync();
        return true;
    }

    @Override
    public void clear() {
        strList.clear();
        sync();
    }

    @Override
    public String get(int i) {
//        return strList.getItem(i);
        return  strList.get(positionList.get(i));
    }

    @Override
    public String set(int i, String s) {
        return strList.set(i, s);
    }

    public ListString setItem(int i, String s) {
        strList.putIn(positionList.get(i), s);
        sync();
        return this;
    }

    public ListString setFilterItem(int i, String s){
        strList.putIn(positionFilter.get(i), s);
        sync();
        return this;
    }

    @Override
    public void add(int i, String s) {
        strList.add(i, s);
        sync();
    }

    @Override
    public String remove(int position) {
        String result =  strList.remove(positionList.get(position));
        sync();
        return result;
    }

    public String removeFilterItem(int position) {
        String result =  strList.remove(positionFilter.get(position));
        sync();
        return result;
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
        return new ArrayList<>(strList.values()).listIterator();
    }

    @NonNull
    @Override
    public ListIterator<String> listIterator(int i) {
        return new ArrayList<>(strList.values()).listIterator(i);
    }

    @NonNull
    @Override
    public List<String> subList(int i, int i1) {
        return new ArrayList<>(strList.values()).subList(i, i1);
    }
}
