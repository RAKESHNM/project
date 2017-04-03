package com.razorthink.application.management;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rakesh on 27/2/17.
 */
public class MethodLinePrinter {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DisplayMethodContent.class);

    static List<String> listOfMethods;

    static int noOfLines = 0;

    static String returnFilePath;

    private static int id = 0;

    public static List<String> noOfLinesInAMethod( List<String> filePaths, int lines ) throws FileNotFoundException
    {
        /**
         * creates an input stream for all file paths of list and then parses each java file using
         * javaParser and then calls MethodVisitor node for all the methods in that java file
         */
        id = 0;
        noOfLines = lines;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;

        try
        {
            for( String filePath : filePaths )
            {
                returnFilePath = filePath;
                in = new FileInputStream(filePath);

                cu = JavaParser.parse(in);

                new MethodVisitor().visit(cu, null);
            }

        }
        catch( FileNotFoundException e )
        {
            logger.error("error in creating file input stream", e);
        }
        return listOfMethods;
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        /**
         * visit method visits each node of method declaration and
         * 
         * @param n
         * @param arg
         */
        @Override
        public void visit( MethodDeclaration n, Void arg )
        {
            if( (n.getEnd().orElse(null).line - n.getBegin().orElse(null).line) >= noOfLines )
            {
                StringBuilder param = new StringBuilder();
                if( n.getParameters() != null || !n.getParameters().isEmpty() )
                    for( Parameter p : n.getParameters() )
                    {
                        param.append(p);
                    }
                listOfMethods.add(id + " " + n.getName());
                id++;
                listOfMethods.add(String.valueOf(n.getEnd().orElse(null).line - n.getBegin().orElse(null).line));
                if( n.getParameters() == null || n.getParameters().isEmpty() )
                    listOfMethods.add(returnFilePath + "+" + "none");
                else
                    listOfMethods.add(returnFilePath + "+" + param);

            }

        }
    }
}
