package com.razorthink.application.management;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 28/2/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MethodLinePrinter.class)
public class MethodLinePrinterTest {

    @InjectMocks
    MethodLinePrinter methodLinePrinter;

    private int lines;
    private String filePath ;
    List<String> list;

    @Before
    public void setUp(){
        list = new ArrayList<>();
        lines = 2;
        filePath = "/home/rakesh/Project.java";
        list.add(filePath);
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
        methodLinePrinter.noOfLinesInAMethod(list,2);
    }

}
