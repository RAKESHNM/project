package com.razorthink.application.management;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antolivish on 10/3/17.
 */
public class DisplayMethodContent {
     List<String> listOfMethods;

    public static  Frame f = new Frame();

    private  String name;
    public void showMethodContent(List<String> filePaths, String methodName) throws Exception {

        name = methodName;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;

            for (String filePath : filePaths) {
                 in = new FileInputStream(filePath);

                try {
                    cu = JavaParser.parse(in);
                }catch (Exception e){continue;}

                new MethodVisitor().visit(cu, null);
            }

    }

    private  class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {

            if(n.getName().equals("setLocalRepoPath")){
                //Frame f =  new Frame();
                TextArea textArea = new TextArea(String.valueOf(n.getBody()));
                textArea.setBounds(10,30,1200,600);
                f.add(textArea);
                f.setSize(400,400);
                f.setLayout(null);
                f.setVisible(true);
            }
            super.visit(n, arg);
        }
    }
}
