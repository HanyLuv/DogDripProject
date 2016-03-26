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

/**
 * Created by HanyLuv on 2016-03-27.
 */
public class LikeListAdapter extends BaseAdapter {

    private ArrayList<LikeInfo> mLikeInfos;
    private Context mContext;
    private LayoutInflater mInflater;


    public LikeListAdapter(Context context, ArrayList<LikeInfo> likeInfos) {
        mContext = context;
        mLikeInfos = likeInfos;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mLikeInfos.size();
    }

    @Override
    public LikeInfo getItem(int position) {
        return mLikeInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LikeInfo likeUserInfo = mLikeInfos.get(position);
        View vListItem = convertView;
        ViewHolder holder = null;

        if (vListItem == null) {
            holder = new ViewHolder();
            vListItem = mInflater.inflate(R.layout.item_likelist_row, null);
            holder.ivUserImg = (ImageView) vListItem.findViewById(R.id.iv_user);
            holder.tvUserName = (TextView) vListItem.findViewById(R.id.tv_user_name);
            vListItem.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvUserName.setText(likeUserInfo.getUser());

        return vListItem;
    }

    private class ViewHolder {
        public ImageView ivUserImg;
        public TextView tvUserName;
    }
}
