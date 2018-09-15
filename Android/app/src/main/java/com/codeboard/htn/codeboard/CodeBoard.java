package com.codeboard.htn.codeboard;

import android.app.Application;
import android.content.Context;

public class CodeBoard extends Application {

    private static Context appContext;

    public static Context getContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}

