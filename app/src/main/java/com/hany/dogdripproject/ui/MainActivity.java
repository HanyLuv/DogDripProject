package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.fragment.FrameFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;
import com.hany.dogdripproject.ui.fragment.drip.DripFagemnt;
import com.hany.dogdripproject.ui.fragment.setting.FirstPageFragment;
import com.hany.dogdripproject.ui.fragment.setting.LoginFragment;
import com.hany.dogdripproject.ui.fragment.setting.SettingFragment;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private VerticalViewPager mViewPager;
    private FrameFragmentPagerAdapter mAdapter = null;
    private ImageView mArrowUp = null;
    private ImageView mArrowDown = null;
    private ImageView mArrowLeft = null;
    private ImageView mArrowRight = null;

    private List<Class <? extends BaseHorizontalScrollFragment>> mData = null;


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
        mData.add(FirstPageFragment.class);
        mData.add(DripFagemnt.class);
        mData.add(SettingFragment.class);

        if(mAdapter == null){
            mAdapter = new FrameFragmentPagerAdapter(getSupportFragmentManager(), mData);
            mAdapter.setOnChildPageChangeListener(onChildPageChangeListener);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setOnPageChangeListener(this);
            if(mAdapter.getCount() > 0){
                BaseHorizontalScrollFragment f = (BaseHorizontalScrollFragment) mAdapter.getItem(0);
                if(f != null){
                    updateHorizontalArrows(0, f.getPageCount());
                }
            }
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }


    private void initView(){
        mViewPager = (VerticalViewPager) findViewById(R.id.vp_main_vertical);
        mArrowUp = (ImageView) findViewById(R.id.iv_main_arrow_up);
        mArrowDown = (ImageView) findViewById(R.id.iv_main_arrow_down);
        mArrowLeft = (ImageView) findViewById(R.id.iv_main_arrow_left);
        mArrowRight = (ImageView) findViewById(R.id.iv_main_arrow_right);
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

    private void updateHorizontalArrows(int pos, int count){
        if(mArrowLeft != null && mArrowRight != null){
            if(pos == 0 && count <= 0){
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.GONE);
            }else if(pos == 0 && count > pos){
                mArrowLeft.setVisibility(View.GONE);
                mArrowRight.setVisibility(View.VISIBLE);
            }else if(pos > 0 && pos == count){
                mArrowLeft.setVisibility(View.VISIBLE);
                mArrowRight.setVisibility(View.GONE);
            }else{
                mArrowLeft.setVisibility(View.VISIBLE);
                mArrowRight.setVisibility(View.VISIBLE);
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
            BaseHorizontalScrollFragment f = (BaseHorizontalScrollFragment) mAdapter.getItem(position);
            if(f != null){
                /**
                 * update horizontal arrows
                 */
                int pos = f.getCurrentPagePosition();
                int count = f.getPageCount();
                updateHorizontalArrows(pos, count - 1);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * VerticalViewPager의 ChildView인 HorizontalFragmentPager의 PageChangeListener
     */
    private ViewPager.OnPageChangeListener onChildPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(mViewPager != null && mAdapter != null){
                BaseHorizontalScrollFragment f = (BaseHorizontalScrollFragment) mAdapter.getItem(mViewPager.getCurrentItem());
                if(f != null && f.getPagerAdapter() != null){
                    updateHorizontalArrows(position, f.getPageCount() - 1);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
