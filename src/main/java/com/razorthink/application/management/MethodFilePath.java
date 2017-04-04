package com.razorthink.application.management;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antolivish on 16/3/17.
 */
public class MethodFilePath {

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DisplayMethodContent.class);

    private String name;

    private String returnValue = null;

    private String filePath = null;

    public String showMethodContent( List<String> filePaths, String methodName ) throws FileNotFoundException
    {

        /**
         * creates an input stream for all file paths of list and then parses each java file using
         * javaParser and then calls MethodVisitor node for all the methods in that java file
         */
        name = methodName;
        FileInputStream in;
        CompilationUnit cu;

        try
        {
            for( String currentFilePath : filePaths )
            {

                returnValue = currentFilePath;

                in = new FileInputStream(currentFilePath);

                cu = JavaParser.parse(in);

                new MethodFilePath.GetFilePath().visit(cu, null);
            }
        }
        catch( FileNotFoundException e )
        {
            logger.error("error in creating input stream", e);
        }
        return filePath;
    }

    private class GetFilePath extends VoidVisitorAdapter<Void> {

        @Override
        public void visit( MethodDeclaration n, Void arg )
        {

            /**
             * returns a file path of a given method
             */
            if( name.equals(n.getNameAsString()) )

                filePath = returnValue;

            super.visit(n, arg);
        }
    }

}
