package com.konka.dailynews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * Created by ljm on 2017-6-8.
 */
public class CollapseLayout extends LinearLayout
{
    private static final String TAG = "CollapseLayout";

    int childCount = 0;
    int titleViewHeight = 0;
    View titleView;
    WebView webView;

    public CollapseLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        childCount = getChildCount();
        titleView = getChildAt(0);
        webView = (WebView) getChildAt(1);
        Log.i(TAG, "childCount:" + childCount);

        LinearLayout.LayoutParams lp= (LayoutParams) titleView.getLayoutParams();
        titleViewHeight = lp.height;
        Log.i(TAG, "titleView Height:" + lp.height);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.i(TAG,"onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.i(TAG,"触发onTouchEvent");
        return super.onTouchEvent(event);
    }
}
