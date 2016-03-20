package com.hany.dogdripproject.net;


import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class BaseApiResponse<DATA> {

    public interface OnResponseListener<DATA>{
        void onResponse(BaseApiResponse<DATA> response);
        void onError(VolleyError error);
    }

    protected static final String KEY_MESSAGE = "message";
    protected static final String KEY_ERROR_CODE = "errorCode";
    protected static final String KEY_RESPONSE_TIME = "duration";
    protected static final String KEY_RESPONSE_DURATION = "duration";
    protected static final String KEY_DEFAULT_DATA = "result";

    private String message = null;
    private int errorCode = -1;
    private long responseTime = 0;
    private long duration = 0;
    private DATA data = null;

    private OnResponseListener<DATA> mOnResponseListener = null;
    private Type mType = null;


    private Gson mGson = null;

    public BaseApiResponse(OnResponseListener<DATA> listener, Type type){
        mGson = new Gson();
        mOnResponseListener = listener;
        mType = type;
    }

    public void setResponse(JSONObject response){
        if(response != null){
            message = response.optString(KEY_MESSAGE);
            errorCode = response.optInt(KEY_ERROR_CODE);
            responseTime = response.optLong(KEY_RESPONSE_TIME);
            duration = response.optLong(KEY_RESPONSE_DURATION);
            String jData = response.optString(getDataRootKey());
            if(jData != null){
                data = getGson().fromJson(jData, getType());
            }
            mOnResponseListener.onResponse(this);
        }
    }

    public void setError(VolleyError error){
        if(mOnResponseListener != null){
            mOnResponseListener.onError(error);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    public void setOnResponseListener(OnResponseListener l){
        mOnResponseListener = l;
    }

    public Type getType(){
        return mType;
    }

    protected String getDataRootKey(){
        return KEY_DEFAULT_DATA;
    };

    protected Gson getGson(){
        return mGson;
    }

}
