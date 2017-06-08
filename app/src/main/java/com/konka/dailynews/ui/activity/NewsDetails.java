package com.konka.dailynews.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.konka.dailynews.R;
import com.konka.dailynews.base.BaseActivity;
import com.konka.dailynews.model.DailyDetail;
import com.konka.dailynews.network.HttpResponseListener;
import com.konka.dailynews.network.NetConstant;
import com.konka.dailynews.network.OkhttpHelper;
import com.konka.dailynews.utils.HtmlUtil;

import java.io.IOException;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ljm on 2017-6-8.
 */
public class NewsDetails extends BaseActivity
{
    @Bind(R.id.toobar)
    Toolbar toolbar;

    @Bind(R.id.wv_content)
    WebView wvContent;

    public static final String TAG = "NewsDetails";

    ActionBar mActionBar;

    DailyDetail dailyDetail;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:

                    break;

                case 1:
                    String htmlData = HtmlUtil.createHtmlData(dailyDetail);
                    wvContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_news_details;
    }

    @Override
    public void initView(Bundle savedInstanceState)
    {
        getDailyDetail("9464996");
    }

    @Override
    public void initToolBar()
    {
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        if (mActionBar != null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mActionBar.setTitle("");

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void getDailyDetail(String id)
    {
        OkhttpHelper okhttpHelper = OkhttpHelper.getInstance();
        okhttpHelper.setResponseListener(new HttpResponseListener()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr)
            {
                Log.i(TAG,"NewsDetails bodyStr"+bodyStr);
                Gson gson = new Gson();
                dailyDetail = gson.fromJson(bodyStr,DailyDetail.class);
                handler.sendEmptyMessage(1);
            }
        });
        okhttpHelper.httpGet(NetConstant.ZHIHU_DAILY_URL+NetConstant.DAILY_DETAIL+id);
    }
}
