package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class SettingFragment extends BaseHorizontalScrollFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List pageDatas) {
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
