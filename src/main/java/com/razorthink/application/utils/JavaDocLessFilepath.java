package com.razorthink.application.utils;

import com.razorthink.application.constants.Constants;

import java.io.File;
import java.io.IOException;

/**
 * Created by antolivish on 20/3/17.
 */
public class JavaDocLessFilepath {
//    public static void main(String[] args) throws Exception {
//        String path = "/home/antolivish/BackUp/UIGithub/bean/project/";
//        String specific = "/bean/";
//        if((searchStr(path,Constants.BEAN))||(searchStr(path,Constants.REPOSITORY))||(searchStr(path,Constants.CONSTANTS))){
//            System.out.println("true");
//            System.out.println(path.toLowerCase());
//        }
//        else{
//            System.out.println("false");
//        }
//    }
    public static boolean searchStr(String search, String what) {
        if(!search.replaceAll(what,"_").equals(search)) {
            return true;
        }
        return false;
    }

}
