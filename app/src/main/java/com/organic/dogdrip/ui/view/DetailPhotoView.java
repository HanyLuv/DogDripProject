package com.organic.dogdrip.ui.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.organic.dogdrip.image.ImageLoadManager;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by HanyLuv on 2016-05-09.
 */
public class DetailPhotoView extends PhotoView {
    public DetailPhotoView(Context context) {
        super(context);
    }

    public DetailPhotoView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public DetailPhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    public void setImageURI(String uri, final PhotoViewAttacher attacher) {
        if (!TextUtils.isEmpty(uri) && attacher != null) {
            ImageLoadManager.getImageLoader().get(uri, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        setImageBitmap(response.getBitmap());
                        attacher.update();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }
}
