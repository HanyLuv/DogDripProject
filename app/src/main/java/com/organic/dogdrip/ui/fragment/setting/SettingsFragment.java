package com.organic.dogdrip.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.organic.dogdrip.R;
import com.organic.dogdrip.preferences.ConfigPreferenceManager;
import com.organic.dogdrip.ui.fragment.BaseFragment;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class SettingsFragment extends BaseFragment {

    private static final String TAG = "SettingsFragment";

    private CheckBox mCheckPush = null;

    private ConfigPreferenceManager mConfigPref = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigPref = new ConfigPreferenceManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        mCheckPush = (CheckBox) view.findViewById(R.id.cb_fragment_setting_push);
        
        mCheckPush.setChecked(mConfigPref.isSettingPush());
        mCheckPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mConfigPref.setSettingPush(isChecked);
            }
        });
    }

    @Override
    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    @Override
    public String getFragmentTitle() {
        return null;
    }
}
