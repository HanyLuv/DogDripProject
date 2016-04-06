package com.hany.dogdripproject;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class Constants {


    /**
     * Intent Actions
     */
    public static final String ACTION_USER_INFO_CHANGED = "com.hany.dogdripproject.user.changed";


    //호스트 주소
    public static final String API_SERVER_HOST = "http://52.76.24.8:8080/dogdic";

    //드립 등록
    private final String DRIP_PUT = "/drip/put";
    //회원가입.
    private final String DRIP_REGISTER_USER = "/user/register";
    //로그인
    private final String DRIP_LOGIN_USER = "/user/login";

    //드립 추천
    private final String DRIP_LIKE = "/drip/like";
}
