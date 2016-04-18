package com.hany.dogdripproject.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.LoginRequst;
import com.hany.dogdripproject.preferences.UserInfoPreferenceManager;
import com.hany.dogdripproject.vo.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kwonojin on 16. 4. 14..
 */
public class UserInfoManager {

    public static final String ACTION_USER_INFO_STATE_CHANGED = "com.hany.dogdripproject.userinfo.changed";

    private static final String EMAIL_REGEX =  "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 16;

    public interface OnUserLoginListener{
        void onLoginCompleted(User user);
        void onLoginFailed(String errorMessage);
    }


    /**
     * Application Context
     */
    private Context mContext = null;
    private User mMe = null;
    private UserInfoPreferenceManager mPref = null;


    private static UserInfoManager sInstance = null;

    public static void init(Context context){
        sInstance = new UserInfoManager(context);
    }

    public static UserInfoManager getInstance(){
        if(sInstance == null){
            throw new IllegalStateException("UserInfoManager must be init in Application");
        }
        return sInstance;
    }

    private UserInfoManager(Context context){
        mContext = context;
        mPref = new UserInfoPreferenceManager(mContext);
    }

    public void autoLogin(final OnUserLoginListener ll){
        String email = mPref.loadLoginId();
        long lastConn = mPref.loadLastConnection();

        if(!TextUtils.isEmpty(email) && lastConn > 0){
            if(mMe == null){
                LoginRequst loginRequst = new LoginRequst(mContext, new BaseApiResponse.OnResponseListener<User>() {
                    @Override
                    public void onResponse(BaseApiResponse<User> response) {
                        if(response != null){
                            if(response.getErrorCode() == 0 && response.getData() != null){
                                mMe = response.getData();
                                saveUserInfo(mMe);
                                if(ll != null){
                                    ll.onLoginCompleted(mMe);
                                    Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                                    intent.putExtra(User.class.getName(), mMe);
                                    mContext.sendBroadcast(intent);
                                }
                            }else {
                                mMe = null;
                                ll.onLoginFailed(response.getMessage());
                                saveUserInfo(null);
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if(ll != null){
                            ll.onLoginFailed(error.toString());
                        }
                    }
                });

                loginRequst.setUserInfoForAutoLogin(email, lastConn);
                NetworkManager.getInstance().request(loginRequst);
            }else{
                if(ll != null){
                    ll.onLoginCompleted(mMe);
                    Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                    intent.putExtra(User.class.getName(), mMe);
                    mContext.sendBroadcast(intent);
                }
            }
        }else{
            if(ll != null){
                ll.onLoginFailed("User infomation is empty");
            }
        }
    }

    public void login(String email, String password, final OnUserLoginListener ll){
        if(!isValidEmail(email)){
            if(ll != null){
                ll.onLoginFailed(mContext.getString(R.string.invalid_user_email));
            }
            return;
        }

        if(!isValidPassword(password)){
            if(ll != null){
                ll.onLoginFailed(mContext.getString(R.string.invalid_user_password));
            }
            return;
        }

        if(mMe == null){
            LoginRequst loginRequst = new LoginRequst(mContext, new BaseApiResponse.OnResponseListener<User>() {
                @Override
                public void onResponse(BaseApiResponse<User> response) {
                    if(response != null){
                        if(response.getErrorCode() == 0 && response.getData() != null){
                            mMe = response.getData();
                            saveUserInfo(mMe);
                            if(ll != null){
                                ll.onLoginCompleted(mMe);
                                Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                                intent.putExtra(User.class.getName(), mMe);
                                mContext.sendBroadcast(intent);
                            }
                        }else {
                            mMe = null;
                            saveUserInfo(null);
                            ll.onLoginFailed(response.getMessage());
                        }
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    if(ll != null){
                        ll.onLoginFailed(error.toString());
                    }
                }
            });

            loginRequst.setUserInfo(email, password);
            NetworkManager.getInstance().request(loginRequst);
        }else{
            if(ll != null){
                ll.onLoginCompleted(mMe);
                Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                intent.putExtra(User.class.getName(), mMe);
                mContext.sendBroadcast(intent);
            }
        }
    }

    private void facebookLogin(){

    }
    private void saveUserInfo(User user){
        if(user != null){
            mPref.saveLoginId(user.getEmail());
            mPref.saveLastConnection(user.getLastconn());
            mPref.saveNickName(user.getNickname());
        }else{
            mPref.saveLoginId(null);
            mPref.saveLastConnection(0);
            mPref.saveNickName(null);
        }
    }

    public User getUserInfo(){
        return mMe;
    }

    public static boolean isValidPassword(String password){
        boolean err = false;
        if(password != null && password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH){
            err = true;
        }
        return err;
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        if(email != null && email.length() > 0){
            Pattern p = Pattern.compile(EMAIL_REGEX);
            Matcher m = p.matcher(email);
            if(m.matches()) {
                err = true;
            }
        }
        return err;
    }

}


