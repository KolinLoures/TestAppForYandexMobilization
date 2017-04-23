package com.sbilsky.yandexacademytestapplicationtranslator.translator.translator.recycler_view_helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;

/**
 * @author Cвятослав Бильский s.bislky
 */

public class BorderItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paintBorder;
    private int color = Color.GRAY;
    private float boarderWidth = 10;

    public BorderItemDecoration(float boarderWidth) {
        this.boarderWidth = boarderWidth;
        initPaint();
    }

    private void initPaint() {
        paintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBorder.setColor(color);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeWidth(boarderWidth);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        c.drawRect(0, 0, c.getWidth(), c.getHeight(), paintBorder);
    }

    public void setBoarderColor(int color) {
        this.color = color;
        paintBorder.setColor(color);
    }
}
