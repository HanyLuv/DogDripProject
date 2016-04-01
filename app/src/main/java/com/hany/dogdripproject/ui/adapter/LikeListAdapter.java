package com.hany.dogdripproject.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.BaseActivity;
import com.hany.dogdripproject.vo.drip.LikeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanyLuv on 2016-03-27.
 */
public class LikeListAdapter extends BaseListAdapter<LikeInfo, LikeListAdapter.ViewHolder> {


    public LikeListAdapter(Context context, List<LikeInfo> data) {
        super(context, data, R.layout.item_likelist_row);
    }

    @Override
    protected ViewHolder makeHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.ivUserImg = (ImageView) view.findViewById(R.id.iv_user);
        holder.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        return holder;
    }

    @Override
    protected void initView(ViewHolder holder, int position) {
        LikeInfo info = getItem(position);
        if(info != null){
            holder.tvUserName.setText(info.getUser());
        }
    }

    protected static class ViewHolder {
        public ImageView ivUserImg;
        public TextView tvUserName;
    }
}
