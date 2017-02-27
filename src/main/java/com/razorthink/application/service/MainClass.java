package com.razorthink.application.service;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 * Created by antolivish on 25/2/17.
 */

public class MainClass {

    public static void main(String[] args) throws Exception {

        List<String> FileList = new ArrayList<String>();

        System.out.println("\nGithub Credentials");
        GithubOperations githubOperations = new GithubOperations();
        String Username = githubOperations.getUsername();
        String Password = githubOperations.getPassword();

        GitHubClient client = githubOperations.gitCredentials(Username,Password);
        RepositoryService service = new RepositoryService(client);
        githubOperations.gitRemoteRepository(service);
        String remoteRepo = githubOperations.gitRemoteRepoSelect();
        String localrepopath = "/home/antolivish/githubrepo/"+remoteRepo+"/";
        String REMOTE_URL = (githubOperations.gitRemote_URL(service,remoteRepo)) + ".git";

        githubOperations.gitRemoteBranches(service,remoteRepo,REMOTE_URL,Username,Password);
        String branch = githubOperations.branch();
        githubOperations.gitCloning(REMOTE_URL,branch,localrepopath,Username,Password);


        FileList = githubOperations.gitListingFiles(localrepopath);
        int indexNum = githubOperations.getIndexOfFile();
        githubOperations.gitFetchContent(FileList.get(indexNum-1));

    }
}


