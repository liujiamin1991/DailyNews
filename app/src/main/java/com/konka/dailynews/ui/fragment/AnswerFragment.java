package com.konka.dailynews.ui.fragment;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.konka.dailynews.R;
import com.konka.dailynews.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by ljm on 2017-5-27.
 */
public class AnswerFragment extends BaseFragment
{
    @Bind(R.id.iv_progress)
    ImageView ivProgress;

    private Animation animation;

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
    }

    private void initProgressAnim()
    {
        animation = AnimationUtils.loadAnimation(activity,R.anim.progress_rotate);
        animation.setFillAfter(true);
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
}
