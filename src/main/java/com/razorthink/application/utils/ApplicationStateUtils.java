package com.razorthink.application.utils;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 25/2/17.
 */
public class ApplicationStateUtils {
    @Autowired
    private Environment env;

    List<Project> availableProjects = new ArrayList<>();

    public void storeProject(Project project) throws FileNotFoundException {
        System.out.println(env.getProperty(Constants.LOCAL_DIRECTORY_PATH));
        availableProjects.add(project);
        try {
            try (FileOutputStream fileOutputStream = new FileOutputStream(Constants.LOCAL_DIRECTORY_PATH)) {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(availableProjects);
            }

        } catch (Exception e) {
        }
    }


    public List<Project> loadProjects() throws FileNotFoundException, IOException {
        try {
            try (FileInputStream fileInputStream = new FileInputStream(Constants.LOCAL_DIRECTORY_PATH)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                return (List<Project>) objectInputStream.readObject();
            }

        } catch (Exception e) {
        }
        return null;
    }

    public ProjectSummary projectSummary(String filePath) throws IOException, XmlPullParserException {
        if (filePath != null) {
            MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
            Model model = mavenXpp3Reader.read(new FileInputStream(filePath));
            ProjectInformation projectInformation = new ProjectInformation();
            ProjectOrganization projectOrganization = new ProjectOrganization();
            BuildInformation buildInformation = new BuildInformation();
            projectInformation.setName(model.getName());
            projectInformation.setDescription(model.getDescription());
            projectOrganization.setUrl(model.getUrl());
            buildInformation.setArtifactId(model.getArtifactId());
            buildInformation.setGroupId(model.getGroupId());
            buildInformation.setVersion(model.getVersion());
            buildInformation.setModelVersion(model.getModelVersion());
            ProjectSummary projectSummary = new ProjectSummary();
            projectSummary.setBuildInformation(buildInformation);
            projectSummary.setProjectInformation(projectInformation);
            projectSummary.setProjectOrganization(projectOrganization);
            return projectSummary;
        }
        return null;
    }
}
