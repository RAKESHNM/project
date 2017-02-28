package com.razorthink.application.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rakesh on 27/2/17.
 */
public class ProjectList implements Serializable {
    ArrayList<Project> projectArrayList = new ArrayList<>();

    public ArrayList<Project> getProjectArrayList() {
        return projectArrayList;
    }

    public void setProjectArrayList(ArrayList<Project> projectArrayList) {
        this.projectArrayList = projectArrayList;
    }
}
