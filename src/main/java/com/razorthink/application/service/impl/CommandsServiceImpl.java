package com.razorthink.application.service.impl;

import com.razorthink.application.management.*;
import com.razorthink.application.service.CommandsService;
import com.razorthink.application.service.GithubOperations;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by rakesh on 28/2/17.
 */
public class CommandsServiceImpl implements CommandsService{
    @Override
    public List<String> listAllMethods(List<String> filePaths) throws FileNotFoundException {

        return new MethodPrinter().listAllMethods(filePaths);
    }
    @Override
    public List<String> listAllMethodsOfNLines(List<String> filePaths, int lines) throws FileNotFoundException {

        return MethodLinePrinter.noOfLinesInAMethod(filePaths,lines);
    }

    @Override
    public com.razorthink.application.beans.ProjectSummary getProjectSummary(String filePath) throws IOException, XmlPullParserException {
        return new ProjectSummary().projectSummary(filePath);
    }

    @Override
    public List<String> getAllMethodsWithJavaDocsComment(List<String> filePaths) throws FileNotFoundException {

        return JavaDocCommentsFinder.getJavaDocCommentedMethods(filePaths);
    }



}
