package com.konka.dailynews.network;

import com.konka.dailynews.base.DailyNewsApp;
import com.konka.dailynews.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ljm on 2017-5-23.
 */
public class OkhttpHelper
{
    public static final String TAG = "OkhttpHelper";
    public static final int CACHE_TIME_LONG = 60 * 60 * 24 * 7;

    private static OkhttpHelper instance = null;

    private OkHttpClient okHttpClient = null;
    private HttpResponseListener responseListener = null;

    public static synchronized OkhttpHelper getInstance()
    {
        if(instance == null)
            instance = new OkhttpHelper();
        return  instance;
    }

    private OkhttpHelper()
    {
        //设置http log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置http缓存
        Cache cache = new Cache(new File(DailyNewsApp.getContext().getCacheDir(),"Httpcache"),1024 * 1024 * 100);

        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    private Interceptor mRewriteCacheControlInterceptor = new Interceptor()
    {

        @Override
        public Response intercept(Chain chain) throws IOException
        {

            Request request = chain.request();
            if (!NetWorkUtil.isNetworkConnected())
            {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetworkConnected())
            {
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl).removeHeader("Pragma").build();
            } else
            {
                return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_TIME_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };

    public void setResponseListener(HttpResponseListener responseListener)
    {
        this.responseListener = responseListener;
    }

    public void httpGet(String urlStr)
    {
        if(okHttpClient == null)  return;

        Request.Builder builder = new Request.Builder().url(urlStr);
        builder.method("GET",null);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if(responseListener != null)
                {
                    responseListener.onFailure(call,e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if(responseListener != null)
                {
                    responseListener.onResponse(call,response,response.body().string());
                }
            }
        });
    }

    public void httpPost()
    {

    }
}
