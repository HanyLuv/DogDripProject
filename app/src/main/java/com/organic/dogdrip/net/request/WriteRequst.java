package com.organic.dogdrip.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.vo.drip.Drip;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class WriteRequst extends BasicRequest<Drip>{

    private static final String API = Constants.API_SERVER_HOST + "/drip/put";

    public WriteRequst(Context context, BaseApiResponse.OnResponseListener<Drip> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<Drip>(){}.getType();
    }
}
