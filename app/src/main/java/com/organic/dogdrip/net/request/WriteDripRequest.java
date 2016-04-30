package com.organic.dogdrip.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.vo.drip.Drip;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class WriteDripRequest extends BasicRequest<Drip>{

    private static final String API = Constants.API_SERVER_HOST + "/drip/put";

    private static final String AUTHOR = "author";
    private static final String DRIP = "drip";
    private static final String IMAGE_URL = "imageUrl";

    public WriteDripRequest(Context context, BaseApiResponse.OnResponseListener<Drip> responseListener) {
        super(context, API, responseListener);
    }

    public void setDataInfo(String author, String drip){
        setDataInfo(author, drip, null);
    }

    public void setImageUrl(String imageUrl){
        if(!TextUtils.isEmpty(imageUrl)){
            getParams().put(IMAGE_URL, imageUrl);
        }
    }

    public void setDataInfo(String author, String drip, String imageUrl){
        if(!TextUtils.isEmpty(author) && !TextUtils.isEmpty(drip)){
            getParams().put(AUTHOR, author);
            getParams().put(DRIP, drip);
            if(!TextUtils.isEmpty(imageUrl)){
                getParams().put(IMAGE_URL, imageUrl);
            }
        }
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<Drip>(){}.getType();
    }
}
