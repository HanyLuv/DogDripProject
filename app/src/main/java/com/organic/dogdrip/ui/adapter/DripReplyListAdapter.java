package com.organic.dogdrip.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.organic.dogdrip.R;
import com.organic.dogdrip.image.ImageLoadManager;
import com.organic.dogdrip.vo.drip.LikeInfo;
import com.organic.dogdrip.vo.drip.Reply;

import java.util.List;

/**
 * Created by HanyLuv on 2016-03-27.
 */
public class DripReplyListAdapter extends BaseListAdapter<Reply, DripReplyListAdapter.ViewHolder> {


    public DripReplyListAdapter(Context context, List<Reply> data) {
        super(context, data, R.layout.item_reply_row);
    }

    @Override
    protected ViewHolder makeHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.ivUserImg = (NetworkImageView) view.findViewById(R.id.iv_item_reply_image);
        holder.tvUserName = (TextView) view.findViewById(R.id.tv_item_reply_name);
        holder.tvComment = (TextView) view.findViewById(R.id.tv_item_reply_comment);
        return holder;
    }

    @Override
    protected void initView(ViewHolder holder, int position) {
        Reply info = getItem(position);
        if(info != null){
            holder.ivUserImg.setImageUrl(info.getDrip().getUser().getUserimage(), ImageLoadManager.getImageLoader());
            holder.tvUserName.setText(info.getDrip().getUser().getNickname());
            holder.tvComment.setText(info.getComment());
        }
    }

    protected static class ViewHolder {
        public NetworkImageView ivUserImg;
        public TextView tvUserName;
        public TextView tvComment;
    }
}
