package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.hany.dogdripproject.R;
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

    public String getFragmentTag(){
        return getClass().getSimpleName();
    }

    public String getFragmentTitle(){
        return getClass().getSimpleName();
    }

    public String getBackstackName(){
        return this.getClass().getName();
    }

    public void addChildFragment(Class<? extends BaseFragment> fClss, Bundle bundle) {
        BaseFragment f = null;
        try {
            f = fClss.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(f != null){
            BaseFragment of = (BaseFragment) getChildFragmentManager().findFragmentByTag(f.getFragmentTag());
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (of != null) {
                if(of.isDetached()){
                    ft.replace(getChildFragmentAnchorId(), of);
                    if(bundle != null){
                        of.setArguments(bundle);
                    }
                    ft.setBreadCrumbTitle(of.getFragmentTitle())
                            .addToBackStack(of.getBackstackName())
                            .commitAllowingStateLoss();
                }
                f = null;
            } else {
                ft.add(getChildFragmentAnchorId(), f, f.getFragmentTag());
                if(bundle != null){
                    f.setArguments(bundle);
                }
                ft.setBreadCrumbTitle(f.getFragmentTitle())
                        .addToBackStack(f.getBackstackName())
                        .commitAllowingStateLoss();
            }
        }
    }

    protected int getChildFragmentAnchorId(){
        return getView().getId();
    }

}