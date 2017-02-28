package com.razorthink.application.utils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by rakesh on 27/2/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class MethodPrinterTest {

    @InjectMocks
    MethodPrinter methodPrinter;

    private String filePath = "/home/rakesh/Project.java";

    @Before
    public void setUp(){

    }

    /**
     *Test for listAllMethods() for null file path.
     * @throws Exception(NullPointerException or java.io.FileNotFoundException)
     */
    @Test
    public void listAllMethodsForNullFilePathTest() throws Exception {
        methodPrinter.listAllMethods(null);
    }

    /**
     * Test for listAllMethods() for valid file path.
     * @throws Exception
     */
    @Ignore
    @Test
    public void listAllMethodsForValidFilePathTest() throws Exception {

    }


}
