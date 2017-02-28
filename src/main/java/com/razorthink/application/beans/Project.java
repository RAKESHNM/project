package com.razorthink.application.beans;

import java.io.Serializable;

/**
 * Created by rakesh on 25/2/17.
 */
public class Project implements Serializable{
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

        if (gitUrl != null ? !gitUrl.equals(project.gitUrl) : project.gitUrl != null) return false;
        if (username != null ? !username.equals(project.username) : project.username != null) return false;
        if (password != null ? !password.equals(project.password) : project.password != null) return false;
        if (localDirectory != null ? !localDirectory.equals(project.localDirectory) : project.localDirectory != null)
            return false;
        if (status != null ? !status.equals(project.status) : project.status != null) return false;
        if (branch != null ? !branch.equals(project.branch) : project.branch != null) return false;
        return remoteRepo != null ? remoteRepo.equals(project.remoteRepo) : project.remoteRepo == null;
    }

    @Override
    public int hashCode() {
        int result = gitUrl != null ? gitUrl.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (localDirectory != null ? localDirectory.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (remoteRepo != null ? remoteRepo.hashCode() : 0);
        return result;
    }
}
