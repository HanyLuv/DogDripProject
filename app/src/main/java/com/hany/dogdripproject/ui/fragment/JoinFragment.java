package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hany.dogdripproject.net.request.JoinRequest;
import com.hany.dogdripproject.vo.user.User;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class JoinFragment extends BaseFragment {

    private EditText etEmail;
    private EditText etNickname;
    private EditText etPassword;
    private Button btnJoin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        etEmail = (EditText) view.findViewById(R.id.et_email);
        etNickname = (EditText) view.findViewById(R.id.et_nickname);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnJoin = (Button) view.findViewById(R.id.bt_join);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String nickname = etNickname.getText().toString();
                String password = etPassword.getText().toString();
                requestJoin(email, password, nickname);
            }
        });
    }

    private void requestJoin(String email, String password, String nickname) {

        JoinRequest joinRequest = new JoinRequest(getActivity(), new BaseApiResponse.OnResponseListener<User>() {
            @Override
            public void onResponse(BaseApiResponse<User> response) {
                if(!isRequestSuccessfully(response)){
                    return;
                }
                showToast(response.getData().getNickname() + getResources().getString(R.string.join_welcome));
            }

            @Override
            public void onError(VolleyError error) {
                    showToast(error.getMessage());
            }
        });

        joinRequest.putParam(Constants.PARAM_EMAIL, email);
        joinRequest.putParam(Constants.PARAM_PASSWORD, password);
        joinRequest.putParam(Constants.PARAM_NICKNAME, nickname);

        request(joinRequest);
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
