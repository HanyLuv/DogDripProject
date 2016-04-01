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
    public static final String PARAM_ID  = "id";
    public static final String PARAM_USER  = "user";

    public LikeRequest(Context context, BaseApiResponse.OnResponseListener<Like> responseListener) {
        super(context, API, responseListener);
    }

    public void setLikeInfo(int dripID,String user){
        getParams().put(PARAM_ID, String.valueOf(dripID));
        getParams().put(PARAM_USER, user);
    }
    @Override
    protected Type getClassType() {
        return new TypeToken<Like>() {}.getType();
    }
}
