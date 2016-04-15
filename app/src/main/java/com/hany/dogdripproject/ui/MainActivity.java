package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.fragment.FrameFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.drip.DripBookFragment;
import com.hany.dogdripproject.ui.fragment.setting.SettingBookFragment;
import com.hany.dogdripproject.vo.user.User;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private VerticalViewPager mViewPager;
    private FrameFragmentPagerAdapter mAdapter = null;
    private ImageView mArrowUp = null;
    private ImageView mArrowDown = null;

    private List<Class <? extends BaseFragment>> mData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initModel();
    }
    private void initModel(){
        if(mData == null){
            mData = new ArrayList<>();
        }else{
            mData.clear();
        }
//        mData.add(FirstPageFragment.class);
        mData.add(DripBookFragment.class);
        mData.add(SettingBookFragment.class);

        if(mAdapter == null){
            mAdapter = new FrameFragmentPagerAdapter(getSupportFragmentManager(), mData);
//            mAdapter.setOnChildPageChangeListener(onChildPageChangeListener);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOnPageChangeListener(this);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }


    private void initView(){
        mViewPager = (VerticalViewPager) findViewById(R.id.vp_main_vertical);
        mArrowUp = (ImageView) findViewById(R.id.iv_main_arrow_up);
        mArrowDown = (ImageView) findViewById(R.id.iv_main_arrow_down);
        mArrowUp.setVisibility(View.GONE);
    }

    private void updateVerticalArrows(int pos, int count){
        if(mArrowUp != null && mArrowDown != null){

            if(pos == 0 && count <= 0){
                mArrowUp.setVisibility(View.GONE);
                mArrowDown.setVisibility(View.GONE);
            }else if(pos == 0 && count > pos){
                mArrowUp.setVisibility(View.GONE);
                mArrowDown.setVisibility(View.VISIBLE);
            }else if(pos > 0 && pos == count){
                mArrowUp.setVisibility(View.VISIBLE);
                mArrowDown.setVisibility(View.GONE);
            }else{
                mArrowUp.setVisibility(View.VISIBLE);
                mArrowDown.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(mAdapter != null){
            /**
             * update vertical arrows
             */
            updateVerticalArrows(position, mAdapter.getCount() - 1);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected FragmentManager getCurrentFragmentManager() {
        if(mAdapter != null && mViewPager != null){
            BaseFragment f = (BaseFragment) mAdapter.getItem(mViewPager.getCurrentItem());
            if(f != null && f.getChildFragmentManager().getBackStackEntryCount() > 0){
                return f.getChildFragmentManager();
            }
        }
        return super.getCurrentFragmentManager();
    }

    @Override
    protected boolean popFragment(FragmentManager fm) {
        boolean b = false;
        if(fm != null && fm.getBackStackEntryCount() > 1){
            b = fm.popBackStackImmediate();
        }
        return b;
    }

    @Override
    protected void onUserInfoChanged(User user) {
        super.onUserInfoChanged(user);
        if(super.getCurrentFragmentManager() != null
                && super.getCurrentFragmentManager().getFragments() != null){
            for(Fragment f  : super.getCurrentFragmentManager().getFragments()){
                if(f instanceof BaseFragment){
                    ((BaseFragment) f).onUserInfoChanged(user);
                }
            }
        }
    }
}
