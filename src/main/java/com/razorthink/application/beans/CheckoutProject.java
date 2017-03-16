package com.razorthink.application.beans;

/**
 * Created by rakesh on 1/3/17.
 */
public class CheckoutProject {

    private String remoteRepo;
    private String branch;
    private String dir;

    public CheckoutProject(){

    }

    public CheckoutProject(String remoteRepo, String branch, String dir) {
        this.remoteRepo = remoteRepo;
        this.branch = branch;
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
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
