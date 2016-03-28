package com.hany.dogdripproject.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by kwonojin on 16. 3. 28..
 */
public class GCMService extends IntentService {

    private static final String TAG = "GCMService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GCMService(String name) {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent != null){
            Bundle extra = intent.getExtras();
        }

    }
}
