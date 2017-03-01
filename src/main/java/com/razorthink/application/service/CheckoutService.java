package com.razorthink.application.service;

import com.razorthink.application.beans.CheckoutProject;
import com.razorthink.application.constants.Constants;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 * Created by rakesh on 1/3/17.
 */
public class CheckoutService {

    public String setLocalRepoPath(CheckoutProject checkoutProject){

       return  Constants.LOCAL_DIRECTORY_PATH + checkoutProject.getRemoteRepo() + "/";
    }

   public String setRemoteUrl(RepositoryService service , CheckoutProject checkoutProject) throws Exception {

      return (new GithubOperations().gitRemote_URL(service, checkoutProject.getRemoteRepo())) + ".git";
   }
}
