package com.razorthink.application.management;

import japa.parser.JavaParser;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antolivish on 10/3/17.
 */
public class DisplayMethodContent {
    static List<String> listOfMethods;

    public static Frame f = new Frame();

    public void showMethodContent(List<String> filePaths, String methodName) throws Exception {

        listOfMethods = new ArrayList<>();

        try {
            for (String filePath : filePaths) {
                FileInputStream in = new FileInputStream(filePath);
                japa.parser.ast.CompilationUnit cu = JavaParser.parse(in);
                new DisplayMethodContent.MethodVisitor().visit(cu, null);
            }

        } catch ( Exception e ) {}

    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {

            if(n.getName() == "methodName"){
                TextArea textArea = new TextArea(String.valueOf(n.getBody()));
                textArea.setBounds(10,30,1200,600);
                f.add(textArea);
                f.setSize(400,400);
                f.setLayout(null);
                f.setVisible(true);
            }
        }
    }
}
