package com.hany.dogdripproject.net.response;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by kwonojin on 16. 3. 15..
 */
abstract public class BaseResponse <DATA> {

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

    private Gson mGson = null;

    public BaseResponse(){
        mGson = new Gson();
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

    public void setResponse(JSONObject response){
        if(response != null){
            String jData = response.optString(getDataRootKey());
            if(jData != null){

            }
        }
    }

    public abstract Type getType();

    protected String getDataRootKey(){
        return KEY_DEFAULT_DATA;
    };

    protected Gson getGson(){
        return mGson;
    }

}
