package com.kelfan.filepicker;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 7/03/2018.
 */

public class FilterComposite implements FileFilter, Serializable {

    private ArrayList<FileFilter> mFilters;

    public FilterComposite(ArrayList<FileFilter> filters) {
        mFilters = filters;
    }

    @Override
    public boolean accept(File f) {
        for (FileFilter filter : mFilters) {
            if (!filter.accept(f)) {
                return false;
            }
        }

        return true;
    }
}
