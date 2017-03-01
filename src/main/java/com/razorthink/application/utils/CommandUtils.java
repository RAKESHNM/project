package com.razorthink.application.utils;
import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.beans.Project;
import com.razorthink.application.beans.Result;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.service.impl.CommandsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rakesh on 1/3/17.
 */
public class CommandUtils {


    GithubOperations githubOperations = new GithubOperations();

    List<String> FileList = new ArrayList<>();

    public Result getUserInput(CommandPojo commandPojo, Project project) throws Exception {

        Result result = new Result();
        result.setProjectName(project.getRemoteRepo());
        result.setBrach(project.getBranch());
        if (commandPojo.getCommand().equalsIgnoreCase("getCommit")) {
            githubOperations.gitCommits(project.getLocalDirectory());
        }
        else if(commandPojo.getCommand().equalsIgnoreCase("getprojectsummary")){
            String pomFilePath = project.getLocalDirectory()+"pom.xml";
            result.setObject(new CommandsServiceImpl().getProjectSummary(pomFilePath));
            return result;
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

            if (commandPojo.getCommand().equalsIgnoreCase("Listallmathods")) {
                result.setObject(new CommandsServiceImpl().listAllMethods(FileList));
                return result;
            }
            if (commandPojo.getCommand().equalsIgnoreCase("ListallMethodsOfNLines")) {
                System.out.println("Enter number of lines that method should atleast have");
                Scanner scanner = new Scanner(System.in);
                int lines = scanner.nextInt();
                result.setObject(new CommandsServiceImpl().listAllMethodsOfNLines(FileList,lines));
                return result;
            }
            if(commandPojo.getCommand().equalsIgnoreCase("listAllJavaDocsCommentedMethods")){
                result.setObject(new CommandsServiceImpl().getAllMethodsWithJavaDocsComment(FileList));
                return result;
            }
        }
        return null;
    }

}
