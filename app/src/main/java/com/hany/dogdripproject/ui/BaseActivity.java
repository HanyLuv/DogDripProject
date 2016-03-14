package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.hany.dogdripproject.R;

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

    private View initView(View childView){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        mLayoutMainContainer = (FrameLayout) rootView.findViewById(R.id.layout_main_container);
        mLayoutMainContainer.addView(childView);
        return rootView;
    }

    protected int getFragmentAchorViewId(){
        return mLayoutMainContainer.getId();
    }
}
