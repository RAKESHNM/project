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
    public String showMethodContent(List<String> filePaths, String methodName) throws Exception {

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

            if(n.getName().equals(name))
                filePath = returnValue;
            super.visit(n, arg);
        }
    }

}
