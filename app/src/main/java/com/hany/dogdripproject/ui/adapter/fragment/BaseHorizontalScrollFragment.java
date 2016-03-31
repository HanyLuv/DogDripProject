package com.hany.dogdripproject.ui.adapter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by kwonojin on 16. 3. 31..
 */
abstract public class BaseHorizontalScrollFragment extends BaseFragment {

    private ViewPager mViewPager = null;
    private BaseFragmentPagerAdapter mPagerAdapter = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract List<? extends BaseFragment> getFragmentsList();
}
