package com.razorthink.application.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rakesh on 25/2/17.
 */
public class Project implements Serializable{
    private String gitUrl;
    private String username;
    private String password;
    private String localDirectory;
    private String branch;
    private String remoteRepo;

    public String getBranch() {
        return branch;
    }


    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getRemoteRepo() {
        return remoteRepo;
    }

    public void setRemoteRepo(String remoteRepo) {
        this.remoteRepo = remoteRepo;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
