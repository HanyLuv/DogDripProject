package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.hany.dogdripproject.preferences.UserLoginPreferenceManager;
import com.hany.dogdripproject.ui.BaseActivity;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;
import com.hany.dogdripproject.utils.Log;
import com.hany.dogdripproject.vo.drip.Drip;
import com.hany.dogdripproject.vo.user.User;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class LoginFragment extends BaseHorizontalScrollFragment {

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
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List pageDatas) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
//        testLogin();
    }

    private void testLogin() {
        UserLoginPreferenceManager userLoginPreferenceManager = new UserLoginPreferenceManager(getActivity());
        String loginId = userLoginPreferenceManager.loadLoginId();
//        String password = userLoginPreferenceManager.loadPassword();
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
        Log.d("hany tag", "Id " + loginId);
//        loginRequst.setUserInfo(loginId.trim(), password);

        request(loginRequst);
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

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("user_login_info", response.getData());
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        MypageFragment mypageFragment = new MypageFragment();
                        mypageFragment.setArguments(bundle);

                        fragmentTransaction.replace(R.id.layout_fragment_first_page, mypageFragment);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

//                        UserLoginPreferenceManager userLoginPreferenceManager = new UserLoginPreferenceManager(getActivity());
//                        userLoginPreferenceManager.saveLoginId(response.getData().getEmail());
//                        userLoginPreferenceManager.savePassword(response.getData().getPassword());
//                        userLoginPreferenceManager.saveNickName(response.getData().getNickname());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        showToast(error.getMessage());
                    }
                });

                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();
                loginRequst.setUserInfo(strEmail.trim(), strPassword);

                request(loginRequst);

            }

        });
    }
}
