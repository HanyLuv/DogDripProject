package com.organic.dogdrip.ui.fragment.drip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.organic.dogdrip.R;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.NetworkManager;
import com.organic.dogdrip.net.request.DripListRequest;
import com.organic.dogdrip.ui.adapter.BaseFragmentPagerAdapter;
import com.organic.dogdrip.ui.adapter.fragment.DripFragmentPagerAdapter;
import com.organic.dogdrip.ui.fragment.BaseHorizontalScrollFragment;
import com.organic.dogdrip.utils.Log;
import com.organic.dogdrip.vo.drip.Drip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanyLuv on 2016-03-31.
 */
public class DripBookFragment extends BaseHorizontalScrollFragment {

    private TextView mDripPage = null;
    private Button mWriteButton = null;

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
        mWriteButton = (Button) view.findViewById(R.id.btn_fragment_drip_book_page_write);
        mWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(UserInfoManager.getInstance().getUserInfo() != null){
                    addChildFragment(DripWriteFragment.class, null);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.login_need);
                    builder.setPositiveButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserInfoManager.getInstance().sendNeedLoginBroadcast();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        mDripPage.setText((position + 1) + "/" + getPagerAdapter().getCount());
    }
}
