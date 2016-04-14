package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.preferences.ConfigPreferenceManager;
import com.hany.dogdripproject.preferences.UserLoginPreferenceManager;
import com.hany.dogdripproject.ui.fragment.BaseFragment;


/**
 * Created by HanyLuv on 2016-03-31.
 */
public class SettingBookFragment extends BaseFragment {

    private ConfigPreferenceManager mConfigPref = null;
    private UserLoginPreferenceManager mUserPref = null;

    private Button mSettingButton = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_book, container, false);
        mSettingButton = (Button) view.findViewById(R.id.btn_setting_book_setting);
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChildFragment(SettingsFragment.class, null);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigPref = new ConfigPreferenceManager(getActivity());
        mUserPref = new UserLoginPreferenceManager(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(TextUtils.isEmpty(mUserPref.loadLoginId())){
            addChildFragment(LoginFragment.class, null);
        }else{
            addChildFragment(MypageFragment.class, null);
        }
    }

    @Override
    protected int getChildFragmentAnchorId() {
        return R.id.layout_setting_book_root;
    }

}
