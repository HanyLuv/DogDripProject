package com.organic.dogdrip.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.organic.dogdrip.R;

/**
 * Created by kwonojin on 16. 5. 3..
 */
public class DripDetailActivity extends BaseActivity {

    private TextView mDripTitle = null;
    private ListView mReplyList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drip_detail);
        mDripTitle = (TextView) findViewById(R.id.tv_drip_detail_title);
        mReplyList = (ListView) findViewById(R.id.lv_drip_detail_reply);
    }
}
