package com.lemon.cx.histogra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.ArrayList;
import java.util.Iterator;

public class MiHistogramSportView extends View {
    private static final float SPORT_DURATION = 1440.0f;
    private int colorSelect = Color.parseColor("#80b9e4ff");
    private int colorUnselect = Color.parseColor("#b9e4ff");
    private boolean ifUp = true;
    private ArrayList<MiHistogramSportData> mSportList = new ArrayList();
    private int mViewHeight;
    private int mViewWidth;
    private int maxValue;
    private Paint paintColumnar;
    private OnHistogramClickListener<MiHistogramSportData> sportListener;
    private float touchX = -1.0f;

    public MiHistogramSportView(Context context) {
        super(context);
        init();
    }

    public MiHistogramSportView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setColorSelect(int colorSelect) {
        this.colorSelect = colorSelect;
    }

    public void setColorUnselect(int colorUnselect) {
        this.colorUnselect = colorUnselect;
    }

    public void setSportListener(OnHistogramClickListener<MiHistogramSportData> listener) {
        this.sportListener = listener;
    }

    public void setSportData(ArrayList<MiHistogramSportData> data) {
        if (!(data == null || data.isEmpty())) {
            this.mSportList = data;
            Iterator it = this.mSportList.iterator();
            while (it.hasNext()) {
                MiHistogramSportData tmp = (MiHistogramSportData) it.next();
                if (tmp.getStep() > this.maxValue) {
                    this.maxValue = tmp.getStep();
                }
            }
        }
        if (this.maxValue == 0) {
            this.maxValue = 1;
        } else {
            this.maxValue = (int) (((double) this.maxValue) * 1.1d);
        }
        invalidate();
    }

    private void init() {
        this.paintColumnar = new Paint();
        this.paintColumnar.setColor(this.colorUnselect);
        this.paintColumnar.setStyle(Style.FILL);
        this.paintColumnar.setAntiAlias(true);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSportView(canvas);
    }

    public void drawSportView(Canvas canvas) {
        RectF rect = new RectF();
        if (this.mSportList != null && !this.mSportList.isEmpty()) {
            Iterator it = this.mSportList.iterator();
            while (it.hasNext()) {
                MiHistogramSportData tmp = (MiHistogramSportData) it.next();
                float startX = (((float) tmp.getStartTime()) / SPORT_DURATION) * ((float) this.mViewWidth);
                float endX = (((float) tmp.getEndTime()) / SPORT_DURATION) * ((float) this.mViewWidth);
                if (startX > this.touchX || this.touchX > endX || this.sportListener == null || this.ifUp) {
                    this.paintColumnar.setColor(this.colorUnselect);
                } else {
                    this.paintColumnar.setColor(this.colorSelect);
                    this.sportListener.getData(tmp);
                }
                rect.set(startX, ((float) this.mViewHeight) * (1.0f - (((float) tmp.getStep()) / ((float) this.maxValue))), endX, (float) this.mViewHeight);
                canvas.drawRect(rect, this.paintColumnar);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.ifUp = false;
                this.touchX = event.getX();
                invalidate();
                break;
            case 1:
                this.ifUp = true;
                if (this.sportListener != null) {
                    this.sportListener.onTouchUp();
                }
                invalidate();
                break;
            case 2:
                this.touchX = event.getX();
                invalidate();
                break;
        }
        return true;
    }
}
