package com.hany.dogdripproject.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.manager.UserInfoManager;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.ConfigReqeust;
import com.hany.dogdripproject.net.request.GcmRegisterRequest;
import com.hany.dogdripproject.preferences.ConfigPreferenceManager;
import com.hany.dogdripproject.vo.config.AppConfig;
import com.hany.dogdripproject.vo.user.User;

import java.io.IOException;

/**
 * Created by kwonojin on 16. 3. 19..
 */
public class SplashActivity extends BaseActivity {

    private static final int RETRY_COUNT = 3;

    private ConfigReqeust mConfigReqeust = null;
    private GcmRegisterRequest mGcmRegisterRequest = null;
    private ConfigPreferenceManager mConfigPref = null;

    private TextView mTvLoading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initModels();
        NetworkManager.getInstance().request(mConfigReqeust);
    }

    private void initView(){
        mTvLoading = (TextView) findViewById(R.id.tv_splash_loading);
    }

    private void initModels(){
        mConfigPref = new ConfigPreferenceManager(this);
        mConfigReqeust = new ConfigReqeust(this, onConfigResponseListener);
        mGcmRegisterRequest = new GcmRegisterRequest(this, onGcmRegisterResponseListener);
    }

    private BaseApiResponse.OnResponseListener<AppConfig> onConfigResponseListener = new BaseApiResponse.OnResponseListener<AppConfig>() {

        private int mRetryCount = 0;

        @Override
        public void onResponse(BaseApiResponse<AppConfig> response) {
            if(response != null){
                registerDevice(response.getData());
            }else{
                if(RETRY_COUNT > mRetryCount){
                    NetworkManager.getInstance().request(mConfigReqeust);
                    mRetryCount++;
                }
            }
        }

        @Override
        public void onError(VolleyError error) {
            if(RETRY_COUNT > mRetryCount){
                NetworkManager.getInstance().request(mConfigReqeust);
                mRetryCount++;
            }
        }
    };

    private BaseApiResponse.OnResponseListener<Void> onGcmRegisterResponseListener = new BaseApiResponse.OnResponseListener<Void>() {

        private int mRetryCount = 0;

        @Override
        public void onResponse(BaseApiResponse<Void> response) {
            if(response != null && response.getErrorCode() == 0){
                doStartNextStep();
            }else{
                if(RETRY_COUNT > mRetryCount){
                    NetworkManager.getInstance().request(mGcmRegisterRequest);
                    mRetryCount++;
                }
            }
        }

        @Override
        public void onError(VolleyError error) {
            if(RETRY_COUNT > mRetryCount){
                NetworkManager.getInstance().request(mGcmRegisterRequest);
                mRetryCount++;
            }
        }
    };



    private void registerDevice(final AppConfig appConfig){
        mTvLoading.setText(R.string.config_data_register);
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
                if(value != null){
                    mConfigPref.setGcmDeviceId(value);
                    mGcmRegisterRequest.getParams().put("gcm", value);
                    mGcmRegisterRequest.getParams().put("device", Build.MODEL);
                    NetworkManager.getInstance().request(mGcmRegisterRequest);
                }else{
                    doStartNextStep();
                }
            }
        }.execute();
    }

    private void doStartNextStep(){
        mTvLoading.setText(R.string.config_data_auto_login);
        UserInfoManager.getInstance().autoLogin(new UserInfoManager.OnUserLoginListener() {
            @Override
            public void onLoginCompleted(User user) {
                Toast.makeText(SplashActivity.this,
                        user.getNickname() + getResources().getString(R.string.login_welcome),
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            @Override
            public void onLoginFailed(String errorMessage) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
    }

}
