package com.razorthink.application.management;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.razorthink.application.controllers.GitHubCkeckoutController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antolivish on 10/3/17.
 */
public class DisplayMethodContent {


    private String name;
    private String returnValue = null;
    private String currentFilePath;
    private String classMethodFilePath;
    public static int id;

    public String showMethodContent( List<String> filePaths, String methodName, String methodFilePath ) throws FileNotFoundException
    {
        final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DisplayMethodContent.class);

        /**
         * Loop through each files, parse it and extract all the methods till method name matches with given method
         * name and send contents,name ,parameters and java docs for a given method name.
         */
        id = 0;
        classMethodFilePath = methodFilePath;
        name = methodName;
        FileInputStream in;
        CompilationUnit cu;
        //filePaths.clear();
        //filePaths.add("/home/rakesh/bigbrain_master/designer/commons/src/main/java/com/razorthink/bigbrain/designer/commons/domain/ModelRun.java");

        try {
            for (String filePath : filePaths) {

                currentFilePath = filePath;

                in = new FileInputStream(filePath);

                cu = JavaParser.parse(in);

                new MethodVisitor().visit(cu, null);
            }
        }catch (FileNotFoundException e){logger.error("error in creating file input stream",e);}
        return returnValue;
    }

    private class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit( MethodDeclaration n, Void arg )
        {
            /**
             * if the method name matches with given method name then send contents,parameters,javadocs of that
             * method according to which is null assign none value.
             */
            if( n.getName().toString().equals(name.substring(name.indexOf(' ') + 1)) )
            {
                StringBuilder param = new StringBuilder();
                if( n.getParameters() != null || !n.getParameters().isEmpty() )
                    for( Parameter p : n.getParameters() )
                        param.append(p);
                if( (n.getParameters() != null && !n.getParameters().isEmpty()) && (currentFilePath + "+" + param).equals(classMethodFilePath) )
                {
                        if( n.getComment().isPresent() && n.getBody().isPresent() )
                        {
                            returnValue = "<b> JavaDocs: </b>" + n.getComment().orElse(null) + "<br><br>"
                                    + "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>"
                                    + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody().orElse(null) + "</pre>";
                        }
                        if( n.getComment().isPresent() && !n.getBody().isPresent() )
                        {

                            returnValue = "<b> JavaDocs: </b>" + n.getComment().orElse(null) + "<br><br>"
                                    + "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>"
                                    + "<b> Method Logic: </b>" + "<br>" + "<pre>" + "none" + "</pre>";
                        }
                        if( n.getBody().isPresent() && !n.getComment().isPresent() )
                        {
                            returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" + "<b> Method Name: </b>"
                                    + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>"
                                    + "<pre>" + n.getBody().orElse(null) + "</pre>";
                        }
                        if( !n.getBody().isPresent() && !n.getComment().isPresent() )
                        {
                            returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" + "<b> Method Name: </b>"
                                    + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>"
                                    + "<pre>" + "none" + "</pre>";
                        }

                }

                if( (n.getParameters() == null || n.getParameters().isEmpty()) && (currentFilePath + "+" + "none").equals(classMethodFilePath))
                {

                        if( n.getBody().isPresent() && !n.getComment().isPresent() )
                            returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" + "<b> Method Name: </b>"
                                    + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>"
                                    + "<pre>" + n.getBody().orElse(null) + "</pre>";
                        if( n.getComment().isPresent() && !n.getBody().isPresent() )
                            returnValue = "<b> JavaDocs: </b>" + n.getComment().orElse(null) + "<br><br>"
                                    + "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>"
                                    + "<b> Method Logic: </b>" + "<br>" + "<pre>" + "none" + "</pre>";
                        if( n.getBody().isPresent() && n.getComment().isPresent() )
                            returnValue = "<b> JavaDocs: </b>" + n.getComment().orElse(null) + "<br><br>"
                                    + "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>"
                                    + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody().orElse(null) + "</pre>";
                        if( !n.getBody().isPresent() && !n.getComment().isPresent() )
                            returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" + "<b> Method Name: </b>"
                                    + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>"
                                    + "<pre>" + "none" + "</pre>";


                }
            }
            super.visit(n, arg);
        }
    }

}
