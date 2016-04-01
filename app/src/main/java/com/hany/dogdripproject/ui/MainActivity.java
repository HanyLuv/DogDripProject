package com.hany.dogdripproject.ui;

import android.os.Bundle;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.fragment.FrameFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.drip.DripFagemnt;
import com.hany.dogdripproject.ui.fragment.LoginFragment;
import com.hany.dogdripproject.ui.fragment.SettingFragment;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends BaseActivity {

    private VerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_fragment_pager);
        mViewPager = (VerticalViewPager) findViewById(R.id.content_frame);
        ArrayList<Class <? extends BaseFragment>> baseFragments = new ArrayList<>();

        baseFragments.add(LoginFragment.class);
        baseFragments.add(DripFagemnt.class);
        baseFragments.add(SettingFragment.class);


        mViewPager.setAdapter(new FrameFragmentPagerAdapter(getSupportFragmentManager(),baseFragments));
    }


}
