package com.razorthink.application.controllers;
import com.razorthink.application.beans.Project;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.service.GithubOperations;
import com.razorthink.application.utils.ApplicationStateUtils;
import com.razorthink.application.utils.MethodPrinter;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
@RestController
public class GitHubCkeckoutController {
    Project project = new Project();
    GithubOperations githubOperations = new GithubOperations();


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

        GitHubClient client;
        client = githubOperations.gitCredentials(project.getUsername(),project.getPassword());
        RepositoryService service = new RepositoryService(client);
            githubOperations.gitRemoteRepository(service);
            String remoteRepo = githubOperations.gitRemoteRepoSelect();
            project.setRemoteRepo(remoteRepo);
            String localrepopath = Constants.LOCAL_DIRECTORY_PATH + remoteRepo + "/";
            project.setLocalDirectory(localrepopath);
            String REMOTE_URL = (githubOperations.gitRemote_URL(service, remoteRepo)) + ".git";
            project.setGitUrl(REMOTE_URL);
            githubOperations.gitRemoteBranches(service, remoteRepo, REMOTE_URL, project.getUsername(), project.getPassword());
            String branch = githubOperations.branch();
            project.setBranch(branch);
            githubOperations.gitCloning(REMOTE_URL, branch, localrepopath, project.getUsername(), project.getPassword());
            new ApplicationStateUtils().storeProject(project);
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
