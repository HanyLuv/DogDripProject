package com.hany.dogdripproject.ui;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.CallbackManager;

/**
 * Created by HanyLuv on 2016-04-19.
 */
public class FaceBookLoginActivity extends BaseActivity {

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

}
