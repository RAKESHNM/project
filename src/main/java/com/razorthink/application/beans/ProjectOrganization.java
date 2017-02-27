package com.razorthink.application.beans;

/**
 * Created by rakesh on 26/2/17.
 */
public class ProjectOrganization {
    private String name;
    private String url;

    public ProjectOrganization(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
