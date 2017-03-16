package com.razorthink.application.controllers;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.exceptions.InvalidCreadentialException;
import com.razorthink.application.management.DisplayMethodContent;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.service.InferUserCommandService;
import com.razorthink.application.service.ReadFile;
import com.razorthink.application.utils.ApplicationStateUtils;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
@RestController()
@RequestMapping("/rest")
public class GitHubCkeckoutController {

  @Autowired
  Environment env;

  private Project project = new Project();

  private GithubOperations githubOperations = new GithubOperations();

  private GitHubClient client;

  private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GitHubCkeckoutController.class);

  List<Project> projectList = new ApplicationStateUtils().loadProjects();

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
      System.out.println(env.getProperty("projects.local.directory"));
      project.setUsername(login.getUserName());
      project.setPassword(login.getPassword());
      client = githubOperations.gitCredentials(login.getUserName(),login.getPassword());
      RepositoryService service = new RepositoryService(client);
      if( githubOperations.gitRemoteRepository(service) != null)
        return "Success";
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
    client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
    RepositoryService service = new RepositoryService(client);
    project.setRemoteRepo(checkoutProject.getRemoteRepo());
    project.setLocalDirectory(Constants.LOCAL_DIRECTORY_PATH + checkoutProject.getRemoteRepo() + Constants.SLASH_EXTENSION);
    project.setGitUrl((githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION);
    project.setBranch(checkoutProject.getBranch());
    logger.info("Cloning  into . . .");
//     new ApplicationStateUtils().storeProject(project);
//           if(new ApplicationStateUtils().loadProjects().contains(project)) {
                githubOperations.gitCloning((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
                        Constants.LOCAL_DIRECTORY_PATH + checkoutProject.getRemoteRepo() + Constants.SLASH_EXTENSION,
                      project.getUsername(), project.getPassword());
//             new ApplicationStateUtils().storeProject(project);
//            }

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

      return new InferUserCommandService().getUserInput(commandPojo, project);

    }catch (Exception e){}

    return null;
  }


  @RequestMapping(value = Constants.SHOW_METHOD_CONTENTS,method = RequestMethod.POST)
  @ResponseBody()
  public String showMethodContents(@RequestBody MethodDeclaration methodDeclaration){

    try{

      return  new DisplayMethodContent().showMethodContent(githubOperations.gitListingFiles(project.getLocalDirectory()).get(0),methodDeclaration.getMethodName());

    }catch (Exception e){}
    return  null;
  }


  @RequestMapping(value = Constants.SHOW_FILE_CONTENTS,method = RequestMethod.POST)
  @ResponseBody()
  public String showFileContents(@RequestBody String filename){
    try{
         return new ReadFile().extractingFilepath(project.getLocalDirectory(),filename);
    }
    catch (Exception e){}
    return null;
  }

  @RequestMapping(value = Constants.SHOW_COMMIT_DETAILS,method = RequestMethod.POST)
  @ResponseBody()
  public List<String> showCommits(@RequestBody String filepath){
    try{
      return new GithubOperations().getCommitsFromFile(project.getLocalDirectory(),filepath);
    }
    catch (Exception e){}
    return null;
  }



}