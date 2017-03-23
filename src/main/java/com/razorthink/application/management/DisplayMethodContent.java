package com.razorthink.application.management;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
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
//        filePaths.clear();
//        filePaths.add("/home/antolivish/FinalTest/bigbrain_master/refinement/bigbrain-job-executor/src/main/java/com/razorthink/bigbrain/refine/jobexecutor/config/SwaggerConfig.java");
            for (String filePath : filePaths) {


                currentFilePath = filePath;

                 in = new FileInputStream(filePath);

                try {
                    cu = JavaParser.parse(in);
                }catch (Exception e){
                    e.printStackTrace();
                    continue;}

                new MethodVisitor().visit(cu, null);
            }
         return returnValue;
    }

    private  class MethodVisitor extends VoidVisitorAdapter<Void>  {


           @Override
           public void visit (MethodDeclaration n, Void arg){
               if( n.getName().toString().equals(name.substring(name.indexOf(' ')+1)) ){
                   String param = null;
                   if(n.getParameters() != null || !n.getParameters().isEmpty())
                       for(Parameter p : n.getParameters())
                           param += p;
                   if(n.getParameters() != null &&  !n.getParameters().isEmpty() ) {
                       if ((currentFilePath + "+" + param).equals(classMethodFilePath)) {
                           if (n.getComment().isPresent() && n.getBody().isPresent()) {
                               returnValue = "<b> JavaDocs: </b>" + n.getComment().get() + "<br><br>" +
                                       "<b> Method Name: </b>" + n.getName() + "<br><br>"
                                       + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody().get() + "</pre>";
                           }
                           if (n.getComment().isPresent() && !n.getBody().isPresent()) {

                               returnValue = "<b> JavaDocs: </b>" + n.getComment().get() + "<br><br>" +
                                       "<b> Method Name: </b>" + n.getName() + "<br><br>"
                                       + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + "none" + "</pre>";
                           }
                           if (n.getBody().isPresent() && !n.getComment().isPresent()) {
                               returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" +
                                       "<b> Method Name: </b>" + n.getName() + "<br><br>"
                                       + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody().get() + "</pre>";
                           }
                           if (!n.getBody().isPresent() && !n.getComment().isPresent()) {
                               returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" +
                                       "<b> Method Name: </b>" + n.getName() + "<br><br>"
                                       + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + "none" + "</pre>";
                           }
                       }
                   }

                   if (n.getParameters() == null || n.getParameters().isEmpty()) {
                           if ((currentFilePath + "+" + "none").equals(classMethodFilePath)) {
                               if( n.getBody().isPresent() && !n.getComment().isPresent())
                               returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" +
                                       "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody().get() + "</pre>";
                                if(n.getComment().isPresent() && !n.getBody().isPresent())
                                   returnValue = "<b> JavaDocs: </b>" + n.getComment().get() + "<br><br>" +
                                           "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + "none" + "</pre>";
                               if(n.getBody().isPresent() && n.getComment().isPresent())
                                   returnValue = "<b> JavaDocs: </b>" + n.getComment().get() + "<br><br>" +
                                           "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + n.getBody().get() + "</pre>";
                               if(!n.getBody().isPresent() && !n.getComment().isPresent())
                                   returnValue = "<b> JavaDocs: </b>" + "none" + "<br><br>" +
                                           "<b> Method Name: </b>" + n.getName() + "<br><br>" + "<br><br>" + "<b> Method Logic: </b>" + "<br>" + "<pre>" + "none" + "</pre>";

                           }
                       }
               }

               //returnValue = String.valueOf(n.getBody());

               super.visit(n, arg);
           }
    }

}
