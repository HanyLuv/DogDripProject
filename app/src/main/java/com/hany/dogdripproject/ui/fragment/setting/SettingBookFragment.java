package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.preferences.ConfigPreferenceManager;
import com.hany.dogdripproject.preferences.UserLoginPreferenceManager;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.adapter.fragment.SettingFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class SettingBookFragment extends BaseHorizontalScrollFragment {

    private ConfigPreferenceManager mConfigPref = null;
    private UserLoginPreferenceManager mUserPref = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigPref = new ConfigPreferenceManager(getActivity());
        mUserPref = new UserLoginPreferenceManager(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeClassList();
    }

    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List pageDatas) {
        SettingFragmentPagerAdapter adapter = new SettingFragmentPagerAdapter(getFragmentManager(), pageDatas);
        return adapter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void makeClassList(){
        List<Class<? extends BaseFragment>> classes = new ArrayList<>();
        if(TextUtils.isEmpty(mUserPref.loadLoginId())){
            classes.add(LoginFragment.class);
            classes.add(JoinFragment.class);
        }
        classes.add(SettingsFragment.class);
        setPageData(classes);
    }
}
