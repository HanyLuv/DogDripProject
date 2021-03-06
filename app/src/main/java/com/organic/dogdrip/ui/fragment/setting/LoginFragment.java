package com.organic.dogdrip.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.FacebookException;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.organic.dogdrip.R;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.ui.FaceBookLoginActivity;
import com.organic.dogdrip.ui.dialog.FootProgressDialog;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.user.User;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class LoginFragment extends BaseFragment {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnFaceBookLogin;
    private Button btnKakaoLogin;
    private Button btnJoin;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etEmail = (EditText) view.findViewById(R.id.et_id);
        etPassword = (EditText) view.findViewById(R.id.et_password);
        btnLogin = (Button) view.findViewById(R.id.bt_login);
        btnFaceBookLogin = (Button) view.findViewById(R.id.bt_facebook_login);
        btnKakaoLogin = (Button) view.findViewById(R.id.bt_kakao_login);
        btnJoin = (Button) view.findViewById(R.id.bt_join);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                    getLoginInfo(false);
                    return true;
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginInfo(false);
            }
        });

        btnFaceBookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doFaceBookLogin();
            }
        });

        btnKakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doKaKaoLogin();
            }
        });


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doJoin();
            }
        });
    }

    private void doJoin() {
        SettingBookFragment settingBookFragment = null;
        JoinFragment joinFragment = new JoinFragment();
        List<Fragment> fragments = getFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof SettingBookFragment) {
                settingBookFragment = (SettingBookFragment) fragment;
                break;
            }
        }
        if (settingBookFragment != null) {
            getFragmentManager().beginTransaction()
                    .add(settingBookFragment.getChildFragmentAnchorId(), joinFragment)
                    .addToBackStack(joinFragment.getBackstackName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }


    private void doKaKaoLogin() {
        UserInfoManager.getInstance().kakaoLogin(getActivity(),onJoinRequestListener);
    }
    private void getLoginInfo(boolean external) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        doLogin(email,password, external);
    }

    private void getLoginInfo(User user, boolean external) {
        String email = user.getUserid();
        String password = user.getUserid();
        doLogin(email, password, external);
    }

    private void doLogin(String email,String password, boolean external){
        if(getActivity() != null){
            final FootProgressDialog dialog = new FootProgressDialog(getActivity());
            dialog.show();
            UserInfoManager.getInstance().login(email, password, new UserInfoManager.OnUserLoginListener() {
                @Override
                public void onLoginCompleted(User user) {
                    showToast(user.getNickname() + getResources().getString(R.string.login_welcome));
                    dialog.dismiss();
                }

                @Override
                public void onLoginFailed(String errorMessage) {
                    showToast(errorMessage);
                    dialog.dismiss();
                }
            }, external);
        }
    }

    private void doFaceBookLogin() {
        if (getActivity() instanceof FaceBookLoginActivity) {
            FaceBookLoginActivity faceBookLoginActivity = (FaceBookLoginActivity) getActivity();
            UserInfoManager.getInstance().facebookLoginInit(
                    faceBookLoginActivity.getCallbackManager(),
                    onFacebookCallbackListener,
                    onGraphRequestListener,
                    onJoinRequestListener);

            UserInfoManager.getInstance().facebookLogin(getActivity());
        }
    }

    /* 회원가입까지는 유저매니저 내부에서 하고 로그인을 이곳에서 하기위해 리스너를 달았어요.*/
    private UserInfoManager.OnJoinRequestListener onJoinRequestListener = new UserInfoManager.OnJoinRequestListener() {
        @Override
        public void onResponse(BaseApiResponse<User> response) {
            if (response.getData() != null) {
                getLoginInfo(response.getData(), true);
            }
        }

        @Override
        public void onError(VolleyError error) {
            showToast(error.getMessage());
            Log.e(getClass().getSimpleName().toString(), error.getMessage());
        }
    };

    private UserInfoManager.OnFacebookGraphRequestListener onGraphRequestListener = new UserInfoManager.OnFacebookGraphRequestListener() {
        @Override
        public void onCompleted(JSONObject userInfo, GraphResponse response) {
        }
    };

    private  UserInfoManager.OnFacebookCallbackListener onFacebookCallbackListener =  new UserInfoManager.OnFacebookCallbackListener() {
        @Override
        public void onSuccess(LoginResult loginResult) {
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            showToast(error.getMessage());
            Log.e(getClass().getSimpleName().toString(), error.getMessage());
        }
    };

}
