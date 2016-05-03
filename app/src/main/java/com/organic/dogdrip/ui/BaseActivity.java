package com.organic.dogdrip.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.organic.dogdrip.R;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.vo.user.User;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class BaseActivity extends FragmentActivity {

    private FrameLayout mLayoutMainContainer = null;

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(UserInfoManager.ACTION_USER_INFO_STATE_CHANGED);
        registerReceiver(bindActivityBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bindActivityBroadcastReceiver != null){
            unregisterReceiver(bindActivityBroadcastReceiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(UserInfoManager.ACTION_USER_NEED_LOGIN);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
//        super.setContentView(layoutResID);
        View activityView = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(activityView);
    }

    @Override
    public void setContentView(View view) {
        View mergeView = initView(view);
        super.setContentView(mergeView);
    }

    private View initView(View childView) {
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        mLayoutMainContainer = (FrameLayout) rootView.findViewById(R.id.layout_main_container);
        mLayoutMainContainer.addView(childView);
        return rootView;
    }

    protected int getFragmentAchorViewId() {
        return mLayoutMainContainer.getId();
    }

    /**
     * BaseFragment를 추가한다. 이미 추가된 Fragment일 경우 FragmentManager에서 TAG값을 이용해 Frament를 가져와
     * 다시 Replace 시킨다
     * @param fClss BaseFragment 를 상속 받은 Fragment의 class
     * @param bundle 전달한 Fragment Argument
     */
    public void addFragment(Class<? extends BaseFragment> fClss, Bundle bundle) {
        BaseFragment f = null;
        try {
            f = fClss.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(f != null){
            BaseFragment of = (BaseFragment) getCurrentFragmentManager().findFragmentByTag(f.getFragmentTag());
            FragmentTransaction ft = getCurrentFragmentManager().beginTransaction();
            if(of == null){
                ft.add(getFragmentAchorViewId(), f, f.getFragmentTag());
                if(bundle != null){
                    f.setArguments(bundle);
                }
                ft.setBreadCrumbTitle(f.getFragmentTitle())
                        .addToBackStack(f.getBackstackName())
                        .commitAllowingStateLoss();
            }else{
                f = null;
            }
        }

//        if(f != null){
//            BaseFragment of = (BaseFragment) getCurrentFragmentManager().findFragmentByTag(f.getFragmentTag());
//            FragmentTransaction ft = getCurrentFragmentManager().beginTransaction();
//            if (of != null) {
//                ft.replace(getFragmentAchorViewId(), of);
//                if(bundle != null){
//                    of.setArguments(bundle);
//                }
//                ft.setBreadCrumbTitle(of.getFragmentTitle())
//                        .addToBackStack(of.getBackstackName())
//                        .commitAllowingStateLoss();
//                f = null;
//            } else {
//                ft.add(getFragmentAchorViewId(), f, f.getFragmentTag());
//                if(bundle != null){
//                    f.setArguments(bundle);
//                }
//                ft.setBreadCrumbTitle(f.getFragmentTitle())
//                        .addToBackStack(f.getBackstackName())
//                        .commitAllowingStateLoss();
//            }
//        }
    }

    public AlertDialog createAlertDialog(String msg,DialogInterface.OnClickListener okListener,DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton(getResources().getText(R.string.ok), okListener);
        builder.setNegativeButton(getResources().getText(R.string.cancel),cancelListener);
        return builder.create();
    }

    protected void onUserInfoChanged(User user){

    }

    protected void onUserNeedLogin(){

    }

    private BroadcastReceiver bindActivityBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String action = intent.getAction();
                if(action != null){
                    if(action.equals(UserInfoManager.ACTION_USER_INFO_STATE_CHANGED)){
                        User user = intent.getParcelableExtra(User.class.getName());
                        onUserInfoChanged(user);
                    }
                }
            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String action = intent.getAction();
                if(action != null){
                    if(action.equals(UserInfoManager.ACTION_USER_NEED_LOGIN)){
                        onUserNeedLogin();
                    }
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        if(popFragment(getCurrentFragmentManager())){
            return;
        }
        super.onBackPressed();
    }

    protected boolean popFragment(FragmentManager fm){
        return fm.popBackStackImmediate();
    }

    protected FragmentManager getCurrentFragmentManager(){
        return getSupportFragmentManager();
    }
}
