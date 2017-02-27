package com.razorthink.application.utils;

import org.junit.Before;
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
public class MethodLinePrinterTest {

    @InjectMocks
    MethodLinePrinter methodLinePrinter;

    private int lines;
    private String filePath ;

    @Before
    public void setUp(){
    lines = 2;
    filePath = "home/rakesh/Project.java";
    }

    /**
     * listing no of lines for all methods when file path is null
     * @throws Exception(NullPointerException)
     */
    @Test
    public void noOfLinesInAMethodForNullFilePathTest() throws Exception {
        methodLinePrinter.noOfLinesInAMethod(null,lines);
    }

    /**
     * listing no of lines for all methods when given a valid file path
     */
    @Test
    public void noOfLinesInAMethodForValidFilePathTest() throws Exception {
        methodLinePrinter.noOfLinesInAMethod(filePath,lines);
    }

}
