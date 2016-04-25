package com.organic.dogdrip.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.organic.dogdrip.R;

/**
 * Created by kwonojin on 16. 4. 26..
 */
public class FootProgress extends View{

    private static final String TAG = "FootProgress";


    public FootProgress(Context context) {
        super(context);
    }

    public FootProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FootProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        setBackgroundResource(R.drawable.loading_dog_foot);
        ((AnimationDrawable)getBackground()).start();
    }
}
