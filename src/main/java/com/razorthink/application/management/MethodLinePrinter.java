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

    static Map<String,Integer> map = new HashMap<>();

    public static int noOfLines = 0;

    public Map<String,Integer> noOfLinesInAMethod(List<String> filePaths, int lines) throws Exception {

        noOfLines = lines;

        try {
            for (String filePath : filePaths) {
                FileInputStream in = new FileInputStream(filePath);
                japa.parser.ast.CompilationUnit cu = JavaParser.parse(in);
                new MethodVisitor().visit(cu, null);
            }

        } catch ( Exception e ) {}
        return map;
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {

            if((n.getEndLine() - n.getBeginLine())>=noOfLines) {

               // System.out.println("Method name: " + n.getName() + "No of lines: " + (n.getEndLine() - n.getBeginLine()));
                map.put(n.getName(), (n.getEndLine() - n.getBeginLine()));
            }
        }
    }
}

