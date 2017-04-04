package com.razorthink.application.management;

import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.ValidNames;

import java.io.File;

/**
 * Created by antolivish on 23/3/17.
 */
public class ValidatingInputs {
    /**
     * set module,submodule,no of lines and file size to default value is they are empty
     * @param commandPojo
     * @return
     */
    public CommandPojo trimWhiteSpace(CommandPojo commandPojo){
        commandPojo.setFile(commandPojo.getFile().trim());
        commandPojo.setNoOfLines(commandPojo.getNoOfLines().trim());
        commandPojo.setFilesize(commandPojo.getFilesize().trim());
        if( commandPojo.getSubModule().equals(Constants.SELECT_MODULE) )
            commandPojo.setSubModule(null);
        if( commandPojo.getFile().equals(Constants.EMPTY_STRING) )
            commandPojo.setFile(null);
        if( commandPojo.getNoOfLines().equals(Constants.EMPTY_STRING) )
            commandPojo.setNoOfLines(Constants.ZERO_STRING);
        if( commandPojo.getFilesize().equals(Constants.EMPTY_STRING) )
            commandPojo.setFilesize(Constants.ZERO_STRING);
        return commandPojo;
    }

    /**
     * validating directory path
     * @param directory
     * @return
     */
    public String directoryValidation(String directory){
        String dir = directory;
        if(dir.charAt(0)!= ValidNames.File_SEPERATOR && dir.length()>1){
            dir = File.separator.concat(dir);
        }
        if (dir.charAt(dir.length()-1)==ValidNames.File_SEPERATOR && dir.length()>1){
            dir = dir.substring(0,dir.length()-1);
        }
        return dir;
    }



}
