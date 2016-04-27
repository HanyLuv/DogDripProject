package com.organic.dogdrip.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.organic.dogdrip.R;

/**
 * Created by kwonojin on 16. 4. 27..
 */
public class DripWriteActivity extends BaseActivity {

    private EditText etWrite;
    private Button btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_write);
        etWrite = (EditText) findViewById(R.id.et_fragment_write_drip);
        btnWrite = (Button) findViewById(R.id.btn_fragment_write_drip);
    }
}
