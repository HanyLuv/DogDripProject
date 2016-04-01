package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.adapter.fragment.DripFragmentPagerAdapter;
import com.hany.dogdripproject.vo.drip.Drip;
import com.hany.dogdripproject.vo.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanyLuv on 2016-03-15.
 */
//메인화면 프래그먼트
public class HomeFragment extends BaseHorizontalScrollFragment<User> {
    public static String TAG = "HomeFragment";


    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List<User> pageDatas) {
        return null;
    }

    @Override
    public String getFragmentTag() {
        return "homefragment";
    }

    @Override
    public String getFragmentTitle() {
        return "홈";
    }
}
