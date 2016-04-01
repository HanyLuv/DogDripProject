package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.drip.LikeInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-28.
 */
public class LikeCheckRequest extends BasicRequest<ArrayList<LikeInfo>> {

    private static final String API = Constants.API_SERVER_HOST + "/drip/check";
    private static final String KEY_ID = "id";

    public LikeCheckRequest(Context context, BaseApiResponse.OnResponseListener<ArrayList<LikeInfo>> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<ArrayList<LikeInfo>>(){}.getType();
    }

    public void setDripId(int id){
        putParam(KEY_ID, String.valueOf(id));
    }
}
