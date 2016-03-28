package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.user.User;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class LoginRequst extends BasicRequest<User> {
    private static final String API = Constants.API_SERVER_HOST + "/user/login";

    public LoginRequst(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<User>() {}.getType();
    }
}
