package com.razorthink.application.service.impl;

import com.razorthink.application.management.JavaDocCommentsFinder;
import com.razorthink.application.management.MethodLinePrinter;
import com.razorthink.application.management.MethodPrinter;
import com.razorthink.application.management.ProjectSummary;
import com.razorthink.application.service.CommandsService;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by rakesh on 28/2/17.
 */
public class CommandsServiceImpl implements CommandsService{
    @Override
    public void listAllMethods(List<String> filePaths) throws Exception {

        new MethodPrinter().listAllMethods(filePaths);
    }

    @Override
    public void listAllMethodsOfNLines(List<String> filePaths,int lines) throws Exception {

        new MethodLinePrinter().noOfLinesInAMethod(filePaths,lines);
    }

    @Override
    public void getProjectSummary(String filePath) throws IOException, XmlPullParserException {

        new ProjectSummary().projectSummary(filePath);

    }

    @Override
    public void getAllMethodsWithJavaDocsComment(List<String> filePaths) throws Exception {

        new JavaDocCommentsFinder().getJavaDocCommentedMethods(filePaths);

    }
}
