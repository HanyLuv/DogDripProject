package com.organic.dogdrip.ui.fragment.drip;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.organic.dogdrip.R;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.request.ReplyWriteRequest;
import com.organic.dogdrip.ui.BaseActivity;
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

    private InputMethodManager mImm = null;

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
        mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private void setData(List<Reply> data){
        if(data != null){
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

            mWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInput.getText().toString().length() > 0){
                        if(getActivity() != null && getActivity() instanceof BaseActivity){
                            ((BaseActivity) getActivity()).createAlertDialog(getString(R.string.write_notice), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ReplyWriteRequest request = new ReplyWriteRequest(getActivity(), replyOnResponseListener);
                                    request.setReplyInfo(UserInfoManager.getInstance().getUserInfo().getEmail(),
                                            mDrip.getId(),
                                            mInput.getText().toString());
                                    request(request);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                            mImm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
                        }
                    }
                }
            });
        }
    }

    private BaseApiResponse.OnResponseListener<Reply> replyOnResponseListener = new BaseApiResponse.OnResponseListener<Reply>() {
        @Override
        public void onResponse(BaseApiResponse<Reply> response) {
            if(response != null && isRequestSuccessfully(response)){
                if(response.getData() != null && mAdapter != null){
                    mAdapter.getData().add(response.getData());
                    mAdapter.notifyDataSetChanged();
                    showToast("댓글 등록 완료");
                    mInput.setText("");
                    for(Fragment f : getFragmentManager().getFragments()){
                        if(f instanceof DripPageFragment){
                            ((DripPageFragment) f).updateCommentCount();
                        }
                    }
                }
            }

        }

        @Override
        public void onError(VolleyError error) {

        }
    };

}
