package com.konka.dailynews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.konka.dailynews.R;
import com.konka.dailynews.model.TopDailyBean;

import java.util.List;

/**
 * Created by ljm on 2017-6-2.
 */
public class ADPagerAdapter extends PagerAdapter
{
    private static final int PAGER_NUMBER = 2000;

    private Context context;
    List<TopDailyBean> ltTop;
    LayoutInflater inflater;

    public ADPagerAdapter(Context con, List<TopDailyBean> ltTop)
    {
        context = con;
        this.ltTop = ltTop;
        inflater = LayoutInflater.from(con);
    }

    @Override
    public int getCount()
    {
        return PAGER_NUMBER;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = inflater.inflate(R.layout.layout_pager_tab,container,false);
        ImageView ivPager = (ImageView) view.findViewById(R.id.iv_pager);
        TextView tvPager = (TextView) view.findViewById(R.id.tv_pager);
        position %= ltTop.size();
        TopDailyBean dailyBean = ltTop.get(position);
        ivPager.setImageResource(R.mipmap.ic_launcher);
        tvPager.setText("hello");
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View)object);
    }

    public int getSize()
    {
        if(ltTop == null) return -1;

        return ltTop.size();
    }
}
