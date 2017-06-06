package com.konka.dailynews.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konka.dailynews.R;
import com.konka.dailynews.base.BaseActivity;
import com.konka.dailynews.base.BaseFragment;
import com.konka.dailynews.ui.fragment.AnswerFragment;
import com.konka.dailynews.ui.fragment.ArticleFragment;
import com.konka.dailynews.ui.fragment.ColumnFragment;
import com.konka.dailynews.ui.fragment.FavoriteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ljm on 2017-5-24.
 */
public class HomeActivity extends BaseActivity implements OnClickListener
{

    @Bind(R.id.toobar)
    Toolbar toolbar;

    @Bind(R.id.fl_content)
    FrameLayout flContent;

    @Bind(R.id.ly_tab)
    LinearLayout lyTab;

    @Bind(R.id.ly_answer)
    LinearLayout lyAnswer;

    @Bind(R.id.iv_answer)
    ImageView ivAnswer;

    @Bind(R.id.tv_answer)
    TextView tvAnswer;

    @Bind(R.id.ly_article)
    LinearLayout lyArticle;

    @Bind(R.id.iv_article)
    ImageView ivArticle;

    @Bind(R.id.tv_article)
    TextView tvArticle;

    @Bind(R.id.ly_column)
    LinearLayout lyColumn;

    @Bind(R.id.iv_column)
    ImageView ivColumn;

    @Bind(R.id.tv_column)
    TextView tvColumn;

    @Bind(R.id.ly_favorite)
    LinearLayout lyFavorite;

    @Bind(R.id.iv_favorite)
    ImageView ivFavorite;

    @Bind(R.id.tv_favorite)
    TextView tvFavorite;

    private List<BaseFragment> ltFmts = new ArrayList<>();

    private List<LinearLayout> ltlys = new ArrayList<>();
    private List<ImageView> ltIvs = new ArrayList<>();
    private List<TextView> ltTvs = new ArrayList<>();
    int currIndex = 0;

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_home;
    }

    @Override
    public void initView(Bundle savedInstanceState)
    {
        initContent();
        initTab();
    }

    @Override
    public void initToolBar()
    {
        toolbar.setTitle("头条");
        setSupportActionBar(toolbar);
    }

    private void initContent()
    {
        ltFmts.add(new AnswerFragment());
        ltFmts.add(new ArticleFragment());
        ltFmts.add(new ColumnFragment());
        ltFmts.add(new FavoriteFragment());

        setFragment(ltFmts.get(0));
    }

    private void initTab()
    {
        ltlys.add(lyAnswer);
        ltlys.add(lyArticle);
        ltlys.add(lyColumn);
        ltlys.add(lyFavorite);

        ltIvs.add(ivAnswer);
        ltIvs.add(ivArticle);
        ltIvs.add(ivColumn);
        ltIvs.add(ivFavorite);

        ltTvs.add(tvAnswer);
        ltTvs.add(tvArticle);
        ltTvs.add(tvColumn);
        ltTvs.add(tvFavorite);

        for(int i=0; i<ltlys.size(); i++)
        {
            ltlys.get(i).setOnClickListener(this);

            if(currIndex == i) {tabSelector(ltIvs.get(i),ltTvs.get(i));}
            else {tabNomal(ltIvs.get(i),ltTvs.get(i));}
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ly_answer:
                if(0 != currIndex)
                {
                    setFragment(ltFmts.get(0));
                    validateLTab(0);
                    currIndex = 0;
                }
                break;
            case R.id.ly_article:
                if(1 != currIndex)
                {
                    setFragment(ltFmts.get(1));
                    validateLTab(1);
                    currIndex = 1;
                }
                break;
            case R.id.ly_column:
                if(2 != currIndex)
                {
                    setFragment(ltFmts.get(2));
                    validateLTab(2);
                    currIndex = 2;
                }
                break;
            case R.id.ly_favorite:
                if(3 != currIndex)
                {
                    setFragment(ltFmts.get(3));
                    validateLTab(3);
                    currIndex = 3;
                }
                break;
        }
    }

    private void setFragment(BaseFragment fragment)
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction trs = fm.beginTransaction();
        if(!fragment.isAdded())
        {
            trs.add(R.id.fl_content,fragment);
        }
        trs.hide(ltFmts.get(currIndex));
        trs.show(fragment);
        trs.commit();
    }

    private void validateLTab(int index)
    {
        tabNomal(ltIvs.get(currIndex),ltTvs.get(currIndex));
        tabSelector(ltIvs.get(index),ltTvs.get(index));
    }

    private void tabNomal(ImageView iv, TextView tv)
    {
        iv.setColorFilter(getResources().getColor(R.color.tabColorNomal));
        tv.setTextColor(getResources().getColor(R.color.tabColorNomal));
    }

    private void tabSelector(ImageView iv, TextView tv)
    {
        iv.setColorFilter(getResources().getColor(R.color.tabColorSelector));
        tv.setTextColor(getResources().getColor(R.color.tabColorSelector));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("testljm", "activity onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        Log.i("testljm", "activity onStart");
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        Log.i("testljm", "activity onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.i("testljm", "activity onPause");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.i("testljm", "activity onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.i("testljm", "activity onDestroy");
        super.onDestroy();
    }
}
