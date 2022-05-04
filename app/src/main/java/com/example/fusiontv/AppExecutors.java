package com.example.fusiontv;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    //Singleton Pattern
    private static AppExecutors instance;

    public static AppExecutors getInstance() {
        if(instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    //creates the number of background threads we are going to use
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO() {
        return mNetworkIO;
    }
}
