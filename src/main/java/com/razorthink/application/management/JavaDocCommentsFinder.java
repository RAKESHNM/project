package com.razorthink.application.management;

//import japa.parser.JavaParser;
//import japa.parser.ast.CompilationUnit;
//import japa.parser.ast.body.MethodDeclaration;
//import japa.parser.ast.stmt.Statement;
//import japa.parser.ast.visitor.VoidVisitorAdapter;
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

    public static int id = 0;
    static List<String> listOfMethods;
    FileInputStream in;
    CompilationUnit cu;
   public static String currentFilePath;

    /**
     *
     * @param list
     * @return
     * @throws Exception
     */

    public List<String> getJavaDocCommentedMethods(List<String> list) throws FileNotFoundException {
        id = 0;
        listOfMethods = new ArrayList<>();
        try
        {
            for( String filePath : list )
            {
                /**
                 * creates an input stream for all file paths of list and then parses each java file using
                 * javaParser and then calls MethodVisitor node for all the methods in that java file
                 */
                currentFilePath = filePath;
                in = new FileInputStream(filePath);
                cu = JavaParser.parse(in);
                //visits all the methods in a class
                new MethodVisitor().visit(cu, null);
            }
        }
        catch( Exception e )
        {
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
             * checks if a method level comment is not null and method body is present,then checks for each
             * statement in a method body whether it is a comment,if is then it will not list that method
             */

            int count = 0;
            if(!n.getComment().isPresent() && n.getBody().isPresent()) {
                String param = null;
                if(n.getParameters() != null || !n.getParameters().isEmpty())
                    for(Parameter p : n.getParameters())
                        param += p;
                count = 0;
                NodeList<Statement> nodeList = (n.getBody().get().getStatements());
                for(Statement s : nodeList){
                    if(!s.getAllContainedComments().isEmpty()) {
                        count++;
                    }
                    if(s.getComment().isPresent()) {
                        count++;

                    }
                }
                if (count == 0) {
                    listOfMethods.add(id + " " + n.getName());
                    if (n.getParameters() != null && !n.getParameters().isEmpty())
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
