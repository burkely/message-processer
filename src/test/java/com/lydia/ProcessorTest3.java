package com.lydia;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;


public class ProcessorTest3 {


    @Test
    public void loadTest() throws IOException{
        ProcessorTest test1 = new ProcessorTest();
        ProcessorTest test2 = new ProcessorTest();
        ProcessorTest test3 = new ProcessorTest();

        test1.testHandlerThread();
        test2.testHandlerThread();
        test3.testHandlerThread();

    }
}