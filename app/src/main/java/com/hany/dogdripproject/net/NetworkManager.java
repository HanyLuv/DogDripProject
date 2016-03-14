package com.hany.dogdripproject.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by HanyLuv on 2016-03-14.
 */
public class NetworkManager {

    //호스트 주소
    private final String DRIP_HOST = "http://52.76.24.8:8080/dogdic";
    //모든 드립을 가져온다.
    private final String DRIP_GET = "/drip/get";
    //드립 등록
    private final String DRIP_PUT = "/drip/put";
    //회원가입.
    private final String DRIP_REGISTER_USER = "user/register";
    //로그인
    private final String DRIP_LOGIN_USER = "user/login";

    private RequestQueue mQueue;

    private static NetworkManager instance = null;


    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager() {
    }

    // P요청
    public void request(Context context, RequestType type, Map keyValues, NetworkListener listener) {
        mQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = createRequest(type, keyValues, listener);
        mQueue.add(request);
    }

    //파라미터를 만든다.
    private String getParameter(Map keys) {
        String param = "";
        StringBuilder builder = new StringBuilder();
        Iterator iterator = keys.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry key = (Map.Entry) iterator.next();
            builder.append(param).append(key.getKey()).append("=").append(key.getValue()).append("&");
        }
        param = "?" + builder.toString();
        Log.d("hany_tag", "param : " + param);
        return param;
    }

    //타입에 맞는 요청을 만든다.
    private JsonObjectRequest createRequest(RequestType requestType, Map keyValues, final NetworkListener listener) {
        JsonObjectRequest request = null;
        String parameter = null;
        int RequestMethod = Request.Method.GET;

        if (keyValues != null) {
            parameter = getParameter(keyValues);
        }

        String apiAddr = null;

        switch (requestType) { //요청 타입에따라 api주소를 (apiAddr) 설정한다.
            case DRIP_PUT:
                apiAddr = DRIP_PUT;
                RequestMethod = Request.Method.POST;
                break;
            case DRIP_REGISTER_USER:
                apiAddr = DRIP_REGISTER_USER;
                RequestMethod = Request.Method.POST;
                break;
            case DRIP_GET:
                apiAddr = DRIP_GET;
                break;
            case DRIP_LOGIN_USER:
                apiAddr = DRIP_LOGIN_USER;
                break;
        }

        String url = null;
        if (parameter != null && RequestMethod != Request.Method.GET) {
            url = new StringBuilder().append(DRIP_HOST).append(apiAddr).append(parameter).toString();
        } else {
            url = new StringBuilder().append(DRIP_HOST).append(apiAddr).toString();
        }

        Log.d("hany_tag", "** url : " + url);
        Log.d("hany_tag", "** Request.Method : " + RequestMethod);

        request = new JsonObjectRequest(RequestMethod, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        return request;
    }

    public enum RequestType {
        DRIP_GET,
        DRIP_PUT,
        DRIP_REGISTER_USER,
        DRIP_LOGIN_USER;
    }

    //네트워크 결과 응답 리스너
    public interface NetworkListener {
        void onResponse(JSONObject jsonObj);
        void onErrorResponse(VolleyError error);
    }

}
