package com.hany.dogdripproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.hany.dogdripproject.net.BaseApiRequest;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;

import java.util.Objects;

/**
 * Created by kwonojin on 16. 3. 18..
 */
public class BaseFragment extends Fragment {

    protected void showToast(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    protected void request(BaseApiRequest request) {
        NetworkManager.getInstance().request(request);
    }

    /**
     * 요청 성공여부 판단
     *
     * @param response 결과객체
     * @return 성공 true, 실패 false
     */
    protected boolean isRequestSuccessfully(BaseApiResponse response) {
        if (response.getErrorCode() != 0) {
            showToast(response.getMessage());
            return false;
        }
        return true;
    }
}