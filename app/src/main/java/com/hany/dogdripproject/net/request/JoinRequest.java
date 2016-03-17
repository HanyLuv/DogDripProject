package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.drip.Drip;
import com.hany.dogdripproject.vo.user.User;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-18.
 */
public class JoinRequest extends BasicRequest<User> {

    private static final String API = Constants.API_SERVER_HOST + "user/register";

    public JoinRequest(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
    }


    @Override
    protected Type getClassType() {
        return null;
    }
}
