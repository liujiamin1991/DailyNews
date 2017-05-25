package com.konka.dailynews.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.konka.dailynews.R;
import com.konka.dailynews.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by ljm on 2017-5-24.
 */
public class HomeActivity extends BaseActivity
{

    @Bind(R.id.toobar)
    Toolbar toolbar;

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_home;
    }

    @Override
    public void initView(Bundle savedInstanceState)
    {

    }

    @Override
    public void initToolBar()
    {
        toolbar.setTitle("头条");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }
}
