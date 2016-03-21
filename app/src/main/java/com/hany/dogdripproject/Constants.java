package com.hany.dogdripproject;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class Constants {




    //호스트 주소
    public static final String API_SERVER_HOST = "http://52.76.24.8:8080/dogdic";

    //드립 등록
    private final String DRIP_PUT = "/drip/put";
    //회원가입.
    private final String DRIP_REGISTER_USER = "user/register";
    //로그인
    private final String DRIP_LOGIN_USER = "user/login";

    //파라미터 값들
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_NICKNAME = "nickname";
    public static final String PARAM_DEVICE = "device";
    public static final String PARAM_DRIP= "drip";
    public static final String PARAM_AUTHOR = "author";

}
