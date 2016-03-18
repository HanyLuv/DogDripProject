package com.hany.dogdripproject.ui;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.ConfigReqeust;
import com.hany.dogdripproject.vo.config.AppConfig;

/**
 * Created by kwonojin on 16. 3. 19..
 */
public class SplashActivity extends BaseActivity {

    private ConfigReqeust mConfigReqeust = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mConfigReqeust = new ConfigReqeust(this, onResponseListener);
        NetworkManager.getInstance().request(mConfigReqeust);
    }

    BaseApiResponse.OnResponseListener<AppConfig> onResponseListener = new BaseApiResponse.OnResponseListener<AppConfig>() {
        @Override
        public void onResponse(BaseApiResponse<AppConfig> response) {
            if(response != null){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }

        @Override
        public void onError(VolleyError error) {

        }
    };
}
