package com.konka.dailynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.dailynews.network.HttpResponseListener;
import com.konka.dailynews.network.NetConstant;
import com.konka.dailynews.network.OkhttpHelper;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class EntryActivity extends AppCompatActivity
{

    @Bind(R.id.iv_launch)
    ImageView ivLauncher;

    @Bind(R.id.tv_form)
    TextView tvForm;

    public static final String TAG = "EntryActivity";
    private static final String LAUNCHER_URL = NetConstant.ZHIHU_DAILY_URL+"start-image/1080*1776";

    private OkhttpHelper okhttpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);
        initData();
    }

    private void initData()
    {
        okhttpHelper = OkhttpHelper.getInstance();
        okhttpHelper.setResponseListener(new HttpResponseListener()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.i(TAG,"failure");
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr)
            {
                Log.i(TAG,"success");
                Log.i(TAG,"launchBean:"+bodyStr);
            }
        });
        okhttpHelper.httpGet(LAUNCHER_URL);
    }
}
