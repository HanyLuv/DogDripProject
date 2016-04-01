package com.hany.dogdripproject.ui.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class FrameFragmentPagerAdapter extends BaseFragmentPagerAdapter {

    private Map<Integer, BaseHorizontalScrollFragment> mFragmentMap = null;
    private List<Class<? extends BaseHorizontalScrollFragment>> mFragmentClasses;

    private ViewPager.OnPageChangeListener mOnPageChangeListener = null;

    public FrameFragmentPagerAdapter(FragmentManager fm, List<Class<? extends BaseHorizontalScrollFragment>> classList) {
        super(fm);
        mFragmentClasses = classList;
        mFragmentMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        BaseHorizontalScrollFragment fragment = mFragmentMap.get(position);
        if(fragment == null){
            Class fClass = mFragmentClasses.get(position);
            try {
                fragment = (BaseHorizontalScrollFragment) fClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fragment != null){
                mFragmentMap.put(position, fragment);
            }
        }
        if(mOnPageChangeListener != null){
            fragment.setPageChangeListener(mOnPageChangeListener);
        }
        return fragment;
    }

    public void setOnChildPageChangeListener(ViewPager.OnPageChangeListener ll){
        mOnPageChangeListener = ll;
    }

    @Override
    public int getCount() {
        return mFragmentClasses.size();
    }

    @Override
    protected boolean ignoreDestroyObject(int position, Object object) {
        return true;
    }
}
