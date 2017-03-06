package com.razorthink.application.service;

import com.razorthink.application.management.ProjectSummary;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by rakesh on 28/2/17.
 */
public interface CommandsService {

     List<String> listAllMethods(List<String> filePaths) throws Exception;

     List<String> listAllMethodsOfNLines(List<String> filePaths, int lines) throws Exception;

     com.razorthink.application.beans.ProjectSummary getProjectSummary(String fileath) throws IOException, XmlPullParserException;

     List<String> getAllMethodsWithJavaDocsComment(List<String> filePaths) throws Exception;
}
