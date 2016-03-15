package com.hany.dogdripproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.entry.Drip;
import com.hany.dogdripproject.fragment.adapater.HomeFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-15.
 */
//메인화면 프래그먼트
public class HomeFragment extends Fragment {


    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Drip> drips = getArguments().getParcelableArrayList("drips");
        init(drips);
    }

    private void init(ArrayList<Drip> drips) {
        if (drips != null) {
            mViewPager.setAdapter(new HomeFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), drips));
        }
    }
}