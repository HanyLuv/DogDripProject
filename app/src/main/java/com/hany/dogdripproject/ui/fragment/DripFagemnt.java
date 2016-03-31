package com.hany.dogdripproject.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.DripListRequest;
import com.hany.dogdripproject.ui.BaseDripActivity;
import com.hany.dogdripproject.ui.fragment.adapater.DripFragmentPagerAdapter;
import com.hany.dogdripproject.utils.Log;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class DripFagemnt extends BaseFragment {

    private BaseDripActivity mDripActivity;
    private Activity mActivity;

    private ViewPager mViewPager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mDripActivity = (BaseDripActivity) mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drip, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_drip_pager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DripListRequest request = new DripListRequest(mActivity, new BaseApiResponse.OnResponseListener<ArrayList<Drip>>() {
            @Override
            public void onResponse(BaseApiResponse<ArrayList<Drip>> response) {
                if (!isRequestSuccessfully(response)) {
                    Log.d("Error",response.getMessage());
                    return;
                }
            if (response.getData() != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("drips", response.getData());
                mDripActivity.setDrips(response.getData());
                DripFragmentPagerAdapter pagerAdapter = new DripFragmentPagerAdapter(getActivity().getSupportFragmentManager(),response.getData());
                mViewPager.setAdapter(pagerAdapter);
            }
            }

            @Override
            public void onError(VolleyError error) {
                showToast(error.getMessage());

            }
        });
        NetworkManager.getInstance().request(request);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
