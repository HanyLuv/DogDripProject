package com.hany.dogdripproject.ui.adapter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.drip.DripPageFragment;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HanyLuv on 2016-03-16.
 */
public class SettingFragmentPagerAdapter extends BaseFragmentPagerAdapter {

    private Map<Integer, BaseFragment> mFragmentMap = null;
    private List<Class< ? extends BaseFragment>> mClassList = null;

    public SettingFragmentPagerAdapter(FragmentManager fm, List<Class< ? extends BaseFragment>> classes) {
        super(fm);
        mFragmentMap = new HashMap<>();
        mClassList = classes;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment f = mFragmentMap.get(position);
        if(f == null){
            Class< ? extends BaseFragment> cls = mClassList.get(position);
            try {
                f = cls.newInstance();
                mFragmentMap.put(position, f);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return f;
    }

    @Override
    public int getCount() {
        return mClassList.size();
    }

    @Override
    protected boolean ignoreDestroyObject(int position, Object object) {
        return true;
    }
}
