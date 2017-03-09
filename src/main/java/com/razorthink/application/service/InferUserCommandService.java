package com.razorthink.application.service;
import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.beans.Project;
import com.razorthink.application.beans.Result;
import com.razorthink.application.service.impl.CommandsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rakesh on 1/3/17.
 */
public class InferUserCommandService {


    GithubOperations githubOperations = new GithubOperations();

    List<String> FileList = new ArrayList<>();
    List<String> CommitList = new ArrayList<>();

    public Result getUserInput(CommandPojo commandPojo, Project project) throws Exception {

        if(commandPojo.getSubModule().equals(""))
               commandPojo.setSubModule(null);
        if(commandPojo.getDirectory().equals(""))
             commandPojo.setDirectory(null);
        if(commandPojo.getFile().equals(""))
            commandPojo.setFile(null);

        Result result = new Result();
        result.setProjectName(project.getRemoteRepo());
        result.setBrach(project.getBranch());
        if (commandPojo.getCommand().equalsIgnoreCase("Commit Details")) {
            result.setObject(githubOperations.gitCommitDetails(project.getLocalDirectory(),project.getBranch()));
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
                    if (commandPojo.getFile() != null)
                        FileList.add(project.getLocalDirectory() + commandPojo.getSubModule() + commandPojo.getDirectory() + commandPojo.getFile());
                    else
                        FileList = githubOperations.gitListingFiles(project.getLocalDirectory() + commandPojo.getSubModule() + commandPojo.getFile());
                } else {
                    FileList = githubOperations.gitListingFiles(project.getLocalDirectory() + commandPojo.getSubModule());
                }
            } else {
                FileList = githubOperations.gitListingFiles(project.getLocalDirectory());
            }

            if (commandPojo.getCommand().equalsIgnoreCase("List all methods")) {
                result.setObject(new CommandsServiceImpl().listAllMethods(FileList));
                return result;
            }
            if (commandPojo.getCommand().equalsIgnoreCase("List all methods having lines greater than n")) {
                int lines  = Integer.parseInt(commandPojo.getNoOfLines());
                result.setObject(new CommandsServiceImpl().listAllMethodsOfNLines(FileList,lines));
                return result;
            }
            if(commandPojo.getCommand().equalsIgnoreCase("List all methods without javadocs")){
                result.setObject(new CommandsServiceImpl().getAllMethodsWithJavaDocsComment(FileList));
                return result;
            }
        }
        return null;
    }

}
