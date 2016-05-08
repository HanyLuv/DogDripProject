package com.organic.dogdrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.organic.dogdrip.R;
import com.organic.dogdrip.ui.view.DetailPhotoView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by HanyLuv on 2016-05-09.
 */
public class ImageDetailActivity extends BaseActivity {


    private DetailPhotoView mDetailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image_view);
        mDetailImageView = (DetailPhotoView) findViewById(R.id.im_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        PhotoViewAttacher attacher = new PhotoViewAttacher(mDetailImageView);

        if (!TextUtils.isEmpty(imageUrl)) {
            mDetailImageView.setImageURI(imageUrl, attacher);
        }

    }

}
