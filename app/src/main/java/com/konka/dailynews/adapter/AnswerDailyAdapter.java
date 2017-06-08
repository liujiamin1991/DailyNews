package com.konka.dailynews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.konka.dailynews.R;
import com.konka.dailynews.model.DailyBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljm on 2017-6-6.
 */
public class AnswerDailyAdapter extends BaseAdapter
{
    private Context context;
    private List<DailyBean> ltDailys;
    private LayoutInflater inflater;

    public AnswerDailyAdapter(Context cont, List<DailyBean> ltDailys)
    {
        context = cont;
        this.ltDailys = ltDailys;
        inflater = LayoutInflater.from(cont);
    }

    @Override
    public int getCount()
    {
        return ltDailys.size();
    }

    @Override
    public Object getItem(int position)
    {
        return ltDailys.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_answerdaily_item,null);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.ivTitle = (ImageView) convertView.findViewById(R.id.iv_title);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        if(position == 0)
        {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText("今日新闻");
        }
        else
        {
            if(ltDailys.get(position-1).getDate().equals(ltDailys.get(position).getDate()))
            {
                holder.tvTime.setVisibility(View.GONE);
            }
            else
            {
                holder.tvTime.setVisibility(View.VISIBLE);
                holder.tvTime.setText(ltDailys.get(position).getDate());
            }
        }
        holder.tvTitle.setText(ltDailys.get(position).getTitle());
        Glide.with(context).load(ltDailys.get(position).getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.account_avatar).into(holder.ivTitle);
        return convertView;
    }

    public List<DailyBean> getLtDailys()
    {
        return ltDailys;
    }

    public void setLtDailys(List<DailyBean> ltDailys)
    {
        this.ltDailys = ltDailys;
        notifyDataSetChanged();
    }

    static class ViewHolder
    {
        public TextView tvTime;
        public TextView tvTitle;
        public ImageView ivTitle;
    }
}
