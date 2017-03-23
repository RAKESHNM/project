package com.razorthink.application.service;
import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.beans.Project;
import com.razorthink.application.beans.Result;
import com.razorthink.application.constants.Constants;
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

        if(commandPojo.getSubModule().equals(Constants.SELECT_MODULE))
               commandPojo.setSubModule(null);
        if(commandPojo.getDirectory().equals(""))
             commandPojo.setDirectory(null);
        if(commandPojo.getFile().equals(""))
            commandPojo.setFile(null);
        if(commandPojo.getNoOfLines().equals(""))
            commandPojo.setNoOfLines("0");
        if(commandPojo.getFilesize().equals(""))
            commandPojo.setFilesize("0");

        Result result = new Result();
        result.setProjectName(project.getRemoteRepo());
        result.setBranch(project.getBranch());
        if (commandPojo.getCommand().equalsIgnoreCase("Commit Details")) {
            result.setObject(githubOperations.gitCommitDetails(project.getLocalDirectory(),project.getBranch()));
            return result;
        }
        else if(commandPojo.getCommand().equalsIgnoreCase("Project Summary")){
            String pomFilePath = project.getLocalDirectory()+"pom.xml";
            result.setObject(new CommandsServiceImpl().getProjectSummary(pomFilePath));
            return result;
        }
        else {

            if (commandPojo.getSubModule() != null) {
                    if (commandPojo.getFile() != null){
                        List<String> temp = new ArrayList<>();
                        temp.add(project.getLocalDirectory()+commandPojo.getSubModule()+commandPojo.getFile());
                        FileList.add(temp);
                        commandPojo1.setFileList(FileList.get(0));}
                    else
                        FileList = githubOperations.gitListingFiles(project.getLocalDirectory() + commandPojo.getSubModule());
                        commandPojo1.setFileList(FileList.get(0));
            } else {
                FileList = githubOperations.gitListingFiles(project.getLocalDirectory());
                commandPojo1.setFileList(FileList.get(0));

            }

//            if (commandPojo.getCommand().equalsIgnoreCase("//List all methods"))
//            {
//                result.setObject(new CommandsServiceImpl().listAllMethods(FileList.get(0)));
//                commandPojo1.setFileList(FileList.get(0));
//
//                return result;
//            }
            if (commandPojo.getCommand().equalsIgnoreCase("List all methods having lines greater than n")) {
                int lines  = Integer.parseInt(commandPojo.getNoOfLines());
                result.setObject(new CommandsServiceImpl().listAllMethodsOfNLines(FileList.get(0),lines));
                commandPojo1.setFileList(FileList.get(0));

                return result;
            }
            if(commandPojo.getCommand().equalsIgnoreCase("List all methods without javadocs")){
                List<String> list = new ArrayList<>();
                String[] filter = {Constants.BEAN,Constants.BEANS,Constants.REPOSITORY,Constants.REPOSITORIES};
                for(String temp : FileList.get(0)){
                    int count = 0;
                    for(int i =0;i<filter.length;i++){
                        if(searchStr(temp.toLowerCase(),filter[i])) {
                            count++;
                        }
                        if(count==filter.length){
                            list.add(temp);
                            System.out.println(temp);
                        }
                    }

                }
                result.setObject(new CommandsServiceImpl().getAllMethodsWithJavaDocsComment(list));
                commandPojo1.setFileList(list);

                return result;
            }
            if(commandPojo.getCommand().equalsIgnoreCase("List all files")){
                List<List<String>> resultList = new ArrayList<>();

                double size  = Integer.parseInt(commandPojo.getFilesize());
                for(List<String> files : FileList){

                    List<String> subList = new ArrayList<>();
                    for(int i = 0; i < files.size(); i++){

                        File file = new File(files.get(i));
                        if( (file.length()/1024) >= size) {
                            subList.add(file.getName());
                            if(file.length() < 1024)
                                subList.add((double)(file.length())+"bytes");
                            if(file.length() > 1024  && file.length()<1048576)
                                subList.add(String.valueOf(Math.round(((double) (file.length()) / 1024 * 100.0)) / 100.0).concat("KB"));
                                //subList.add( (double) (file.length()/1024)+"KB");
                                if(file.length() > 1048576)
                                subList.add((file.length()/(1024*1024))+"MB");
                                subList.add(files.get(i));
                        }

                    }
                    resultList.add(subList);
                }

                result.setObject(resultList);
               return result;
            }
        }
        return null;
    }

    public void showMethodContents(String methodName,Project project) throws Exception {

        System.out.println(githubOperations.gitListingFiles(project.getLocalDirectory()));
       // new DisplayMethodContent().showMethodContent(githubOperations.gitListingFiles(project.getLocalDirectory()).get(0),methodName);
    }
    public boolean searchStr(String search, String what) {
        if(search.replaceAll(what,"_").equals(search)) {
            return true;
        }
        return false;
    }

}
