package com.kelfan.utillibrary;

import java.io.File;
import java.io.IOException;

public class FileLocal {


    private File file;
    public String extension;
    public String fileName;
    public String nameNoExtension;
    public String path;

    public FileLocal(File file) {
        this.file = file;
    }

    public static FileLocal set(String filePath) {
        return new FileLocal(new File(filePath)).auto();
    }

    /**
     * automatically to run essential processes
     * @return
     */
    public FileLocal auto(){
        doFileName().doExtension().doPath();
        return this;
    }

    /**
     * get extension from file
     * @return
     */
    public FileLocal doExtension() {
        this.extension = "." + StringWorker.getLast2end(file.getName(), ".");
        return this;
    }

    /**
     * get file name from file
     * @return
     */
    public FileLocal doFileName(){
        fileName = file.getName();
        nameNoExtension = StringWorker.getStart2First(fileName, ".");
        return this;
    }

    /**
     * get file path from file
     * @return
     */
    public FileLocal doPath(){
        path = file.getPath().substring(0,file.getPath().lastIndexOf("\\")+1);
        return this;
    }


    /**
     * change the name of the file
     * @param newFileName new file name
     * @return
     */
    public FileLocal setNewFileName(String newFileName){
        String filePath = path + newFileName;
        file = new File(filePath);
        auto();
        return this;
    }

    /**
     * change the name of file but keep the same extension
     * @param fileName new file name
     * @return
     */
    public FileLocal setNewNameNoExtension(String fileName){
        file = new File(path + fileName + extension);
        auto();
        return this;
    }

    public FileLocal setNameWithPostfix(String fileName, String postfix){
        file = new File(path + fileName + postfix + extension);
        auto();
        return this;
    }

    public FileLocal addPostfix(String postfix){
        file = new File(path + nameNoExtension + postfix + extension);
        auto();
        return this;
    }

    public  FileLocal checkPath(){
        File f= new File(path);
        if (!f.exists()){
            f.mkdirs();
        }
        return this;
    }

    public  FileLocal checkFile(){
        checkPath();
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

}