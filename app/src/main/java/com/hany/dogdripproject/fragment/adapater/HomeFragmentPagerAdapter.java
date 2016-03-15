package com.hany.dogdripproject.fragment.adapater;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hany.dogdripproject.entry.Drip;
import com.hany.dogdripproject.fragment.DripPageFragment;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-16.
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<Drip> mDrips;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context, List<Drip> drips) {
        super(fm);
        mContext = context;
        mDrips = drips;

    }

    @Override
    public Fragment getItem(int position) {
        Drip drip = mDrips.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("author", drip.getAuthor());
        bundle.putString("drip", drip.getDrip());
        return DripPageFragment.newInstance(mContext, bundle);
    }

    @Override
    public int getCount() {
        return mDrips.size();
    }
}
