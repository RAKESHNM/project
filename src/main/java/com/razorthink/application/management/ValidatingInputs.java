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

        if(directory.charAt(0)!= ValidNames.File_SEPERATOR && directory.length()>1){
            directory = File.separator.concat(directory);
        }
        if (directory.charAt(directory.length()-1)==ValidNames.File_SEPERATOR && directory.length()>1){
            directory = directory.substring(0,directory.length()-1);
        }
        return directory;
    }



}
