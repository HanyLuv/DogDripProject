package com.organic.dogdrip.ui.fragment.drip;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.organic.dogdrip.R;
import com.organic.dogdrip.manager.UserInfoManager;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.net.request.WriteDripRequest;
import com.organic.dogdrip.ui.BaseActivity;
import com.organic.dogdrip.ui.fragment.BaseFragment;
import com.organic.dogdrip.utils.AnimationUtil;
import com.organic.dogdrip.vo.drip.Drip;

/**
 * Created by HanyLuv on 2016-03-20.
 */
public class DripWriteFragment extends BaseFragment {

    public static final String TAG = DripWriteFragment.class.getName();

    private EditText etWrite;
    private Button btnWrite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        etWrite = (EditText) view.findViewById(R.id.et_fragment_write_drip);
        btnWrite = (Button) view.findViewById(R.id.btn_fragment_write_drip);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etWrite.getText().toString()) && UserInfoManager.getInstance().getUserInfo() != null) {
                    showToast(getResources().getString(R.string.write_empty_notice).toString());
                    return;
                }
                    ((BaseActivity) getActivity()).createAlertDialog(getActivity().getResources().getText(R.string.write_notice).toString(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestWriteDrip();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                }
            });
        }

    private void requestWriteDrip() {
        if(UserInfoManager.getInstance().getUserInfo() != null){
            WriteDripRequest writeRequst = new WriteDripRequest(getActivity(), new BaseApiResponse.OnResponseListener<Drip>() {
                @Override
                public void onResponse(BaseApiResponse<Drip> response) {
                    if (!isRequestSuccessfully(response)) {
                        return;
                    }
                    showToast(response.getData().getAuthor() + getResources().getString(R.string.write_welcome));
                    getFragmentManager().beginTransaction().remove(DripWriteFragment.this).commit();
                }

                @Override
                public void onError(VolleyError error) {
                    showToast(error.getMessage());
                }
            });

            //TODO : Have to fix blow codes
            String dripContent = etWrite.getText().toString();
            writeRequst.setDataInfo(UserInfoManager.getInstance().getUserInfo().getEmail(), dripContent);
            request(writeRequst);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.login_need);
            builder.setPositiveButton(getResources().getText(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserInfoManager.getInstance().sendNeedLoginBroadcast();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

}
