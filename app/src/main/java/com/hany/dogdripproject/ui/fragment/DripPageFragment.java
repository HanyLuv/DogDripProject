package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hany.dogdripproject.R;

/**
 * Created by HanyLuv on 2016-03-15.
 */
public class DripPageFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment newInstance(Bundle bundle) {
        DripPageFragment dripFragment = new DripPageFragment();
        dripFragment.setArguments(bundle);
        return dripFragment;
    }


    private TextView mTvAuthor;
    private TextView mTvDrip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drip, container, false);
        mTvAuthor = (TextView) view.findViewById(R.id.tv_author);
        mTvDrip = (TextView) view.findViewById(R.id.tv_drip);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTvAuthor.setText(getArguments().getString("author"));
        mTvDrip.setText(getArguments().getString("drip"));
    }
}
