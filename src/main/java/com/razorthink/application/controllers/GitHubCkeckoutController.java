package com.razorthink.application.controllers;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.exceptions.InvalidCreadentialException;
import com.razorthink.application.management.DisplayMethodContent;
import com.razorthink.application.management.MethodFilePath;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.service.InferUserCommandService;
import com.razorthink.application.service.ReadFile;
import com.razorthink.application.utils.ApplicationStateUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
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


  public GitHubCkeckoutController() throws IOException {
  }

  /**
   * Controller for login service
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
        return "Success";
      }
    }
    catch(Exception e ){
      throw new InvalidCreadentialException(Constants.INVALID_CREDENTIAL);
    }
    return null;
  }


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
    if(new GithubOperations().validateRepo(service,checkoutProject) )
      return "false";
    project.setRemoteRepo(checkoutProject.getRemoteRepo());
    if(checkoutProject.getDir() == ""){
      System.out.println(System.getProperty("user.home"));
      checkoutProject.setDir(System.getProperty("user.home"));
    }
    int idx = checkoutProject.getBranch().lastIndexOf("/");
    if(idx>0){
      project.setBranch(checkoutProject.getBranch().substring(idx+1));
      System.out.println(project.getBranch());
    }
    else{
      project.setBranch(checkoutProject.getBranch());
    }
    project.setLocalDirectory(checkoutProject.getDir()+ File.separator + project.getRemoteRepo()+"_"+project.getBranch() + File.separator);
    project.setGitUrl((githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION);
    File dir = new File(project.getLocalDirectory());
    if (dir.exists()) {
      System.out.println("Exist");
      return project.getLocalDirectory();
    }
    else {
      logger.info("Cloning  into . . .");
      githubOperations.gitCloning((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
              project.getLocalDirectory(),
              project.getUsername(), project.getPassword());
      logger.info("Done");
    }
    return "true";
  }

  @RequestMapping(value = Constants.CLONE,method = RequestMethod.POST)
  @ResponseBody
  public String clone(@RequestBody CheckoutProject checkoutProject)throws Exception {
    Project project = getProject();
    client = githubOperations.gitCredentials(project.getUsername(), project.getPassword());
    RepositoryService service = new RepositoryService(client);
    project.setRemoteRepo(checkoutProject.getRemoteRepo());
    int idx = checkoutProject.getBranch().lastIndexOf("/");
    if (idx > 0) {
      project.setBranch(checkoutProject.getBranch().substring(idx + 1));
      System.out.println(project.getBranch());
    } else {
      project.setBranch(checkoutProject.getBranch());
    }
      if(checkoutProject.getDir()==""){
          System.out.println(System.getProperty("user.home"));
          checkoutProject.setDir(System.getProperty("user.home"));
      }
    project.setLocalDirectory(checkoutProject.getDir() + File.separator + project.getRemoteRepo() + "_" + project.getBranch() + File.separator);
    project.setGitUrl((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION);
    File dir = new File(project.getLocalDirectory());
    if (dir.exists()) {
      System.out.println("Exist");
      FileUtils.forceDelete(dir);
    }
      logger.info("Cloning  into . . .");
      githubOperations.gitCloning((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
              project.getLocalDirectory(),
              project.getUsername(), project.getPassword());
      logger.info("Done");
    return "Done";
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


  @RequestMapping(value = Constants.SHOW_METHOD_CONTENTS,method = RequestMethod.POST)
  @ResponseBody()
  public MethodInfo showMethodContents(@RequestBody MethodDeclaration methodDeclaration){

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



}