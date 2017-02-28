package com.razorthink.application.management;
import com.razorthink.application.constants.Constants;
import com.razorthink.application.exceptions.NullFilePathsListException;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by rakesh on 26/2/17.
 */
public class MethodPrinter {
    public void listAllMethods(List<String> list) throws Exception {
        try {
            for(String filePath : list) {
                // creates an input stream for the file to be parsed
                FileInputStream in = new FileInputStream(filePath);

                // parse it
                CompilationUnit cu = JavaParser.parse(in);

                // visit and print the methods names
                new MethodVisitor().visit(cu, null);
            }
        }catch (Exception e){}
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
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }
}
