package com.razorthink.application.management;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.exceptions.NullFilePathsListException;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 26/2/17.
 */
public class MethodPrinter {
    static List<String> listOfMethods;
    static String filePathReturn;
    public List<String>  listAllMethods(List<String> list) throws Exception {

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
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            //System.out.println(n.getName());
            listOfMethods.add("<b>"+n.getName()+"</b> ");
            listOfMethods.add(filePathReturn);
            super.visit(n, arg);
        }
    }
}
