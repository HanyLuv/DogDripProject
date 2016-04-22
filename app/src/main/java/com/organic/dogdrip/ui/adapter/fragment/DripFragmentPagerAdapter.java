package com.organic.dogdrip.ui.adapter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.organic.dogdrip.ui.adapter.BaseFragmentPagerAdapter;
import com.organic.dogdrip.ui.fragment.drip.DripPageFragment;
import com.organic.dogdrip.vo.drip.Drip;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HanyLuv on 2016-03-16.
 */
public class DripFragmentPagerAdapter extends BaseFragmentPagerAdapter {

    Map<Drip, DripPageFragment> mFragmentMap = null;
    private List<Drip> mDrips;

    public DripFragmentPagerAdapter(FragmentManager fm, List<Drip> drips) {
        super(fm);
        mDrips = drips;
        mFragmentMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Drip drip = mDrips.get(position);
        DripPageFragment f = mFragmentMap.get(drip);
        if(f == null){
            Bundle bundle = DripPageFragment.makeArgument(drip);
            bundle.putInt(DripPageFragment.KEY_ARGUMENT_PAGER_POSITION, position);
            f = (DripPageFragment) DripPageFragment.newInstance(bundle);
            mFragmentMap.put(drip, f);
        }
        return f;
    }

    @Override
    protected String makeFragmentName(int viewId, long id) {
        return super.makeFragmentName(viewId, id) + getClass().getSimpleName();
    }

    @Override
    public int getCount() {
        return mDrips.size();
    }

    @Override
    protected boolean ignoreDestroyObject(int position, Object object) {
        return true;
    }
}
