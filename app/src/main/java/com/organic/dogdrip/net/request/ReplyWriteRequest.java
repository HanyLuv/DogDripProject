package com.organic.dogdrip.net.request;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.organic.dogdrip.Constants;
import com.organic.dogdrip.net.BaseApiResponse;
import com.organic.dogdrip.vo.drip.Reply;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by HanyLuv on 2016-03-22.
 */
public class ReplyWriteRequest extends BasicRequest<Reply>{

    private static final String API = Constants.API_SERVER_HOST + "/reply/put";

    private static final String AUTHOR = "author";
    private static final String DRIP_ID = "dripId";
    private static final String COMMENT = "comment";

    public ReplyWriteRequest(Context context, BaseApiResponse.OnResponseListener<Reply> responseListener) {
        super(context, API, responseListener);
    }

    public void setReplyInfo(String author, int dripId, String comment){
            getParams().put(AUTHOR, author);
            getParams().put(DRIP_ID, dripId +"");
            getParams().put(COMMENT, comment);
    }

    @Override
    protected Type getClassType() {
        return new TypeToken<Reply>(){}.getType();
    }
}
