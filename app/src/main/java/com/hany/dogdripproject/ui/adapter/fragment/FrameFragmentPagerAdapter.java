package com.hany.dogdripproject.ui.adapter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class FrameFragmentPagerAdapter extends BaseFragmentPagerAdapter {

    private Map<Integer, BaseFragment> mFragmentMap = null;
    private List<Class<? extends BaseFragment>> mFragmentClasses;

    public FrameFragmentPagerAdapter(FragmentManager fm, List<Class<? extends BaseFragment>> classList) {
        super(fm);
        mFragmentClasses = classList;
        mFragmentMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = mFragmentMap.get(position);
        if(fragment == null){
            Class fClass = mFragmentClasses.get(position);
            try {
                fragment = (BaseFragment) fClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fragment != null){
                mFragmentMap.put(position, fragment);
            }
        }
        return fragment;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getCount() {
        return mFragmentClasses.size();
    }
}
