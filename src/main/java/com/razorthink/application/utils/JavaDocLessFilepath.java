package com.razorthink.application.utils;

import com.razorthink.application.constants.Constants;

import java.io.File;
import java.io.IOException;

/**
 * Created by antolivish on 20/3/17.
 */
public class TestClass {
    public static void main(String[] args) throws Exception {
        String path = "/home/antolivish/BackUp/UIGithub/project/";
        File currentDir = new File(path); // current directory
        displayDirectoryContents(currentDir);

    }

public static void displayDirectoryContents(File dir) throws IOException {

        try {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (!(file.getName().substring(file.getName().lastIndexOf(File.separator) + 1).equals("beans")) && !(file.getName().substring(file.getName().lastIndexOf(File.separator) + 1).equals("repository")) && !(file.getName().substring(file.getName().lastIndexOf(File.separator) + 1).equals("constants")))
                {
                    System.out.println(file.getName().substring(file.getName().lastIndexOf(File.separator) + 1));
                    System.out.println("directory:" + file.getCanonicalPath());
                displayDirectoryContents(file);
            }
            } else {
                System.out.println("file:" + file.getCanonicalPath());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
