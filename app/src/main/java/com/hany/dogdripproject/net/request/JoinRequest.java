package com.hany.dogdripproject.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.user.User;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class JoinRequest extends BasicRequest<User> {

    private static final String API = Constants.API_SERVER_HOST + "/user/register";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_NICKNAME = "nickname";
    public JoinRequest(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
    }


    public void setUserInfo(String email, String password, String nickname) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(nickname)) {
            getParams().put(PARAM_EMAIL, email);
            getParams().put(PARAM_PASSWORD, password);
            getParams().put(PARAM_NICKNAME, nickname);
        }
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<User>() {
        }.getType();
    }
}
