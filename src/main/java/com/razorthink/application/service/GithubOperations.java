package com.razorthink.application.service;

import com.google.common.collect.Lists;
import com.razorthink.application.beans.CheckoutProject;
import com.razorthink.application.beans.Project;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.constants.HtmlConstants;
import com.razorthink.application.constants.ValidNames;
import com.razorthink.application.controllers.GitHubCkeckoutController;
import com.razorthink.application.management.ValidatingInputs;
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
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GitHubCkeckoutController.class);

    //Getting remote repositories
    public List<String> gitRemoteRepository( RepositoryService service ) throws Exception
    {
        List<String> list = new ArrayList<>();
        logger.info("\nRemote Repository");
        logger.info("-----------------------");
        for( Repository repo : service.getRepositories() )
        {
            logger.info(repo.getName());
            list.add(repo.getName());
        }
        return list;
    }

    public String gitRemote_URL( RepositoryService service, String remoteRepo ) throws Exception
    {
        String Remote_URL = "";
        logger.info(remoteRepo);
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
        logger.info("Remote Branches");
        List<String> list = new ArrayList<>();
        logger.info("------------------------");
        for( Repository repo : service.getRepositories() )
        {
            if( repo.getName().equals(localrepo) )
            {
                Collection<Ref> refs = Git.lsRemoteRepository().setHeads(true).setTags(true).setRemote(REMOTE_URL)
                        .setCredentialsProvider(new UsernamePasswordCredentialsProvider(Username, Password)).call();

                for( Ref ref : refs )
                {
                    logger.info(ref.getName());
                    list.add(ref.getName());
                }
                list = filterBranch(list);
                return list;
            }
        }
        return null;
    }

    public List<String> filterBranch(List<String> branch){
        List<String> list = new ArrayList<>();
        for(String temp : branch){
            temp = temp.replace(ValidNames.BRANCH_HEADS,"");
            temp = temp.replace(ValidNames.BRANCH_TAGS,"tags/");
            logger.info(temp);
            list.add(temp);
        }
        return list;
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
        logger.info("\nFile path list");
        logger.info("------------------------");
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
//            logger.info("Index : " + index + " file: " + file.getCanonicalPath());
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
        return POMList;
    }

    //Fetching File Content
    public void gitFetchContent( String path ) throws Exception
    {

        String FetchFile = readFile.readFile(path);
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
            commitList.add(commit.getFullMessage());
            commitList.add(commit.getAuthorIdent().getName());
            commitList.add(date.toString());
        }
        git.close();
        return commitList;
    }

    //Github Credentials
    public GitHubClient gitCredentials( String Username, String Password )
    {
        //Passing credentials
        GitHubClient client = new GitHubClient();
        client.setCredentials(Username, Password);
        return client;
    }

    public List<String> getCommitsFromFile( String localRepoPath, String filepath ) throws Exception
    {
        System.out.println("filepath Commit before : "+ filepath);
//        String filepath = new ReadFile().getFilepath(localRepoPath, filename);
        filepath = filepath.substring(1, filepath.length()-1);
        filepath = filepath.replace(localRepoPath, "");
        int idx = filepath.lastIndexOf("+");
        if(idx>0) {
            filepath = filepath.substring(0, idx);
        }
        List<String> list = new ArrayList<>();
        File dir = new File(localRepoPath);
        Git git = Git.open(dir);
        System.out.println("Filepath Commit replaced: " + filepath);
        System.out.println("local repo path : "+localRepoPath);
        Iterable<RevCommit> commits = git.log().addPath(filepath).call();
        int count = 0;
        for( RevCommit commit : commits )
        {
            Date date = new Date(commit.getCommitTime() * 1000L);
            list.add((HtmlConstants.LINE_BREAK +HtmlConstants.LINE_BREAK +commit.getFullMessage()+HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + commit.getAuthorIdent().getName() + HtmlConstants.BOLD_END + " committed on "
                    + date.toString()));
        }
//        System.out.println("Number of Commits :" + count);
        return list;
    }
    public void slackMessage(){

    }

    public Project gitCheckout(RepositoryService service, CheckoutProject checkoutProject, Project project) throws Exception{

        GithubOperations githubOperations = new GithubOperations();

        project.setRemoteRepo(checkoutProject.getRemoteRepo());
        if(checkoutProject.getBranch().equals("Select Branch")){
            checkoutProject.setBranch(Constants.MASTER_BRANCH);
        }
        int idx = checkoutProject.getBranch().lastIndexOf("/");
        if(idx>0){
            project.setBranch(checkoutProject.getBranch().substring(idx+1));
        }
        else{
            project.setBranch(checkoutProject.getBranch());
        }
        checkoutProject.setDir(new ValidatingInputs().directoryValidation(checkoutProject.getDir()));
        project.setLocalDirectory(checkoutProject.getDir()+ File.separator + project.getRemoteRepo()+"_"+project.getBranch() + File.separator);
        project.setGitUrl((githubOperations.gitRemote_URL(service,checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION);
        return project;
    }

    public String validatedClone(RepositoryService service, CheckoutProject checkoutProject, Project project) throws Exception{
        GithubOperations githubOperations = new GithubOperations();
        File dir = new File(project.getLocalDirectory());
        if (dir.exists()) {
            return project.getLocalDirectory();
        }
        else {
            logger.info("Cloning  into . . .");
            githubOperations.gitCloning((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
                    project.getLocalDirectory(),
                    project.getUsername(), project.getPassword());
            logger.info("Done");
        }
        return ValidNames.TRUE;
    }

    public String deleteDirectory_Clone(RepositoryService service, CheckoutProject checkoutProject, Project project) throws Exception{
        GithubOperations githubOperations = new GithubOperations();
        File dir = new File(project.getLocalDirectory());
        if (dir.exists()) {
            FileUtils.forceDelete(dir);
        }
        logger.info("Cloning  into . . .");
        githubOperations.gitCloning((githubOperations.gitRemote_URL(service, checkoutProject.getRemoteRepo())) + Constants.DOT_GIT_EXTENSION, checkoutProject.getBranch(),
                project.getLocalDirectory(),
                project.getUsername(), project.getPassword());
        logger.info("Done");
        return "Done";
    }

    public boolean validateRepo(RepositoryService service, CheckoutProject checkoutProject) throws Exception {
        if(!new GithubOperations().gitRemoteRepository(service).contains(checkoutProject.getRemoteRepo()))
            return true;
        else
            return false;
    }
}