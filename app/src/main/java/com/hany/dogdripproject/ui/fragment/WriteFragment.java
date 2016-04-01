package com.hany.dogdripproject.ui.fragment;

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
import com.hany.dogdripproject.Constants;
import com.hany.dogdripproject.R;
import com.hany.dogdripproject.net.BaseApiResponse;
import com.hany.dogdripproject.net.NetworkManager;
import com.hany.dogdripproject.net.request.WriteRequst;
import com.hany.dogdripproject.ui.BaseActivity;
import com.hany.dogdripproject.vo.drip.Drip;

/**
 * Created by HanyLuv on 2016-03-20.
 */
public class WriteFragment extends BaseFragment {

    private EditText etWrite;
    private Button btnWrite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        etWrite = (EditText) view.findViewById(R.id.et_write);
        btnWrite = (Button) view.findViewById(R.id.btn_write);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(etWrite.getText().toString())) {
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
        WriteRequst writeRequst = new WriteRequst(getActivity(), new BaseApiResponse.OnResponseListener<Drip>() {
            @Override
            public void onResponse(BaseApiResponse<Drip> response) {
                if (!isRequestSuccessfully(response)) {
                    return;
                }
                showToast(response.getData().getAuthor() + getResources().getString(R.string.write_welcome));
            }

            @Override
            public void onError(VolleyError error) {
                showToast(error.getMessage());
            }
        });

        //TODO : Have to fix blow codes
        String dripContent = etWrite.getText().toString();
//            writeRequst.putParam(Constants.PARAM_AUTHOR, "admin");
//            writeRequst.putParam(Constants.PARAM_DRIP, dripContent);
            request(writeRequst);
    }

}
