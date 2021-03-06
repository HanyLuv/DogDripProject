package com.organic.dogdrip.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.organic.dogdrip.R;
import com.organic.dogdrip.ui.adapter.BaseFragmentPagerAdapter;

import java.util.List;

/**
 * Created by kwonojin on 16. 3. 31..
 */
abstract public class BaseHorizontalScrollFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    private RelativeLayout mViewContainer = null;
    private ViewPager mViewPager = null;
    private BaseFragmentPagerAdapter mPagerAdapter = null;

    private List<?> mPageList = null;

    private ViewPager.OnPageChangeListener mPageChangeListener = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_horizontal, container, false);
        mViewContainer = (RelativeLayout) view.findViewById(R.id.layout_base_horizontal_container);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_base_horizontal);
        mViewPager.addOnPageChangeListener(this);
        onCreateChildView(inflater, mViewContainer);
        return view;
    }

    protected void onCreateChildView(LayoutInflater inflater, RelativeLayout parent){
    }


    protected abstract BaseFragmentPagerAdapter makeFragmentPagerAdapter(List<?> pageDatas);

    protected void setPageData(List<?> pageDatas){
        if(pageDatas != null){
            mPageList = pageDatas;
            if(mPagerAdapter == null){
                mPagerAdapter = makeFragmentPagerAdapter(mPageList);
                if(mPagerAdapter != null){
                    mViewPager.setAdapter(mPagerAdapter);
                }
            }else{
                mPagerAdapter.notifyDataSetChanged();
            }
        }
    }

    public ViewPager getViewPager(){
        return mViewPager;
    }

    public BaseFragmentPagerAdapter getPagerAdapter(){
        return mPagerAdapter;
    }

    public int getCurrentPagePosition(){
        int pos = 0;
        if(mViewPager != null){
            pos = mViewPager.getCurrentItem();
        }
        return pos;
    }

    public int getPageCount(){
        int count = 0;
        if(mPageList != null){
            count = mPageList.size();
        }
        return count;
    }

    public void setPageChangeListener(ViewPager.OnPageChangeListener ll){
        mPageChangeListener = ll;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(mPageChangeListener != null){
            mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(mPageChangeListener != null){
            mPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(mPageChangeListener != null){
            mPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
