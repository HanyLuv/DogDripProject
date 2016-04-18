package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.manager.UserInfoManager;
import com.hany.dogdripproject.ui.FaceBookActivity;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.vo.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class LoginFragment extends BaseFragment {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnFaceBookLogin;
    private CallbackManager callbackManager;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = (EditText) view.findViewById(R.id.et_id);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.bt_login);
        btnFaceBookLogin = (Button) view.findViewById(R.id.bt_facebook_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callbackManager = ((FaceBookActivity) getActivity()).getCallbackManager();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
        init();
    }

    private void init() {
        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT){
                    etPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    doLogin();
                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        btnFaceBookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doFaceBookLogin();
            }
        });
    }

    private void doLogin(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        UserInfoManager.getInstance().login(email, password, new UserInfoManager.OnUserLoginListener() {
            @Override
            public void onLoginCompleted(User user) {
                showToast(user.getNickname() + getResources().getString(R.string.login_welcome));
            }

            @Override
            public void onLoginFailed(String errorMessage) {
                showToast(errorMessage);
            }
        });
    }

    private void doFaceBookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "email"));
    }

    private void requestFaceBookUserInfo(AccessToken accessToken){
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject userInfo, GraphResponse response) {
                try {
                    String email = userInfo.getString("email");
                    String id = userInfo.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private  FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            AccessToken.setCurrentAccessToken(accessToken);
            requestFaceBookUserInfo(accessToken);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            showToast(error.getMessage());
        }
    };
}
