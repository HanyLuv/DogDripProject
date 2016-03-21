package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.JoinRequest;
import com.hany.dogdripproject.vo.user.User;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class JoinFragment extends BaseFragment {

    private final String JOIN_PARAM_EMAIL = "email";
    private final String JOIN_PARAM_PASSWORD = "password";
    private final String JOIN_PARAM_NICKNAME = "nickname";
    private final String JOIN_PARAM_DEVICE = "device";

    private EditText etEmail;
    private EditText etNickname;
    private EditText etPassword;
    private EditText etDevice;
    private Button btJoin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        etEmail = (EditText) view.findViewById(R.id.et_email);
        etNickname = (EditText) view.findViewById(R.id.et_nickname);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        etDevice = (EditText) view.findViewById(R.id.et_device);
        btJoin = (Button) view.findViewById(R.id.bt_join);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        btJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String nickname = etNickname.getText().toString();
                String password = etPassword.getText().toString();
                String device = etDevice.getText().toString();
                requestJoin(email, password, nickname, device);
            }
        });
    }

    private void requestJoin(String email, String password, String nickname, String device) {

        JoinRequest joinRequest = new JoinRequest(getContext(), new BaseApiResponse.OnResponseListener<User>() {
            @Override
            public void onResponse(BaseApiResponse<User> response) {
                if (response.getErrorCode() != 0) {
                    showToast(response.getMessage());
                    return;
                }
                showToast(response.getData().getNickname() + " 님 환영합니다!");
            }

            @Override
            public void onError(VolleyError error) {
                showToast(String.valueOf(error.networkResponse.statusCode));
            }
        });

        joinRequest.putParam(JOIN_PARAM_EMAIL, email);
        joinRequest.putParam(JOIN_PARAM_PASSWORD, password);
        joinRequest.putParam(JOIN_PARAM_NICKNAME, nickname);
        joinRequest.putParam(JOIN_PARAM_DEVICE, device);

        NetworkManager.getInstance().request(joinRequest);
    }

}
