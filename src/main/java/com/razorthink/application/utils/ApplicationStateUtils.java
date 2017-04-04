package com.razorthink.application.utils;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.controllers.GitHubCkeckoutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rakesh on 25/2/17.
 */
public class ApplicationStateUtils {
    @Autowired
    private Environment env;

   private  List<Project> availableProjects = new ArrayList<>();

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationStateUtils.class);


    /**
     * serialisation for storing checked out projects
     * @param project
     * @throws IOException
     */
    public void storeProject(Project project) throws IOException {
        if(project != null)
        {
                availableProjects.add(project);
                try {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(project.getLocalDirectory())) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(availableProjects);
                    }

                } catch (IOException e) {
                    logger.info("unable to store information of current projects",e);
                }

        }
    }

    /**
     * deserialisation  for retrieving all checked out projects
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<Project> loadProjects() throws  IOException {
        try {
            try (FileInputStream fileInputStream = new FileInputStream(Constants.LOCAL_DIRECTORY_PATH)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                return (List<Project>) objectInputStream.readObject();
            }

        } catch (Exception e) {
            logger.info("failed to load projects",e);
        }
        return Collections.emptyList();
    }
}
