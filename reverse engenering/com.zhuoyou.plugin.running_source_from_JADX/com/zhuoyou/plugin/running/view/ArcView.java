package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.C1680R;

public class ArcView extends View {
    private Paint arcPaint = new Paint();
    private RectF arcRect = new RectF();
    private int colorBack;
    private int colorValue;
    private float endAngel;
    private int offsetAngel;
    private float startAngel;
    private float stokeWidth;
    private float valueAngel = 0.01f;
    private float viewWdith;
    private float xdown = 0.0f;
    private float ydown = 0.0f;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, C1680R.styleable.ArcView));
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context.obtainStyledAttributes(attrs, C1680R.styleable.ArcView));
    }

    private void parseAttributes(TypedArray a) {
        this.stokeWidth = a.getDimension(0, 3.0f);
        this.offsetAngel = a.getInteger(1, 80);
        this.startAngel = (float) (a.getInteger(2, 90) + (this.offsetAngel / 2));
        this.colorBack = a.getColor(3, Color.parseColor("#08ffffff"));
        this.colorValue = a.getColor(4, -1);
        this.endAngel = (float) (360 - this.offsetAngel);
        a.recycle();
    }

    private void setupPaints() {
        this.arcPaint.setStyle(Style.STROKE);
        this.arcPaint.setStrokeWidth(this.stokeWidth);
        this.arcPaint.setStrokeCap(Cap.ROUND);
        this.arcPaint.setAntiAlias(true);
    }

    private void setupBounds() {
        this.arcRect.set(this.stokeWidth / 2.0f, this.stokeWidth / 2.0f, this.viewWdith - (this.stokeWidth / 2.0f), this.viewWdith - (this.stokeWidth / 2.0f));
    }

    public void draw(int total, int value) {
        this.valueAngel = (((float) value) / ((float) total)) * this.endAngel;
        if (this.valueAngel <= 0.0f) {
            this.valueAngel = 0.01f;
        } else if (this.valueAngel > this.endAngel) {
            this.valueAngel = this.endAngel;
        }
        postInvalidate();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWdith = (float) w;
        setupPaints();
        setupBounds();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.xdown = event.getX();
                this.ydown = event.getY();
                break;
            case 1:
                float xup = event.getX();
                float yup = event.getY();
                if (Math.abs(this.xdown - xup) < TitleBar.SHAREBTN_RIGHT_MARGIN && Math.abs(this.ydown - yup) < TitleBar.SHAREBTN_RIGHT_MARGIN && judgePointInCircle(xup, yup)) {
                    callOnClick();
                    break;
                }
        }
        return true;
    }

    private boolean judgePointInCircle(float x, float y) {
        return ((x - this.arcRect.centerX()) * (x - this.arcRect.centerX())) + ((y - this.arcRect.centerY()) * (y - this.arcRect.centerY())) < (this.arcRect.width() / 2.0f) * (this.arcRect.width() / 2.0f);
    }

    protected void onDraw(Canvas canvas) {
        this.arcPaint.setColor(this.colorBack);
        canvas.drawArc(this.arcRect, this.startAngel, this.endAngel, false, this.arcPaint);
        this.arcPaint.setColor(this.colorValue);
        canvas.drawArc(this.arcRect, this.startAngel, this.valueAngel, false, this.arcPaint);
    }
}
