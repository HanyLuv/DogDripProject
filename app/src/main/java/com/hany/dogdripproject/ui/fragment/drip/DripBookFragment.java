package com.hany.dogdripproject.ui.fragment.drip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
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
public class DripBookFragment extends BaseHorizontalScrollFragment {

    private TextView mDripPage = null;

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

    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List<?> pageDatas) {
        DripFragmentPagerAdapter adapter = new DripFragmentPagerAdapter(getFragmentManager(), (List<Drip>) pageDatas);
        return adapter;
    }

    @Override
    protected void setPageData(List<?> pageDatas) {
        super.setPageData(pageDatas);
        if(pageDatas != null && pageDatas.size() > 0){
            mDripPage.setText(1 + "/" + pageDatas.size());
        }
    }

    @Override
    protected void onCreateChildView(LayoutInflater inflater, RelativeLayout parent) {
        super.onCreateChildView(inflater, parent);
        View view = inflater.inflate(R.layout.fragment_drip_book, parent, true);
        mDripPage = (TextView) view.findViewById(R.id.tv_fragment_drip_book_page);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        mDripPage.setText((position + 1) + "/" + getPagerAdapter().getCount());
    }
}
