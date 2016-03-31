package com.hany.dogdripproject.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.fivetrue.dotopguide.R;
import com.fivetrue.dotopguide.network.NetworkInstance;

/**
 * Created by kwonojin on 16. 2. 17..
 */
public class SmallItemView extends RelativeLayout {

    private static final int INVALID_VALUE = -1;
    private RelativeLayout mSmallView = null;
    private FrameLayout mTopLayout = null;
    private FrameLayout mBottomLayout = null;
    private NetworkImageView mImageView = null;
    private TextView mTitleText = null;
    private TextView mLargeText = null;

    private float mTopHeight = INVALID_VALUE;
    private float mBottomHeight = INVALID_VALUE;
    private float mWidth = INVALID_VALUE;
    private float mMargin = INVALID_VALUE;
    private int mColor = INVALID_VALUE;

    public SmallItemView(Context context) {
        super(context);
        initView(context);
    }

    public SmallItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeset(context, attrs);
        initView(context);
    }

    private void initAttributeset(Context context, AttributeSet attrs){
        Resources res = context.getResources();
        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmallItemView);
            mTopHeight = a.getDimension(R.styleable.SmallItemView_topHeight, res.getDimension(R.dimen.small_item_view_default_top_height));
            mBottomHeight = a.getDimension(R.styleable.SmallItemView_bottomHeight, res.getDimension(R.dimen.small_item_view_default_bottom_height));
            mWidth = a.getDimension(R.styleable.SmallItemView_width, res.getDimension(R.dimen.small_item_view_default_width));
            mMargin = a.getDimension(R.styleable.SmallItemView_margin, res.getDimension(R.dimen.small_item_view_default_margin));
            mColor = a.getColor(R.styleable.SmallItemView_smallviewColor, Color.TRANSPARENT);
            a.recycle();
        }
    }



    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.small_item_view, this, true);
        mImageView = (NetworkImageView) findViewById(R.id.iv_small_item);
        mTitleText = (TextView) findViewById(R.id.tv_small_item_title);
        mLargeText = (TextView) findViewById(R.id.tv_small_item_center);

        mSmallView = (RelativeLayout) findViewById(R.id.layout_small_view);
        mTopLayout = (FrameLayout) findViewById(R.id.layout_small_item_view_top);
        mBottomLayout = (FrameLayout) findViewById(R.id.layout_small_item_view_bottom);

        if(mColor > INVALID_VALUE){
            mSmallView.setBackgroundColor(mColor);
        }

        if(mWidth > INVALID_VALUE){
            mSmallView.getLayoutParams().width = (int) mWidth;
        }
        if(mMargin > INVALID_VALUE){
            ((LayoutParams)mSmallView.getLayoutParams()).leftMargin = (int)mMargin;
            ((LayoutParams)mSmallView.getLayoutParams()).topMargin = (int)mMargin;
            ((LayoutParams)mSmallView.getLayoutParams()).bottomMargin = (int)mMargin;
            ((LayoutParams)mSmallView.getLayoutParams()).rightMargin = (int)mMargin;
        }

        if(mTopHeight > INVALID_VALUE){
            mTopLayout.getLayoutParams().height = (int) mTopHeight;
        }

        if(mBottomHeight > INVALID_VALUE){
            mBottomLayout.getLayoutParams().height = (int) mBottomHeight;
        }

        mTitleText.setVisibility(View.GONE);
    }

    public void setImageUrl(String url){
        if(mImageView != null){
            mImageView.setImageUrl(url, NetworkInstance.getInstance().getImageLoader());
        }
    }

    public void setTitle(String title){
        if(title != null && mTitleText != null){
            mTitleText.setVisibility(View.VISIBLE);
            mTitleText.setText(title);
        }
    }

    public void setLargeText(String text){
        if(text != null && mLargeText != null){
            mLargeText.setVisibility(View.VISIBLE);
            mLargeText.setText(text);
        }
    }

    public NetworkImageView getImageView(){
        return mImageView;
    }

    public TextView getTitleText(){
        return mTitleText;
    }

    public TextView getLargeText(){
        return mLargeText;
    }

    @Override
    public void setBackgroundColor(int color) {
        mSmallView.setBackgroundColor(color);
    }

}
