package com.razorthink.application.service;
import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.beans.Project;
import com.razorthink.application.beans.Result;
import com.razorthink.application.management.DisplayMethodContent;
import com.razorthink.application.service.impl.CommandsServiceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rakesh on 1/3/17.
 */
public class InferUserCommandService {


    GithubOperations githubOperations = new GithubOperations();

    List<List<String>> FileList = new ArrayList<>();
    List<String> CommitList = new ArrayList<>();
CommandPojo commandPojo1 = new CommandPojo();
    public Result getUserInput(CommandPojo commandPojo, Project project) throws Exception {

        if(commandPojo.getSubModule().equals(""))
               commandPojo.setSubModule(null);
        if(commandPojo.getDirectory().equals(""))
             commandPojo.setDirectory(null);
        if(commandPojo.getFile().equals(""))
            commandPojo.setFile(null);
        if(commandPojo.getNoOfLines().equals(""))
            commandPojo.setNoOfLines("0");

        Result result = new Result();
        result.setProjectName(project.getRemoteRepo());
        result.setBranch(project.getBranch());
        if (commandPojo.getCommand().equalsIgnoreCase("Commit Details")) {
            result.setObject(githubOperations.gitCommitDetails(project.getLocalDirectory(),project.getBranch()));
//              result.setObject(githubOperations.getCommitsFromFile(project.getLocalDirectory(),"src/main/java/com/razorthink/application/service/GithubOperations.java"));
            return result;
        }
        else if(commandPojo.getCommand().equalsIgnoreCase("Project Summary")){
            String pomFilePath = project.getLocalDirectory()+"pom.xml";
            result.setObject(new CommandsServiceImpl().getProjectSummary(pomFilePath));
            return result;
            /*list.add("Artifact ID: "+projectSummary.getBuildInformation().getArtifactId());
            list.add("Group ID: "+projectSummary.getBuildInformation().getGroupId());
            list.add("Version: "+projectSummary.getBuildInformation().getVersion());
            list.add("Mode Version: "+projectSummary.getBuildInformation());*/
        }
        else {

            if (commandPojo.getSubModule() != null) {
                if (commandPojo.getDirectory() != null) {
                    if (commandPojo.getFile() != null){
                        FileList.get(0).add(project.getLocalDirectory() + commandPojo.getSubModule() + commandPojo.getDirectory() + commandPojo.getFile());
                        commandPojo1.setFileList(FileList.get(0));}
                    else
                        FileList = githubOperations.gitListingFiles(project.getLocalDirectory() + commandPojo.getSubModule() + commandPojo.getFile());
                        commandPojo1.setFileList(FileList.get(0));

                } else {
                    FileList = githubOperations.gitListingFiles(project.getLocalDirectory() + commandPojo.getSubModule());
                    commandPojo1.setFileList(FileList.get(0));

                }
            } else {
                FileList = githubOperations.gitListingFiles(project.getLocalDirectory());
                commandPojo1.setFileList(FileList.get(0));

            }

            if (commandPojo.getCommand().equalsIgnoreCase("//List all methods"))
            {
                result.setObject(new CommandsServiceImpl().listAllMethods(FileList.get(0)));
                commandPojo1.setFileList(FileList.get(0));

                return result;
            }
            if (commandPojo.getCommand().equalsIgnoreCase("List all methods having lines greater than n")) {
                int lines  = Integer.parseInt(commandPojo.getNoOfLines());
                result.setObject(new CommandsServiceImpl().listAllMethodsOfNLines(FileList.get(0),lines));
                commandPojo1.setFileList(FileList.get(0));

                return result;
            }
            if(commandPojo.getCommand().equalsIgnoreCase("List all methods without javadocs")){
                result.setObject(new CommandsServiceImpl().getAllMethodsWithJavaDocsComment(FileList.get(0)));
                commandPojo1.setFileList(FileList.get(0));

                return result;
            }
            if(commandPojo.getCommand().equalsIgnoreCase("List all methods")){
                List<List<String>> resultList = new ArrayList<>();
                List<String> subList = new ArrayList<>();
                int size  = Integer.parseInt(commandPojo.getNoOfLines());
                for(List<String> files : FileList){

                    for(int i = 0; i < files.size(); i++){

                        File file = new File(files.get(i));
                        if( file.length()/1024 > size) {
                            subList.add(file.getName());
                            subList.add(String.valueOf(file.length())+"Kb");
                            subList.add(files.get(i));
                        }
                        resultList.add(subList);
                    }
                }
                result.setObject(resultList);
               return result;
            }
        }
        return null;
    }

    public void showMethodContents(String methodName,Project project) throws Exception {

        System.out.println(githubOperations.gitListingFiles(project.getLocalDirectory()));
        new DisplayMethodContent().showMethodContent(githubOperations.gitListingFiles(project.getLocalDirectory()).get(0),methodName);
    }

}
