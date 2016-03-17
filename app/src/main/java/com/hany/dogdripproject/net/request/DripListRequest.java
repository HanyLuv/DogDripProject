package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.drip.Drip;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by kwonojin on 16. 3. 17..
 */
public class DripListRequest extends BasicRequest<ArrayList<Drip>> {

    private static final String API = Constants.API_SERVER_HOST + "/drip/get";

    public DripListRequest(Context context, BaseApiResponse.OnResponseListener<ArrayList<Drip>> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<ArrayList<Drip>>(){}.getType();
    }
}
