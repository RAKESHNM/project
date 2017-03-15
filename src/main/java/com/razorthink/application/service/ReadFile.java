package com.razorthink.application.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antolivish on 25/2/17.
 */
public class ReadFile {
    private String filecontent;
    public String getFilepath(String localRepoPath, String filename) throws Exception {
        filename = filename.substring(1, filename.length()-1);
        GithubOperations githubOperations = new GithubOperations();
        List<List<String>> Filelist = new ArrayList<>();
        Filelist = githubOperations.gitListingFiles(localRepoPath);
        for(int i=0;i<Filelist.size();i++){
            for(String list : Filelist.get(i)){
                Path p = Paths.get(list);
                String file = p.getFileName().toString();
                if(file.equals(filename)){
//                    System.out.println(file +"       " + filename);
//                    System.out.println("----------------------------");
//                    System.out.println("----------------------------");
//                    System.out.println(filecontent);
                    return list;
                }

            }
        }
        return null;
    }
    public String extractingFilepath(String localRepoPath, String filename) throws Exception {
        filename = filename.substring(1, filename.length()-1);
        GithubOperations githubOperations = new GithubOperations();
        List<List<String>> Filelist = new ArrayList<>();
        Filelist = githubOperations.gitListingFiles(localRepoPath);
        for(int i=0;i<Filelist.size();i++){
            for(String list : Filelist.get(i)){
                Path p = Paths.get(list);
                String file = p.getFileName().toString();
                if(file.equals(filename)){
                    String filecontent = new ReadFile().readFile(list);
                    return filecontent;
                }

            }
        }
        return null;
    }
    public String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    public static void main(String[] args) throws Exception{
        ReadFile readFile = new ReadFile();
        readFile.readFile("/home/antolivish/StoreProjectsSampleProject/src/main/java/com/razorthink/countFiles/GithubOperations.java");
    }
}