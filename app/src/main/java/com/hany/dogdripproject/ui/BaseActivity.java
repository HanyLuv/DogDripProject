package com.hany.dogdripproject.ui;

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

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.manager.UserInfoManager;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.vo.user.User;

import java.util.ArrayList;

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
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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


    /***
     * Fragment 추가
     *
     */
    public void addFragment(Class<? extends BaseFragment> fClss) {
        addFragment(fClss, null);
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
            if (of != null) {
                if(of.isDetached()){
                    ft.replace(getFragmentAchorViewId(), of);
                    if(bundle != null){
                        of.setArguments(bundle);
                    }
                    ft.setBreadCrumbTitle(of.getFragmentTitle())
                            .addToBackStack(of.getBackstackName())
                            .commitAllowingStateLoss();
                }
                f = null;
            } else {
                ft.add(getFragmentAchorViewId(), f, f.getFragmentTag());
                if(bundle != null){
                    f.setArguments(bundle);
                }
                ft.setBreadCrumbTitle(f.getFragmentTitle())
                        .addToBackStack(f.getBackstackName())
                        .commitAllowingStateLoss();
            }
        }
    }

    /**
     * 확인,취소만 존재하는 가장 기본적인 Alert Dialog 를 만들어 리턴.
     * 음.. 이친구 선언 위치 고민중.
     * **/
    public AlertDialog createAlertDialog(String msg,DialogInterface.OnClickListener okListener,DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setPositiveButton(getResources().getText(R.string.ok), okListener);
        builder.setNegativeButton(getResources().getText(R.string.cancel),cancelListener);
        return builder.create();
    }

    protected void onUserInfoChanged(User user){

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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
