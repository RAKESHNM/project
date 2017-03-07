package com.razorthink.application.utils;
import com.razorthink.application.beans.*;
import com.razorthink.application.constants.Constants;
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

    public void storeProject(Project project) throws IOException {
        if(project != null)
        {
                availableProjects.add(project);
                try {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(Constants.LOCAL_DIRECTORY_PATH)) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(availableProjects);
                        System.out.println("Hi");
                    }

                } catch (Exception e) {
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
}
