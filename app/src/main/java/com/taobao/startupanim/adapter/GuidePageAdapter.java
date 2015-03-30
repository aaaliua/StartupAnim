package com.taobao.startupanim.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rowandjj on 2015/3/29.
 */
public class GuidePageAdapter extends PagerAdapter
{
    private List<View> mPageViews;
    public GuidePageAdapter(List<View> views)
    {
        this.mPageViews = views;
    }

    @Override
    public int getCount()
    {
        return mPageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o)
    {
        return view==o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(mPageViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(mPageViews.get(position));
        return mPageViews.get(position);
    }
}
