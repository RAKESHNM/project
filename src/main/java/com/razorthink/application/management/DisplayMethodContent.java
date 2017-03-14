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
public class DisplayMethodContent  {
     List<String> listOfMethods;
    private  String name;
    private String returnValue = null;
    public String showMethodContent(List<String> filePaths, String methodName) throws Exception {

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
         return returnValue;
    }

    private  class MethodVisitor extends VoidVisitorAdapter<Void>  {


           @Override
           public void visit (MethodDeclaration n, Void arg){

               if(n.getName().equals(name))
               //returnValue = String.valueOf(n.getBody());
                   returnValue = "JavaDocs: "+ n.getComment()+ "\n" +
                           "Method Name: "+ n.getName()+ "\n" + "\n" + "Parameters: " +
                           n.getParameters() + "\n" + "\n"+" Method Logic: " + "\n" + n.getBody();
               super.visit(n, arg);
           }



    }
}
