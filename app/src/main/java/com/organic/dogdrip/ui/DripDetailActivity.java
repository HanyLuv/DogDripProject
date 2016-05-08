package com.organic.dogdrip.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.organic.dogdrip.R;
import com.organic.dogdrip.vo.drip.Drip;
import com.organic.dogdrip.vo.drip.Reply;

import java.util.ArrayList;

/**
 * Created by kwonojin on 16. 5. 3..
 */
public class DripDetailActivity extends BaseActivity {

    private TextView mDripTitle = null;
    private ListView mReplyList = null;

    private Drip mDrip = null;
    private ArrayList<Reply> mReplyArrayList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drip_detail);
        initModel();
        initView();
    }

    private void initModel(){
        mDrip = getIntent().getParcelableExtra(Drip.class.getName());
        mReplyArrayList = getIntent().getParcelableArrayListExtra(Reply.class.getName());
    }

    private void initView(){
        mDripTitle = (TextView) findViewById(R.id.tv_drip_detail_title);
        mReplyList = (ListView) findViewById(R.id.lv_drip_detail_reply);
        if(mDrip != null){
            mDripTitle.setText(mDrip.getDrip());
        }
    }


}
