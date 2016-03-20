package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.user.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class JoinRequest extends BasicRequest<User> {


    /* Constants 여기다가 주소를 모아두신것같은데
        어떻게 사용하실지 몰라서 일단 아래처럼 붙여서 사용했어요! */

    private static final String API = Constants.API_SERVER_HOST + "/user/register";

    public JoinRequest(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
    }


    @Override
    protected Type getClassType() {
        return new TypeToken<User>() {
        }.getType();
    }
}
