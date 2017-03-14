package com.razorthink.application.beans;

/**
 * Created by antolivish on 11/3/17.
 */
public class MethodDeclaration {

    private String methodName;

    public MethodDeclaration(){

    }

    public MethodDeclaration(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
