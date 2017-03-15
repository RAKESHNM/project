package com.razorthink.application.constants;

import java.io.File;

/**
 * Created by rakesh on 25/2/17.
 */
public class Constants {
    public static final String LOCAL_DIRECTORY_PATH = "/home/rakesh/StoreProjects";
    public static final String CONTROLLER_ROUTE = "/";
    public static final String NULL_PROJECT_PROP = "project properties should not be null";
    public static final String GITHUB_CREDENTIAL = "/credential";
    public static final String GITHUB_CHECKOUT_ROUTE = "/checkout";
  //  public static final String LIST_AVAILABLE_PROJECTS_ROUTE = "/projects";
    public static final String LIST_ALL_METHODS="/methods";
    public static final String LIST_ALL_FILES="allfiles";
    public static final String NULL_FILE_PATHS_LIST = "file paths list should not be null";
    public static final String LIST_AVAILABLE_PROJECTS_ROUTE = "/projects";
    public static final String INPUTS_FROM_USER = "/inputs";
    public static final String LIST_BRANCH="/branch";
    public static final String INVALID_CREDENTIAL = "Invalid credential";
    public static final String DOT_GIT_EXTENSION = ".git";
    public static final String SLASH_EXTENSION = System.getProperty(File.separator);
    public static final String LIST_ALL_REPOSITORIES = "repositories";
    public static final String SHOW_METHOD_CONTENTS = "/methodcontents";
    public static final String SHOW_FILE_CONTENTS = "/filecontents";
}
