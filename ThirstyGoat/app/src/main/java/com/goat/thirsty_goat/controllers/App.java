package com.goat.thirsty_goat.controllers;

import android.app.Application;
import android.content.Context;

/**
 * Created by GabrielNAN on 3/7/17.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static String getResString(int val) {
        return mContext.getString(val);
    }
}
