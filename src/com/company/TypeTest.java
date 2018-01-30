package com.company;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class TypeTest extends TestCase{
    @Test
    public void test(){
        testCheckType("file", ".txt", "file.txt");
        testCheckType("file.txt", ".txt", "file.txt");
        //testCheckType("file.txt", ".txt", "file");
    }


    public void testCheckType(String fileName, String fileType, String result){
        Assert.assertTrue(MyForm.checkType(fileName, fileType).equals(result));
    }
}

