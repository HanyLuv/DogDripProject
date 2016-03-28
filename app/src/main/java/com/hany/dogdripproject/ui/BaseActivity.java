package com.hany.dogdripproject.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.ArrayList;

/**
 * Created by kwonojin on 16. 3. 15..
 */
public class BaseActivity extends FragmentActivity {
    private FrameLayout mLayoutMainContainer = null;

    private static final String TAG = BaseActivity.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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


    public void replaceFragment(BaseFragment fragment, String tag) {
        replaceFragment(fragment, null, tag);
    }


    /**
     * 이미 추가되어있는 Fragment를 다른 Fragment로 대체
     *
     * @param tag Fragment Tag 값
     */
    public void replaceFragment(BaseFragment fragment, Bundle bundle, String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(getFragmentAchorViewId(), fragment).commit();
    }


    /***
     * Fragment 추가
     *
     * @param tag Fragment Tag 값
     */
    public void addFragment(BaseFragment fragment, String tag) {
        addFragment(fragment, null, tag);
    }

    public void addFragment(BaseFragment fragment, Bundle bundle, String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().add(getFragmentAchorViewId(), fragment).addToBackStack(null).commit();
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
}
