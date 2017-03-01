package com.razorthink.application.controllers;

import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.exceptions.InvalidCreadentialException;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.utils.CommandUtils;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
@RestController
public class GitHubCkeckoutController {
    private Project project = new Project();

    private GithubOperations githubOperations = new GithubOperations();

    private List<String> fileList = new ArrayList<String>();

    private GitHubClient client;

    /**
     * Controller for login service
     * @param login
     * @return
     * @throws InvalidCreadentialException
     */
    @RequestMapping(value = Constants.GITHUB_CREDENTIAL, method = RequestMethod.POST)
    @ResponseBody
    public List<String> credentialGitHub(@RequestBody Login login) throws InvalidCreadentialException {
        try{
            project.setUsername(login.getUserName());
            project.setPassword(login.getPassword());
            client = githubOperations.gitCredentials(login.getUserName(),login.getPassword());
            RepositoryService service = new RepositoryService(client);
            return  githubOperations.gitRemoteRepository(service);
        }
        catch(Exception e ){throw new InvalidCreadentialException(Constants.INVALID_CREDENTIAL);}
    }

    /**
     * Controller for listing all branches for a given repository
     * @param branch
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Constants.LIST_BRANCH,method = RequestMethod.POST)
    @ResponseBody
    public List<String> listbranch(@RequestBody Branch branch) throws Exception{

        client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
        RepositoryService service = new RepositoryService(client);
       return  githubOperations.gitRemoteBranches(service, branch.getRemoteRepo(),
               (githubOperations.gitRemote_URL(service, branch.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, project.getUsername(), project.getPassword());
    }

    /**
     * Controller for checkout to a particular branch and clone it local repository
     * @param checkoutProject
     * @throws Exception
     */
    @RequestMapping(value = Constants.GITHUB_CHECKOUT_ROUTE,method = RequestMethod.POST)
    @ResponseBody
    public void checkoutGitHub(@RequestBody CheckoutProject checkoutProject)throws Exception {
        client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
        RepositoryService service = new RepositoryService(client);
        project.setRemoteRepo(checkoutProject.getRemoteRepo());
        //String localrepopath = Constants.LOCAL_DIRECTORY_PATH + checkoutProject.getRemoteRepo() + "/";
        project.setLocalDirectory(Constants.LOCAL_DIRECTORY_PATH + checkoutProject.getRemoteRepo() + "/");
        //String REMOTE_URL = (githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION;
        project.setGitUrl((githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION);
        project.setBranch(checkoutProject.getBranch());
        System.out.println("Cloning  into . . .");
        githubOperations.gitCloning((githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
                Constants.LOCAL_DIRECTORY_PATH + checkoutProject.getRemoteRepo() + "/",
                project.getUsername(), project.getPassword());
        System.out.println("Done");
    }

    /**
     * controller for command various command services
     * @param commandPojo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = Constants.INPUTS_FROM_USER,method = RequestMethod.POST)
    @ResponseBody
    public Result getUserInput(@RequestBody CommandPojo commandPojo) throws Exception {
        try {

            return new CommandUtils().getUserInput(commandPojo, project);
        }catch (Exception e){}

        return null;
   }



}