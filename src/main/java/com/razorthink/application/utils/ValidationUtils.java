package com.razorthink.application.utils;

import com.razorthink.application.beans.CheckoutProject;
import com.razorthink.application.beans.Project;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.ValidNames;
import com.razorthink.application.controllers.GitHubCkeckoutController;
import com.razorthink.application.management.ValidatingInputs;
import com.razorthink.application.service.GithubOperations;
import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.service.RepositoryService;
import java.io.File;
import java.util.HashMap;

/**
 * Created by rakesh on 24/3/17.
 */
public class ValidationUtils {

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ValidationUtils.class);

    public String getRepoPath( HashMap hm, String localDirectoryName, String directoryFilePath )
    {

        if( hm.containsKey(localDirectoryName) )
            return (String) hm.get(localDirectoryName);
        else
            hm.put(localDirectoryName, directoryFilePath);

        return null;
    }

    /**
     *
     * @param project
     * @param service
     * @param checkoutProject
     * @param hm
     * @return
     * @throws Exception
     */
    public String validateCheckout( Project project, RepositoryService service, CheckoutProject checkoutProject,
            HashMap hm ) throws Exception
    {
        GithubOperations githubOperations = new GithubOperations();
        //check for whether given repository exists in users github account,if not return false
        if( new GithubOperations().validateRepo(service, checkoutProject) )
            return ValidNames.FALSE;
        project.setRemoteRepo(checkoutProject.getRemoteRepo());
        //select default branch master
        if( checkoutProject.getBranch().equals("Select Branch") )
        {
            checkoutProject.setBranch(Constants.MASTER_BRANCH);
        }
        int idx = checkoutProject.getBranch().lastIndexOf("/");
        if( idx > 0 )
        {
            project.setBranch(checkoutProject.getBranch().substring(idx + 1));
            System.out.println(project.getBranch());
        }
        else
        {
            project.setBranch(checkoutProject.getBranch());
        }
        if(checkoutProject.getDir().equals(""))
        {
            checkoutProject.setDir(Constants.LOCAL_DIRECTORY_PATH);
        }
        checkoutProject.setDir(new ValidatingInputs().directoryValidation(checkoutProject.getDir()));
        project.setLocalDirectory(checkoutProject.getDir() + File.separator + project.getRemoteRepo() + "_"
                + project.getBranch() + File.separator);
        project.setGitUrl((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo()))
                + Constants.DOT_GIT_EXTENSION);
        File dir = new File(project.getLocalDirectory());
        //if( dir.exists() )
         //   return project.getLocalDirectory();
        if(getRepoPath(hm,project.getRemoteRepo()+"_"+project.getBranch(),project.getLocalDirectory())!= null)
          return getRepoPath(hm,project.getRemoteRepo()+"_"+project.getBranch(),project.getLocalDirectory());
        else
        {
            githubOperations.gitCloning(
                    (githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo()))
                            + Constants.DOT_GIT_EXTENSION,
                    checkoutProject.getBranch(), project.getLocalDirectory(), project.getUsername(),
                    project.getPassword());
        }
        return ValidNames.TRUE;
    }

    public Project gitCheckout(RepositoryService service, CheckoutProject checkoutProject, Project project) throws Exception{

        GithubOperations githubOperations = new GithubOperations();

        project.setRemoteRepo(checkoutProject.getRemoteRepo());
        if(checkoutProject.getBranch().equals("Select Branch")){
            checkoutProject.setBranch(Constants.MASTER_BRANCH);
        }
        int idx = checkoutProject.getBranch().lastIndexOf("/");
        if(idx>0){
            project.setBranch(checkoutProject.getBranch().substring(idx+1));
        }
        else{
            project.setBranch(checkoutProject.getBranch());
        }
        if(checkoutProject.getDir().equals(""))
        {
            checkoutProject.setDir(Constants.LOCAL_DIRECTORY_PATH);
        }
        checkoutProject.setDir(new ValidatingInputs().directoryValidation(checkoutProject.getDir()));
        project.setLocalDirectory(checkoutProject.getDir()+ File.separator + project.getRemoteRepo()+"_"+project.getBranch() + File.separator);
        project.setGitUrl((githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION);
        return project;
    }

    public String deleteDirectory_Clone(RepositoryService service, CheckoutProject checkoutProject, Project project) throws Exception{
        GithubOperations githubOperations = new GithubOperations();
        File dir = new File(project.getLocalDirectory());
        if (dir.exists()) {
            FileUtils.forceDelete(dir);
        }
        logger.info("Cloning  into . . .");
        githubOperations.gitCloning((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
                project.getLocalDirectory(),
                project.getUsername(), project.getPassword());
        logger.info("Done");
        return "Done";
    }


}
