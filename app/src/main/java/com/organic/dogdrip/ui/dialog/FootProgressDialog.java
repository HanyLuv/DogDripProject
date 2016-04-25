package com.organic.dogdrip.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.organic.dogdrip.R;

/**
 * Created by kwonojin on 16. 4. 26..
 */
public class FootProgressDialog extends Dialog {

    private TextView mTvMessage = null;
    private ProgressBar mPbProgress = null;

    public FootProgressDialog(Context context) {
        this(context, android.R.style.Theme_Translucent_NoTitleBar);
        setContentView(R.layout.dialog_foot_progress);
    }

    public FootProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_foot_progress);
    }

    protected FootProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.dialog_foot_progress);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTvMessage = (TextView) findViewById(R.id.tv_dialog_foot_progress_message);
        mPbProgress = (ProgressBar) findViewById(R.id.pb_dialog_foot_progress_bar);
        setCanceledOnTouchOutside(false);
        setCancelable(false);


    }
}
