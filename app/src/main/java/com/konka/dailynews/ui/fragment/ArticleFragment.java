package com.konka.dailynews.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konka.dailynews.R;
import com.konka.dailynews.base.BaseFragment;

/**
 * Created by ljm on 2017-5-27.
 */
public class ArticleFragment extends BaseFragment
{
    public ArticleFragment()
    {
        super();
    }
    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_article;
    }

    @Override
    protected void initView()
    {

    }



    @Override
    public void onAttach(Context context)
    {
        Log.i("testljm", "Fragment2 onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i("testljm","Fragment2 onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("testljm","Fragment2 onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.i("testljm","Fragment2 onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        Log.i("testljm","Fragment2 onStart");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.i("testljm","Fragment2 onResume");
        super.onResume();
    }

    @Override
    public void onPause()
    {
        Log.i("testljm","Fragment2 onPause");
        super.onPause();
    }

    @Override
    public void onStop()
    {
        Log.i("testljm","Fragment2 onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        Log.i("testljm","Fragment2 onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        Log.i("testljm","Fragment2 onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        Log.i("testljm","Fragment2 onDetach");
        super.onDetach();
    }
}
