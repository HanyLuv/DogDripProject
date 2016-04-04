package com.hany.dogdripproject.preferences;

import android.content.Context;

/**
 * Created by HanyLuv on 2016-04-05.
 */
public class UserLoginPreferenceManager {
    private static final String PREF_NAME = "login";
    private static final String KEY_LOGIN_ID = "id";
    private static final String KEY_NICK_NAME = "nickname";
    private static final String KEY_PASSWORD = "password";
    private SharedPreferenceHelper mHelper;

    public UserLoginPreferenceManager(Context context) {
        mHelper = new SharedPreferenceHelper(context, PREF_NAME);
    }

    /**
     * 로그인 아이디를 저장한다.
     *
     * @param id email 형식의 id
     */
    public void saveLoginId(String id) {
        mHelper.putData(KEY_LOGIN_ID, id);
    }

    /**
     * 닉네임을 저장한다.
     */
    public void saveNickName(String nickname) {
        mHelper.putData(KEY_NICK_NAME, nickname);
    }

//    /**
//     * 비밀번호 저장.
//     */
//    public void savePassword(String password) {
//        mHelper.putData(KEY_PASSWORD, password);
//    }
//
//    public String loadPassword() {
//        return mHelper.getData(KEY_PASSWORD, "");
//    }

    public String loadNickName() {
        return mHelper.getData(KEY_NICK_NAME, "");
    }

    public String loadLoginId() {
        return mHelper.getData(KEY_LOGIN_ID, "");
    }
}
