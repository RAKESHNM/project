package com.razorthink.application.beans;

/**
 * Created by antolivish on 9/3/17.
 */
public class Directory {
    private String LOCAL_DIRECTORY_PATH;
    private String LOCAL_FILEPATH_SERIALISATION;

    public String getLOCAL_DIRECTORY_PATH() {
        return LOCAL_DIRECTORY_PATH;
    }

    public void setLOCAL_DIRECTORY_PATH(String LOCAL_DIRECTORY_PATH) {
        this.LOCAL_DIRECTORY_PATH = LOCAL_DIRECTORY_PATH;
    }

    public String getLOCAL_FILEPATH_SERIALISATION() {
        return LOCAL_FILEPATH_SERIALISATION;
    }

    public void setLOCAL_FILEPATH_SERIALISATION(String LOCAL_FILEPATH_SERIALISATION) {
        this.LOCAL_FILEPATH_SERIALISATION = LOCAL_FILEPATH_SERIALISATION;
    }
}
