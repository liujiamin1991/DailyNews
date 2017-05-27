package com.konka.dailynews.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by ljm on 2017-5-27.
 */
public abstract class BaseFragment extends Fragment
{
    protected BaseActivity activity;
    public BaseFragment()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,rootView);
        initView();
        return rootView;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
}
