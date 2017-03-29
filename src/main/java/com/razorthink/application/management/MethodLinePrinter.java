package com.razorthink.application.management;

//import japa.parser.JavaParser;
//import japa.parser.ast.CompilationUnit;
///import japa.parser.ast.body.MethodDeclaration;
//import japa.parser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rakesh on 27/2/17.
 */
public class MethodLinePrinter {

    static List<String> listOfMethods;

    public static int noOfLines = 0;

    public static String returnFilePath;

    public static int id = 0;

    public List<String> noOfLinesInAMethod(List<String> filePaths, int lines) throws Exception {
        /**
         * creates an input stream for all file paths of list and then parses each java file using
         * javaParser and then calls MethodVisitor node for all the methods in that java file
         */
        id = 0;
        noOfLines = lines;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;

        try {
            for (String filePath : filePaths) {
                returnFilePath = filePath;
                in = new FileInputStream(filePath);
                try {

                     cu = JavaParser.parse(in);

                }catch (Exception e){continue;}

                new MethodVisitor().visit(cu, null);
            }

        } catch ( Exception e ) {}
        return listOfMethods;
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        /**
         * visit method visits each node of method declaration and
         * @param n
         * @param arg
         */
        @Override
        public void visit(MethodDeclaration n, Void arg) {

            if((n.getEnd().get().line - n.getBegin().get().line)>=noOfLines) {
                String param = null;
                if(n.getParameters() != null || !n.getParameters().isEmpty())
                    for(Parameter p : n.getParameters())
                    param += p;
               // System.out.println("Method name: " + n.getName() + "No of lines: " + (n.getEndLine() - n.getBeginLine()));
                listOfMethods.add(id + " " + n.getName() );
                id++;
                listOfMethods.add( String.valueOf( n.getEnd().get().line - n.getBegin().get().line));
                if(n.getParameters() == null || n.getParameters().isEmpty()  )
                    listOfMethods.add(returnFilePath + "+" + "none" );
                else
                    listOfMethods.add(returnFilePath + "+" + param);

            }
            //super.visit(n, arg);
        }
    }
}

