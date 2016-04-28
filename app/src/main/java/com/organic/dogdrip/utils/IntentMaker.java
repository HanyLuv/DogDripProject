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

    public static class SharedElemetData{
        private View view = null;
        private String transitionName = null;

        public SharedElemetData(View view, String transitionName){
            this.view = view;
            this.transitionName = transitionName;
        }

        public Pair<View, String> toPair(){
            return new Pair<>(view, transitionName);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static class PairBuilder{

        ArrayList<Pair<View,String>> mPariList = new ArrayList<>();

        public static PairBuilder makeBuilder(){
            return  new PairBuilder();
        }

        public PairBuilder addPair(View view, String transitionName){
            mPariList.add(new Pair<View, String>(view, transitionName));
            return this;
        }

        public PairBuilder addPair(Pair<View, String> pair){
            mPariList.add(pair);
            return this;
        }

        public Pair<View, String>[] build(){
            Pair<View, String>[] pairs = new Pair[mPariList.size()];
            mPariList.toArray(pairs);
            return pairs;
        }
    }


    public static Intent makeIntent(Context context, Class< ? extends Activity> cls){
        Intent intent = new Intent(context, cls);
        return intent;
    }


    public static void startActivityWithSharedTransition(Activity activity, Class< ? extends Activity> cls
            , SharedElemetData... datas){
        if(isAboveL()){
            PairBuilder builder = PairBuilder.makeBuilder();
            for(SharedElemetData data : datas){
                builder.addPair(data.toPair());
            }
            Intent intent = makeIntent(activity, cls);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, builder.build());
            activity.startActivity(intent, options.toBundle());
        }else {
            Intent intent = makeIntent(activity, cls);
            activity.startActivity(intent);
        }
    }

    public static void startActivityWithSharedTransition(Activity activity, Class< ? extends Activity> cls
            , PairBuilder builder){
        if(isAboveL()){
            Intent intent = makeIntent(activity, cls);
            ActivityOptions.makeSceneTransitionAnimation(activity, builder.build());
            activity.startActivity(intent);
        }else {
            Intent intent = makeIntent(activity, cls);
            activity.startActivity(intent);
        }
    }

    public static boolean isAboveL(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
