package com.razorthink.application.service;

import com.google.common.collect.Lists;
import com.razorthink.application.beans.CheckoutProject;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.HtmlConstants;
import com.razorthink.application.constants.ValidNames;
import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by antolivish on 25/2/17.
 */
public class GithubOperations {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GithubOperations.class);

    /**
     * Listing all repositories for a current user
     * @param service
     * @return
     * @throws IOException
     */
    public List<String> gitRemoteRepository( RepositoryService service ) throws IOException
    {
        List<String> list = new ArrayList<>();
            for (Repository repo : service.getRepositories()) {
                list.add(repo.getName());
            }
        return list;
    }


    /**
     * for  gits remote URL
     * @param service
     * @param remoteRepo
     * @return
     * @throws IOException
     */
    public String gitRemote_URL( RepositoryService service, String remoteRepo ) throws IOException
    {
        String remoteURL = "";
        try {
            for (Repository repo : service.getRepositories()) {
                if (repo.getName().equals(remoteRepo)) {
                    return repo.getHtmlUrl();
                }
            }
        }catch (IOException i){logger.info(i.getMessage(),i);}
        return remoteURL;
    }

    /**
     * getting branches of the specified remote repository
     * @param service
     * @param localrepo
     * @param remoteUrl
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public List<String> gitRemoteBranches( RepositoryService service, String localrepo, String remoteUrl,
            String username, String password ) throws IOException, GitAPIException {
        List<String> list = new ArrayList<>();
        for( Repository repo : service.getRepositories() )
        {
            if( repo.getName().equals(localrepo) )
            {
                Collection<Ref> refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(remoteUrl)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();

                for( Ref ref : refs )
                {
                    list.add(ref.getName());
                }
                list = filterBranch(list);
                return list;
            }
        }
        return Collections.emptyList();
    }

    /**
     * trim refs and tags from the branch remote branch names
     * @param branch
     * @return
     */
    public  List<String> filterBranch(List<String> branch){
        List<String> list = new ArrayList<>();
        for(String temp : branch){
            temp = temp.replace(ValidNames.BRANCH_HEADS,"");
            temp = temp.replace(ValidNames.BRANCH_TAGS,"tags/");
            list.add(temp);
        }
        return list;
    }

    /**
     * list all the modules for current project
     * @param path
     * @return
     */
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
     *listing all the files
     * @param localRepoPath
     * @return
     * @throws Exception
     */
    public List<List<String>> gitListingFiles( String localRepoPath ) throws IOException
    {
        List<String> javaFiles = new ArrayList<>();
        List<String> htmlFiles = new ArrayList<>();
        List<String> cssFiles = new ArrayList<>();
        List<String> jsFiles = new ArrayList<>();
        List<List<String>> fileList = new ArrayList();
        File dir = new File(localRepoPath);
        String[] extensions = new String[] { "java", "py", "html", "css", "js" };
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for( File file : files )
        {
            if( file.getName().substring(file.getName().lastIndexOf('.') + 1).equals(Constants.JAVA) )
                javaFiles.add(file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf('.') + 1).equals(Constants.HTML) )
                htmlFiles.add(file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf('.') + 1).equals(Constants.JS) )
                jsFiles.add(file.getCanonicalPath());
            if( file.getName().substring(file.getName().lastIndexOf('.') + 1).equals(Constants.CSS) )
                cssFiles.add(file.getCanonicalPath());
        }
        fileList.add(javaFiles);
        fileList.add(jsFiles);
        fileList.add(cssFiles);
        fileList.add(htmlFiles);
        return fileList;
    }

    public List<String> gitListPOMFiles( String localRepoPath ) throws IOException
    {
        List<String> pomlist = new ArrayList<String>();
        File dir = new File(localRepoPath);
        String[] extensions = new String[] { "xml" };
        List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);
        for( File file : files )
        {
            pomlist.add(file.getCanonicalPath());
        }
        return pomlist;
    }

    /**
     * cloning repository with a specifies branch into local machine(into specified path)
     * @param remoteURL
     * @param branch
     * @param localRepoPath
     * @param username
     * @param password
     * @throws Exception
     */
    public void gitCloning( String remoteURL, String branch, String localRepoPath, String username, String password )
            throws IOException,GitAPIException
    {
        File dir = new File(localRepoPath);
        if( dir.exists() )
        {
            FileUtils.forceDelete(dir);
        }
         Git.cloneRepository().setURI(remoteURL).setDirectory(dir).setBranch(branch)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password)).call();
    }

    public void gitCommits( String localRepoPath ) throws IOException,GitAPIException
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

    /**
     * getting commit details of a particular file or a method
     * @param localRepoPath
     * @param branch
     * @return
     * @throws Exception
     */
    public List<String> gitCommitDetails( String localRepoPath, String branch ) throws IOException,GitAPIException
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
            commitList.add(commit.getFullMessage());
            commitList.add(commit.getAuthorIdent().getName());
            commitList.add(date.toString());
        }
        git.close();
        return commitList;
    }

    //Github Credentials
    public GitHubClient gitCredentials( String username, String password )
    {
        //Passing credentials
        GitHubClient client = new GitHubClient();
        client.setCredentials(username, password);
        return client;
    }



    /**
     * listing commits for a particular file
     * @param localRepoPath
     * @param filepath
     * @return
     * @throws IOException,GitAPIException
     */
    public List<String> getCommitsFromFile( String localRepoPath, String filepath ) throws IOException,GitAPIException
    {
        filepath = filepath.substring(1, filepath.length()-1);
        filepath = filepath.replace(localRepoPath, "");
        int idx = filepath.lastIndexOf("+");
        if(idx>0) {
            filepath = filepath.substring(0, idx);
        }
        List<String> list = new ArrayList<>();
        File dir = new File(localRepoPath);
        Git git = Git.open(dir);
        Iterable<RevCommit> commits = git.log().addPath(filepath).call();
        int count = 0;
        for( RevCommit commit : commits )
        {
            Date date = new Date(commit.getCommitTime() * 1000L);
            list.add((HtmlConstants.LINE_BREAK +HtmlConstants.LINE_BREAK +commit.getFullMessage()+HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + commit.getAuthorIdent().getName() + HtmlConstants.BOLD_END + " committed on "
                    + date.toString()));
        }
        return list;
    }

    /**
     * check if repo name id valid and is exists ina
     * @param service
     * @param checkoutProject
     * @return
     * @throws IOException
     */
    public boolean validateRepo(RepositoryService service, CheckoutProject checkoutProject) throws IOException {
        if(!new GithubOperations().gitRemoteRepository(service).contains(checkoutProject.getRemoteRepo()))
            return true;
        else
            return false;
    }
}