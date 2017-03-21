package com.razorthink.application.beans;

/**
 * Created by rakesh on 20/3/17.
 */
public class MethodInfo {

    private String logic;
    private String parameters;
    private String name;
    private String comments;

    public MethodInfo() {
    }

    public MethodInfo(String logic, String parameters, String name, String comments) {
        this.logic = logic;
        this.parameters = parameters;
        this.name = name;
        this.comments = comments;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
