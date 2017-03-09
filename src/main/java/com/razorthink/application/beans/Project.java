package com.razorthink.application.beans;

import java.io.Serializable;

/**
 * Created by rakesh on 25/2/17.
 */
public class Project implements Serializable {
    private String gitUrl;
    private String username;
    private String password;
    private String localDirectory;
    private String status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (branch != null ? !branch.equals(project.branch) : project.branch != null) return false;
        return remoteRepo != null ? remoteRepo.equals(project.remoteRepo) : project.remoteRepo == null;
    }

    @Override
    public int hashCode() {
        int result = branch != null ? branch.hashCode() : 0;
        result = 31 * result + (remoteRepo != null ? remoteRepo.hashCode() : 0);
        return result;
    }
}
