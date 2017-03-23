package com.razorthink.application.management;

import com.razorthink.application.beans.CommandPojo;
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
