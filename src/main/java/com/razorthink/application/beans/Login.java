package com.razorthink.application.beans;

/**
 * Created by rakesh on 1/3/17.
 */
public class Login {

    String userName;
    String password;

    public Login(){

    }

    public Login(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
