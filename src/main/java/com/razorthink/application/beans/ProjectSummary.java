package com.razorthink.application.beans;

import com.razorthink.application.beans.BuildInformation;
import com.razorthink.application.beans.ProjectInformation;
import com.razorthink.application.beans.ProjectOrganization;

/**
 * Created by rakesh on 26/2/17.
 */
public class ProjectSummary {
    private ProjectInformation projectInformation;
    private ProjectOrganization projectOrganization;
    private BuildInformation buildInformation;

    public ProjectInformation getProjectInformation() {
        return projectInformation;
    }

    public void setProjectInformation(ProjectInformation projectInformation) {
        this.projectInformation = projectInformation;
    }

    public ProjectOrganization getProjectOrganization() {
        return projectOrganization;
    }

    public void setProjectOrganization(ProjectOrganization projectOrganization) {
        this.projectOrganization = projectOrganization;
    }

    public BuildInformation getBuildInformation() {
        return buildInformation;
    }

    public void setBuildInformation(BuildInformation buildInformation) {
        this.buildInformation = buildInformation;
    }
}
