package com.razorthink.application.beans;

import java.util.List;

/**
 * Created by rakesh on 1/3/17.
 */
public class CommandPojo {

    private String command;
    private String directory;
    private String subModule;
    private String file;
    private String  noOfLines;
    private List<String> FileList;

    public List<String> getFileList() {
        return FileList;
    }

    public void setFileList(List<String> fileList) {
        FileList = fileList;
    }

    public String getNoOfLines() {
        return noOfLines;
    }

    public void setNoOfLines(String noOfLines) {
        this.noOfLines = noOfLines;
    }

    public CommandPojo(){

    }

    public CommandPojo(String command, String module, String subModule, String file,String noOfLines) {
        super();
        this.command = command;
        this.directory = module;
        this.subModule = subModule;
        this.file = file;
        this.noOfLines = noOfLines;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String module) {
        this.directory = module;
    }

    public String getSubModule() {
        return subModule;
    }

    public void setSubModule(String subModule) {
        this.subModule = subModule;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
