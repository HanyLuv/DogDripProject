package com.organic.dogdrip.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.vo.drip.Drip;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kwonojin on 16. 3. 17..
 */
public class DripListRequest extends BasicRequest<List<Drip>> {

    private static final String API = Constants.API_SERVER_HOST + "/drip/get";

    public DripListRequest(Context context, BaseApiResponse.OnResponseListener<List<Drip>> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<List<Drip>>(){}.getType();
    }
}
