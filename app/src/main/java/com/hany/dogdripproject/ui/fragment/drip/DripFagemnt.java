package com.hany.dogdripproject.ui.fragment.drip;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.DripListRequest;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.adapter.fragment.DripFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;
import com.hany.dogdripproject.utils.Log;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class DripFagemnt extends BaseHorizontalScrollFragment<Drip> {

    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List<Drip> pageDatas) {
        DripFragmentPagerAdapter pagerAdapter = new DripFragmentPagerAdapter(getActivity().getSupportFragmentManager(), pageDatas);
        return pagerAdapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final DripListRequest request = new DripListRequest(getActivity(), new BaseApiResponse.OnResponseListener<ArrayList<Drip>>() {
            @Override
            public void onResponse(BaseApiResponse<ArrayList<Drip>> response) {
                if (!isRequestSuccessfully(response)) {
                    Log.d("Error",response.getMessage());
                }else {
                    setPageData(response.getData());
                }
            }

            @Override
            public void onError(VolleyError error) {
                showToast(error.getMessage());

            }
        });
        NetworkManager.getInstance().request(request);
    }
}
