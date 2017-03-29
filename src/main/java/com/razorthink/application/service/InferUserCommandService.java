package com.razorthink.application.service;

import com.razorthink.application.beans.CommandPojo;
import com.razorthink.application.beans.Project;
import com.razorthink.application.beans.Result;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.management.ValidatingInputs;
import com.razorthink.application.service.impl.CommandsServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 1/3/17.
 */
public class InferUserCommandService {

    GithubOperations githubOperations = new GithubOperations();

    List<List<String>> FileList = new ArrayList<>();

    CommandPojo commandPojo1 = new CommandPojo();

    /**
     * @param commandPojo
     * @param project
     * @return
     * @throws Exception
     */
    public Result getUserInput( CommandPojo commandPojo, Project project ) throws Exception
    {
        commandPojo = new ValidatingInputs().trimWhiteSpace(commandPojo);
        Result result = new Result();
        result.setProjectName(project.getRemoteRepo());
        result.setBranch(project.getBranch());
        //if  user command is commit details
        if( commandPojo.getCommand().equalsIgnoreCase(Constants.COMMIT_DETAILS) )
        {
            result.setObject(githubOperations.gitCommitDetails(project.getLocalDirectory(), project.getBranch()));
            return result;
        }

        //if user command is project summary
        else if( commandPojo.getCommand().equalsIgnoreCase(Constants.PROJECT_SUMMARY) )
        {
            String pomFilePath = project.getLocalDirectory() + "pom.xml";
            result.setObject(new CommandsServiceImpl().getProjectSummary(pomFilePath));
            return result;
        }
        else
        {

            if( commandPojo.getSubModule() != null )
            {
                if( commandPojo.getFile() != null )
                {
                    //listing files according to given level of directories
                    List<String> temp = new ArrayList<>();
                    temp.add(project.getLocalDirectory() + commandPojo.getSubModule() + commandPojo.getFile());
                    FileList.add(temp);
                    commandPojo1.setFileList(FileList.get(0));
                }
                else
                {
                    FileList = githubOperations
                            .gitListingFiles(project.getLocalDirectory() + commandPojo.getSubModule());
                    commandPojo1.setFileList(FileList.get(0));
                }
            }
            else
            {
                FileList = githubOperations.gitListingFiles(project.getLocalDirectory());
                commandPojo1.setFileList(FileList.get(0));
            }
            if( commandPojo.getCommand().equalsIgnoreCase(Constants.LIST_ALL_METHODS_HAVING_LINES_GREATER_THEN_N) )
            {
                int lines = Integer.parseInt(commandPojo.getNoOfLines());
                result.setObject(new CommandsServiceImpl().listAllMethodsOfNLines(FileList.get(0), lines));
                commandPojo1.setFileList(FileList.get(0));

                return result;
            }
            if( commandPojo.getCommand().equalsIgnoreCase(Constants.LIST_ALL_METHODS_WITHOUT_JAVADOCS) )
            {
                List<String> list = filterBeansAndReposJavaFiles();
                result.setObject(new CommandsServiceImpl().getAllMethodsWithJavaDocsComment(list));
                commandPojo1.setFileList(list);

                return result;
            }
            if( commandPojo.getCommand().equalsIgnoreCase(Constants.LIST_ALL_FILES) )
            {

                double size = Integer.parseInt(commandPojo.getFilesize());
                List<List<String>> resultList = listAllFiles(size);
                result.setObject(resultList);
                return result;
            }
        }
        return null;
    }

    /**
     *
     * @param size
     * @return
     */
    public List<List<String>> listAllFiles( double size )
    {
        List<List<String>> resultList = new ArrayList<>();
        for( List<String> files : FileList )
        {

            List<String> subList = new ArrayList<>();
            for( int i = 0; i < files.size(); i++ )
            {

                File file = new File(files.get(i));
                if( (file.length() / 1024) >= size )
                {
                    subList.add(file.getName());
                    //if file size is less than 1KB
                    if( file.length() < 1024 )
                        subList.add((double) (file.length()) + "bytes");
                    //if file size is greater than 1KB
                    if( file.length() > 1024 && file.length() < 1048576 )
                        subList.add(String.valueOf(Math.round(((double) (file.length()) / 1024 * 100.0)) / 100.0)
                                .concat("KB"));
                    //if file size is greater than 1MB
                    if( file.length() > 1048576 )
                        subList.add((file.length() / (1024 * 1024)) + "MB");
                    subList.add(files.get(i));
                }
            }
            resultList.add(subList);
        }
        return resultList;
    }

    /**
     * Exclude files from directories which are under beans and repositories
     * 
     * @return
     */
    public List<String> filterBeansAndReposJavaFiles()
    {

        List<String> list = new ArrayList<>();
        String[] filter = { Constants.BEAN, Constants.BEANS, Constants.REPOSITORY, Constants.REPOSITORIES };
        for( String temp : FileList.get(0) )
        {
            int count = 0;
            for( int i = 0; i < filter.length; i++ )
            {
                if( searchStr(temp.toLowerCase(), filter[i]) )
                {
                    count++;
                }
                if( count == filter.length )
                {
                    list.add(temp);
                    System.out.println(temp);
                }
            }

        }
        return list;
    }

    public boolean searchStr( String search, String what )
    {
        if( search.replaceAll(what, "_").equals(search) )
        {
            return true;
        }
        return false;
    }

}
