package com.hany.dogdripproject.ui.fragment.adapater;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hany.dogdripproject.ui.fragment.DripPageFragment;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-16.
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Drip> mDrips;

    public HomeFragmentPagerAdapter(FragmentManager fm, List<Drip> drips) {
        super(fm);
        mDrips = drips;
    }

    @Override
    public Fragment getItem(int position) {
        Drip drip = mDrips.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("author", drip.getAuthor());
        bundle.putString("drip", drip.getDrip());
        return DripPageFragment.newInstance(bundle);
    }

    @Override
    public int getCount() {
        return mDrips.size();
    }
}
