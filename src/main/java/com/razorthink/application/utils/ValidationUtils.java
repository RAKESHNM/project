package com.razorthink.application.utils;

import com.razorthink.application.beans.CheckoutProject;
import com.razorthink.application.beans.Project;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.ValidNames;
import com.razorthink.application.management.ValidatingInputs;
import com.razorthink.application.service.GithubOperations;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rakesh on 24/3/17.
 */
public class ValidationUtils {

    public String getRepoPath(Map hm, String localDirectoryName, String directoryFilePath )
    {
            if (hm.containsKey(localDirectoryName))
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
     * @throws IOException,GitAPIException
     */
    public String validateCheckout( Project project, RepositoryService service, CheckoutProject checkoutProject,
            Map hm ) throws IOException,GitAPIException
    {
        GithubOperations githubOperations = new GithubOperations();
        //check for whether given repository exists in users github account,if not return false
        if( new GithubOperations().validateRepo(service, checkoutProject) )
            return ValidNames.FALSE;
        project.setRemoteRepo(checkoutProject.getRemoteRepo());
        //select default branch master
        if( checkoutProject.getBranch().equals(Constants.SELECT_BRANCH) )
        {
            checkoutProject.setBranch(Constants.MASTER_BRANCH);
        }
        int idx = checkoutProject.getBranch().lastIndexOf('.');
        if( idx > 0 )
        {
            project.setBranch(checkoutProject.getBranch().substring(idx + 1));
        }
        else
        {
            project.setBranch(checkoutProject.getBranch());
        }
        checkoutProject.setDir(new ValidatingInputs().directoryValidation(checkoutProject.getDir()));
        project.setLocalDirectory(checkoutProject.getDir() + File.separator + project.getRemoteRepo() + "_"
                + project.getBranch() + File.separator);
        project.setGitUrl((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo()))
                + Constants.DOT_GIT_EXTENSION);
        File dir = new File(project.getLocalDirectory());
        if( dir.exists() )
            return project.getLocalDirectory();
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
}
