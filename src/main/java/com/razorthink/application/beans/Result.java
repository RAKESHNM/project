package com.razorthink.application.beans;

/**
 * Created by rakesh on 1/3/17.
 */
public class Result {

    private String projectName;
    private String brach;
    private Object object;

    public Result(){

    }

    public Result(String projectName, String brach, Object object) {
        this.projectName = projectName;
        this.brach = brach;
        this.object = object;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBrach() {
        return brach;
    }

    public void setBrach(String brach) {
        this.brach = brach;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
