package com.razorthink.application.management;
import com.razorthink.application.beans.BuildInformation;
import com.razorthink.application.beans.ProjectInformation;
import com.razorthink.application.beans.ProjectOrganization;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
public class ProjectSummary {
    public com.razorthink.application.beans.ProjectSummary projectSummary(String filePaths) throws IOException, XmlPullParserException {
        if (filePaths != null) {
                MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
                Model model = mavenXpp3Reader.read(new FileInputStream(filePaths));
                ProjectInformation projectInformation = new ProjectInformation(model.getName(), model.getDescription());
                ProjectOrganization projectOrganization = new ProjectOrganization(model.getName(), model.getUrl());
                BuildInformation buildInformation = new BuildInformation(model.getGroupId(), model.getArtifactId(),
                        model.getVersion(), model.getModelVersion());
                return new com.razorthink.application.beans.ProjectSummary(projectInformation, projectOrganization
                        , buildInformation);
            }

        return null;
    }

}
