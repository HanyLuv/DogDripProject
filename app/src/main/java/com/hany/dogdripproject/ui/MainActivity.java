package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.NetworkManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map keys = new HashMap();
        keys.put("author", "admin");
        keys.put("drip", "야호222");


        //등록하기
        NetworkManager.getInstance().request(getApplicationContext(), NetworkManager.RequestType.DRIP_PUT, keys, new NetworkManager.NetworkListener() {
            @Override
            public void onResponse(JSONObject jsonObj) {
                Log.d("hany_tag", "onResponse : " + jsonObj.toString());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hany_tag", "onErrorResponse : " + error.getMessage());
            }
        });

        //모든항목가져오기
        NetworkManager.getInstance().request(getApplicationContext(), NetworkManager.RequestType.DRIP_GET, null, new NetworkManager.NetworkListener() {
            @Override
            public void onResponse(JSONObject jsonObj) {
                Log.d("hany_tag", "onResponse : " + jsonObj.toString());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hany_tag", "onErrorResponse : " + error.getMessage());
            }
        });
    }

}
