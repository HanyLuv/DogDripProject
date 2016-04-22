package com.organic.dogdrip.net;

import com.android.volley.toolbox.DiskBasedCache;

import java.io.File;

public class NetworkCache extends DiskBasedCache {

    public static final int MAX_CACHE_SIZE = 1024 * 1024 * 10;

    public NetworkCache(File dir) {
        super(dir, MAX_CACHE_SIZE);
    }
}