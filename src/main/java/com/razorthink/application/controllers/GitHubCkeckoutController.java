package com.razorthink.application.controllers;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.ValidNames;
import com.razorthink.application.exceptions.InvalidCreadentialException;
import com.razorthink.application.management.DisplayMethodContent;
import com.razorthink.application.management.MethodFilePath;
import com.razorthink.application.management.ValidatingInputs;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.service.InferUserCommandService;
import com.razorthink.application.service.ReadFile;
import com.razorthink.application.utils.ApplicationStateUtils;
import com.razorthink.application.utils.ValidationUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
@RestController()
@RequestMapping("/rest")
public class GitHubCkeckoutController extends AbstractContrller {

  @Autowired
  Environment env;

  //private Project project = new Project();

  private GithubOperations githubOperations = new GithubOperations();

  private GitHubClient client;

  private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GitHubCkeckoutController.class);

  List<Project> projectList = new ApplicationStateUtils().loadProjects();

  List<String>  branches = new ArrayList<>();

  HashMap<String,String>  userRepos = new HashMap<>();

  public GitHubCkeckoutController() throws IOException {
  }

  /**
   * Creates a session for a given user and validates for existence of git user,if credentials are valid
   * then it returns success else fail
   * @param login
   * @return
   * @throws InvalidCreadentialException
   */
//   @CrossOrigin(origins = "http://localhost:63342")
  @RequestMapping(value = Constants.GITHUB_CREDENTIAL, method = RequestMethod.POST)
  @ResponseBody
  public String credentialGitHub(@RequestBody Login login) throws InvalidCreadentialException {
    try{
      Project project = new Project();
      project.setUsername(login.getUserName());
      project.setPassword(login.getPassword());
      client = githubOperations.gitCredentials(login.getUserName(),login.getPassword());
      RepositoryService service = new RepositoryService(client);
      if( githubOperations.gitRemoteRepository(service) != null){
        request.getSession().setAttribute("user-det",project);
        //request.getSession().setAttribute("pass",login.getPassword());
        return ValidNames.SUCCESS;
      }
    }
    catch(Exception e ){
      throw new InvalidCreadentialException(Constants.INVALID_CREDENTIAL);
    }
    return null;
  }


  /**
   * Listing all repos from the current user account
   * @return
   * @throws Exception
   */
  //     @CrossOrigin(origins = "http://localhost:63342")
  @RequestMapping(value = Constants.LIST_ALL_REPOSITORIES,method = RequestMethod.GET)
  @ResponseBody
  public List<String> listRepos() throws Exception {
    Project project = getProject();
    client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
    RepositoryService service = new RepositoryService(client);
    try {
      return  githubOperations.gitRemoteRepository(service);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   * Controller for listing all branches for a given repository
   * @param branch
   * @return
   * @throws Exception
   */
//    @CrossOrigin(origins = "http://localhost:63342")
  @RequestMapping(value = Constants.LIST_BRANCH,method = RequestMethod.POST)
  @ResponseBody
  public List<String> listbranch(@RequestBody Branch branch) throws Exception{
    try {
      Project project = getProject();
      client = githubOperations.gitCredentials(project.getUsername(), project.getPassword());
      RepositoryService service = new RepositoryService(client);
      return githubOperations.gitRemoteBranches(service, branch.getRemoteRepo(),
              (githubOperations.gitRemote_URL(service, branch.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, project.getUsername(), project.getPassword());

    }catch (Exception e){logger.info("error in listing branches");}
    return Collections.emptyList();
  }

  /**
   * Controller for checkout to a particular branch and clone it local repository
   * @param checkoutProject
   * @throws Exception
   */
//   @CrossOrigin(origins = "http://localhost:63342")
  @RequestMapping(value = Constants.GITHUB_CHECKOUT_ROUTE,method = RequestMethod.POST)
  @ResponseBody
  public String checkoutGitHub(@RequestBody CheckoutProject checkoutProject)throws Exception {

    Project project = getProject();
    client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
    RepositoryService service = new RepositoryService(client);
    return  new ValidationUtils().validateCheckout(project,service,checkoutProject, userRepos);
  }

  @RequestMapping(value = Constants.CLONE,method = RequestMethod.POST)
  @ResponseBody
  public String clone(@RequestBody CheckoutProject checkoutProject)throws Exception {
    Project project = getProject();
    client = githubOperations.gitCredentials(project.getUsername(), project.getPassword());
    RepositoryService service = new RepositoryService(client);
    project =  new GithubOperations().gitCheckout(service,checkoutProject,project);
    return new GithubOperations().deleteDirectory_Clone(service,checkoutProject,project);
  }

    /**
     * controller for command various command services
     * @param commandPojo
     * @return
     * @throws Exception
     */
//   @CrossOrigin(origins = "http://localhost:63342")
  @RequestMapping(value = Constants.INPUTS_FROM_USER,method = RequestMethod.POST)
  @ResponseBody
  public Result getUserInput(@RequestBody CommandPojo commandPojo) throws Exception {
    try {
      Project project = getProject();

      return new InferUserCommandService().getUserInput(commandPojo, project);

    }catch (Exception e){}
    return null;
  }

  /**
   * Controller fot logout service and
   */
  @RequestMapping(value = Constants.LOG_OUT,method = RequestMethod.POST)
  @ResponseBody()
  public void logout(){
    request.getSession().setAttribute("user-det",null);
  }

  @RequestMapping(value = Constants.SHOW_METHOD_CONTENTS,method = RequestMethod.POST)
  @ResponseBody()
  public String showMethodContents(@RequestBody MethodDeclaration methodDeclaration){

    try{
      Project project = getProject();
      return  new DisplayMethodContent().showMethodContent(githubOperations.gitListingFiles(project.getLocalDirectory()).get(0),methodDeclaration.getMethodName()
              ,methodDeclaration.getFilePath());

    }catch (Exception e){}
    return  null;
  }


  @RequestMapping(value = Constants.SHOW_FILE_CONTENTS,method = RequestMethod.POST)
  @ResponseBody()
  public String showFileContents(@RequestBody String filename){
    try{
      Project project = getProject();
         return new ReadFile().extractingFilepath(project.getLocalDirectory(),filename);
    }
    catch (Exception e){}
    return null;
  }

  @RequestMapping(value = Constants.SHOW_COMMIT_DETAILS,method = RequestMethod.POST)
  @ResponseBody()
  public List<String> showCommits(@RequestBody String filename){
    try{
      Project project = getProject();
      return new GithubOperations().getCommitsFromFile(project.getLocalDirectory(),filename);
    }
    catch (Exception e){}
    return null;
  }

  @RequestMapping(value = Constants.SHOW_METHOD_COMMIT,method = RequestMethod.POST)
  @ResponseBody()
  public String showMethodCommmits(@RequestBody String methodName){
    try{
      Project project = getProject();
      return new MethodFilePath().showMethodContent(githubOperations.gitListingFiles(project.getLocalDirectory()).get(0),methodName);
    }
    catch (Exception e){}
    return null;
  }

  @RequestMapping(value = Constants.GET_MODULE,method = RequestMethod.GET)
  @ResponseBody()
  public List<String> getModuleNames() throws Exception {
    try{
        Project project = getProject();
        return new GithubOperations().getModules(project.getLocalDirectory());
    }
    catch (Exception e){}
    return null;
  }



}