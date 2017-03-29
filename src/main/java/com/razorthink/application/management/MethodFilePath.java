package com.razorthink.application.management;

//import japa.parser.JavaParser;
//import japa.parser.ast.CompilationUnit;
//import japa.parser.ast.body.MethodDeclaration;
//import japa.parser.ast.visitor.VoidVisitorAdapter;
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
    List<String> listOfMethods;
    private  String name;
    private String returnValue = null;
    static String filePath = null;
    public String showMethodContent(List<String> filePaths, String methodName) throws FileNotFoundException {

        /**
         * creates an input stream for all file paths of list and then parses each java file using
         * javaParser and then calls MethodVisitor node for all the methods in that java file
         */
        name = methodName;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;

        for (String filePath : filePaths) {

            returnValue = filePath;
            in = new FileInputStream(filePath);

            try {
                cu = JavaParser.parse(in);
            } catch (Exception e) {
                continue;
            }

            new MethodFilePath.getFilePath().visit(cu, null);
        }
        return filePath;
    }
    private  class getFilePath extends VoidVisitorAdapter<Void> {


        @Override
        public void visit (MethodDeclaration n, Void arg){

            /**
             * returns a file path of a given method
             */
            if(n.getName().equals(name))
                filePath = returnValue;
            super.visit(n, arg);
        }
    }

}
