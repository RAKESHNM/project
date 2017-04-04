package com.razorthink.application.management;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.razorthink.application.constants.HtmlConstants;
import com.razorthink.application.constants.ValidNames;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antolivish on 10/3/17.
 */
public class DisplayMethodContent {

    List<String> listOfMethods;
    private String name;
    private String returnValue = null;
    private String currentFilePath;
    private String classMethodFilePath;
    public static int id = 0;

    public String showMethodContent( List<String> filePaths, String methodName, String methodFilePath ) throws Exception
    {
        /**
         * Loop through each files, parse it and extract all the methods till method name matches with given method
         * name and send contents,name ,parameters and java docs for a given method name.
         */
        id = 0;
        classMethodFilePath = methodFilePath;
        name = methodName;
        listOfMethods = new ArrayList<>();
        FileInputStream in;
        CompilationUnit cu;
        //filePaths.clear();
        //filePaths.add("/home/rakesh/bigbrain_master/designer/commons/src/main/java/com/razorthink/bigbrain/designer/commons/domain/ModelRun.java");

        try {
            for (String filePath : filePaths) {
                currentFilePath = filePath;
                in = new FileInputStream(filePath);
                cu = JavaParser.parse(in);
                new MethodVisitor().visit(cu, null);
            }
        }catch (IOException io){}
        return returnValue;
    }

    private class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
           public void visit (MethodDeclaration n, Void arg){
            /**
             * if the method name matches with the given method name then send contents,parameters,javadocs of that method
             * according to which is null assign null value
             */
               if( n.getName().toString().equals(name.substring(name.indexOf(' ')+1)) ){
                   String param = null;
                   if(n.getParameters() != null || !n.getParameters().isEmpty())
                       for(Parameter p : n.getParameters())
                           param += p;
                   if(n.getParameters() != null &&  !n.getParameters().isEmpty() ) {
                       if ((currentFilePath + "+" + param).equals(classMethodFilePath)) {
                           if (n.getComment().isPresent() && n.getBody().isPresent()) {
                               returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END + n.getComment().get() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                       HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK
                                       + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END +  HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + n.getBody().get() + HtmlConstants.PRE_END;
                           }
                           if (n.getComment().isPresent() && !n.getBody().isPresent()) {

                               returnValue = HtmlConstants.BOLD_BEGIN+ ValidNames.JAVADOC+HtmlConstants.BOLD_END+ n.getComment().get() + HtmlConstants.LINE_BREAK+HtmlConstants.LINE_BREAK +
                                       HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK
                                       + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + "none" + HtmlConstants.PRE_END;
                           }
                           if (n.getBody().isPresent() && !n.getComment().isPresent()) {
                               returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END  + "none" + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                       HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK
                                       + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + n.getBody().get() + HtmlConstants.PRE_END;
                           }
                           if (!n.getBody().isPresent() && !n.getComment().isPresent()) {
                               returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END + "none" + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                       HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK
                                       + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + "none" + HtmlConstants.PRE_END;
                           }
                       }
                   }

                   if (n.getParameters() == null || n.getParameters().isEmpty()) {
                           if ((currentFilePath + "+" + "none").equals(classMethodFilePath)) {
                               if( n.getBody().isPresent() && !n.getComment().isPresent())
                               returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END  + "none" + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                       HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END  + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + n.getBody().get() + HtmlConstants.PRE_END;
                                if(n.getComment().isPresent() && !n.getBody().isPresent())
                                   returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END + n.getComment().get() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                           HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + "none" + HtmlConstants.PRE_END;
                               if(n.getBody().isPresent() && n.getComment().isPresent())
                                   returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END + n.getComment().get() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                           HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END  + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + n.getBody().get() + HtmlConstants.PRE_END;
                               if(!n.getBody().isPresent() && !n.getComment().isPresent())
                                   returnValue = HtmlConstants.BOLD_BEGIN + ValidNames.JAVADOC + HtmlConstants.BOLD_END + "none" + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK +
                                           HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_NAME + HtmlConstants.BOLD_END + n.getName() + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.LINE_BREAK + HtmlConstants.BOLD_BEGIN + ValidNames.METHOD_LOGIC + HtmlConstants.BOLD_END + HtmlConstants.LINE_BREAK + HtmlConstants.PRE_BEGIN + "none" + HtmlConstants.PRE_END;

                    }
                }
            }
               super.visit(n, arg);
        }
    }

}
