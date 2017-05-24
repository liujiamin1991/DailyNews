package com.konka.dailynews.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by ljm on 2017-5-23.
 */
public class DailyNewsApp extends Application
{
    public static Context mAppContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mAppContext = this;
    }

    public static Context getContext()
    {
        return mAppContext;
    }
}
