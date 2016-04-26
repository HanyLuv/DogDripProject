package com.organic.dogdrip.ui;

import android.content.Intent;
import android.os.Bundle;

import com.kakao.Session;
import com.kakao.exception.KakaoException;
import com.organic.dogdrip.utils.Log;

/**
 * Created by HanyLuv on 2016-04-26.
 */
public class KaKaoLoginActivity extends BaseActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
