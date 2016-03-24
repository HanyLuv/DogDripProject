package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.drip.Like;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-25.
 */
public class LikeRequest extends BasicRequest<Like> {

    private static final String API = Constants.API_SERVER_HOST + "/drip/like";

    public LikeRequest(Context context, BaseApiResponse.OnResponseListener<Like> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<Like>() {}.getType();
    }
}
