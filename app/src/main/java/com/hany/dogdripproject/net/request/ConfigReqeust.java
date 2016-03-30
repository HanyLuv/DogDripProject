package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.config.AppConfig;

import java.lang.reflect.Type;

/**
 * Created by kwonojin on 16. 3. 19..
 */
public class ConfigReqeust extends BasicRequest<AppConfig> {

    private static final String API = Constants.API_SERVER_HOST + "/config/app";

    public ConfigReqeust(Context context, BaseApiResponse.OnResponseListener<AppConfig> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<AppConfig>(){}.getType();
    }

    @Override
    public long getCacheTimeMilliseconds() {
        return 1000 * 60 * 5;
    }

    @Override
    public boolean isCache() {
        return true;
    }
}
