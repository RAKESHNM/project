package com.razorthink.application.beans;

/**
 * Created by rakesh on 1/3/17.
 */
public class CheckoutProject {

    private String remoteRepo;
    private String branch;

    public CheckoutProject(){

    }

    public CheckoutProject(String remoteRepo, String branch) {
        this.remoteRepo = remoteRepo;
        this.branch = branch;
    }

    public String getRemoteRepo() {
        return remoteRepo;
    }

    public void setRemoteRepo(String remoteRepo) {
        this.remoteRepo = remoteRepo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
