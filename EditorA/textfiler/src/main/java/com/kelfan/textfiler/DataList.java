package com.kelfan.textfiler;

import android.support.annotation.NonNull;

import com.kelfan.utillibrary.ListString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataList implements List {

    private String text;
    private List dataList = new ArrayList();
    private Configer configer;

    public DataList(String text) {
        this.text = text;
    }

    public static DataList set(String text){
        DataList d = new DataList(text);
        return d;
    }

    public DataList auto(){
        this.setConfiger().split();
        return this;
    }

    public DataList setConfiger(){
        configer = Configer.set(text);
        return this;
    }

    public DataList split(){
        ListString ls = ListString.set(text).setDelimiter(configer.getDelimiters()[0]);
        for (String s:ls){
            DataObject d = DataObject.set(s);
            dataList.add(d);
        }
        return this;
    }


    @Override
    public int size() {
        return dataList.size();
    }

    @Override
    public boolean isEmpty() {
        return dataList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return dataList.contains(o);
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return dataList.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return dataList.toArray();
    }

    @Override
    public boolean add(Object o) {
        return dataList.add((DataObject) o);
    }

    @Override
    public boolean remove(Object o) {
        return dataList.remove(o);
    }

    @Override
    public boolean addAll(@NonNull Collection c) {
        return dataList.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection c) {
        return dataList.addAll(index, c);
    }

    @Override
    public void clear() {
        dataList.clear();
    }

    @Override
    public Object get(int index) {
        return dataList.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return dataList.set(index, (DataObject) element);
    }

    @Override
    public void add(int index, Object element) {
        dataList.add(index, (DataObject) element);
    }

    @Override
    public Object remove(int index) {
        return dataList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return dataList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return dataList.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator listIterator() {
        return dataList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator listIterator(int index) {
        return dataList.listIterator(index);
    }

    @NonNull
    @Override
    public List subList(int fromIndex, int toIndex) {
        return dataList.subList(fromIndex, toIndex);
    }

    @Override
    public boolean retainAll(@NonNull Collection c) {
        return dataList.removeAll(c);
    }

    @Override
    public boolean removeAll(@NonNull Collection c) {
        return dataList.removeAll(c);
    }

    @Override
    public boolean containsAll(@NonNull Collection c) {
        return dataList.containsAll(c);
    }

    @NonNull
    @Override
    public Object[] toArray(@NonNull Object[] a) {
        return dataList.toArray();
    }
}
