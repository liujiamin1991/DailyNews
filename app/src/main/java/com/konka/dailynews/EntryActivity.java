package com.konka.dailynews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.konka.dailynews.model.LuanchImageBean;
import com.konka.dailynews.network.HttpResponseListener;
import com.konka.dailynews.network.NetConstant;
import com.konka.dailynews.network.OkhttpHelper;
import com.konka.dailynews.ui.activity.HomeActivity;

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
    private static final float ANIMATIONX_END = 1.2f;
    private static final float ANIMATIONY_END = 1.2f;
    private static final long ANIMATION_DURATION = 2000;

    private OkhttpHelper okhttpHelper;
    private LuanchImageBean luanchImageBean = null;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    Glide.with(EntryActivity.this).load(R.drawable.default_splash).into(ivLauncher);
                    handler.sendEmptyMessageDelayed(2,2000);
                    break;
                case 1:
                    if(luanchImageBean == null)
                    {
                        Glide.with(EntryActivity.this).load(R.drawable.default_splash).into(ivLauncher);
                    }
                    else
                    {
                        String img = luanchImageBean.getImg();
                        String text = luanchImageBean.getText();
                        Glide.with(EntryActivity.this).load(img).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.default_splash).into(ivLauncher);
                        tvForm.setText(text);
                    }
                    handler.sendEmptyMessageDelayed(2,2000);
                    break;
                case 2:
                    playAnim();
                    break;
            }
        }
    };

    private void playAnim()
    {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivLauncher,"scaleX",1.0f,ANIMATIONX_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivLauncher,"scaleY",1.0f,ANIMATIONY_END);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(ANIMATION_DURATION);
        animSet.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                Intent intent = new Intent(EntryActivity.this, HomeActivity.class);
                EntryActivity.this.startActivity(intent);
                EntryActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        animSet.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ButterKnife.unbind(this);
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
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr)
            {
                Log.i(TAG,"success");
                Log.i(TAG,"launchBean:"+bodyStr);

                if(bodyStr==null || bodyStr=="")
                {
                    handler.sendEmptyMessage(0);
                    return;
                }
                Gson gson = new Gson();
                try
                {
                    luanchImageBean = gson.fromJson(bodyStr, LuanchImageBean.class);
                } catch (JsonSyntaxException e)
                {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        });
        okhttpHelper.httpGet(LAUNCHER_URL);
    }
}
