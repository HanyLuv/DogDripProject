package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.LikeListAdapter;
import com.hany.dogdripproject.vo.drip.LikeInfo;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-27.
 */
public class LikeListFragment extends BaseFragment {
    public static String TAG = "LikeListFragment";
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_likes);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ArrayList<LikeInfo> likeList = getArguments().getParcelableArrayList("likelist");
        init(likeList);
        super.onActivityCreated(savedInstanceState);
    }

    private void init(ArrayList<LikeInfo> likeList) {
        LikeListAdapter likeListAdapter = new LikeListAdapter(getActivity(), likeList);
        mListView.setAdapter(likeListAdapter);

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public String getFragmentTitle() {
        return "목록";
    }
}
