package com.hany.dogdripproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.hany.dogdripproject.net.BaseApiRequest;
import com.hany.dogdripproject.net.NetworkManager;

/**
 * Created by kwonojin on 16. 3. 18..
 */
public class BaseFragment extends Fragment {

    protected void showToast(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void request(BaseApiRequest request){
        NetworkManager.getInstance().request(request);
    }
}
