package com.razorthink.application.management;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

/**
 * Created by rakesh on 28/2/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class ProjectSummaryTest {

    @InjectMocks
    ProjectSummary projectSummary;

    private String filePath = "/home/rakesh/pom.xml";

    @Before
    public void setUp(){

    }

    @Test
    public void projectSummaryForValidPomFile() throws IOException, XmlPullParserException {
        projectSummary.projectSummary(filePath);
    }
    @Test
    public void projectSuammryForNullFilePath() throws IOException, XmlPullParserException {
         projectSummary.projectSummary(null);
    }


}
