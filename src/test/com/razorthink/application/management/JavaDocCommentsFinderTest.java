package com.razorthink.application.management;

import org.junit.Before;
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
public class JavaDocCommentsFinderTest {

    @InjectMocks
    JavaDocCommentsFinder javaDocCommentsFinder;

    String filePath = "/home/rakesh/pom.xml";

    List<String> list;

    @Before
    public void main(){
     list = new ArrayList<>();
     list.add(filePath);
    }

    /**
     * Test for getJavaDocCommentedMethods for null file path
     */
    @Test
    public void getJavaDocCommentedMethodsForNullFilePathTest() throws Exception {
        javaDocCommentsFinder.getJavaDocCommentedMethods(null);
    }

    @Test
    public void getJavaDocCommentedMethodsForValidFilePathTest() throws Exception {
        javaDocCommentsFinder.getJavaDocCommentedMethods(list);
    }
}
