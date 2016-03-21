package com.hany.dogdripproject.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.vo.user.User;

import java.lang.reflect.Type;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class LoginRequst extends BasicRequest<User> {
    private static final String API = Constants.API_SERVER_HOST + "/user/login";

    public LoginRequst(Context context, BaseApiResponse.OnResponseListener<User> responseListener) {
        super(context, API, responseListener);
    }

    @Override
    protected Type getClassType() {
        /**
         * 선배님! 저거 아래 해석이 제가 생각하는게 맞나요?
         *
         * 보니깐 TypeToken의 생성자가 protected던데..
         * 익명 클래스로 재정의한다는 의미아닌가요? {} 이 괄호가..
         * 그럼 재정의한 타입토큰 객체를 선언해서 그 타입을 가져온다는의미..
         * 맞나요?
         * */
        return new TypeToken<User>() {}.getType();
    }
}
