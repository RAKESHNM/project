package com.razorthink.application.utils;
import japa.parser.JavaParser;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;

/**
 * Created by rakesh on 27/2/17.
 */
public class MethodLinePrinter {
    public static int noOfLines = 0;
    public void noOfLinesInAMethod(String filePath,int lines) throws Exception {
        noOfLines = lines;
        try {
            // creates an input stream for the file to be parsed
            FileInputStream in = new FileInputStream(filePath);
            // parse it
            japa.parser.ast.CompilationUnit cu = JavaParser.parse(in);

            // visit and print the methods names
            new MethodVisitor().visit(cu, null);
        }catch (Exception e ){}
    }
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this
             CompilationUnit, including inner class methods */
            // System.out.println(MethodLinePrinter.noOfLines);
            //System.out.println(n.getName());
            //System.out.println("From [" + n.getBeginLine() + "," + n.getBeginColumn() + "] to [" + n.getEndLine() + ","
            //      + n.getEndColumn() + "] is method:");
            if((n.getEndLine() - n.getBeginLine())>=noOfLines)
                System.out.println(n.getName() + " : "+(n.getEndLine()-n.getBeginLine()));

        }
    }
}

