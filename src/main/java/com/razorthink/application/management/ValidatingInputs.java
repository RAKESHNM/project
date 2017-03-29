package com.razorthink.application.management;

import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.ValidNames;

import java.io.File;

/**
 * Created by antolivish on 23/3/17.
 */
public class ValidatingInputs {
    public CommandPojo trimWhiteSpace(CommandPojo commandPojo){
        commandPojo.setFile(commandPojo.getFile().trim());
        commandPojo.setNoOfLines(commandPojo.getNoOfLines().trim());
        commandPojo.setFilesize(commandPojo.getFilesize().trim());
        if( commandPojo.getSubModule().equals(Constants.SELECT_MODULE) )
            commandPojo.setSubModule(null);
        if( commandPojo.getFile().equals("") )
            commandPojo.setFile(null);
        if( commandPojo.getNoOfLines().equals("") )
            commandPojo.setNoOfLines("0");
        if( commandPojo.getFilesize().equals("") )
            commandPojo.setFilesize("0");
        return commandPojo;
    }
    public String directoryValidation(String directory){

        if(directory.charAt(0)!= ValidNames.File_SEPERATOR && directory.length()>1){
            directory = File.separator.concat(directory);
        }
        if (directory.charAt(directory.length()-1)==ValidNames.File_SEPERATOR && directory.length()>1){
            directory = directory.substring(0,directory.length()-1);
        }
        return directory;
    }



}
