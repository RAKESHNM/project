package com.razorthink.application.controllers;
import com.razorthink.application.beans.Project;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.utils.ApplicationStateUtils;
import com.razorthink.application.management.MethodPrinter;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
@RestController
public class GitHubCkeckoutController  {
    Project project = new Project();

    GithubOperations githubOperations = new GithubOperations();

    ApplicationStateUtils applicationStateUtils = new ApplicationStateUtils();

    public GitHubCkeckoutController() throws IOException {
    }

    @RequestMapping(value = Constants.GITHUB_CREDENTIAL)
    public void credentialGitHub() throws Exception {
        System.out.println("\nGithub Credentials");
        String Username = githubOperations.getUsername();
        project.setUsername(Username);
        String Password = githubOperations.getPassword();
        project.setPassword(Password);
    }

    @RequestMapping(value = Constants.GITHUB_CHECKOUT_ROUTE)
    public void checkoutGitHub() throws Exception {
        List<String> FileList = new ArrayList<String>();
        Project project1 = new Project();
        GitHubClient client;
        client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
        RepositoryService service = new RepositoryService(client);
            githubOperations.gitRemoteRepository(service);
            String remoteRepo = githubOperations.gitRemoteRepoSelect();
            project1.setRemoteRepo(remoteRepo);
            String localrepopath = Constants.LOCAL_DIRECTORY_PATH + remoteRepo + "/";
            project1.setLocalDirectory(localrepopath);
            String REMOTE_URL = (githubOperations.gitRemote_URL(service, remoteRepo)) + ".git";
            project1.setGitUrl(REMOTE_URL);
            githubOperations.gitRemoteBranches(service, remoteRepo, REMOTE_URL, project.getUsername(), project.getPassword());
            String branch = githubOperations.branch();
            project1.setBranch(branch);
            if(!applicationStateUtils.loadProjects().contains(project1)) {
                applicationStateUtils.storeProject(project1);
                githubOperations.gitCloning(REMOTE_URL, branch, localrepopath, project.getUsername(), project.getPassword());
            }

        for(Project item :applicationStateUtils.loadProjects()){
            System.out.println(" -------------------------------");
            System.out.println("UserName: " + project.getUsername());
            System.out.println("ReomoteRepo: " + item.getRemoteRepo());
            System.out.println("Branch: " + item.getBranch());
            System.out.println("GitURL: " + item.getGitUrl());
        }

        FileList = githubOperations.gitListingFiles(localrepopath);
//        int indexNum = githubOperations.getIndexOfFile();
//        githubOperations.gitFetchContent(FileList.get(indexNum-1));
            new MethodPrinter().listAllMethods(FileList);
    }

    @RequestMapping(value = Constants.LIST_AVAILABLE_PROJECTS_ROUTE)
    public void listAvailableProjects() throws IOException {
        List<Project> list = new ApplicationStateUtils().loadProjects();
        for(Project item : list){
            System.out.println(" -------------------------------");
            System.out.println("UserName: " + item.getUsername());
            System.out.println("ReomoteRepo: " + item.getRemoteRepo());
            System.out.println("Branch: " + item.getBranch());
            System.out.println("GitURL: " + item.getGitUrl());
        }
    }
}
