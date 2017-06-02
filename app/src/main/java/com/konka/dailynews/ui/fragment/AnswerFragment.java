package com.konka.dailynews.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.konka.dailynews.R;
import com.konka.dailynews.adapter.ADPagerAdapter;
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
    @Bind(R.id.iv_progress)
    ImageView ivProgress;

    @Bind(R.id.vp_ad)
    ViewPager vpAD;

    @Bind(R.id.pager_indicator)
    CircleIndicator pagerIndicator;

    private Animation animation;

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
        initPager();
    }

    private void initProgressAnim()
    {
        animation = AnimationUtils.loadAnimation(activity,R.anim.progress_rotate);
        animation.setFillAfter(true);
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
                }
                else if(action == MotionEvent.ACTION_UP)
                {
                    mIsUserTouched = false;
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
                    getActivity().runOnUiThread(AnswerFragment.this);
                }
            }
        }
    }
}
