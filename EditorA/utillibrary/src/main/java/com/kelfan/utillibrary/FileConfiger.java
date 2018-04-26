package com.kelfan.utillibrary;

import java.io.File;

/**
 * Created by Administrator on 14/03/2018.
 */

public class FileConfiger extends FileWorker {
    public static final String CONFIG_PATH = "/Editor";
    public static final String CONFIG_FILE = "config.txt";
    public static final String OPEN_FILE_LIST = "filelist";
    public static final String RECENT_OPEN_FILE = "recent";

    public static String readConfig() {
        File path = FileWorker.getStoragePath(FileWorker.STORAGE_EXTERNAL, CONFIG_PATH + "/" + CONFIG_FILE);
        if (!path.exists()) {
            FileWorker.writeToSD(CONFIG_PATH, CONFIG_FILE, "");
        }
        return FileWorker.readStringFromSD(CONFIG_PATH, CONFIG_FILE);
    }

    public static String getConfigStr(String configName) {
        String config = readConfig();
        String[] configs = config.split(";");
        for (String conf : configs) {
            if (conf.contains(configName)) {
                int i = conf.indexOf(":");
                int jj = conf.length();
                if (conf.indexOf(":") == conf.length()-1){
                    return "";
                }
                return StringWorker.getLast2end(conf, ":");
            }
        }
        return "";
    }

    public static void writeConfig(String configName, String sContent) {
        String config = readConfig();
        String[] configs = config.split(";");
        String result = "";
        if (config.contains(configName+":")){
            for (String conf : configs) {
                if (conf.contains(configName)) {
                    conf = configName + ":" + sContent ;
                }
                if (!conf.equals("") | !conf.equals(";")){
                    result += conf+ ";";
                }
            }
        }else{
             result = config + configName + ":" + sContent + ";";
        }

        writeConfig(result);
    }

    public static void writeConfig(String sContent) {
        FileWorker.writeToSD(CONFIG_PATH, CONFIG_FILE, sContent);
    }
}
