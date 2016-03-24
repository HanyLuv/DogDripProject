package com.hany.dogdripproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
                String dripContent = etWrite.getText().toString();
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
                /**
                 * api 정의문서보면
                 * author 가 존재하는 회원이어야한다고 하셨는데
                 * 조회 api는 따로 주실예정이신거죠?
                 * 그렇담 일단 그냥 무조건 등록되겠금
                 * author 는 admin으로 해놓겠습니다 >-<
                 */
                writeRequst.putParam(Constants.PARAM_AUTHOR, "admin");
                writeRequst.putParam(Constants.PARAM_DRIP, dripContent);

                request(writeRequst);
            }
        });
    }

}
