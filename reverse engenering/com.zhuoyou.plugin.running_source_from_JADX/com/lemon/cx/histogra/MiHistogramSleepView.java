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
import com.lemon.cx.micolumnar.Tools;
import java.util.List;

public class MiHistogramSleepView extends View {
    private int colorDsellpSelect = Color.parseColor("#809d73f1");
    private int colorDsellpUnselect = Color.parseColor("#9d73f1");
    private int colorLsellpSelect = Color.parseColor("#80c094fd");
    private int colorLsellpUnselect = Color.parseColor("#c094fd");
    private boolean ifUp = true;
    private List<MiHistogramSleepData> mSleepList;
    private int mViewHeight;
    private int mViewWidth;
    private int padding;
    private Paint paintColumnar;
    private OnHistogramClickListener<MiHistogramSleepData> sleepListener;
    private float sleepTotal = 1440.0f;
    private int space;
    private float touchX = -1.0f;

    public MiHistogramSleepView(Context context) {
        super(context);
        init();
    }

    public MiHistogramSleepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setColorDsellpUnselect(int colorDsellpUnselect) {
        this.colorDsellpUnselect = colorDsellpUnselect;
    }

    public void setColorDsellpSelect(int colorDsellpSelect) {
        this.colorDsellpSelect = colorDsellpSelect;
    }

    public void setColorLsellpUnselect(int colorLsellpUnselect) {
        this.colorLsellpUnselect = colorLsellpUnselect;
    }

    public void setColorLsellpSelect(int colorLsellpSelect) {
        this.colorLsellpSelect = colorLsellpSelect;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setSleepListener(OnHistogramClickListener<MiHistogramSleepData> listener) {
        this.sleepListener = listener;
    }

    public void setSleepData(List<MiHistogramSleepData> data) {
        if (!(data == null || data.isEmpty())) {
            this.mSleepList = data;
            this.sleepTotal = (float) (((MiHistogramSleepData) this.mSleepList.get(this.mSleepList.size() - 1)).getEndTime() - ((MiHistogramSleepData) this.mSleepList.get(0)).getStartTime());
            if (this.sleepTotal < 0.0f) {
                this.sleepTotal += 1440.0f;
            }
        }
        invalidate();
    }

    private void init() {
        this.padding = Tools.dip2px(getContext(), 6.0f);
        this.space = Tools.dip2px(getContext(), 1.0f);
        this.paintColumnar = new Paint();
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
        drawSleepView(canvas);
    }

    public void drawSleepView(Canvas canvas) {
        RectF rect = new RectF();
        if (this.mSleepList != null && !this.mSleepList.isEmpty()) {
            int totalSpace = (this.space * (this.mSleepList.size() - 1)) + (this.padding * 2);
            for (int i = 0; i < this.mSleepList.size(); i++) {
                MiHistogramSleepData tmp = (MiHistogramSleepData) this.mSleepList.get(i);
                int startTimeDif = tmp.getStartTime() - ((MiHistogramSleepData) this.mSleepList.get(0)).getStartTime();
                int endTineDif = tmp.getEndTime() - ((MiHistogramSleepData) this.mSleepList.get(0)).getStartTime();
                if (startTimeDif < 0) {
                    startTimeDif += 1440;
                }
                if (endTineDif < 0) {
                    endTineDif += 1440;
                }
                float startX = (((((float) startTimeDif) / this.sleepTotal) * ((float) (this.mViewWidth - totalSpace))) + ((float) this.padding)) + ((float) (this.space * i));
                float endX = (((((float) endTineDif) / this.sleepTotal) * ((float) (this.mViewWidth - totalSpace))) + ((float) this.padding)) + ((float) (this.space * i));
                if (startX > this.touchX || this.touchX > endX || this.sleepListener == null || this.ifUp) {
                    this.paintColumnar.setColor(tmp.isDeep() ? this.colorDsellpUnselect : this.colorLsellpUnselect);
                } else {
                    this.sleepListener.getData(tmp);
                    this.paintColumnar.setColor(tmp.isDeep() ? this.colorDsellpSelect : this.colorLsellpSelect);
                }
                rect.set(startX, ((float) this.mViewHeight) * (tmp.isDeep() ? 0.3f : 0.5f), endX, (float) this.mViewHeight);
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
                if (this.sleepListener != null) {
                    this.sleepListener.onTouchUp();
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
