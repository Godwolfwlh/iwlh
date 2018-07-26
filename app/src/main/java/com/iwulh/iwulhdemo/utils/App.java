package com.iwulh.iwulhdemo.utils;

import android.app.Application;

import com.luliang.shapeutils.DevShapeUtils;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DevShapeUtils.init(this);
    }
}
