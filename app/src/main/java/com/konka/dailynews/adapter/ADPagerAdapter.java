package com.konka.dailynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.konka.dailynews.R;
import com.konka.dailynews.model.TopDailyBean;
import com.konka.dailynews.model.TopDailys;
import com.konka.dailynews.ui.activity.NewsDetails;

import java.util.List;

/**
 * Created by ljm on 2017-6-2.
 */
public class ADPagerAdapter extends PagerAdapter
{
    private static final int PAGER_NUMBER = 2000;

    private Context context;
    List<TopDailys> ltTop;
    LayoutInflater inflater;

    public ADPagerAdapter(Context con, List<TopDailys> ltTop)
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
        TopDailys dailyBean = ltTop.get(position);
        Glide.with(context).load(dailyBean.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.account_avatar).into(ivPager);
        tvPager.setText(dailyBean.getTitle());

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, NewsDetails.class);
                context.startActivity(intent);
            }
        });

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
