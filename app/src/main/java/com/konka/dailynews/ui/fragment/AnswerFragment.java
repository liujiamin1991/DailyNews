package com.konka.dailynews.ui.fragment;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;

import com.konka.dailynews.R;
import com.konka.dailynews.adapter.ADPagerAdapter;
import com.konka.dailynews.adapter.AnswerDailyAdapter;
import com.konka.dailynews.base.BaseFragment;
import com.konka.dailynews.model.TopDailyBean;
import com.konka.dailynews.widget.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

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

    ViewPager vpAD;

    CircleIndicator pagerIndicator;

    private Animation animation;

    private List<String> ltDailys = new ArrayList<>();
    AnswerDailyAdapter dailyAdapter;

    private Timer mTimer;
    private BannerTask mTimerTask;
    private boolean mIsUserTouched = false;
    private int mPagerPosition = 0;
    private int size;

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
        initProgressAnim();
        initContentView();
        initData();
    }

    private void initProgressAnim()
    {
        animation = AnimationUtils.loadAnimation(activity,R.anim.progress_rotate);
        animation.setFillAfter(true);
    }

    private void initContentView()
    {
        final View viewHeader = LayoutInflater.from(activity).inflate(R.layout.layout_answerdaily_header,null);
        vpAD = (ViewPager) viewHeader.findViewById(R.id.vp_ad);
        pagerIndicator = (CircleIndicator) viewHeader.findViewById(R.id.pager_indicator);
        lvDailys.addHeaderView(viewHeader);
        initPager();

        dailyAdapter = new AnswerDailyAdapter(activity,ltDailys);
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
                if(viewHeader.getTop() >= 0)
                {
                    swRefresh.setEnabled(true);
                }
                else
                {
                    swRefresh.setEnabled(false);
                }
                if(firstVisibleItem+visibleItemCount == totalItemCount)
                {
                    View lastVisibleItemView = lvDailys.getChildAt(lvDailys.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == lvDailys.getHeight())
                    {
                        initData();
                    }
                }
            }
        });
    }

    private void initData()
    {
        for(int i=0; i<20; i++)
        {
            ltDailys.add("test"+i);
        }
        dailyAdapter.setLtDailys(ltDailys);
        dailyAdapter.notifyDataSetChanged();
    }

    private void initPager()
    {
        vpAD.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                if(action==MotionEvent.ACTION_DOWN || action==MotionEvent.ACTION_MOVE)
                {
                    mIsUserTouched = true;
                    swRefresh.setEnabled(false);
                }
                else if(action == MotionEvent.ACTION_UP)
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

        //test
        List<TopDailyBean> ltTop = new ArrayList<>();
        for(int i=0; i<5; i++)
        {
            ltTop.add(new TopDailyBean("tst","tst"));
        }
        size = ltTop.size();
        ADPagerAdapter adapter = new ADPagerAdapter(activity,ltTop);
        vpAD.setAdapter(adapter);
        pagerIndicator.setViewPager(ltTop.size(), vpAD);
        vpAD.setCurrentItem(mPagerPosition);

        startViewPagerRun();
    }

    private void showProgress()
    {
        if(animation == null) return;

        ivProgress.setVisibility(View.VISIBLE);
        ivProgress.startAnimation(animation);
    }

    private void hideProgress()
    {
        ivProgress.clearAnimation();
        ivProgress.setVisibility(View.INVISIBLE);
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
