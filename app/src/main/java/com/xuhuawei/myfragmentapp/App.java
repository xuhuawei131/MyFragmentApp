package com.xuhuawei.myfragmentapp;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    //全局应用上下文
    private static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
            //初始化全局Application
            mAppContext = this;
    }
    /*
     * 获得全局上下文
     */
    public static Context getAppContext() {
        return mAppContext;
    }
}
