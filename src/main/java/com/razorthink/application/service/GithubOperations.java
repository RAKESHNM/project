package com.razorthink.application.service;

import com.google.common.collect.Lists;
import com.razorthink.application.beans.CheckoutProject;
import com.razorthink.application.constants.Constants;
import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by antolivish on 25/2/17.
 */
public class GithubOperations {

    ReadFile readFile = new ReadFile();

    //Getting remote repositories
    public List<String> gitRemoteRepository( RepositoryService service ) throws Exception
    {
        List<String> list = new ArrayList<>();
        System.out.println("\nRemote Repository");
        System.out.println("-----------------------");
        for( Repository repo : service.getRepositories() )
        {
            System.out.println(repo.getName());
            list.add(repo.getName());
        }
        return list;
    }

    public String gitRemote_URL( RepositoryService service, String remoteRepo ) throws Exception
    {
        String Remote_URL = "";
        System.out.println(remoteRepo);
        for( Repository repo : service.getRepositories() )
        {
            if( repo.getName().equals(remoteRepo) )
            {
                String URL = repo.getHtmlUrl();
                return URL;
            }
        }
        return Remote_URL;
    }

    //Getting branches of specific repository
    public List<String> gitRemoteBranches( RepositoryService service, String localrepo, String REMOTE_URL,
            String Username, String Password ) throws Exception
    {
        System.out.println("\nRemote Branches");
        List<String> list = new ArrayList<>();
        System.out.println("------------------------");
        for( Repository repo : service.getRepositories() )
        {
            if( repo.getName().equals(localrepo) )
            {
                //                String REMOTE_URL = "https://github.com/" + Username + "/" + repo.getName() + ".git";
                Collection<Ref> refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(REMOTE_URL)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(Username, Password)).call();

                for( Ref ref : refs )
                {
                    System.out.println(ref.getName());
                    list.add(ref.getName());
                }
                return list;
            }
        }
        return null;
    }

    public List<String> getModules(String path){
        List<String> list = new ArrayList<>();
        File file = new File(path);
        String[] names = file.list();

        for(String name : names)
        {
            if ((new File(path + name).isDirectory()) && (!name.equals(Constants.DOT_GIT_EXTENSION)))
            {
                list.add(name);
            }
        }
      return list;
    }

    //Listing Files

    /**
     *
     * @param localRepoPath
     * @return
     * @throws Exception
     */
    public List<List<String>> gitListingFiles( String localRepoPath ) throws Exception
    {
        int index = 1;
        System.out.println("\nFile path list");
        System.out.println("------------------------");
        List<String> javaFiles = new ArrayList<>();
        List<String> htmlFiles = new ArrayList<>();
        List<String> cssFiles = new ArrayList<>();
        List<String> jsFiles = new ArrayList<>();
        int count = 0;
        List<List<String>> fileList = new ArrayList();
        File dir = new File(localRepoPath);
        String[] extensions = new String[] { "java", "py", "html", "css", "js" };
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for( File file : files )
        {
            System.out.println("Index : " + index + " file: " + file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("java") )
                javaFiles.add(file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("html") )
                htmlFiles.add(file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("js") )
                jsFiles.add(file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("css") )
                cssFiles.add(file.getCanonicalPath());

            count++;
            index++;
        }
        fileList.add(javaFiles);
        fileList.add(jsFiles);
        fileList.add(cssFiles);
        fileList.add(htmlFiles);
        System.out.println("\nCount :" + count);
        System.out.println(fileList.get(0));
        System.out.println(fileList.get(1));
        System.out.println(fileList.get(2));
        return fileList;
    }

    public List<String> gitListPOMFiles( String localRepoPath ) throws Exception
    {
        int index = 1;
        //        System.out.println("\nFile path list");
        //        System.out.println("------------------------");
        int count = 0;
        List<String> POMList = new ArrayList<String>();
        File dir = new File(localRepoPath);
        String[] extensions = new String[] { "xml" };
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for( File file : files )
        {
            //            System.out.println("Index : "+index+" file: " + file.getCanonicalPath());
            POMList.add(file.getCanonicalPath());
            count++;
            index++;
        }
        System.out.println("\nCount :" + count);
        return POMList;
    }

    //Fetching File Content
    public void gitFetchContent( String path ) throws Exception
    {
        System.out.println("\nFile Content");
        System.out.println("------------------------");
        String FetchFile = readFile.readFile(path);
        System.out.println(FetchFile);
    }

    //Cloning to local repository
    public void gitCloning( String Remote_URL, String branch, String localRepoPath, String Username, String Password )
            throws Exception
    {
        File dir = new File(localRepoPath);
        if( dir.exists() )
        {
            FileUtils.forceDelete(dir);
        }
        Git git = Git.cloneRepository().setURI(Remote_URL).setDirectory(dir).setBranch(branch)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(Username, Password)).call();
    }

    public void gitCommits( String localRepoPath ) throws Exception
    {
        File dir = new File(localRepoPath);
        Git git = Git.open(dir);
        Iterable<RevCommit> commits = git.log().call();
        int count = 0;
        for( RevCommit commit : commits )
        {
            count++;
        }
        System.out.println("Number of Commits :" + count);
    }

    //get commit details
    public List<String> gitCommitDetails( String localRepoPath, String branch ) throws Exception
    {
        List<String> commitList = new ArrayList<>();
        org.eclipse.jgit.lib.Repository dir = new FileRepository(localRepoPath + ".git");
        Git git = new Git(dir);
        Iterable<RevCommit> commits = git.log().add(dir.resolve(branch)).call();
        int count = 0;
        List<RevCommit> commitsList = Lists.newArrayList(commits.iterator());
        for( RevCommit commit : commitsList )
        {
            count++;
            Date date = new Date(commit.getCommitTime() * 1000L);
            System.out.println(commit.getAuthorIdent().getName());
            System.out.println(date.toString());
            System.out.println(commit.getFullMessage());
            commitList.add(commit.getFullMessage());
            commitList.add(commit.getAuthorIdent().getName());
            commitList.add(date.toString());
            System.out.println(commit.getFullMessage().length());
            //           commitList.add(commit.getAuthorIdent().getName() + " committed on " + date.toString() + "\n");
            //            commitList.add(date.toString());
        }
        System.out.println("Total commits : " + count);
        git.close();
        return commitList;
    }

    //get Github Username
    public String getUsername()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nUsername : ");
        final String Username = scanner.nextLine();
        return Username;
    }

    //get Github password
    public String getPassword()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPassword : ");
        final String Password = scanner.nextLine();
        return Password;
    }

    //Github Credentials
    public GitHubClient gitCredentials( String Username, String Password )
    {
        //Passing credentials
        GitHubClient client = new GitHubClient();
        client.setCredentials(Username, Password);
        return client;
    }

    //Get remote branch from User
    public String branch()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBranch : ");
        final String Branch = scanner.nextLine();
        if( Branch == null )
        {
            return "master";
        }
        return Branch;
    }

    //Select Remote Repo by user
    public String gitRemoteRepoSelect()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSelect Remote Repository : ");
        final String remoteRepo = scanner.nextLine();
        return remoteRepo;
    }

    public int getIndexOfFile()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter Index of file to read : ");
        final int Index = scanner.nextInt();
        return Index;
    }

    public String getCommand()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nCommand : ");
        final String Command = scanner.nextLine();
        return Command;
    }

    public String getSubModule()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSubModule : ");
        final String SubModule = scanner.nextLine();
        return SubModule;
    }

    public String getDirectory()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDirectory : ");
        final String Directory = scanner.nextLine();
        return Directory;
    }

    public String getFile()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nFile : ");
        final String File = scanner.nextLine();
        return File;
    }

    public List<String> getCommitsFromFile( String localRepoPath, String filename ) throws Exception
    {

        String filepath = new ReadFile().getFilepath(localRepoPath, filename);
        filepath = filepath.replace(localRepoPath, "");
        List<String> list = new ArrayList<>();
        File dir = new File(localRepoPath);
        Git git = Git.open(dir);
        Iterable<RevCommit> commits = git.log().addPath(filepath).call();
        int count = 0;
        for( RevCommit commit : commits )
        {
            //            System.out.println(commit.getAuthorIdent().getName());
            //            System.out.println(commit.getFullMessage());
            Date date = new Date(commit.getCommitTime() * 1000L);
            list.add((commit.getFullMessage() + " " + commit.getAuthorIdent().getName() + " committed on "
                    + date.toString() + "\n"));
        }
        System.out.println("Number of Commits :" + count);
        return list;
    }
    public void slackMessage(){

    }

    public boolean validateRepo(RepositoryService service, CheckoutProject checkoutProject) throws Exception {
        if(!new GithubOperations().gitRemoteRepository(service).contains(checkoutProject.getRemoteRepo()))
            return true;
        else
            return false;
    }
}