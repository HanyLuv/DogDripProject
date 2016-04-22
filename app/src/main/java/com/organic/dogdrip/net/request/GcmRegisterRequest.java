package com.organic.dogdrip.net.request;

import android.content.Context;
import android.os.Build;

import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;

import java.lang.reflect.Type;

/**
 * Created by kwonojin on 16. 3. 29..
 */
public class GcmRegisterRequest extends BasicRequest<Void> {

    private static final String API = Constants.API_SERVER_HOST + "/user/registerGcm";

    private static final String PARAM_GCM = "gcm";
    private static final String PARAM_DEVICE = "devcie";

    public GcmRegisterRequest(Context context, BaseApiResponse.OnResponseListener<Void> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return null;
    }

    public void setGcmID(String gcmId){
        getParams().put(PARAM_GCM, gcmId);
        getParams().put(PARAM_DEVICE, Build.MODEL);
    }

}
