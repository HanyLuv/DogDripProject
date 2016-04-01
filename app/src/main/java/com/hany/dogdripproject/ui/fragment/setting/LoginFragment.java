package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.LoginRequst;
import com.hany.dogdripproject.preferences.ConfigPreferenceManager;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.vo.user.User;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class LoginFragment extends BaseFragment {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = (EditText) view.findViewById(R.id.et_id);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.bt_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginRequst loginRequst = new LoginRequst(getActivity(), new BaseApiResponse.OnResponseListener<User>() {
                    @Override
                    public void onResponse(BaseApiResponse<User> response) {
                        if (!isRequestSuccessfully(response)) {
                            return;
                        }
                        showToast(response.getData().getNickname() + getResources().getString(R.string.login_welcome));
                    }

                    @Override
                    public void onError(VolleyError error) {
                        showToast(error.getMessage());
                    }
                });

                ConfigPreferenceManager preferenceManager  = new ConfigPreferenceManager(getActivity());
                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();
                loginRequst.setUserInfo( strEmail.trim(),strPassword);

                request(loginRequst);

            }

        });
    }
}
