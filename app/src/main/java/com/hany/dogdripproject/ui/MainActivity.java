package com.hany.dogdripproject.ui;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.entry.Drip;
import com.hany.dogdripproject.fragment.HomeFragment;
import com.hany.dogdripproject.net.NetworkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NetworkManager.getInstance().request(getApplicationContext(), NetworkManager.RequestType.DRIP_GET, null, new NetworkManager.NetworkListener() {
            @Override
            public void onResponse(JSONObject jsonObj) {
                try {
                    Gson gson = new Gson();
                    Bundle bundle = new Bundle();
                    Type type = new TypeToken<Collection<Drip>>() {}.getType();
                    JSONArray resultJsonObj = jsonObj.getJSONArray("result");
                    List<Drip> drips = gson.fromJson(resultJsonObj.toString(), type);
                    bundle.putParcelableArrayList("drips", (ArrayList) drips);
                    init(bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hany_tag", "" + error.getMessage());
            }
        });

    }


    private void init(Bundle bundle) {
        HomeFragment HomeFragment = new HomeFragment();
        HomeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.layout_main_container, HomeFragment).commit();
    }
}
