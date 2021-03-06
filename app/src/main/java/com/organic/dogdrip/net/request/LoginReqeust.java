package com.organic.dogdrip.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.preferences.ConfigPreferenceManager;
import com.organic.dogdrip.preferences.UserInfoPreferenceManager;
import com.organic.dogdrip.vo.user.User;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class LoginReqeust extends BasicRequest<User> {
    private static final String API = Constants.API_SERVER_HOST + "/user/login";

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_GCM = "gcm";
    public static final String KEY_AUTO_LOGIN = "auto_login";
    public static final String KEY_LAST_CONNECTION = "last_conn";
    public static final String KEY_EXTERNAL = "external";

    private ConfigPreferenceManager mPref = null;

    public LoginReqeust(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
        mPref = new ConfigPreferenceManager(context);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<User>() {}.getType();
    }

    public void setUserInfo(String email, String password, boolean external){
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            getParams().put(KEY_EMAIL, email);
            getParams().put(KEY_PASSWORD, password);
            getParams().put(KEY_GCM, mPref.getGcmDeviceId());
            if(external){
                getParams().put(KEY_EXTERNAL, "true");
            }

        }
    }

    public void setUserInfo(String email, String password){
        setUserInfo(email, password , false);
    }





    public void setUserInfoForAutoLogin(String email, long lastConn, boolean external){
        if(!TextUtils.isEmpty(email)){
            getParams().put(KEY_AUTO_LOGIN, "true");
            getParams().put(KEY_EMAIL, email);
            getParams().put(KEY_LAST_CONNECTION, String.valueOf(lastConn));
            getParams().put(KEY_GCM, mPref.getGcmDeviceId());
            if(external){
                getParams().put(KEY_EXTERNAL, "true");
            }
        }
    }

    public void setUserInfoForAutoLogin(String email, long lastConn){
        boolean isExternal = UserInfoManager.isValidEmail(email);
        setUserInfoForAutoLogin(email, lastConn, !isExternal);
    }

}
