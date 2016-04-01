package com.hany.dogdripproject.ui.adapter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.drip.DripPageFragment;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-16.
 */
public class DripFragmentPagerAdapter extends BaseFragmentPagerAdapter {
    private List<Drip> mDrips;

    public DripFragmentPagerAdapter(FragmentManager fm, List<Drip> drips) {
        super(fm);
        mDrips = drips;
    }

    @Override
    public Fragment getItem(int position) {
        Drip drip = mDrips.get(position);
        Bundle bundle = DripPageFragment.makeArgument(drip);
        bundle.putInt(DripPageFragment.KEY_ARGUMENT_PAGER_POSITION, position);
        return DripPageFragment.newInstance(bundle);
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
