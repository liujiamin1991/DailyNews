package com.konka.dailynews.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ljm on 2017-5-23.
 */
public interface HttpResponseListener
{
    void onFailure(Call call, IOException e);

    void onResponse(Call call, Response response, String bodyStr);
}
