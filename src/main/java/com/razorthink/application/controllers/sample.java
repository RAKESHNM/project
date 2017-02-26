package com.razorthink.application.controllers;
import com.razorthink.application.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by rakesh on 26/2/17.
 */
@RestController
@Service
@Transactional
public class sample {
    @Autowired
    private Environment env;
    @RequestMapping("/path")
    public void path(){
        System.out.println(env);
        System.out.println(env.getProperty(Constants.LOCAL_DIRECTORY_PATH));
    }
}
