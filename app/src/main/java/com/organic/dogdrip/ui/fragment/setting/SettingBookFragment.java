package com.organic.dogdrip.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.organic.dogdrip.R;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.preferences.ConfigPreferenceManager;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.vo.user.User;


/**
 * Created by HanyLuv on 2016-03-31.
 */
public class SettingBookFragment extends BaseFragment {

    private ConfigPreferenceManager mConfigPref = null;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(UserInfoManager.getInstance().getUserInfo() == null){
            addChildFragment(LoginFragment.class, null, false);
        }else{
            addChildFragment(MypageFragment.class, null, false);
        }
    }

    @Override
    protected int getChildFragmentAnchorId() {
        return R.id.layout_setting_book_root;
    }

    @Override
    public void onUserInfoChanged(User user) {
        super.onUserInfoChanged(user);
        while(getChildFragmentManager().popBackStackImmediate());
        if(user != null){
            addChildFragment(MypageFragment.class, null, false);
        }else{
            addChildFragment(LoginFragment.class, null, false);
        }
    }
}
