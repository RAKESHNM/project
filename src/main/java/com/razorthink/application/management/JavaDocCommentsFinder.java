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
import java.util.List;

/**
 * Created by rakesh on 27/2/17.
 */
public class JavaDocCommentsFinder {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DisplayMethodContent.class);

    public static int id = 0;

    static List<String> listOfMethods;

    static String currentFilePath;

    /**
     *
     * @param list @return @throws
     */

    public static List<String> getJavaDocCommentedMethods( List<String> list ) throws FileNotFoundException
    {
        CompilationUnit cu;
        FileInputStream in;
        id = 1;
        listOfMethods = new ArrayList<>();
        try
        {
            for( String filePath : list )
            {
                /**
                 * creates an input stream for all file paths of list and then parses each java file
                 * using javaParser and then calls MethodVisitor node for all the methods in that
                 * java file
                 */
                currentFilePath = filePath;
                in = new FileInputStream(filePath);
                cu = JavaParser.parse(in);
                //visits all the methods in a class
                new MethodVisitor().visit(cu, null);
            }
        }
        catch( FileNotFoundException e )
        {
            logger.error("error in creating input stream ", e);
        }
        return listOfMethods;
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit( MethodDeclaration n, Void arg )
        {
            /**
             * checks if a method level comment is not null and method body is present,then checks
             * for each statement in a method body whether it is a comment,if is then it will not
             * list that method
             */

            int count;
            if( !n.getComment().isPresent() && n.getBody().isPresent() )
            {
                StringBuilder param = new StringBuilder();
                if( n.getParameters() != null || !n.getParameters().isEmpty() )
                    for( Parameter p : n.getParameters() )
                        param.append(p);
                count = 0;
                NodeList<Statement> nodeList = n.getBody().orElse(null).getStatements();
                for( Statement s : nodeList )
                {
                    if( !s.getAllContainedComments().isEmpty() )
                    {
                        count++;
                    }
                    if( s.getComment().isPresent() )
                    {
                        count++;

                    }
                }
                if( count == 0 )
                {
                    listOfMethods.add(id + " " + n.getName());
                    if( n.getParameters() != null && !n.getParameters().isEmpty() )
                        listOfMethods.add(currentFilePath + "+" + param);
                    else
                        listOfMethods.add(currentFilePath + "+" + "none");
                    id++;
                }
            }

            super.visit(n, arg);
        }
    }

}
