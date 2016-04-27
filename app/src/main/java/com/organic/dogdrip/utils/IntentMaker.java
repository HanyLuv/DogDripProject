package com.organic.dogdrip.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by kwonojin on 16. 4. 27..
 */
public class IntentMaker {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static class PairBuilder{

        ArrayList<Pair<View,String>> mPariList = new ArrayList<>();

        public static PairBuilder makeBuilder(){
            return  new PairBuilder();
        }

        PairBuilder addPair(View view, String transitionName){

        }

        private View view;
        private String transitionName;
    }


    public static Intent makeIntent(Context context, Class< ? extends Activity> cls){
        Intent intent = new Intent(context, cls);
        return intent;
    }


    public static void startActivityWithSharedTransition(Context context, Class< ? extends Activity> cls
            , SharedElementData... datas){

        if(isAboveL()){
            Intent intent = makeIntent(context, cls);
            ActivityOptions.
            context.startActivity(intent);
        }else {
        }

    }

    public static boolean isAboveL(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
