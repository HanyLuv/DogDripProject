package com.organic.dogdrip.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.organic.dogdrip.R;
import com.organic.dogdrip.ui.adapter.DripReplyListAdapter;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.vo.drip.Drip;
import com.organic.dogdrip.vo.drip.Reply;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kwonojin on 16. 5. 3..
 */
public class DripReplyListFragment extends BaseFragment {

    private TextView mDripTitle = null;
    private ListView mList = null;

    private EditText mInput = null;
    private ImageView mWrite = null;

    private Drip mDrip = null;
    private ArrayList<Reply> mReplies = null;

    private DripReplyListAdapter mAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drip_reply, null);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData(mReplies);
    }

    private void initModel(){
        mDrip = getArguments().getParcelable(Drip.class.getName());
        mReplies = getArguments().getParcelableArrayList(Reply.class.getName());
    }

    private void setData(List<Reply> data){
        if(data != null && data.size() > 0){
            if(mAdapter == null){
                mAdapter = new DripReplyListAdapter(getActivity(), data);
                mList.setAdapter(mAdapter);
            }else{
                mAdapter.setData(data);
            }
        }
    }

    private void initView(View view){
        if(view != null){
            mDripTitle = (TextView) view.findViewById(R.id.tv_fragment_drip_reply_title);
            mList = (ListView) view.findViewById(R.id.lv_fragment_drip_reply);
            mInput = (EditText) view.findViewById(R.id.et_fragment_drip_reply_input);
            mWrite = (ImageView) view.findViewById(R.id.iv_fragment_drip_reply_write);
            if(mDrip != null){
                mDripTitle.setText(mDrip.getDrip());
            }
        }
    }
}
