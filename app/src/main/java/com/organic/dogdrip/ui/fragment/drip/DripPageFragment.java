package com.organic.dogdrip.ui.fragment.drip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.organic.dogdrip.R;
import com.organic.dogdrip.image.ImageLoadManager;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.request.LikeCheckRequest;
import com.organic.dogdrip.net.request.LikeRequest;
import com.organic.dogdrip.net.request.ReplyListRequest;
import com.organic.dogdrip.ui.BaseActivity;
import com.organic.dogdrip.ui.ImageDetailFragment;
import com.organic.dogdrip.ui.DripDetailActivity;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.utils.IntentMaker;
import com.organic.dogdrip.vo.drip.Drip;
import com.organic.dogdrip.vo.drip.Like;
import com.organic.dogdrip.vo.drip.LikeInfo;
import com.organic.dogdrip.vo.drip.Reply;

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
    private NetworkImageView mImageView = null;

    private Drip mDrip = null;
    private int mPagePosition = -1;

    private ArrayList<Reply> mReplyList = null;

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
        mImageView = (NetworkImageView) view.findViewById(R.id.iv_drip_background);
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
            if (!TextUtils.isEmpty(mDrip.getImageurl())) {
                mImageView.setVisibility(View.VISIBLE);
                mImageView.setImageUrl(mDrip.getImageurl(), ImageLoadManager.getImageLoader());
                mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getActivity() != null && getActivity() instanceof BaseActivity){
                            if(v != null && v instanceof ImageView && ((ImageView) v).getDrawable() != null){
                                Bitmap bm = ((BitmapDrawable)((ImageView) v).getDrawable()).getBitmap();
                                Bundle b = new Bundle();
                                b.putParcelable(Bitmap.class.getName(), bm);
                                ((BaseActivity) getActivity()).addFragment(ImageDetailFragment.class, b);
                            }
                        }
                    }
                });
            }

            mTvcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DripDetailActivity.class);
                    intent.putExtra(Drip.class.getName(), mDrip);
                    intent.putParcelableArrayListExtra(Reply.class.getName(), mReplyList);
                    IntentMaker.startActivityWithSharedTransition(getActivity(), intent,
                            new IntentMaker.SharedElemetData(mTvDrip, getString(R.string.single_shared_object_text)));
                }
            });
            requestReplyList(mDrip);
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

    private void requestReplyList(Drip drip){
        if(drip != null && getActivity() != null){
            ReplyListRequest request = new ReplyListRequest(getActivity(), onReplyListResponseListener);
            request.setDripId(drip.getId());
            request(request);
        }
    }


    private void requestLikeDrip(Drip drip) {
        if(UserInfoManager.getInstance().getUserInfo() != null){
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
            likeRequest.setLikeInfo(drip.getId(), UserInfoManager.getInstance().getUserInfo().getEmail());
            request(likeRequest);
        }else{
            //TODO : Login 확인 다이얼로그 띄우기
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


    private BaseApiResponse.OnResponseListener<ArrayList<Reply>> onReplyListResponseListener = new BaseApiResponse.OnResponseListener<ArrayList<Reply>>() {
        @Override
        public void onResponse(BaseApiResponse<ArrayList<Reply>> response) {
            if(response != null){
                if(response.getData() != null && getActivity() != null){
                    mTvcomment.setText(String.format(getString(R.string.drip_comment), response.getData().size()));
                }
            }

        }

        @Override
        public void onError(VolleyError error) {

        }
    };

}
