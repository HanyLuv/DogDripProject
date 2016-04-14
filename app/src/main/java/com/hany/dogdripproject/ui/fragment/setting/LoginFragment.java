package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.manager.UserInfoManager;
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
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                UserInfoManager.getInstance().login(email, password, new UserInfoManager.OnUserLoginListener() {
                    @Override
                    public void onLoginCompleted(User user) {
                        showToast(user.getNickname() + getResources().getString(R.string.login_welcome));
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        for (Fragment f : fragmentManager.getFragments()) {
                            if (f instanceof SettingBookFragment) {
                                MypageFragment mypageFragment = new MypageFragment();
                                FragmentManager childFragmentManager = f.getChildFragmentManager();
                                childFragmentManager.beginTransaction().replace(((SettingBookFragment) f).getChildFragmentAnchorId(), mypageFragment).commit();
                            }
                        }

                    }

                    @Override
                    public void onLoginFailed(String errorMessage) {
                        showToast(errorMessage);
                    }
                });
            }
        });
    }
}
