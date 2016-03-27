package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.fragment.HomeFragment;
import com.hany.dogdripproject.ui.fragment.JoinFragment;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.DripListRequest;
import com.hany.dogdripproject.ui.fragment.WriteFragment;
import com.hany.dogdripproject.vo.drip.Drip;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportFragmentManager().beginTransaction().add(getFragmentAchorViewId(), new WriteFragment()).commit();
        DripListRequest request = new DripListRequest(this, new BaseApiResponse.OnResponseListener<ArrayList<Drip>>() {
            @Override
            public void onResponse(BaseApiResponse<ArrayList<Drip>> response) {
                if(response.getData()!=null) {
                    setDrips(response.getData());
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("drips", response.getData());
                    init(bundle);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
        NetworkManager.getInstance().request(request);
    }

    private void init(Bundle bundle) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(getFragmentAchorViewId(), homeFragment).commit();
    }

}
