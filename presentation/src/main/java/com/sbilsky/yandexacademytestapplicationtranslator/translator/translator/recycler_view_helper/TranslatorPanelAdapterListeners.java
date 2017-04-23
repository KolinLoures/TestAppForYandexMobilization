package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper;

/**
 * @author Cвятослав Бильский s.bislky
 */

public interface TranslatorPanelAdapterListeners {
    interface OnPositionChangeListener {
        void onPositionBySwipeChanged(int position);
    }

    interface OnClickItemButtonListener {
        void onClearButtonClick();
    }

    interface OnTextChangeListener {
        void onTextChanged(String text);
    }

    interface OnKeyboardHiddenListener {
        void onKeyboardHidden();
    }

    interface OnEditTextFocusChangedListener {
        void onFocusChanged(boolean isFocus);
    }
}
