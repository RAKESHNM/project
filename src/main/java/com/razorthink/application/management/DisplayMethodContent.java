package com.razorthink.application.management;

import com.razorthink.application.constants.HtmlConstants;
import com.razorthink.application.constants.ValidNames;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

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
    private String currentFilePath;
    private String classMethodFilePath;
    public String showMethodContent(List<String> filePaths, String methodName, String methodFilePath) throws Exception {

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

               if(n.getName().equals(name) && currentFilePath.equals(classMethodFilePath)){
                   returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END + n.getComment()+ HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                           HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName()+ HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.PARAMETERS + HtmlConstants.BOLD_END +
                           n.getParameters() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END+ HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + n.getBody() + HtmlConstants.PRE_END;
               }
               super.visit(n, arg);
           }
    }

}
