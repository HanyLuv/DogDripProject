package com.organic.dogdrip.ui.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by kwonojin on 15. 8. 31..
 */

/**
 *
 * @param <T> List에 사용할 Data type을 Subclass에서 정의한다.
 * @param <H> ViewHolder로 사용할 Class를 Subclass에서 정의 한다.
 */
abstract public class BaseListAdapter <T, H> extends BaseAdapter{

    protected static final int INVALID_VALUE = -1;

    private List<T> mData = null;
    /**
     * 선택 항목을 체크하기 위한 BooleanArray
     */
    private SparseBooleanArray mSelectedArray = null;

    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private H mHolder = null;
    private int mResourceId = INVALID_VALUE;

    public BaseListAdapter(Context context, List<T> data, int resouceId){
        mContext = context;
        mData = data;
        mResourceId = resouceId;
        mSelectedArray = new SparseBooleanArray();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mData;
    }

    @Override
    public T getItem(int position) {
        if(mData.size() > position){
            return mData.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 특정 위치의 선택 여부를 확인 한다
     * @param position
     * @return
     */
    public boolean isSelected(int position){
        return mSelectedArray.get(position);
    }

    /**
     * @return 생성시 전달받은 resourceId를 전달한다
     */
    public int getResourceId() {
        return mResourceId;
    }

    /**
     * 특정 위치의 선택 여부를 toggle할 때 사용된다
     * @param position
     */
    public void toggleSelected(int position){
        selectItem(position, !mSelectedArray.get(position));
    }

    /**
     * 지정된 position 의 선택 여부를 SparseBooleanArray에 저장한다
     * @param position
     * @param isSelected
     */
    public void selectItem(int position, boolean isSelected){
        if(isSelected){
            mSelectedArray.put(position, isSelected);
        }else{
            mSelectedArray.delete(position);
        }
    }

    /**
     * SparseBooleanArray의 값들을 초기화한다
     */
    public void clearSelections(){
        mSelectedArray.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(mResourceId, parent, false);
            mHolder = makeHolder(convertView);
            convertView.setTag(mHolder);
        }else{
            mHolder = (H) convertView.getTag();
            if(mHolder == null){
                mHolder = makeHolder(convertView);
                convertView.setTag(mHolder);
            }
        }
        initView(mHolder, position);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public SparseBooleanArray getSelectedBooleanArray(){
        return mSelectedArray;
    }

    /**
     * @return 생성시 전달받은 Context를 전달
     */
    public Context getContext(){
        return mContext;
    }

    /**
     * @return 생성자에서 생성한 LayoutInflater를 전달
     */
    public LayoutInflater getLayoutInflater(){
        return mInflater;
    }

    /**
     * {@link BaseListAdapter#getView(int, View, ViewGroup)}
     * Subclass에서 정의하는 ViewHolder를 생성한다
     * @param view getView의 convertView가 전달된다
     * @return
     */
    protected abstract H makeHolder(View view);

    /**
     *{@link BaseListAdapter#getView(int, View, ViewGroup)}
     * @param holder makeHolder로 인해 생성된 holder가 전달된다
     * @param position 다시 그려질 view의 position이 전달된다
     */
    protected abstract void initView(H holder, int position);

    protected H getViewHolder(){
        return mHolder;
    }
}

