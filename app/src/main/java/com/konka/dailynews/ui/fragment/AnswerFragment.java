package com.konka.dailynews.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.konka.dailynews.R;
import com.konka.dailynews.adapter.ADPagerAdapter;
import com.konka.dailynews.adapter.AnswerDailyAdapter;
import com.konka.dailynews.base.BaseFragment;
import com.konka.dailynews.model.DailyBean;
import com.konka.dailynews.model.DailyListBean;
import com.konka.dailynews.model.TopDailyBean;
import com.konka.dailynews.model.TopDailys;
import com.konka.dailynews.network.HttpResponseListener;
import com.konka.dailynews.network.NetConstant;
import com.konka.dailynews.network.OkhttpHelper;
import com.konka.dailynews.ui.activity.NewsDetails;
import com.konka.dailynews.widget.CircleIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by ljm on 2017-5-27.
 */
public class AnswerFragment extends BaseFragment implements Runnable
{
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout swRefresh;

    @Bind(R.id.iv_progress)
    ImageView ivProgress;

    @Bind(R.id.lv_dailys)
    ListView lvDailys;

    public static final String TAG = "AnswerFragment";
    public static final String LATEST_NEWS_URL = NetConstant.ZHIHU_DAILY_URL + NetConstant.LATEST_NEWS;

    ViewPager vpAD;

    CircleIndicator pagerIndicator;

    private Animation animation;

    AnswerDailyAdapter dailyAdapter;

    private Timer mTimer;
    private BannerTask mTimerTask;
    private boolean mIsUserTouched = false;
    private int mPagerPosition = 0;
    private int size;

    private OkhttpHelper okhttpHelper;
    private String currTime = "";
    private DailyListBean dailyListBean;
    private List<TopDailys> ltTopDayiys;
    private List<DailyBean> ltDaylyBeans = new ArrayList<>();

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    hideProgress();
                    swRefresh.setRefreshing(false);
                    break;

                case 1:
                    size = ltTopDayiys.size();
                    ADPagerAdapter adapter = new ADPagerAdapter(activity, ltTopDayiys);
                    vpAD.setAdapter(adapter);
                    pagerIndicator.setViewPager(size, vpAD);
                    vpAD.setCurrentItem(mPagerPosition);

                    dailyAdapter.setLtDailys(ltDaylyBeans);

                    hideProgress();
                    swRefresh.setRefreshing(false);
                    startViewPagerRun();

                    break;

                case 2:
                    dailyAdapter.setLtDailys(ltDaylyBeans);
                    break;
            }
        }
    };

    public AnswerFragment()
    {
        super();
    }


    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_answer;
    }

    @Override
    protected void initView()
    {
        okhttpHelper = OkhttpHelper.getInstance();
        initProgressAnim();
        initContentView();
        getLatestNews();
    }

    private void initProgressAnim()
    {
        animation = AnimationUtils.loadAnimation(activity,R.anim.progress_rotate);
        animation.setFillAfter(true);
    }

    private void initContentView()
    {
        showProgress();

        swRefresh.setColorSchemeResources(R.color.colorPrimary);
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getLatestNews();
            }
        });

        final View viewHeader = LayoutInflater.from(activity).inflate(R.layout.layout_answerdaily_header,null);
        vpAD = (ViewPager) viewHeader.findViewById(R.id.vp_ad);
        pagerIndicator = (CircleIndicator) viewHeader.findViewById(R.id.pager_indicator);
        lvDailys.addHeaderView(viewHeader);
        initPager();

        dailyAdapter = new AnswerDailyAdapter(activity,ltDaylyBeans);
        lvDailys.setAdapter(dailyAdapter);
        lvDailys.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (viewHeader.getTop() >= 0)
                {
                    swRefresh.setEnabled(true);
                } else
                {
                    swRefresh.setEnabled(false);
                }
                if (firstVisibleItem + visibleItemCount == totalItemCount)
                {
                    View lastVisibleItemView = lvDailys.getChildAt(lvDailys.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == lvDailys.getHeight())
                    {
                        getBeforeNews(currTime);
                    }
                }
            }
        });

        lvDailys.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(activity, NewsDetails.class);
                activity.startActivity(intent);
            }
        });
    }

    private void getLatestNews()
    {
        okhttpHelper.setResponseListener(new HttpResponseListener()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.i(TAG, "onFailure");
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr)
            {
                Log.i(TAG, "bodyStr:" + bodyStr);

                if (bodyStr == null || bodyStr == "")
                {
                    handler.sendEmptyMessageDelayed(0, 2000);
                    return;
                }

                Gson gson = new Gson();
                try
                {
                    dailyListBean = gson.fromJson(bodyStr, DailyListBean.class);
                } catch (JsonSyntaxException ex)
                {
                    ex.printStackTrace();
                }

                if (dailyListBean == null)
                {
                    handler.sendEmptyMessageDelayed(0, 2000);
                    return;
                }

                ltTopDayiys = dailyListBean.getTop_stories();
                ltDaylyBeans = dailyListBean.getStories();
                if (ltTopDayiys == null || ltDaylyBeans == null)
                {
                    handler.sendEmptyMessageDelayed(0, 2000);
                    return;
                }

                for (DailyBean dailyBean : ltDaylyBeans)
                {
                    dailyBean.setDate(dailyListBean.getDate());
                }
                currTime = dailyListBean.getDate();
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        });
        okhttpHelper.httpGet(LATEST_NEWS_URL);
    }

    private void getBeforeNews(String cuTime)
    {
        okhttpHelper.setResponseListener(new HttpResponseListener()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Log.i(TAG, "Before News onFailure");
            }

            @Override
            public void onResponse(Call call, Response response, String bodyStr)
            {
                Log.i(TAG, "Before News bodyStr:" + bodyStr);

                if(bodyStr==null || bodyStr=="")    return;

                Gson gson = new Gson();
                try
                {
                    dailyListBean = gson.fromJson(bodyStr, DailyListBean.class);
                } catch (JsonSyntaxException ex)
                {
                    ex.printStackTrace();
                }

                if (dailyListBean == null)  return;

                List<DailyBean> bfDailyBeans = dailyListBean.getStories();

                if(bfDailyBeans == null)  return;

                for (DailyBean dailyBean : bfDailyBeans)
                {
                    dailyBean.setDate(dailyListBean.getDate());
                }

                if(ltDaylyBeans == null)
                    ltDaylyBeans = bfDailyBeans;
                else
                    ltDaylyBeans.addAll(bfDailyBeans);

                currTime = dailyListBean.getDate();
                handler.sendEmptyMessage(2);
            }
        });
        okhttpHelper.httpGet(NetConstant.ZHIHU_DAILY_URL+NetConstant.BEFORE_NEWS+cuTime);
    }

    private void initPager()
    {
        vpAD.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                if(action==MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)
                {
                    mIsUserTouched = true;
                    swRefresh.setEnabled(false);
                } else if (action == MotionEvent.ACTION_UP)
                {
                    mIsUserTouched = false;
                    swRefresh.setEnabled(true);
                }
                return false;
            }
        });

        vpAD.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                mPagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    private void showProgress()
    {
        if(animation == null) return;

        ivProgress.setVisibility(View.VISIBLE);
        lvDailys.setVisibility(View.INVISIBLE);
        ivProgress.startAnimation(animation);
    }

    private void hideProgress()
    {
        ivProgress.clearAnimation();
        ivProgress.setVisibility(View.INVISIBLE);
        lvDailys.setVisibility(View.VISIBLE);
    }

    public void startViewPagerRun()
    {
        //执行ViewPager进行轮播
        mTimer = new Timer();
        mTimerTask = new BannerTask();
        mTimer.schedule(mTimerTask, 10000, 10000);
    }

    @Override
    public void run()
    {
        vpAD.setCurrentItem(mPagerPosition);
    }


    private class BannerTask extends TimerTask
    {

        @Override
        public void run()
        {

            if (!mIsUserTouched)
            {
                mPagerPosition = (mPagerPosition + 1) % size;
                if (activity != null)
                {
                    activity.runOnUiThread(AnswerFragment.this);
                }
            }
        }
    }


    @Override
    public void onAttach(Context context)
    {
        Log.i("testljm","Fragment1 onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i("testljm","Fragment1 onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("testljm","Fragment1 onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.i("testljm","Fragment1 onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Log.i("testljm","Fragment1 onStart");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.i("testljm","Fragment1 onResume");
        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.i("testljm","Fragment1 onPause");
        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.i("testljm","Fragment1 onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        Log.i("testljm","Fragment1 onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        Log.i("testljm","Fragment1 onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.i("testljm","Fragment1 onDetach");
        super.onDetach();
    }
}
