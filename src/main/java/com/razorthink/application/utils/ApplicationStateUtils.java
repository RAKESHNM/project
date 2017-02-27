package com.razorthink.application.utils;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
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
        if(project == null)
        {
        if(!availableProjects.contains(project)) {
            availableProjects.add(project);
            try {
                try (FileOutputStream fileOutputStream = new FileOutputStream(Constants.LOCAL_DIRECTORY_PATH)) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(availableProjects);
                }

            } catch (Exception e) {
            }
        }
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
            ProjectInformation projectInformation = new ProjectInformation(model.getName(),model.getDescription());
            ProjectOrganization projectOrganization = new ProjectOrganization(model.getName(),model.getUrl());
            BuildInformation buildInformation = new BuildInformation(model.getGroupId(),model.getArtifactId(),
                    model.getVersion(),model.getModelVersion());
            return  new ProjectSummary(projectInformation,projectOrganization
            ,buildInformation);
        }
        return null;
    }
}
