package com.razorthink.application.management;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.JavaParser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 26/2/17.
 */
public class MethodPrinter {
    static List<String> listOfMethods;
    static String filePathReturn;
    public List<String>  listAllMethods(List<String> list) throws FileNotFoundException {

        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;

            for(String filePath : list) {

                filePathReturn = filePath;
                // creates an input stream for the file to be parsed
                in = new FileInputStream(filePath);

                // parse it
                try {
                    cu = JavaParser.parse(in);
                }catch (Exception e){continue;}

                // visit and print the methods names
                new MethodVisitor().visit(cu, null);
            }
        return listOfMethods;
    }

    public  List<String> returnList(List<String> list){
           return list;
    }
    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        static int i = 0;
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            n.getName();
            super.visit(n, arg);
        }
    }
}
