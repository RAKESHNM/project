package com.razorthink.application.management;
import japa.parser.JavaParser;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
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
        id = 0;
        noOfLines = lines;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        japa.parser.ast.CompilationUnit cu;

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

        @Override
        public void visit(MethodDeclaration n, Void arg) {

            if((n.getEndLine() - n.getBeginLine())>=noOfLines) {

               // System.out.println("Method name: " + n.getName() + "No of lines: " + (n.getEndLine() - n.getBeginLine()));
                listOfMethods.add(id + " " + n.getName() );
                id++;
                listOfMethods.add( String.valueOf( n.getEndLine() - n.getBeginLine()));
                if(n.getParameters() == null || n.getParameters().isEmpty()  )
                    listOfMethods.add(returnFilePath + "+" + "none" );
                else
                    listOfMethods.add(returnFilePath + "+" + n.getParameters().toString());

            }
            //super.visit(n, arg);
        }
    }
}

