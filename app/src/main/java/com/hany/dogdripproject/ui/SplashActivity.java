package com.hany.dogdripproject.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiRequest;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.ConfigReqeust;
import com.hany.dogdripproject.preferences.ConfigPreferenceManager;
import com.hany.dogdripproject.vo.config.AppConfig;

import java.io.IOException;

/**
 * Created by kwonojin on 16. 3. 19..
 */
public class SplashActivity extends BaseActivity {

    private ConfigReqeust mConfigReqeust = null;
    private ConfigPreferenceManager mConfigPref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mConfigPref = new ConfigPreferenceManager(this);
        mConfigReqeust = new ConfigReqeust(this, onResponseListener);
        NetworkManager.getInstance().request(mConfigReqeust);
    }

    BaseApiResponse.OnResponseListener<AppConfig> onResponseListener = new BaseApiResponse.OnResponseListener<AppConfig>() {
        @Override
        public void onResponse(BaseApiResponse<AppConfig> response) {
            if(response != null){
                registerDevice(response.getData());
            }
        }

        @Override
        public void onError(VolleyError error) {

        }
    };

    private void registerDevice(final AppConfig appConfig){
        String regId = mConfigPref.getGcmDeviceId();
        if(regId == null){
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String regId = null;
                    if(appConfig != null){
                        if(regId == null && appConfig.getSenderId() != null){
                            GoogleCloudMessaging gcm =  GoogleCloudMessaging.getInstance(getApplicationContext());
                            try {
                                regId = gcm.register(appConfig.getSenderId());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return regId;
                }

                @Override
                protected void onPostExecute(String value) {
                    super.onPostExecute(value);
                    mConfigPref.setGcmDeviceId(value);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }.execute();
        }else{
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    }
}
