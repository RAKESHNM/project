package com.razorthink.application.beans;

/**
 * Created by rakesh on 1/3/17.
 */
public class Result {

    private String ProjectName;
    private String branch;
    private Object object;

    public Result(){

    }

    public Result(String ProjectName, String branch, Object object) {
        this.ProjectName = ProjectName;
        this.branch = branch;
        this.object = object;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
