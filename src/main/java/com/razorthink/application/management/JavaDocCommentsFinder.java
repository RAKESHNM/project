package com.razorthink.application.management;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.stmt.Statement;
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
   public static String currentFilePath;

    public List<String> getJavaDocCommentedMethods(List<String> list) throws Exception {
        listOfMethods = new ArrayList<>();
        try
        {
            for( String filePath : list )
            {
                currentFilePath = filePath;
                in = new FileInputStream(filePath);
                try
                {
                    cu = JavaParser.parse(in);
                }
                catch( Exception e )
                {
                    continue;
                }
                new MethodVisitor().visit(cu, null);
            }
        }
        catch( Exception e )
        {
        }
        return listOfMethods;
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit( MethodDeclaration n, Void arg )
        {
            /* here you can access the attributes of the method. this method will be called for all
             * methods in this CompilationUnit, including inner class methods */
            if( n.getComment() == null && n.getJavaDoc() == null )
            {
                List<Statement> list =  n.getBody().getStmts();
                int count = 0;
                for(Statement s : list){

                    if(s.getComment() != null )
                        count++;
                        //listOfMethods.add(n.getName());
                }
                if(count == 0) {
                    listOfMethods.add(n.getName());
                    listOfMethods.add(currentFilePath);
                }
                //System.out.println(n.getComment());
                //System.out.println(n.getName());

            }
            super.visit(n, arg);
        }
    }

}
