package com.organic.dogdrip.image;

import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by kwonojin on 16. 3. 30..
 */
public class ImageLoadManager {

    private RequestQueue mRequestQueue = null;
    private LruBitmapCache mBitmapCache = null;

    private ImageLoader mImageLoader = null;

    private static ImageLoadManager sInstance = null;


    private ImageLoadManager(RequestQueue requestQueue) {
        if(requestQueue == null){
            throw new IllegalArgumentException("requestQueue must be not null");
        }
        mRequestQueue = requestQueue;
        mBitmapCache = new LruBitmapCache();
        mImageLoader = new ImageLoader(mRequestQueue, mBitmapCache);
    }

    public void loadImageUrl(String url , ImageLoader.ImageListener ll){
        if(url != null && ll != null){
            mImageLoader.get(url, ll);
        }
    }

    public Bitmap getBitmapFromCache(String key){
        Bitmap bm = null;
        if(key != null){
            bm = mBitmapCache.getBitmap(key);
        }
        return bm;
    }

    public void putBitmapToCache(String key, Bitmap bm){
        if(key != null && bm != null){
            mBitmapCache.putBitmap(key, bm);
        }
    }

    public static void init(RequestQueue requestQueue){
        sInstance = new ImageLoadManager(requestQueue);
    }

    public static ImageLoadManager getInstance(){
        return sInstance;
    }

    public static ImageLoader getImageLoader(){
        return sInstance.mImageLoader;
    }

}
