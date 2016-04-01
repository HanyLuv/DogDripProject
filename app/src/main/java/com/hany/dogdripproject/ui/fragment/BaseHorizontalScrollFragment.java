package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;

import java.util.List;

/**
 * Created by kwonojin on 16. 3. 31..
 */
abstract public class BaseHorizontalScrollFragment <PAGE> extends BaseFragment {

    private ViewPager mViewPager = null;
    private BaseFragmentPagerAdapter mPagerAdapter = null;

    private List<PAGE> mPageList = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_horizontal, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_base_horizontal);
        return view;
    }


    protected abstract BaseFragmentPagerAdapter makeFragmentPagerAdapter(List<PAGE> pageDatas);

    protected void setPageData(List<PAGE> pageDatas){
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
}
