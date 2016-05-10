package com.organic.dogdrip.image;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Fivetrue on 2016-01-27.
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache{
    public static final int MAX_CACHE_SIZE = 1024 * 1024 * 20;

    public LruBitmapCache() {
        super(MAX_CACHE_SIZE);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
