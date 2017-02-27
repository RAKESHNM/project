package com.razorthink.application.utils;

import com.razorthink.application.beans.Project;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by rakesh on 26/2/17.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class ApplicationStateUtilsTest {

  @InjectMocks
    ApplicationStateUtils applicationStateUtils;
  private Project project;
  private String filePath = "/home/rakesh/pom.xml";
  @Before
  public void setUp(){
    project = new Project();
    project.setGitUrl("/github.com/");
  }

    @Test(expected = NullProjectException.class)
    public void storeProjectsForNullProectTest() throws Exception {
        applicationStateUtils.storeProject(null);
    }

    @Test
  public void storeProjectForValidProjectTest() throws FileNotFoundException, NullProjectException {
       applicationStateUtils.storeProject(project);
    }

  @Test
  public void loadProjects() throws IOException {
    applicationStateUtils.loadProjects();
  }

  @Test
  public void projectSummaryForValidPomFile() throws IOException, XmlPullParserException {
   applicationStateUtils.projectSummary(filePath);
  }
  @Test
  public void projectSuammryForNullFilePath() throws IOException, XmlPullParserException {
    applicationStateUtils.projectSummary(null);
  }
}
