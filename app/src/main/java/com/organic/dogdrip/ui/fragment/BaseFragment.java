package com.organic.dogdrip.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.organic.dogdrip.net.BaseApiRequest;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.NetworkManager;
import com.organic.dogdrip.vo.user.User;

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
        return getClass().getName();
    }

    public void addChildFragment(Class<? extends BaseFragment> fClss, Bundle bundle){
        addChildFragment(fClss, bundle, true);
    }

    public void addChildFragment(Class<? extends BaseFragment> fClss, Bundle bundle, boolean addBackStack) {
        BaseFragment f = null;
        try {
            f = fClss.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(f != null){
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if(bundle != null){
                f.setArguments(bundle);
            }
            if(addBackStack){
                ft.addToBackStack(f.getBackstackName());
            }
            ft.setBreadCrumbTitle(f.getFragmentTitle())
                    .replace(getChildFragmentAnchorId(), f, f.getFragmentTag())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commitAllowingStateLoss();
        }
    }

    protected int getChildFragmentAnchorId(){
        return getView().getId();
    }

    public void onUserInfoChanged(User user){

    }
}