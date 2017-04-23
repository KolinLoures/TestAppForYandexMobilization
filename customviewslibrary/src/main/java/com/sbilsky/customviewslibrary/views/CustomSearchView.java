package com.sbilsky.customviewslibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbilsky.customviewslibrary.R;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class CustomSearchView extends LinearLayout {
    private CustomEditText customEditText;
    private View divider;
    private String hint = "Поиск по разделу";
    private AppCompatImageView appCompatImageViewClear;
    private Drawable drawableSearch = ContextCompat.getDrawable(getContext(), R.drawable.ic_search_black_24dp);
    private Drawable drawableClear = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp);
    private float drawablePaddingLeft = 16.0f;
    private float drawablePaddingRight = 16.0f;
    private int lineColorInactive = Color.GRAY;
    private int lineColorActive = Color.YELLOW;
    private OnTextChangedListener onTextChangedListener;


    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }

    public CustomSearchView(Context context) {
        super(context);
        init();
    }

    public CustomSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomSearchView,
                0, 0);

        try {
            hint = a.getString(R.styleable.CustomSearchView_hint);
            if (a.getDrawable(R.styleable.CustomSearchView_search_drawable) != null) {
                drawableSearch = a.getDrawable(R.styleable.CustomSearchView_search_drawable);
            }
            if (a.getDrawable(R.styleable.CustomSearchView_search_drawable) != null) {
                drawableClear = a.getDrawable(R.styleable.CustomSearchView_clear_drawable);
            }
            drawablePaddingLeft = a.getDimension(R.styleable.CustomSearchView_drawable_padding_left, drawablePaddingLeft);
            drawablePaddingRight = a.getDimension(R.styleable.CustomSearchView_drawable_padding_right, drawablePaddingRight);
            lineColorInactive = a.getColor(R.styleable.CustomSearchView_line_color_inactive, lineColorInactive);
            lineColorActive = a.getColor(R.styleable.CustomSearchView_line_color_active, lineColorActive);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.search_view_layout, this);
        this.customEditText = (CustomEditText) findViewById(R.id.custom_edit_text);
        customEditText.setOnKeyBoardHideListener(new CustomEditText.onKeyboardHideListener() {
            @Override
            public void onKeyboardHidden() {
                requestFocus();
            }
        });
        customEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    divider.setBackgroundColor(lineColorActive);
                } else {
                    divider.setBackgroundColor(lineColorInactive);
                }
            }
        });
        customEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //ignore
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    appCompatImageViewClear.setVisibility(VISIBLE);
                } else {
                    appCompatImageViewClear.setVisibility(GONE);
                }
                if (onTextChangedListener != null)
                    onTextChangedListener.onTextChanged(editable.toString());
            }
        });
        customEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE) {
                    requestFocus();
                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                }
                return false;
            }
        });
        customEditText.setHint(hint);
        this.divider = findViewById(R.id.divider);
        divider.setBackgroundColor(lineColorInactive);
        AppCompatImageView appCompatImageViewSearch = (AppCompatImageView) findViewById(R.id.image_view_search);
        appCompatImageViewSearch.setImageDrawable(drawableSearch);
        appCompatImageViewSearch.setPadding((int) drawablePaddingLeft, 0, (int) drawablePaddingRight, 0);
        appCompatImageViewSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                customEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) customEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(customEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        appCompatImageViewClear = (AppCompatImageView) findViewById(R.id.image_view_clear);
        appCompatImageViewClear.setPadding((int) drawablePaddingLeft, 0, (int) drawablePaddingRight, 0);
        appCompatImageViewClear.setVisibility(GONE);
        appCompatImageViewClear.setImageDrawable(drawableClear);
        appCompatImageViewClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clearText();
            }
        });
    }

    public void setOnTextChangeListener(OnTextChangedListener onTextChangeListener) {
        this.onTextChangedListener = onTextChangeListener;
    }

    public String getText() {
        if (customEditText.getText() != null) {
            return customEditText.getText().toString();
        } else {
            return "";
        }
    }

    public void clearText() {
        customEditText.setText("");
    }

}
