package com.razorthink.application.utils;

import java.io.File;
import java.io.IOException;
import java.net.SocketPermission;

/**
 * Created by antolivish on 9/3/17.
 */
public class LocalDirectoryCreation {
    public static String returnCurrentDirectoryPath(){
        String workingDirectory = System.getProperty("user.dir");
        System.out.println("Working Directory : " + workingDirectory);
        return workingDirectory;
    }

    public static String returnSerialisationFilePath() {
        String fileName = "StroreProjectsInfo";
        File f = new File(fileName);
        try {
            f.createNewFile();
        }
        catch(Exception e){}
        System.out.println("Absolute Path : "+ f.getAbsolutePath());
        return f.getAbsolutePath();
    }

}
