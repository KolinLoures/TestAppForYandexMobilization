package com.sbilsky.yandexacademytestapplicationtranslator.simpleMVP;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import java.lang.ref.WeakReference;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class AbstractPresenter<ViewType> {
    private WeakReference<ViewType> viewRef;
    private WeakReference<ViewType> view;

    @UiThread
    public void attachView(ViewType view) {
        this.view = new WeakReference<>(view);
    }

    @UiThread
    public void detachView() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }

    @UiThread
    protected boolean isViewAttached() {
        return view != null && view.get() != null;
    }

    @UiThread
    @Nullable
    protected ViewType getView() {
        return view == null ? null : view.get();
    }
}
