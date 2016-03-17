package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiRequest;
import com.hany.dogdripproject.net.BaseApiResponse;

import java.lang.reflect.Type;

/**
 * Created by kwonojin on 16. 3. 17..
 */
public abstract class BasicRequest<T> extends BaseApiRequest {

    private static final String KEY_APP_ID = "Application-ID";
    private static final String KEY_APP_KEY = "Application-Key";

    private BaseApiResponse.OnResponseListener<T> mOnResponseListener = null;


    public BasicRequest(Context context, String url, BaseApiResponse.OnResponseListener<T> responseListener) {
        super(context, url, null);
        setResponse(baseApiResponse);
        mOnResponseListener = responseListener;
        getHeaders().put(KEY_APP_ID, getContext().getPackageName());
        getHeaders().put(KEY_APP_KEY, getContext().getString(R.string.dogdic_app_key));
    }

    private BaseApiResponse<T> baseApiResponse = new BaseApiResponse<>(new BaseApiResponse.OnResponseListener<T>() {
        @Override
        public void onResponse(BaseApiResponse<T> response) {
            if(mOnResponseListener != null){
                mOnResponseListener.onResponse(response);
            }
        }

        @Override
        public void onError(VolleyError error) {
            if(mOnResponseListener != null){
                mOnResponseListener.onError(error);
            }
        }
    }, getClassType());


    protected abstract Type getClassType();

}
