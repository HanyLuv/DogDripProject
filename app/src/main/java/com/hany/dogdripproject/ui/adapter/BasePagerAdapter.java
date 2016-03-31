package com.hany.dogdripproject.ui.adapter;

import android.support.v4.view.PagerAdapter;

/**
 * Created by kwonojin on 15. 8. 31..
 */

abstract public class BasePagerAdapter extends PagerAdapter{

    protected static final int INVALID_VALUE = -1;

    public abstract Object getItem(int position);
}
