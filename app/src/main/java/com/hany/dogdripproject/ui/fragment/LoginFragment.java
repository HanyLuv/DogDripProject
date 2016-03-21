package com.hany.dogdripproject.ui.fragment;

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
import com.hany.dogdripproject.vo.user.User;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class LoginFragment extends BaseFragment {

    //멤버변수 앞에 m붙여서 주로 선언하라 하셨잖아요!
    //근데 뭔가 View에게 붙이기엔 애매해서 저번부터 m을 제거햇는데
    //이경우 대체로 어떻게 선언하는게 가독성이 좋을까요? 음..

    private EditText etEmail;
    private EditText etPassword;
    private EditText etDevice;
    private Button btnLogin;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = (EditText) view.findViewById(R.id.et_id);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etDevice = (EditText) view.findViewById(R.id.et_device);
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
                        if (response.getErrorCode() != 0) {
                            showToast(response.getMessage());
                            return;
                        }
                        showToast(response.getData().getNickname() + getResources().getString(R.string.login_welcome));
                    }

                    @Override
                    public void onError(VolleyError error) {
                        showToast(error.getMessage());
                    }
                });

                /** 슨배님 >-< ! 디바이스값 없어도 로그인 되어야 하는것아닌가엽!! */

                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();
                String strDevice = etDevice.getText().toString();

                loginRequst.putParam(Constants.PARAM_EMAIL, strEmail);
                loginRequst.putParam(Constants.PARAM_PASSWORD, strPassword);
                loginRequst.putParam(Constants.PARAM_DEVICE, strDevice);

                request(loginRequst);

            }

        });
    }
}
