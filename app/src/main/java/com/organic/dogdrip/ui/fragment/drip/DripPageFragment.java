package com.organic.dogdrip.ui.fragment.drip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.organic.dogdrip.R;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.request.LikeCheckRequest;
import com.organic.dogdrip.net.request.LikeRequest;
import com.organic.dogdrip.ui.BaseActivity;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.vo.drip.Drip;
import com.organic.dogdrip.vo.drip.Like;
import com.organic.dogdrip.vo.drip.LikeInfo;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-15.
 */
public class DripPageFragment extends BaseFragment {

    public static final String KEY_ARGUMENT_DRIP= "drip";
    public static final String KEY_ARGUMENT_PAGER_POSITION = "drip_position";

    private TextView mTvAuthor;
    private TextView mTvDrip;
    private TextView mTvRecommend;
    private TextView mTvcomment;

    private Drip mDrip = null;
    private int mPagePosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrip = getArguments().getParcelable(KEY_ARGUMENT_DRIP);
        mPagePosition = getArguments().getInt(KEY_ARGUMENT_PAGER_POSITION, -1);
    }

    public static Fragment newInstance(Bundle bundle) {
        DripPageFragment dripFragment = new DripPageFragment();
        dripFragment.setArguments(bundle);
        return dripFragment;
    }

    public static Bundle makeArgument(Drip drip){
        Bundle argument = null;
        if(drip != null){
            argument = new Bundle();
            argument.putParcelable(KEY_ARGUMENT_DRIP, drip);
        }
        return argument;
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
        if(mDrip != null && mPagePosition >= 0){
            mTvAuthor.setText(mDrip.getAuthor());
            mTvDrip.setText(mDrip.getDrip());
            String recommend = String.format(getResources().getString(R.string.drip_recommend), mDrip.getHeartcount());
            mTvRecommend.setText(recommend);
//        mTvRecommend.setOnClickListener(recommendCheckClickListener); //추천인 조회
            mTvRecommend.setOnClickListener(recommendClickListener); //추천하기
        }
    }

    //추천 조회 클릭 리스너
    private View.OnClickListener recommendCheckClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getActivity() != null && mDrip != null){
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
                likeCheckRequest.setDripId(mDrip.getId());
                request(likeCheckRequest);
            }
        }
    };

    //추천 클릭 리스너
    private View.OnClickListener recommendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mDrip != null) {
                requestLikeDrip(mDrip);
            }
        }
    };


    private void requestLikeDrip(Drip drip) {
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
        likeRequest.setLikeInfo(drip.getId(),"admin");
        request(likeRequest);
    }

    private void updateRecommendCount(Like like) {
        if(mDrip != null){
            mDrip.setHeartcount(mDrip.getHeartcount() + 1);
            final String recommendCount = String.format(getResources().getString(R.string.drip_recommend), mDrip.getHeartcount());
            mTvRecommend.setText(recommendCount);
        }
    }

//    /**
//     * 포맷형식에 맞춰 String 생성
//     * BaseFragment에 공통으로 뺄까 고민중...
//     *
//     * @param format 포맷형식
//     * @param value  값
//     **/
//
//    private String createStringForFormat(String format, String value) {
//        String createdStr = String.format(format, value);
//        return createdStr;
//    }

    @Override
    public String getFragmentTag() {
        return "drippageFragment";
    }

    @Override
    public String getFragmentTitle() {
        return "Drip";
    }
}
