package com.razorthink.application.management;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by rakesh on 27/2/17.
 */
public class JavaDocCommentsFinder {

    static List<String> listOfMethods;
    FileInputStream in;
    CompilationUnit cu;

    public List<String> getJavaDocCommentedMethods(List<String> list) throws Exception {
        listOfMethods = new ArrayList<>();
        try {
            for(String filePath : list) {
                 in = new FileInputStream(filePath);
                 try {
                     cu = JavaParser.parse(in);
                 }catch (Exception e){continue;}
                new MethodVisitor().visit(cu, null);
            }
        }catch (Exception e){}
        return listOfMethods;
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
            if(n.getComment() == null && n.getJavaDoc()== null) {
                //System.out.println(n.getComment());
                //System.out.println(n.getName());
                listOfMethods.add(n.getName());
            }
            super.visit(n, arg);
        }
    }

}
