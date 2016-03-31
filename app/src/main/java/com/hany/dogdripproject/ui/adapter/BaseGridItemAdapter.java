package com.hany.dogdripproject.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;


import com.hany.dogdripproject.R;
import com.hany.dogdripproject.ui.view.SmallItemView;

import java.util.List;

abstract public class BaseGridItemAdapter <E> extends BaseListAdapter<E, BaseGridItemAdapter.ViewHolder> {

    public interface OnClickGridItemListener <E>{
        void onClickItem(E item, int position);
        void onClickCategory(String name, int position);
    }

    private float mDispalyWidth = 0;
    private float mSmallViewWidth = 0;
    private int mViewCount = 0;

    private OnClickGridItemListener<E> mOnClickGridItemListener = null;

    public BaseGridItemAdapter(Context context, List<E> data) {
        super(context, data, R.layout.item_row_base_grid);
        mDispalyWidth = context.getResources().getDisplayMetrics().widthPixels;
        mSmallViewWidth = context.getResources().getDimension(R.dimen.small_item_view_default_width)
                + (context.getResources().getDimension(R.dimen.small_item_view_default_margin) * 2);
        mViewCount = (int) (mDispalyWidth / mSmallViewWidth);

    }

    @Override
    protected ViewHolder makeHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.smallViewsContainer = (LinearLayout) view.findViewById(R.id.layout_row_item_base_grid);
        holder.smallItemViews = new SmallItemView[mViewCount];
        for(int i = 0 ; i < mViewCount ; i ++){
            holder.smallItemViews[i] = new SmallItemView(getContext());
            holder.smallViewsContainer.addView(holder.smallItemViews[i]);
        }
        return holder;
    }

    @Override
    protected void initView(ViewHolder holder, int position) {
        int target = position * mViewCount;
        for(int i = target ; i < target + mViewCount; i ++) {
            final int pos = i;
            if (getCount() > pos) {
                holder.smallViewsContainer.setVisibility(View.VISIBLE);
                if(isCategory(pos)){
                    holder.smallItemViews[pos % mViewCount].setLargeText(getTitle(pos));
                    holder.smallItemViews[pos % mViewCount].getTitleText().setVisibility(View.GONE);
                    holder.smallItemViews[pos % mViewCount].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickGridItemListener.onClickCategory(getTitle(pos), pos);
                        }
                    });
                }else{
                    holder.smallItemViews[pos % mViewCount].setTitle(getTitle(pos));
                    holder.smallItemViews[pos % mViewCount].getLargeText().setVisibility(View.GONE);
                    holder.smallItemViews[pos % mViewCount].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickGridItemListener.onClickItem(getData().get(pos), pos);
                        }
                    });
                }
                holder.smallItemViews[pos % mViewCount].setImageUrl(getImageUrl(pos));
                holder.smallItemViews[pos % mViewCount].setBackgroundColor(getColor(pos));
            } else {
                holder.smallItemViews[pos % mViewCount].setImageUrl(null);
                holder.smallItemViews[pos % mViewCount].setTitle("");
                holder.smallItemViews[pos % mViewCount].setBackgroundColor(0);
                if(getCount() + mViewCount < pos){
                    holder.smallViewsContainer.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setOnClickGridItemListener(OnClickGridItemListener<E> ll){
        mOnClickGridItemListener = ll;
    }

    abstract protected String getTitle(int position);

    abstract protected String getImageUrl(int position);

    abstract protected boolean isCategory(int position);

    protected int getColor(int position){
        return 0;
    }

    private OnClickGridItemListener<E> onClickGridItemListener = new OnClickGridItemListener<E>() {
        @Override
        public void onClickItem(E item, int position) {
            if(mOnClickGridItemListener != null){
                mOnClickGridItemListener.onClickItem(item, position);
            }
        }

        @Override
        public void onClickCategory(String name, int position) {
            if(mOnClickGridItemListener != null){
                mOnClickGridItemListener.onClickCategory(name, position);
            }
        }
    };

    protected static class ViewHolder{
        private LinearLayout smallViewsContainer = null;
        private SmallItemView[] smallItemViews = null;
    }
}