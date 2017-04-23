package com.sbilsky.customviewslibrary.views;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface onKeyboardHideListener {
        void onKeyboardHidden();
    }

    private onKeyboardHideListener onKeyboardHideListener;

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        int imeActions = outAttrs.imeOptions & EditorInfo.IME_MASK_ACTION;
        if ((imeActions & EditorInfo.IME_ACTION_DONE) != 0) {
            outAttrs.imeOptions ^= imeActions;
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_DONE;
        }
        if ((outAttrs.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
        return connection;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (onKeyboardHideListener != null)
                onKeyboardHideListener.onKeyboardHidden();
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setOnKeyBoardHideListener(onKeyboardHideListener onKeyboardHideListener) {
        this.onKeyboardHideListener = onKeyboardHideListener;
    }
}
