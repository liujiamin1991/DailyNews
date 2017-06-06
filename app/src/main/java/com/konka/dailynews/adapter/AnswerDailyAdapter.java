package com.konka.dailynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.konka.dailynews.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ljm on 2017-6-6.
 */
public class AnswerDailyAdapter extends BaseAdapter
{
    private Context context;
    private List<String> ltDailys;
    private LayoutInflater inflater;

    public AnswerDailyAdapter(Context cont, List<String> ltDailys)
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
            holder.tvDaily = (TextView) convertView.findViewById(R.id.tv_daily);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.tvDaily.setText(ltDailys.get(position));
        return convertView;
    }

    public List<String> getLtDailys()
    {
        return ltDailys;
    }

    public void setLtDailys(List<String> ltDailys)
    {
        this.ltDailys = ltDailys;
    }

    static class ViewHolder
    {
        public TextView tvDaily;
    }
}
