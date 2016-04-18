package com.hany.dogdripproject.preferences;

import android.content.Context;

/**
 * Created by HanyLuv on 2016-04-05.
 */
public class UserInfoPreferenceManager {
    private static final String PREF_NAME = "user_info";
    private static final String KEY_LOGIN_ID = "id";
    private static final String KEY_NICK_NAME = "nickname";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LAST_CONNECTION = "last_conn";

    private SharedPreferenceHelper mHelper;

    public UserInfoPreferenceManager(Context context) {
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

    public void saveLastConnection(long time){
        mHelper.putData(KEY_LAST_CONNECTION, time);
    }

    public long loadLastConnection(){
        return mHelper.getData(KEY_LAST_CONNECTION, 0L);
    }

    public String loadNickName() {
        return mHelper.getData(KEY_NICK_NAME, "");
    }

    public String loadLoginId() {
        return mHelper.getData(KEY_LOGIN_ID, "");
    }
}
