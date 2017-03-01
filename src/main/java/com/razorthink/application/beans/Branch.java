package com.razorthink.application.beans;

/**
 * Created by rakesh on 1/3/17.
 */
public class Branch {

    private String branch;
    private String remoteRepo;

    public Branch(){

    }

    public Branch(String branch, String remoteRepo) {
        this.branch = branch;
        this.remoteRepo = remoteRepo;
    }

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
}
