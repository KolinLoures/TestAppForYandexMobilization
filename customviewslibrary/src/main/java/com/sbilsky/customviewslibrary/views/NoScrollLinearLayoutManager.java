package com.sbilsky.customviewslibrary.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class NoScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
