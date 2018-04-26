package com.kelfan.filepicker;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;

/**
 * Created by Dimorinny on 31.05.16.
 */
public class FilterHidden implements FileFilter, Serializable {

    @Override
    public boolean accept(File f) {
        return !f.isHidden();
    }
}
