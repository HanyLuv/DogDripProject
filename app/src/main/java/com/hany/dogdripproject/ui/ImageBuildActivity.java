package com.hany.dogdripproject.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * Created by kwonojin on 16. 4. 1..
 */
public class ImageBuildActivity extends BaseActivity {

    private static final String TAG  = "ImageBuildActivity";

    private static final int REQUEST_CODE_SELECT_PICTURE = 0x54;

    private static final String GET_CONTENT_TITLE = "Select Pictrue";
    private static final String CONTENT_MIME_TYPE = "image/*";

    private Bitmap mContainerBitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setType(CONTENT_MIME_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, GET_CONTENT_TITLE), REQUEST_CODE_SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SELECT_PICTURE :
                data.getData();

                break;
        }
    }
}
