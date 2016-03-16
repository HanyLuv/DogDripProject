package com.hany.dogdripproject;

/**
 * Created by kwonojin on 16. 3. 16..
 */
public class Constants {

    //호스트 주소
    public static final String API_SERVER_HOST = "http://52.76.24.8:8080/dogdic";

    //모든 드립을 가져온다.
    public static final String DRIP_GET = API_SERVER_HOST + "/drip/get";
    //드립 등록
    private final String DRIP_PUT = "/drip/put";
    //회원가입.
    private final String DRIP_REGISTER_USER = "user/register";
    //로그인
    private final String DRIP_LOGIN_USER = "user/login";
}
