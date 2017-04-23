package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class TranslatorPanelTouchHelperCallBack extends ItemTouchHelper.Callback {
    private AdapterSwipeListener adapterSwipeListener;

    public TranslatorPanelTouchHelperCallBack(AdapterSwipeListener adapterSwipeListener) {
        this.adapterSwipeListener = adapterSwipeListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.START | ItemTouchHelper.END);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapterSwipeListener.swipeTo(direction);
    }

}
