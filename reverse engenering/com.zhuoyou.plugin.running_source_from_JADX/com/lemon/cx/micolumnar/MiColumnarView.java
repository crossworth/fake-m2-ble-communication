package com.lemon.cx.micolumnar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.List;

public class MiColumnarView<T extends MiColumnarData> extends View {
    private static final String TAG = "MiColumnarView";
    public static final int TYPE_DAY = 0;
    public static final int TYPE_MONTH = 2;
    public static final int TYPE_WEEK = 1;
    private int bottomBarHeight = 84;
    private int colorBackground = Color.parseColor("#7f4aec");
    private int colorBootom = Color.parseColor("#f9f6fb");
    private int colorTarget = Color.parseColor("#9d73f1");
    private int colorTargetSelect = Color.parseColor("#b899f6");
    private int colorText = Color.parseColor("#767676");
    private int colorValue = Color.parseColor("#ca9fff");
    private int colorValueSelect = Color.parseColor("#e2cbff");
    private T currentItem;
    private List<T> data = this.dayData;
    private List<T> dayData = new ArrayList();
    private int drawCount;
    protected boolean inited = false;
    private int leftBorder;
    private OnColumnarChangeListener<T> listener;
    private Runnable listenerRun = new C10841();
    private int mColumnarWidth = 100;
    private boolean mDragging = false;
    private float mLastX = 0.0f;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private float mMoveLen = 0.0f;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mViewHeight;
    private int mViewWidth;
    private int maxValue;
    private int maxValueDay;
    private int maxValueMonth;
    private int maxValueWeek;
    private List<T> monthData = new ArrayList();
    private OnFlingListener onFlingListener;
    private Paint paintBootom;
    private Paint paintColumnar;
    private Paint paintText;
    private int rightBorder;
    private int space = 4;
    private int textSize = 35;
    private int type = 0;
    private List<T> weekData = new ArrayList();

    class C10841 implements Runnable {
        C10841() {
        }

        public void run() {
            if (MiColumnarView.this.listener != null && MiColumnarView.this.mScroller.isFinished() && MiColumnarView.this.currentItem != null) {
                MiColumnarView.this.listener.onChange(MiColumnarView.this.currentItem);
            }
        }
    }

    public interface OnFlingListener {
        void onFling(boolean z);
    }

    public MiColumnarView(Context context) {
        super(context);
        init(context);
    }

    public MiColumnarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.bottomBarHeight = Tools.dip2px(context, 32.0f);
        this.mColumnarWidth = Tools.dip2px(context, 40.0f);
        this.textSize = Tools.dip2px(context, 12.0f);
        Drawable drawable = getBackground();
        if (drawable instanceof ColorDrawable) {
            this.colorBackground = ((ColorDrawable) drawable).getColor();
        }
        this.mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.paintBootom = new Paint();
        this.paintBootom.setColor(this.colorBootom);
        this.paintBootom.setStyle(Style.FILL);
        this.paintBootom.setAntiAlias(true);
        this.paintColumnar = new Paint();
        this.paintColumnar.setStyle(Style.FILL);
        this.paintColumnar.setAntiAlias(true);
        this.paintText = new Paint();
        this.paintText.setAntiAlias(true);
        this.paintText.setColor(this.colorText);
        this.paintText.setTextSize((float) this.textSize);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setBottomBarHeight(int bottomBarHeight) {
        this.bottomBarHeight = bottomBarHeight;
    }

    public void setSpaceWidth(int space) {
        this.space = space;
    }

    public void setmColumnarWidth(int mColumnarWidth) {
        this.mColumnarWidth = mColumnarWidth;
    }

    public void setDayData(List<T> dayData) {
        this.dayData = dayData;
        this.maxValueDay = getMaxValue(dayData);
        this.maxValue = this.maxValueDay;
        this.data = dayData;
    }

    public void setWeekData(List<T> weekData) {
        this.weekData = weekData;
        this.maxValueWeek = getMaxValue(weekData);
    }

    public void setMonthData(List<T> monthData) {
        this.monthData = monthData;
        this.maxValueMonth = getMaxValue(monthData);
    }

    protected void refreshSize() {
        if (this.inited) {
            this.leftBorder = ((this.mViewWidth / 2) - this.space) - (this.mColumnarWidth / 2);
            this.rightBorder = -(((this.data.size() * (this.mColumnarWidth + this.space)) - (this.mViewWidth / 2)) - (this.mColumnarWidth / 2));
            this.drawCount = (int) ((((float) Tools.getScreenMetrics(getContext()).x) / ((float) (this.mColumnarWidth + this.space))) + 2.0f);
            this.mMoveLen = (float) this.rightBorder;
        }
    }

    public void setColorTarget(int colorTarget) {
        this.colorTarget = colorTarget;
    }

    public void setColorTargetSelect(int colorTargetSelect) {
        this.colorTargetSelect = colorTargetSelect;
    }

    public void setColorValue(int colorValue) {
        this.colorValue = colorValue;
    }

    public void setColorValueSelect(int colorValueSelect) {
        this.colorValueSelect = colorValueSelect;
    }

    public void setColorBootom(int colorBootom) {
        this.colorBootom = colorBootom;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    private int getMaxValue(List<T> list) {
        int max = 0;
        if (list.size() > 0) {
            for (T item : list) {
                int temp = Math.max(item.getValue(), item.getTarget());
                if (max < temp) {
                    max = temp;
                }
            }
        }
        if (max == 0) {
            return 1;
        }
        return (int) (((double) max) * 1.1d);
    }

    public void setListener(OnColumnarChangeListener<T> listener) {
        this.listener = listener;
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        this.onFlingListener = onFlingListener;
    }

    public void setColumnarType(int type) {
        if (this.type != type) {
            if (type > 2) {
                this.type = 2;
            } else if (type < 0) {
                this.type = 0;
            } else {
                this.type = type;
            }
            switch (this.type) {
                case 1:
                    this.data = this.weekData;
                    this.maxValue = this.maxValueWeek;
                    break;
                case 2:
                    this.data = this.monthData;
                    this.maxValue = this.maxValueMonth;
                    break;
                default:
                    this.data = this.dayData;
                    this.maxValue = this.maxValueDay;
                    break;
            }
            this.rightBorder = -(((this.data.size() * (this.mColumnarWidth + this.space)) - (this.mViewWidth / 2)) - (this.mColumnarWidth / 2));
            invalidate();
            flingToEnd();
        }
    }

    public int getColumnarType() {
        return this.type;
    }

    public T getSelectedData() {
        return this.currentItem;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
        this.leftBorder = ((this.mViewWidth / 2) - this.space) - (this.mColumnarWidth / 2);
        this.rightBorder = -(((this.data.size() * (this.mColumnarWidth + this.space)) - (this.mViewWidth / 2)) - (this.mColumnarWidth / 2));
        this.drawCount = (int) ((((float) Tools.getScreenMetrics(getContext()).x) / ((float) (this.mColumnarWidth + this.space))) + 2.0f);
        if (!this.inited) {
            this.mMoveLen = (float) this.rightBorder;
        }
        this.inited = true;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.inited) {
            drawBootom(canvas);
            drawColumnar(canvas);
        }
    }

    private void drawBootom(Canvas canvas) {
        Path bottom = new Path();
        bottom.moveTo(0.0f, (float) (this.mViewHeight - this.bottomBarHeight));
        bottom.lineTo((float) this.mViewWidth, (float) (this.mViewHeight - this.bottomBarHeight));
        bottom.lineTo((float) this.mViewWidth, (float) this.mViewHeight);
        bottom.lineTo(0.0f, (float) this.mViewHeight);
        bottom.close();
        this.paintBootom.setColor(this.colorBootom);
        canvas.drawPath(bottom, this.paintBootom);
    }

    private void drawColumnar(Canvas canvas) {
        int start = (int) ((-this.mMoveLen) / ((float) (this.mColumnarWidth + this.space)));
        if (start < 0) {
            start = 0;
        }
        int i = start;
        while (i < this.data.size() && i < this.drawCount + start) {
            MiColumnarData item = (MiColumnarData) this.data.get(i);
            RectF rect = new RectF();
            float left = ((float) ((this.mColumnarWidth * i) + (this.space * (i + 1)))) + this.mMoveLen;
            float right = ((float) this.mColumnarWidth) + left;
            float bootom = (float) (this.mViewHeight - this.bottomBarHeight);
            rect.set(left, bootom * (1.0f - (((float) item.getTarget()) / ((float) this.maxValue))), right, bootom);
            drawPath(canvas, rect, i, false);
            rect.set(left, bootom * (1.0f - (((float) item.getValue()) / ((float) this.maxValue))), right, bootom);
            drawPath(canvas, rect, i, true);
            canvas.drawText(item.getLabel(), ((((float) ((this.mColumnarWidth * i) + (this.space * (i + 1)))) + this.mMoveLen) + ((float) (this.mColumnarWidth / 2))) - (this.paintText.measureText(item.getLabel()) / 2.0f), (float) (this.mViewHeight - ((this.bottomBarHeight / 2) - (this.textSize / 2))), this.paintText);
            i++;
        }
    }

    private void drawPath(Canvas canvas, RectF rect, int position, boolean isValue) {
        if (((int) ((((float) (this.mViewWidth / 2)) - this.mMoveLen) / ((float) (this.mColumnarWidth + this.space)))) != position) {
            this.paintColumnar.setColor(isValue ? this.colorValue : this.colorTarget);
            this.paintText.setColor(this.colorText);
        } else {
            this.paintColumnar.setColor(isValue ? this.colorValueSelect : this.colorTargetSelect);
            this.paintText.setColor(this.colorBackground);
            if (this.listener != null && this.mScroller.isFinished()) {
                this.currentItem = (MiColumnarData) this.data.get(position);
            }
        }
        canvas.drawRect(rect, this.paintColumnar);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
        switch (event.getActionMasked()) {
            case 0:
                this.mDragging = true;
                this.mLastX = event.getX();
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mVelocityTracker.clear();
                this.mVelocityTracker.addMovement(event);
                break;
            case 1:
                this.mDragging = false;
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                int velocityX = (int) this.mVelocityTracker.getXVelocity();
                if (Math.abs(velocityX) > this.mMinimumVelocity) {
                    fling(-velocityX);
                } else {
                    fling(100);
                }
                this.mVelocityTracker.clear();
                break;
            case 2:
                doMove(event);
                break;
            case 3:
                this.mDragging = false;
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                    break;
                }
                break;
        }
        return true;
    }

    private void doMove(MotionEvent event) {
        float len = (event.getX() - this.mLastX) + this.mMoveLen;
        if (len > ((float) this.leftBorder) || len < ((float) this.rightBorder)) {
            this.mLastX = event.getX();
            return;
        }
        this.mMoveLen = len;
        this.mLastX = event.getX();
        invalidate();
    }

    public void computeScroll() {
        this.onFlingListener.onFling(true);
        if (this.mScroller.computeScrollOffset()) {
            this.mMoveLen = (float) (this.rightBorder - this.mScroller.getCurrX());
            if (this.mScroller.getCurrX() > 0 && !this.mDragging) {
                this.mScroller.startScroll(this.mScroller.getCurrX(), 0, -(0 - this.mScroller.getCurrX()), 0, 100);
            } else if (this.mScroller.getCurrX() >= this.rightBorder - (this.mViewWidth / 2) || this.mDragging) {
                doListener(100);
            } else {
                this.mScroller.startScroll(this.mScroller.getCurrX(), 0, (-this.mScroller.getCurrX()) + this.rightBorder, 0, 100);
            }
            postInvalidate();
        } else if (this.mScroller.getCurrX() % (this.mColumnarWidth + this.space) != 0) {
            int moveDtX = (-this.mScroller.getCurrX()) - ((this.mColumnarWidth + this.space) * ((-this.mScroller.getCurrX()) / (this.mColumnarWidth + this.space)));
            if (moveDtX > (this.mColumnarWidth / 2) + this.space) {
                moveDtX = (moveDtX - this.mColumnarWidth) - this.space;
            }
            this.mScroller.startScroll(this.mScroller.getCurrX(), 0, moveDtX, 0, 100);
            postInvalidate();
        }
    }

    private void fling(int velocityX) {
        this.mScroller.fling(this.rightBorder - ((int) this.mMoveLen), 0, velocityX, 0, ((this.rightBorder - (this.mViewWidth / 2)) + (this.mColumnarWidth / 2)) + this.space, 0, 0, 0);
        invalidate();
    }

    private void flingToEnd() {
        this.mScroller.fling(0, 0, 100, 0, ((this.rightBorder - (this.mViewWidth / 2)) + (this.mColumnarWidth / 2)) + this.space, 0, 0, 0);
        invalidate();
    }

    protected void doListener(long delay) {
        removeCallbacks(this.listenerRun);
        postDelayed(this.listenerRun, delay);
    }
}
