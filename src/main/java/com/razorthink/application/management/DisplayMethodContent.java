package com.razorthink.application.management;

import com.razorthink.application.constants.HtmlConstants;
import com.razorthink.application.constants.ValidNames;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.lang.*;
import java.util.List;

/**
 * Created by antolivish on 10/3/17.
 */
public class DisplayMethodContent  {
     List<String> listOfMethods;
    private  String name;
    private String returnValue = null;
    private String currentFilePath;
    private String classMethodFilePath;
    public static int id = 0;

    public String showMethodContent(List<String> filePaths, String methodName, String methodFilePath) throws Exception {
        id = 0;
        classMethodFilePath = methodFilePath;
        name = methodName;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;

            for (String filePath : filePaths) {

                currentFilePath = filePath;

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

               if( n.getName().equals(name.substring(name.indexOf(' ')+1)) ){
                   if(n.getParameters() != null) {
                       if ((currentFilePath + "+" + n.getParameters()).equals(classMethodFilePath))
                           returnValue = "<b> JavaDocs: </b>" + n.getComment() + "<br><br>" +
                                   "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<b> Parameters: </b>" +
                                   n.getParameters() + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody() + "</pre>";
                   }
                   if(n.getParameters() == null){
                       if((currentFilePath + "+" + "none").equals(classMethodFilePath))
                           returnValue = "<b> JavaDocs: </b>" + n.getComment() + "<br><br>" +
                                   "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<b> Parameters: </b>" +
                                   n.getParameters() + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody() + "</pre>";
                   }
               }
               id++;
               //returnValue = String.valueOf(n.getBody());

               super.visit(n, arg);
           }
    }

}
