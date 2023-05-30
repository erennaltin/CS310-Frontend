package com.example.test;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebService extends Application {

    ExecutorService srv = Executors.newCachedThreadPool();

    public ExecutorService getSrv()
    {
        return srv;
    }
}
