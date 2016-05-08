package com.organic.dogdrip.net.request;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.vo.drip.Reply;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class ReplyListRequest extends BasicRequest<ArrayList<Reply>>{

    private static final String API = Constants.API_SERVER_HOST + "/reply/get";

    private static final String AUTHOR = "author";
    private static final String DRIP_ID = "dripId";

    public ReplyListRequest(Context context, BaseApiResponse.OnResponseListener<ArrayList<Reply>> responseListener) {
        super(context, API, responseListener);
    }

    public void setAuthor(String author){
        setDataInfo(author, 0);
    }

    public void setDripId(int id){
        setDataInfo(null, id);
    }


    public void setDataInfo(String author, int dripId){

        if(!TextUtils.isEmpty(author)){
            getParams().put(AUTHOR, author);
        }
        if(dripId > 0){
            getParams().put(DRIP_ID, dripId +"");
        }
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<ArrayList<Reply>>(){}.getType();
    }
}
