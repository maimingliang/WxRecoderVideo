package com.maiml.wechatrecodervideolibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by maimingliang on 2016/9/25.
 */
public class CircleBackgroundTextView extends TextView {

    private static final int DEFAULT_STROKE_WIDTH = 2;

    private Context mContext;
    private Paint mPaint;

    private RectF mRectF ;

    private int circleColor = 0xff00ff00;

    public CircleBackgroundTextView(Context context) {
        super(context);

        init(context);
    }


    public CircleBackgroundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleBackgroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);



    }


    private void init(Context context) {

        this.mContext = context;
        setGravity(Gravity.CENTER);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);

        mRectF = new RectF();


    }

    public void setCircleColor(int circleColor) {
         mPaint.setColor(circleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(
                DEFAULT_STROKE_WIDTH / 2f,
                DEFAULT_STROKE_WIDTH / 2f,
                canvas.getWidth() - DEFAULT_STROKE_WIDTH / 2f,
                canvas.getHeight() - DEFAULT_STROKE_WIDTH / 2f);

        canvas.drawOval(mRectF, mPaint);
    }
}
