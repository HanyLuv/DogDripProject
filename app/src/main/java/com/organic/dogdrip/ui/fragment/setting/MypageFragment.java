package com.organic.dogdrip.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.organic.dogdrip.R;
import com.organic.dogdrip.image.ImageLoadManager;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.vo.user.User;

/**
 * Created by HanyLuv on 2016-04-07.
 */
public class MypageFragment extends BaseFragment {

    private NetworkImageView mUserImage = null;
    private TextView mTvUserName;
    private TextView mTvUserLevel;
    private TextView mTvUserRecommendCount;
    private TextView mTvUserDripCount;
    private Button mLogoutButton = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        mUserImage = (NetworkImageView) view.findViewById(R.id.iv_user_photo);
        mTvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mTvUserLevel = (TextView) view.findViewById(R.id.tv_user_level);
        mTvUserRecommendCount = (TextView) view.findViewById(R.id.tv_user_drip_recommend_count);
        mTvUserDripCount = (TextView) view.findViewById(R.id.tv_user_drip_count);
        mLogoutButton = (Button) view.findViewById(R.id.btn_user_drip_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoManager.getInstance().logout();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User user = UserInfoManager.getInstance().getUserInfo();
        initView(user);
    }

    private void initView(User user) {
        if (user == null) {
            return;
        }
        mTvUserName.setText(user.getNickname());
        mTvUserLevel.setText(String.valueOf(user.getPoint()));
        mUserImage.setImageUrl(user.getUserimage(), ImageLoadManager.getImageLoader());
    }


}
