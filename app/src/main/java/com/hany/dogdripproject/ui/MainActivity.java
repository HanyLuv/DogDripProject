package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.request.JoinRequest;
import com.hany.dogdripproject.ui.fragment.BaseFragment;
import com.hany.dogdripproject.ui.fragment.DripFagemnt;
import com.hany.dogdripproject.ui.fragment.HomeFragment;
import com.hany.dogdripproject.ui.fragment.JoinFragment;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.DripListRequest;
import com.hany.dogdripproject.ui.fragment.LoginFragment;
import com.hany.dogdripproject.ui.fragment.SettingFragment;
import com.hany.dogdripproject.ui.fragment.WriteFragment;
import com.hany.dogdripproject.ui.fragment.adapater.FrameFragmentPagerAdapter;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.ArrayList;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends BaseDripActivity {

    private VerticalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_fragment_pager);
        mViewPager = (VerticalViewPager) findViewById(R.id.content_frame);
        ArrayList<Class <? extends BaseFragment>> baseFragments = new ArrayList<>();

        baseFragments.add(LoginFragment.class);
//        baseFragments.add(DripFagemnt.class);
        baseFragments.add(SettingFragment.class);


        mViewPager.setAdapter(new FrameFragmentPagerAdapter(getSupportFragmentManager(),baseFragments));
    }


}
