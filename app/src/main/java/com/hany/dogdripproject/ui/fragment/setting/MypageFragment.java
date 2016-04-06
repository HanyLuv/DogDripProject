package com.hany.dogdripproject.ui.fragment.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;
import com.hany.dogdripproject.vo.user.User;

import java.util.List;

/**
 * Created by HanyLuv on 2016-04-07.
 */
public class MypageFragment extends BaseHorizontalScrollFragment {

    private TextView mTvUserName;
    private TextView mTvUserLevel;
    private TextView mTvUserRecommendCount;
    private TextView mTvUserDripCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);
        mTvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mTvUserLevel = (TextView) view.findViewById(R.id.tv_user_level);
        mTvUserRecommendCount = (TextView) view.findViewById(R.id.tv_user_drip_recommend_count);
        mTvUserDripCount = (TextView) view.findViewById(R.id.tv_user_drip_count);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,InputMethodManager.HIDE_NOT_ALWAYS);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User user = (User) getArguments().get("user_login_info");
        initView(user);
    }

    private void initView(User user) {
        if (user == null) {
            return;
        }
        mTvUserName.setText(user.getNickname());
        mTvUserLevel.setText(String.valueOf(user.getPoint()));
    }


    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List pageDatas) {
        return null;
    }
}
