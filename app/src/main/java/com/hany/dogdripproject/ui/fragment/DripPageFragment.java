package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.request.LikeCheckRequest;
import com.hany.dogdripproject.net.request.LikeRequest;
import com.hany.dogdripproject.ui.BaseActivity;
import com.hany.dogdripproject.ui.MainActivity;
import com.hany.dogdripproject.vo.drip.Like;
import com.hany.dogdripproject.vo.drip.LikeInfo;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-15.
 */
public class DripPageFragment extends BaseFragment {

    private TextView mTvAuthor;
    private TextView mTvDrip;
    private TextView mTvRecommend;
    private TextView mTvcomment;
    private String mHeartCount;

    private int mFragmentPagerPosition;
    private String mDripId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment newInstance(Bundle bundle) {
        DripPageFragment dripFragment = new DripPageFragment();
        dripFragment.setArguments(bundle);
        return dripFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drip_item, container, false);
        mTvAuthor = (TextView) view.findViewById(R.id.tv_drip_author);
        mTvDrip = (TextView) view.findViewById(R.id.tv_drip_drip);

        mTvRecommend = (TextView) view.findViewById(R.id.tv_drip_recommend);
        mTvcomment = (TextView) view.findViewById(R.id.tv_drip_comment);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mDripId = getArguments().getString(Constants.PARAM_ID);
        mFragmentPagerPosition = getArguments().getInt(Constants.DRIP_PAGER_POSITION);
        mTvAuthor.setText(getArguments().getString(Constants.PARAM_AUTHOR));
        mTvDrip.setText(getArguments().getString(Constants.PARAM_DRIP));
        mHeartCount = getArguments().getString(Constants.PARAM_HEARTCOUNT);
        String strCommend = createStringForFormat(getResources().getString(R.string.drip_recommend),mHeartCount);
        mTvRecommend.setText(strCommend);
//        mTvRecommend.setOnClickListener(recommendCheckClickListener); //추천인 조회
        mTvRecommend.setOnClickListener(recommendClickListener); //추천하기
    }

    //추천 조회 클릭 리스너
    private View.OnClickListener recommendCheckClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LikeCheckRequest likeCheckRequest = new LikeCheckRequest(getActivity(), new BaseApiResponse.OnResponseListener<ArrayList<LikeInfo>>() {
                @Override
                public void onResponse(BaseApiResponse<ArrayList<LikeInfo>> response) {
                    if(!isRequestSuccessfully(response)){
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("likelist", response.getData());
                    if (getActivity() != null) {
                        ((BaseActivity) getActivity()).addFragment(LikeListFragment.class, bundle);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    showToast(error.getMessage());
                }
            });
            likeCheckRequest.putParam(Constants.PARAM_ID,mDripId);
            request(likeCheckRequest);
        }
    };

    //추천 클릭 리스너
    private View.OnClickListener recommendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(mDripId)) {
                requestLikeDrip(mDripId);
            }
        }
    };


    private void requestLikeDrip(String dripID) {
        LikeRequest likeRequest = new LikeRequest(getActivity(), new BaseApiResponse.OnResponseListener<Like>() {
            @Override
            public void onResponse(BaseApiResponse<Like> response) {
                if (!isRequestSuccessfully(response)) {
                    return;
                }
                showToast(response.getMessage());
                if (response.getData() != null) {
                    updateRecommendCount(response.getData());
                }
            }
            @Override
            public void onError(VolleyError error) {
                showToast(error.getMessage());
            }
        });
        likeRequest.setLikeInfo(dripID,"admin");
        request(likeRequest);
    }

    private void updateRecommendCount(Like like) {

        final String recommendCount = createStringForFormat(
                getResources().getString(R.string.drip_recommend),
                String.valueOf(like.getRow()));

        if(getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).getDrips().get(mFragmentPagerPosition).setHeartcount(like.getRow());
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvRecommend.setText(recommendCount);
            }
        });
    }

    /**
     * 포맷형식에 맞춰 String 생성
     * BaseFragment에 공통으로 뺄까 고민중...
     *
     * @param format 포맷형식
     * @param value  값
     **/

    private String createStringForFormat(String format, String value) {
        String createdStr = String.format(format, value);
        return createdStr;
    }

    @Override
    public String getFragmentTag() {
        return "drippageFragment";
    }

    @Override
    public String getFragmentTitle() {
        return "Drip";
    }
}
