package com.razorthink.application.service;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by rakesh on 28/2/17.
 */
public interface CommandsService {

     void listAllMethods(List<String> filePaths) throws Exception;

     void listAllMethodsOfNLines(List<String> filePaths, int lines) throws Exception;

     void getProjectSummary(String fileath) throws IOException, XmlPullParserException;

     void getAllMethodsWithJavaDocsComment(List<String> filePaths) throws Exception;
}
