package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.request.JoinRequest;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.vo.user.User;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class SettingsFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
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
