package com.hany.dogdripproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by kwonojin on 16. 3. 18..
 */
public class BaseFragment extends Fragment {

    protected void showToast(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
