package com.organic.dogdrip.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.vo.user.User;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class JoinRequest extends BasicRequest<User> {

    private static final String API = Constants.API_SERVER_HOST + "/user/register";

    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EXTERNAL = "external";

    public JoinRequest(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
    }


    public void setUserInfo(String email, String password, String nickname, boolean external) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(nickname)) {
            getParams().put(KEY_EMAIL, email);
            getParams().put(KEY_PASSWORD, password);
            getParams().put(KEY_NICKNAME, nickname);
            if(external){
                getParams().put(KEY_EXTERNAL, "true");
            }
        }
    }

    public void setUserInfo(String email, String password, String nickname) {
        setUserInfo(email, password, nickname, false);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<User>() {
        }.getType();
    }
}
