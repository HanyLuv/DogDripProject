package com.hany.dogdripproject.ui.fragment.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.adapter.BaseFragmentPagerAdapter;
import com.hany.dogdripproject.ui.fragment.BaseHorizontalScrollFragment;

import java.util.List;

/**
 * Created by HanyLuv on 2016-04-07.
 */
public class FirstPageFragment extends BaseHorizontalScrollFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firstpage, container, false);
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.layout_fragment_first_page, new LoginFragment());
//        fragmentTransaction.commit();
        return view;
    }

    @Override
    protected BaseFragmentPagerAdapter makeFragmentPagerAdapter(List pageDatas) {
        return null;
    }
}
