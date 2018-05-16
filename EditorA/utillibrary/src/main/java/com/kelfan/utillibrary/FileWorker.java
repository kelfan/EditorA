package com.kelfan.utillibrary;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 31/12/2017.
 * @notice: require permission to manipulate files
 * @result: 1 means success, 0 means fail
 */

public class FileWorker {
    public static final int STORAGE_DATA = 0;
    public static final int STORAGE_EXTERNAL = 1;
    public static final int STORAGE_EXTERNAL_SECOND = 2;

    public static final int MODE_APPEND = 0;
    public static final int MODE_WRITE = 1;

    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = 0;

    public static final String FILE_LOG = "log";


    public static ArrayList<String> readSmallFileToList(String sFilename){
        try{
            File file = new File(sFilename);
            if (!file.exists()) {
                throw new IOException();
            }
            ArrayList<String> aList = new ArrayList<String>();
            if (file.length()> 1024*100){
                aList.add("file is too big");
                return aList;
            }
            if (file.length()==0){
                return aList;
            }
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line;
            if (!input.ready()){
                throw new IOException();
            }
            while ((line = input.readLine()) != null) {
                aList.add(line);
            }
            input.close();
            return aList;
        }catch (Exception | OutOfMemoryError e){
            e.printStackTrace();
            ArrayList<String> aList = new ArrayList<String>();
            aList.add("File doesn't exist or cannot be opened correctly");
            return aList;
        }
    }


    public static String readSmallTxtFile(String sFilename){
        try{
            File file = new File(sFilename);
            if (!file.exists()) {
                throw new IOException();
            }
            if (file.length()> 1024*100){
                return "File is too big.";
            }
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine())!=null){
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text.toString();
        }catch (Exception | OutOfMemoryError e){
            e.printStackTrace();
            return "File doesn't exist or cannot be opened correctly";
        }
    }

    /**
     * read file into string
     * @param sPath path of file
     * @param sFileName filename
     * @param iLocation 0 for data, 1 for external SD storage, 2 for secondary SD storage
     * @return String or "fail" if read file fail
     */
    public static String readFileToString(String sPath, String sFileName, int iLocation){
        try{
            File root = getStoragePath(iLocation,sPath);
            if(!root.exists()){
                throw new IOException();
            }
            File file = new File(root, sFileName);
            if (!file.exists()) {
                throw new IOException();
            }
            StringBuilder text = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine())!=null){
                text.append(line);
                text.append('\n');
            }
            br.close();
            return text.toString();
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }

    public static String readStringFromData(String sPath, String sFileName) {
        return readFileToString(sPath, sFileName, 0);
    }
    public static String readStringFromSD(String sPath, String sFileName) {
        return readFileToString( sPath, sFileName, 1);
    }
    public static String readStringFromSecondSD(String sPath, String sFileName) {
        return readFileToString( sPath, sFileName, 2);
    }

    /**
     * read file into arraylist
     * @param sPath path of file
     * @param sFileName filename
     * @param iLocation 0 for data, 1 for external SD storage, 2 for secondary SD storage
     * @return String or "fail" if read file fail
     */
    public static ArrayList<String> readFileToArrayList(String sPath, String sFileName, int iLocation){
        try{
            File root = getStoragePath(iLocation, sPath);
            if(!root.exists()){
                throw new IOException();
            }
            File file = new File(root, sFileName);
            ArrayList<String> aList = new ArrayList<String>();
            BufferedReader input = new BufferedReader(new FileReader(file));
            String line;
            if (!input.ready()){
                throw new IOException();
            }
            while ((line = input.readLine()) != null) {
                aList.add(line);
            }
            input.close();
            return aList;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> readListFromData(String sPath, String sFileName) {
        return readFileToArrayList(sPath, sFileName, 0);
    }
    public static ArrayList<String> readListFromSD(String sPath, String sFileName) {
        return readFileToArrayList( sPath, sFileName, 1);
    }
    public static ArrayList<String> readListFromSecondSD(String sPath, String sFileName) {
        return readFileToArrayList( sPath, sFileName, 2);
    }

    /**
     * function to save string into a file
     * @param sPath path to storage
     * @param sFileName filename
     * @param sBody string to storage
     * @param iLocation 0 for data, 1 for external SD storage, 2 for secondary SD storage
     * @param mode 0 for append, 1 for write
     */
    public static int saveFile(String sPath, String sFileName, String sBody, int iLocation, int mode){
        try{
            File root = getStoragePath(iLocation, sPath);
            if(!root.exists()){
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            FileWriter writer;
            switch (mode) {
                case MODE_APPEND:
                    writer = new FileWriter(gpxfile,true);
                    writer.append(sBody);
                    break;
                case MODE_WRITE:
                    writer = new FileWriter(gpxfile,false);
                    writer.write(sBody);
                    break;
                default:
                    writer = new FileWriter(gpxfile,true);
                    writer.append(sBody);
                    break;
            }
            writer.flush();
            writer.close();
            return RESULT_SUCCESS;
        }catch (IOException e){
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public static int writeToData(String sPath, String sFileName, String sBody) {
        return saveFile(sPath, sFileName, sBody, 0, 1);
    }
    public static int writeToSD(String sPath, String sFileName, String sBody) {
        return saveFile( sPath, sFileName, sBody, 1, 1);
    }
    public static int writeToSecondSD(String sPath, String sFileName, String sBody) {
        return saveFile( sPath, sFileName, sBody, 2, 1);
    }
    public static int appendToData(String sPath, String sFileName, String sBody) {
        return saveFile( sPath, sFileName, sBody, 0, 0);
    }
    public static int appendToSD(String sPath, String sFileName, String sBody) {
        return saveFile( sPath, sFileName, sBody, 1, 0);
    }
    public static int appendToSecondSD(String sPath, String sFileName, String sBody) {
        return saveFile( sPath, sFileName, sBody, 2, 0);
    }

    /**
     * get the Storage Path
     * @param iLocation 0 for data, 1 for external SD storage, 2 for secondary SD storage
     * @return File
     */
    public static File getStoragePath(int iLocation, String sPath){
        File root;
        switch (iLocation){
            case STORAGE_DATA:
                root = new File(Environment.getDataDirectory(), sPath);
                break;
            case STORAGE_EXTERNAL:
                root = new File(Environment.getExternalStorageDirectory(), sPath);
                break;
            case STORAGE_EXTERNAL_SECOND:
                root = new File(System.getenv("SECONDARY_STORAGE"), sPath);
                break;
            default:
                root = new File(Environment.getExternalStorageDirectory(), sPath);
                break;
        }
        return root;
    }

    /**
     * function to save string into a file
     *
     * @param sFileName filename
     * @param sBody     string to storage
     * @param mode      0 for append, 1 for write
     */
    public static int saveFile(String sFileName, String sBody, int mode) {
        try {
            File gpxfile = new File(sFileName);
            if (!gpxfile.exists()) {
                gpxfile.createNewFile();
            }
            FileWriter writer;
            switch (mode) {
                case MODE_APPEND:
                    writer = new FileWriter(gpxfile, true);
                    writer.append(sBody);
                    break;
                case MODE_WRITE:
                    writer = new FileWriter(gpxfile, false);
                    writer.write(sBody);
                    break;
                default:
                    writer = new FileWriter(gpxfile, true);
                    writer.append(sBody);
                    break;
            }
            writer.flush();
            writer.close();
            return RESULT_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return RESULT_ERROR;
        }
    }

    public static int writeToFile(String sFileName, String sBody) {
        return saveFile(sFileName, sBody, 1);
    }

    public static int appendToFile(String sFileName, String sBody) {
        return saveFile(sFileName, sBody, 0);
    }

}
