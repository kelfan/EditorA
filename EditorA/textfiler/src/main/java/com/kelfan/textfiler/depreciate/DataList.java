package com.kelfan.textfiler.depreciate;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataList implements List<DataUnit> {

    private ArrayList dataList = new ArrayList();

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
    public Iterator<DataUnit> iterator() {
        return dataList.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return dataList.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return (T[]) dataList.toArray(ts);
    }

    @Override
    public boolean add(DataUnit dataUnit) {
        return dataList.add(dataUnit);
    }

    @Override
    public boolean remove(Object o) {
        return dataList.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return dataList.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends DataUnit> collection) {
        return dataList.addAll(collection);
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends DataUnit> collection) {
        return dataList.addAll(i, collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return dataList.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return dataList.retainAll(collection);
    }

    @Override
    public void clear() {
        dataList = new ArrayList();
    }

    @Override
    public DataUnit get(int i) {
        return (DataUnit) dataList.get(i);
    }

    @Override
    public DataUnit set(int i, DataUnit dataUnit) {
        return (DataUnit) dataList.set(i, dataUnit);
    }

    @Override
    public void add(int i, DataUnit dataUnit) {
        dataList.add(i, dataUnit);
    }

    @Override
    public DataUnit remove(int i) {
        return (DataUnit) dataList.remove(i);
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
    public ListIterator<DataUnit> listIterator() {
        return dataList.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<DataUnit> listIterator(int i) {
        return dataList.listIterator(i);
    }

    @NonNull
    @Override
    public List<DataUnit> subList(int i, int i1) {
        return dataList.subList(i, i1);
    }
}
