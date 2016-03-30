package com.hany.dogdripproject.net;

import android.content.Context;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class BaseApiRequest{

    private Context mContext = null;
    private String mUrl = null;
    private Map<String, String> mParams = null;
    private Map<String, String> mHeaders = null;
    private int mRequestMethod = Request.Method.POST;
    private BaseApiResponse mResponse = null;

    public BaseApiRequest(Context context, String url, BaseApiResponse response){
        this(context, Request.Method.POST, url, response);
    }

    public BaseApiRequest(Context context, int requestMethod, String url, BaseApiResponse response){
        mContext = context;
        mRequestMethod = requestMethod;
        mUrl = url;
        mResponse = response;
        mParams = new HashMap<>();
        mHeaders = new HashMap<>();
    }

    public void putParam(String key, String value){
        mParams.put(key,value);
    }

    public void putHeader(String key, String value){
        mHeaders.put(key,value);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public int getMethod() {
        return mRequestMethod;
    }

    public BaseApiResponse getResponse() {
        return mResponse;
    }

    public boolean isCache(){
        return false;
    }

    public long getCacheTimeMilliseconds(){
        return 1000;
    }

    protected void setResponse(BaseApiResponse response){
        mResponse = response;
    }

    protected Context getContext(){
        return mContext;
    }

    @Override
    public String toString() {
        return "BaseApiRequest{" +
                "mUrl='" + mUrl + '\'' +
                ", mParams=" + mParams +
                ", mHeaders=" + mHeaders +
                ", mRequestMethod=" + mRequestMethod +
                ", mResponse=" + mResponse +
                '}';
    }
}
