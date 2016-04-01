package com.hany.dogdripproject.ui.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class FrameFragmentPagerAdapter extends BaseFragmentPagerAdapter {
    private List<Class<? extends BaseFragment>> mFragmentClasses;

    public FrameFragmentPagerAdapter(FragmentManager fm, List<Class<? extends BaseFragment>> classList) {
        super(fm);
        mFragmentClasses = classList;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;
        Class fClass = mFragmentClasses.get(position);
        try {
            fragment = (BaseFragment) fClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentClasses.size();
    }

}
