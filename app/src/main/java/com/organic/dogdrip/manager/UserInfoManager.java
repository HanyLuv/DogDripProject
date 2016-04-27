package com.organic.dogdrip.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.APIErrorResult;
import com.kakao.AuthType;
import com.kakao.MeResponseCallback;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.organic.dogdrip.R;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.NetworkManager;
import com.organic.dogdrip.net.request.JoinRequest;
import com.organic.dogdrip.net.request.LoginReqeust;
import com.organic.dogdrip.preferences.UserInfoPreferenceManager;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kwonojin on 16. 4. 14..
 */
public class UserInfoManager {

    private static final String ACTION_USER_PRE_FIX = "com.organic.dogdrip.userinfo.";
    public static final String ACTION_USER_INFO_STATE_CHANGED = ACTION_USER_PRE_FIX + ".changed";
    public static final String ACTION_USER_NEED_LOGIN = ACTION_USER_PRE_FIX + ".needlogin";

    private static final String EMAIL_REGEX =  "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 16;

    public interface OnUserLoginListener{
        void onLoginCompleted(User user);
        void onLoginFailed(String errorMessage);
    }


    /**
     * Application Context
     */
    private Context mContext = null;
    private User mMe = null;
    private UserInfoPreferenceManager mPref = null;


    private static UserInfoManager sInstance = null;

    private AccessToken mAccessToken;

    public static void init(Context context){
        sInstance = new UserInfoManager(context);
    }

    public static UserInfoManager getInstance(){
        if(sInstance == null){
            throw new IllegalStateException("UserInfoManager must be init in Application");
        }
        return sInstance;
    }

    private UserInfoManager(Context context){
        mContext = context;
        mPref = new UserInfoPreferenceManager(mContext);
    }

    public void logout(){
        mMe = null;
        saveUserInfo(null);
        Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
        intent.putExtra(User.class.getName(), mMe);
        mContext.sendBroadcast(intent);
    }

    public void autoLogin(final OnUserLoginListener ll){
        String email = mPref.loadLoginId();
        long lastConn = mPref.loadLastConnection();

        if(!TextUtils.isEmpty(email) && lastConn > 0){
            if(mMe == null){
                LoginReqeust loginRequst = new LoginReqeust(mContext, new BaseApiResponse.OnResponseListener<User>() {
                    @Override
                    public void onResponse(BaseApiResponse<User> response) {
                        if(response != null){
                            if(response.getErrorCode() == 0 && response.getData() != null){
                                mMe = response.getData();
                                saveUserInfo(mMe);
                                if(ll != null){
                                    ll.onLoginCompleted(mMe);
                                    Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                                    intent.putExtra(User.class.getName(), mMe);
                                    mContext.sendBroadcast(intent);
                                }
                            }else {
                                mMe = null;
                                ll.onLoginFailed(response.getMessage());
                                saveUserInfo(null);
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if(ll != null){
                            ll.onLoginFailed(error.toString());
                        }
                    }
                });

                loginRequst.setUserInfoForAutoLogin(email, lastConn);
                NetworkManager.getInstance().request(loginRequst);
            }else{
                if(ll != null){
                    ll.onLoginCompleted(mMe);
                    Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                    intent.putExtra(User.class.getName(), mMe);
                    mContext.sendBroadcast(intent);
                }
            }
        }else{
            if(ll != null){
                ll.onLoginFailed("User infomation is empty");
            }
        }
    }

    public void login(String email, String password, final OnUserLoginListener ll){
        if(!isValidEmail(email)){
            if(ll != null){
                ll.onLoginFailed(mContext.getString(R.string.invalid_user_email));
            }
            return;
        }

        if(!isValidPassword(password)){
            if(ll != null){
                ll.onLoginFailed(mContext.getString(R.string.invalid_user_password));
            }
            return;
        }

        if(mMe == null){
            LoginReqeust loginRequst = new LoginReqeust(mContext, new BaseApiResponse.OnResponseListener<User>() {
                @Override
                public void onResponse(BaseApiResponse<User> response) {
                    if(response != null){
                        if(response.getErrorCode() == 0 && response.getData() != null){
                            mMe = response.getData();
                            saveUserInfo(mMe);
                            if(ll != null){
                                ll.onLoginCompleted(mMe);
                                Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                                intent.putExtra(User.class.getName(), mMe);
                                mContext.sendBroadcast(intent);
                            }
                        }else {
                            mMe = null;
                            saveUserInfo(null);
                            ll.onLoginFailed(response.getMessage());
                        }
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    if(ll != null){
                        ll.onLoginFailed(error.toString());
                    }
                }
            });

            loginRequst.setUserInfo(email, password);
            NetworkManager.getInstance().request(loginRequst);
        }else{
            if(ll != null){
                ll.onLoginCompleted(mMe);
                Intent intent = new Intent(ACTION_USER_INFO_STATE_CHANGED);
                intent.putExtra(User.class.getName(), mMe);
                mContext.sendBroadcast(intent);
            }
        }
    }

    private void saveUserInfo(User user){
        if(user != null){
            mPref.saveLoginId(user.getEmail());
            mPref.saveLastConnection(user.getLastconn());
            mPref.saveNickName(user.getNickname());
        }else{
            mPref.saveLoginId(null);
            mPref.saveLastConnection(0);
            mPref.saveNickName(null);
        }
    }

    public User getUserInfo(){
        return mMe;
    }

    public static boolean isValidPassword(String password){
        boolean err = false;
        if(password != null && password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH){
            err = true;
        }
        return err;
    }

    public static boolean isValidEmail(String email) {
        boolean err = false;
        if(email != null && email.length() > 0){
            Pattern p = Pattern.compile(EMAIL_REGEX);
            Matcher m = p.matcher(email);
            if(m.matches()) {
                err = true;
            }
        }
        return err;
    }

    public void facebookLoginInit(CallbackManager callbackManager,
                                  final OnFacebookCallbackListener facebookCallbackListener,
                                  final OnFacebookGraphRequestListener graphRequestListener,
                                  final OnJoinRequestListener joinRequestListener) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(facebookCallbackListener!=null){
                    facebookCallbackListener.onSuccess(loginResult);
                }
                mAccessToken = loginResult.getAccessToken();
                AccessToken.setCurrentAccessToken(mAccessToken);

                GraphRequest request = GraphRequest.newMeRequest(mAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject userInfo, GraphResponse response) {
                        if (graphRequestListener !=null){
                            graphRequestListener.onCompleted(userInfo,response);
                        }
                        try {

                            String name = userInfo.getString("name");
                            String id = userInfo.getString("id");
                            User user = new User();
                            user.setEmail(id);
                            user.setPassword(id);
                            user.setNickname(name);
                            requestJoin(user,joinRequestListener);

                            Log.d(getClass().getSimpleName().toString()," FACEBOOK RESULT : NAME : "+name+" || ID : "+id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                if(facebookCallbackListener!=null){
                    facebookCallbackListener.onCancel();
                }
            }

            @Override
            public void onError(FacebookException error) {
                if(facebookCallbackListener!=null){
                    facebookCallbackListener.onError(error);
                }
            }
        });
    }

    /** 회원가입 */
    public void requestJoin(User user , final OnJoinRequestListener joinRequestListener) {
        String email = user.getEmail();
        String password = user.getEmail();
        String nickname = user.getNickname();

        JoinRequest joinRequest = new JoinRequest(mContext, new BaseApiResponse.OnResponseListener<User>() {
            @Override
            public void onResponse(BaseApiResponse<User> response) {
                if (joinRequestListener != null) {
                    joinRequestListener.onResponse(response);
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (joinRequestListener != null) {
                    joinRequestListener.onError(error);
                }

                Log.e(getClass().getSimpleName().toString(), error.getMessage());

            }
        });

        joinRequest.setUserInfo(email, password, nickname,true);
        NetworkManager.getInstance().request(joinRequest);

    }
    public void facebookLogin(Activity activity) {
        requestFacebookPermissions(activity);
    }


    public interface OnFacebookCallbackListener {
        void onSuccess(LoginResult loginResult);
        void onCancel();
        void onError(FacebookException error);
    }

    public interface OnFacebookGraphRequestListener{
        void onCompleted(JSONObject userInfo, GraphResponse response);
    }

    public interface OnJoinRequestListener {
        void onResponse(BaseApiResponse<User> response);
        void onError(VolleyError error);
    }

    public void requestFacebookPermissions(Activity activity){
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile"));
    }

    public void sendNeedLoginBroadcast(){
        Intent intent = new Intent(ACTION_USER_NEED_LOGIN);
        mContext.sendBroadcast(intent);
    }

   /**
    * @param activity 세션을 오픈한 액티비티
    * */
    public void kakaoLogin(Activity activity,final OnJoinRequestListener joinRequestListener){
        Session.getCurrentSession().addCallback(new SessionCallback() {
            @Override
            public void onSessionOpened() {
                requestKakaoUserInfo(joinRequestListener);
                Log.d("KakaoSessionCallback", "onSessionOpened");
            }

            @Override
            public void onSessionClosed(KakaoException exception) {
                Log.d("KakaoSessionCallback", "onSessionOpened");
            }

            @Override
            public void onSessionOpening() {
                Log.d("KakaoSessionCallback", "onSessionOpened");
            }
        });

        Session.getCurrentSession().open(AuthType.KAKAO_TALK,activity);
    }

    private void requestKakaoUserInfo(final OnJoinRequestListener joinRequestListener){
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            protected void onSuccess(UserProfile userProfile) {
                String name = userProfile.getNickname();
                String id = String.valueOf(userProfile.getId());
                User user = new User();
                user.setEmail(id);
                user.setPassword(id);
                user.setNickname(name);
                requestJoin(user,joinRequestListener);
            }

            @Override
            protected void onNotSignedUp() {

            }

            @Override
            protected void onSessionClosedFailure(APIErrorResult errorResult) {

            }

            @Override
            protected void onFailure(APIErrorResult errorResult) {

            }
        });
    }


}


