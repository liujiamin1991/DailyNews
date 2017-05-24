package com.konka.dailynews.network;

import com.konka.dailynews.model.LuanchImageBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ljm on 2017-5-23.
 */
public class DailyNewsAPI
{
    public static final String TAG = "DailyNewsAPI";

    private static DailyNewsAPI instance = null;
    private OkhttpHelper okhttpHelper = null;

    public static synchronized DailyNewsAPI getInstance()
    {
        if(instance == null)
            instance = new DailyNewsAPI();

        return instance;
    }

    private DailyNewsAPI()
    {
        okhttpHelper = OkhttpHelper.getInstance();
    }


}
