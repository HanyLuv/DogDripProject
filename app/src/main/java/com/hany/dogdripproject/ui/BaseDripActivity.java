package com.hany.dogdripproject.ui;

import com.hany.dogdripproject.vo.drip.Drip;

import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-28.
 */
public class BaseDripActivity extends BaseActivity {
    /**
     * 서버에서 받아온 드립정보를 담고 있는 Activity
     * 드립에 관해 기본 기능이 제공된다.
     */
    private ArrayList<Drip> drips;

    public void setDrips(ArrayList<Drip> drips) {
        this.drips = drips;
    }

    public ArrayList<Drip> getDrips() {
        return drips;
    }
}
