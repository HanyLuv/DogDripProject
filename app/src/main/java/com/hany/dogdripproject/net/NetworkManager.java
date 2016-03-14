package com.hany.dogdripproject.net;

import android.content.Context;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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


    private Context mContext;
    private RequestQueue mQueue;
    private NetworkListener mNetworkListener;

    public NetworkManager(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(context);
    }

    public void post(RequestType type, NetworkListener listener) {

    }

    //네트워크 결과 응답 리스너
    public interface NetworkListener {
        public void onResponse(JSONObject jsonObj);

        public void onErrorResponse(VolleyError error);
    }


    //타입에 맞는 요청을 가져온다.
    public JsonObjectRequest getRequset(RequestType requestType, final NetworkListener listener) {
        JsonObjectRequest request = null;
        int RequestMethod = Request.Method.GET;

        String type = null;
        switch (requestType) {
            case DRIP_GET:
                type = DRIP_GET;
                break;
            case DRIP_PUT:
                type = DRIP_PUT;
                RequestMethod = Request.Method.POST;
                break;
            case DRIP_LOGIN_USER:
                type = DRIP_LOGIN_USER;
                break;
            case DRIP_REGISTER_USER:
                type = DRIP_REGISTER_USER;
                break;
        }

        String url = new StringBuilder().append(DRIP_HOST).append(type).toString();

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

}
