package com.razorthink.application.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import com.razorthink.application.beans.Project;
import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;

/**
 * Created by antolivish on 25/2/17.
 */
@Service
public class GithubOperations {
    ReadFile readFile = new ReadFile();
    Project project = new Project();

    //Getting remote repositories
    public void gitRemoteRepository(RepositoryService service) throws Exception {
        System.out.println("\nRemote Repository");
        System.out.println("-----------------------");
        for (Repository repo : service.getRepositories()){
            System.out.println(repo.getName());
        }
    }

    public String gitRemote_URL(RepositoryService service,String remoteRepo) throws Exception{
        String Remote_URL = "";
        System.out.println(remoteRepo);
        for (Repository repo : service.getRepositories()) {
            if (repo.getName().equals(remoteRepo)) {
                String URL = repo.getHtmlUrl();
                return URL;
            }
        }
        return Remote_URL;
    }

    //Getting branches of specific repository
    public void gitRemoteBranches(RepositoryService service, String localrepo, String REMOTE_URL,String Username,String Password) throws Exception {
        System.out.println("\nRemote Branches");
        System.out.println("------------------------");
        for (Repository repo : service.getRepositories()) {
            if (repo.getName().equals(localrepo)) {
//                String REMOTE_URL = "https://github.com/" + Username + "/" + repo.getName() + ".git";
                Collection<Ref> refs = Git.lsRemoteRepository()
                        .setHeads(true)
                        .setTags(true)
                        .setRemote(REMOTE_URL)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(Username,Password))
                        .call();

                for (Ref ref : refs)
                    System.out.println(ref.getName());
            }
        }
    }

    //Listing Files

    /**
     *
     * @param localRepoPath
     * @return
     * @throws Exception
     */
    public List<String> gitListingFiles(String localRepoPath) throws Exception {
        int index = 1;
        System.out.println("\nFile path list");
        System.out.println("------------------------");
        int count = 0;
        List<String> fileList = new ArrayList<String>();
        File dir = new File(localRepoPath);
        String[] extensions = new String[]{"txt", "jsp", "java","py"};
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for (File file : files) {
            System.out.println("Index : "+index+" file: " + file.getCanonicalPath());
            fileList.add(file.getCanonicalPath());
            count++;index++;
        }
        System.out.println("\nCount :" + count);
        return fileList;
    }

    //Fetching File Content
    public void gitFetchContent(String path) throws Exception {
        System.out.println("\nFile Content");
        System.out.println("------------------------");
        String FetchFile = readFile.readFile(path);
        System.out.println(FetchFile);
    }

    //Cloning to local repository
    public void gitCloning(String Remote_URL, String branch, String localRepoPath,String Username,String Password) throws Exception {
        File dir = new File(localRepoPath);
        if (dir.exists()) {
            FileUtils.forceDelete(dir);
        }
        Git git = Git.cloneRepository()
                .setURI(Remote_URL)
                .setDirectory(dir)
                .setBranch(branch)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(Username,Password))
                .call();
    }

    //get Github Username
    public String getUsername() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nUsername : ");
        final String Username = scanner.nextLine();
        project.setUsername(Username);
        return Username;
    }

    //get Github password
    public String getPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPassword : ");
        final String Password = scanner.nextLine();
        project.setPassword(Password);
        return Password;
    }

    //Github Credentials
    public GitHubClient gitCredentials(String Username, String Password) {
        //Passing credentials
        GitHubClient client = new GitHubClient();
        client.setCredentials(Username, Password);
        return client;
    }

    //Get remote branch from User
    public String branch() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBranch : ");
        final String Branch = scanner.nextLine();
        project.setBranch(Branch);
        return Branch;
    }

    //Select Remote Repo by user
    public String gitRemoteRepoSelect() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect Remote Repository : ");
        final String remoteRepo = scanner.nextLine();
        project.setRemoteRepo(remoteRepo);
        return remoteRepo;
    }

    public int getIndexOfFile(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter Index of file to read : ");
        final int Index = scanner.nextInt();
        return Index;
    }
}