package com.hany.dogdripproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by HanyLuv on 2016-03-15.
 */
public class DripPageFragment extends Fragment {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment newInstance(Context context, Bundle bundle) {
        DripPageFragment dripFragment = new DripPageFragment();
        dripFragment.setContext(context);
        dripFragment.setArguments(bundle);
        return dripFragment;
    }

    public void setContext(Context context) {
        mContext = context;
    }


}
