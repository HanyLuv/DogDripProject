package com.hany.dogdripproject.net.request;

import android.content.Context;
import android.graphics.Typeface;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiRequest;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.drip.Drip;
import com.hany.dogdripproject.vo.user.User;

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
