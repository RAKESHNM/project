package com.razorthink.application.controllers;

import com.razorthink.application.beans.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by antolivish on 16/3/17.
 */
public class AbstractContrller {

    @Autowired
    HttpServletRequest request;

    /**
     * creates a session for given user details(session based authentication)
     * @return
     * @throws Exception
     */
    public Project getProject() throws Exception{
        try {
            Project project = (Project) request.getSession().getAttribute("user-det");
            if (project == null)
                throw new Exception("User Details not found");
            return project;
        }catch (Exception e){}
        return null;
    }
}
