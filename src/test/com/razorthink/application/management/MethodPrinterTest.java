package com.razorthink.application.management;

import com.razorthink.application.exceptions.NullFilePathsListException;
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
@PrepareForTest()
public class MethodPrinterTest {

    @InjectMocks
    MethodPrinter methodPrinter;

    List<String> list;

    @Before
    public void setUp(){
     list = new ArrayList<>();
     list.add("/home/rakesh/Project.java");
    }

    /**
     * Test for listAllMethods for null fileList
     */
    @Test
    public void listAllMethodsForNullFileListTest() throws Exception {
        methodPrinter.listAllMethods(null);
    }

    /**
     *Test for listAllMethods for valid file paths list
     */
    @Test
    public void listAllMethodsForValidFilesTest() throws Exception {
        methodPrinter.listAllMethods(list);
    }

}
