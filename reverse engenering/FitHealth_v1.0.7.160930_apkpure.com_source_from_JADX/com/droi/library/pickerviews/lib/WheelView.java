package com.droi.library.pickerviews.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.droi.library.pickerviews.C0545R;
import com.droi.library.pickerviews.adapter.WheelAdapter;
import com.droi.library.pickerviews.listener.OnItemSelectedListener;
import com.tencent.open.yyb.TitleBar;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WheelView extends View {
    private static final float CENTERCONTENTOFFSET = 3.0f;
    private static final String GETPICKERVIEWTEXT = "getPickerViewText";
    private static final float SCALECONTENT = 0.8f;
    private static final int VELOCITYFLING = 5;
    WheelAdapter adapter;
    float centerY;
    int change;
    Context context;
    int dividerColor;
    float dividerWidth;
    private int drawCenterContentStart;
    private int drawOutContentStart;
    float firstLineY;
    private GestureDetector gestureDetector;
    int halfCircumference;
    Handler handler;
    int initPosition;
    boolean isLoop;
    float itemHeight;
    int itemsVisible;
    private String label;
    int labelColor;
    float lineSpacingMultiplier;
    ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mFuture;
    private int mGravity;
    private int mOffset;
    int maxTextHeight;
    int maxTextWidth;
    int measuredHeight;
    int measuredWidth;
    OnItemSelectedListener onItemSelectedListener;
    Paint paintCenterText;
    Paint paintIndicator;
    Paint paintOuterText;
    int preCurrentIndex;
    private float previousY;
    int radius;
    float scale;
    float secondLineY;
    private int selectedItem;
    long startTime;
    int textColorCenter;
    int textColorOut;
    int textSize;
    int totalScrollY;
    int widthMeasureSpec;

    public enum ACTION {
        CLICK,
        FLING,
        DAGGLE
    }

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.dividerWidth = 0.5f;
        this.lineSpacingMultiplier = 1.5f;
        this.isLoop = true;
        this.totalScrollY = 0;
        this.initPosition = -1;
        this.itemsVisible = 9;
        this.mOffset = 0;
        this.previousY = 0.0f;
        this.startTime = 0;
        this.mGravity = 17;
        this.drawCenterContentStart = 0;
        this.drawOutContentStart = 0;
        this.textColorOut = getResources().getColor(C0545R.color.pickerview_wheelview_textcolor_out);
        this.textColorCenter = getResources().getColor(C0545R.color.pickerview_wheelview_textcolor_center);
        this.dividerColor = getResources().getColor(C0545R.color.pickerview_wheelview_textcolor_divider);
        this.labelColor = getResources().getColor(C0545R.color.pickerview_wheelview_textcolor_label);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0545R.styleable.wheelview, 0, 0);
            this.mGravity = a.getInt(C0545R.styleable.wheelview_gravitys, 17);
            this.textColorOut = a.getColor(C0545R.styleable.wheelview_textColorOut, this.textColorOut);
            this.textColorCenter = a.getColor(C0545R.styleable.wheelview_textColorCenter, this.textColorCenter);
            this.dividerColor = a.getColor(C0545R.styleable.wheelview_dividerColor, this.dividerColor);
            this.labelColor = a.getColor(C0545R.styleable.wheelview_labelColor, this.labelColor);
            a.recycle();
        }
        initLoopView(context);
    }

    private void initLoopView(Context context) {
        this.context = context;
        this.scale = this.context.getResources().getDisplayMetrics().density;
        this.handler = new MessageHandler(this);
        this.gestureDetector = new GestureDetector(context, new LoopViewGestureListener(this));
        this.gestureDetector.setIsLongpressEnabled(false);
        initPaints();
        setTextSize(22.0f);
    }

    private void initPaints() {
        this.paintOuterText = new Paint();
        this.paintOuterText.setColor(this.textColorOut);
        this.paintOuterText.setAntiAlias(true);
        this.paintOuterText.setTypeface(Typeface.MONOSPACE);
        this.paintOuterText.setTextSize((float) this.textSize);
        this.paintCenterText = new Paint();
        this.paintCenterText.setColor(this.textColorCenter);
        this.paintCenterText.setAntiAlias(true);
        this.paintCenterText.setTextScaleX(1.1f);
        this.paintCenterText.setTypeface(Typeface.MONOSPACE);
        this.paintCenterText.setTextSize((float) this.textSize);
        this.paintIndicator = new Paint();
        this.paintIndicator.setColor(this.dividerColor);
        this.paintIndicator.setAntiAlias(true);
        this.paintIndicator.setStrokeWidth(this.dividerWidth);
        if (VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
    }

    private void remeasure() {
        if (this.adapter != null) {
            measureTextWidthHeight();
            this.halfCircumference = (int) (this.itemHeight * ((float) (this.itemsVisible - 1)));
            this.measuredHeight = (int) (((double) (this.halfCircumference * 2)) / 3.141592653589793d);
            this.radius = (int) (((double) this.halfCircumference) / 3.141592653589793d);
            this.measuredWidth = MeasureSpec.getSize(this.widthMeasureSpec);
            this.firstLineY = (((float) this.measuredHeight) - this.itemHeight) / 2.0f;
            this.secondLineY = (((float) this.measuredHeight) + this.itemHeight) / 2.0f;
            this.centerY = (((float) (this.measuredHeight + this.maxTextHeight)) / 2.0f) - CENTERCONTENTOFFSET;
            if (this.initPosition == -1) {
                if (this.isLoop) {
                    this.initPosition = (this.adapter.getItemsCount() + 1) / 2;
                } else {
                    this.initPosition = 0;
                }
            }
            this.preCurrentIndex = this.initPosition;
        }
    }

    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < this.adapter.getItemsCount(); i++) {
            String s1 = getContentText(this.adapter.getItem(i));
            this.paintCenterText.getTextBounds(s1, 0, s1.length(), rect);
            int textWidth = rect.width();
            if (textWidth > this.maxTextWidth) {
                this.maxTextWidth = textWidth;
            }
            this.paintCenterText.getTextBounds("星期", 0, 2, rect);
            int textHeight = rect.height();
            if (textHeight > this.maxTextHeight) {
                this.maxTextHeight = textHeight;
            }
        }
        this.itemHeight = this.lineSpacingMultiplier * ((float) this.maxTextHeight);
    }

    void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            this.mOffset = (int) (((((float) this.totalScrollY) % this.itemHeight) + this.itemHeight) % this.itemHeight);
            if (((float) this.mOffset) > this.itemHeight / 2.0f) {
                this.mOffset = (int) (this.itemHeight - ((float) this.mOffset));
            } else {
                this.mOffset = -this.mOffset;
            }
        }
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, this.mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    protected final void scrollBy(float velocityY) {
        cancelFuture();
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, velocityY), 0, 5, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        if (this.mFuture != null && !this.mFuture.isCancelled()) {
            this.mFuture.cancel(true);
            this.mFuture = null;
        }
    }

    public final void setCyclic(boolean cyclic) {
        this.isLoop = cyclic;
    }

    public void setTypeface(Typeface type) {
        this.paintOuterText.setTypeface(type);
        this.paintCenterText.setTypeface(type);
    }

    public void setDividerWidth(float width) {
        this.dividerWidth = width;
        this.paintIndicator.setStrokeWidth(px2dip(width));
    }

    public void setLineSpacingMultiplier(float space) {
        this.lineSpacingMultiplier = space;
    }

    public final void setTextSize(float size) {
        if (size > 0.0f) {
            this.textSize = dip2px(size);
            this.paintOuterText.setTextSize((float) this.textSize);
            this.paintCenterText.setTextSize((float) this.textSize);
        }
    }

    public final void setCurrentItem(int currentItem) {
        this.initPosition = currentItem;
        this.totalScrollY = 0;
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener OnItemSelectedListener) {
        this.onItemSelectedListener = OnItemSelectedListener;
    }

    public final void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return this.adapter;
    }

    public final int getCurrentItem() {
        return this.selectedItem;
    }

    protected final void onItemSelected() {
        if (this.onItemSelectedListener != null) {
            postDelayed(new OnItemSelectedRunnable(this), 200);
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.adapter != null) {
            int counter;
            Object[] visibles = new Object[this.itemsVisible];
            this.change = (int) (((float) this.totalScrollY) / this.itemHeight);
            try {
                this.preCurrentIndex = this.initPosition + (this.change % this.adapter.getItemsCount());
            } catch (ArithmeticException e) {
                System.out.println("出错了！adapter.getItemsCount() == 0，联动数据不匹配");
            }
            if (this.isLoop) {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = this.adapter.getItemsCount() + this.preCurrentIndex;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex -= this.adapter.getItemsCount();
                }
            } else {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = 0;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex = this.adapter.getItemsCount() - 1;
                }
            }
            int itemHeightOffset = (int) (((float) this.totalScrollY) % this.itemHeight);
            for (counter = 0; counter < this.itemsVisible; counter++) {
                int index = this.preCurrentIndex - ((this.itemsVisible / 2) - counter);
                if (this.isLoop) {
                    if (index < 0) {
                        index += this.adapter.getItemsCount();
                        if (index < 0) {
                            index = 0;
                        }
                    }
                    if (index > this.adapter.getItemsCount() - 1) {
                        index -= this.adapter.getItemsCount();
                        if (index > this.adapter.getItemsCount() - 1) {
                            index = this.adapter.getItemsCount() - 1;
                        }
                    }
                    visibles[counter] = this.adapter.getItem(index);
                } else if (index < 0) {
                    visibles[counter] = "";
                } else if (index > this.adapter.getItemsCount() - 1) {
                    visibles[counter] = "";
                } else {
                    visibles[counter] = this.adapter.getItem(index);
                }
            }
            canvas.drawLine(0.0f, this.firstLineY, (float) this.measuredWidth, this.firstLineY, this.paintIndicator);
            canvas.drawLine(0.0f, this.secondLineY, (float) this.measuredWidth, this.secondLineY, this.paintIndicator);
            if (this.label != null) {
                this.paintCenterText.setTextSize(((float) this.textSize) / 1.5f);
                this.paintCenterText.setColor(this.labelColor);
                int drawRightContentStart = (((this.measuredWidth - this.maxTextWidth) / 2) + this.maxTextWidth) + dip2px(TitleBar.SHAREBTN_RIGHT_MARGIN);
                canvas.drawText(this.label, (float) drawRightContentStart, this.centerY, this.paintCenterText);
                this.paintCenterText.setTextSize((float) this.textSize);
                this.paintCenterText.setColor(this.textColorCenter);
            }
            for (counter = 0; counter < this.itemsVisible; counter++) {
                canvas.save();
                float itemHeight = ((float) this.maxTextHeight) * this.lineSpacingMultiplier;
                double radian = (((double) ((((float) counter) * itemHeight) - ((float) itemHeightOffset))) * 3.141592653589793d) / ((double) this.halfCircumference);
                float angle = (float) (90.0d - ((radian / 3.141592653589793d) * 180.0d));
                if (angle >= 90.0f || angle <= -90.0f) {
                    canvas.restore();
                } else {
                    String contentText = getContentText(visibles[counter]);
                    measuredCenterContentStart(contentText);
                    measuredOutContentStart(contentText);
                    float translateY = (float) ((((double) this.radius) - (Math.cos(radian) * ((double) this.radius))) - ((Math.sin(radian) * ((double) this.maxTextHeight)) / 2.0d));
                    canvas.translate(0.0f, translateY);
                    canvas.scale(1.0f, (float) Math.sin(radian));
                    if (translateY <= this.firstLineY && ((float) this.maxTextHeight) + translateY >= this.firstLineY) {
                        canvas.save();
                        canvas.clipRect(0.0f, 0.0f, (float) this.measuredWidth, this.firstLineY - translateY);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * SCALECONTENT);
                        canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0.0f, this.firstLineY - translateY, (float) this.measuredWidth, (float) ((int) itemHeight));
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                        canvas.drawText(contentText, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - CENTERCONTENTOFFSET, this.paintCenterText);
                        canvas.restore();
                    } else if (translateY <= this.secondLineY && ((float) this.maxTextHeight) + translateY >= this.secondLineY) {
                        canvas.save();
                        canvas.clipRect(0.0f, 0.0f, (float) this.measuredWidth, this.secondLineY - translateY);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * 1.0f);
                        canvas.drawText(contentText, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - CENTERCONTENTOFFSET, this.paintCenterText);
                        canvas.restore();
                        canvas.save();
                        canvas.clipRect(0.0f, this.secondLineY - translateY, (float) this.measuredWidth, (float) ((int) itemHeight));
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * SCALECONTENT);
                        canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                    } else if (translateY < this.firstLineY || ((float) this.maxTextHeight) + translateY > this.secondLineY) {
                        canvas.save();
                        canvas.clipRect(0, 0, this.measuredWidth, (int) itemHeight);
                        canvas.scale(1.0f, ((float) Math.sin(radian)) * SCALECONTENT);
                        canvas.drawText(contentText, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                    } else {
                        canvas.clipRect(0, 0, this.measuredWidth, (int) itemHeight);
                        canvas.drawText(contentText, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - CENTERCONTENTOFFSET, this.paintCenterText);
                        int preSelectedItem = this.adapter.indexOf(visibles[counter]);
                        if (preSelectedItem != -1) {
                            this.selectedItem = preSelectedItem;
                        }
                    }
                    canvas.restore();
                }
            }
        }
    }

    private String getContentText(Object item) {
        String contentText = item.toString();
        try {
            contentText = item.getClass().getMethod(GETPICKERVIEWTEXT, new Class[0]).invoke(item, new Object[0]).toString();
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e2) {
        } catch (IllegalAccessException e3) {
        } catch (Exception e4) {
        }
        return contentText;
    }

    private void measuredCenterContentStart(String content) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(content, 0, content.length(), rect);
        switch (this.mGravity) {
            case 3:
                this.drawCenterContentStart = 0;
                return;
            case 5:
                this.drawCenterContentStart = this.measuredWidth - rect.width();
                return;
            case 17:
                this.drawCenterContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.5d);
                return;
            default:
                return;
        }
    }

    private void measuredOutContentStart(String content) {
        Rect rect = new Rect();
        this.paintOuterText.getTextBounds(content, 0, content.length(), rect);
        switch (this.mGravity) {
            case 3:
                this.drawOutContentStart = 0;
                return;
            case 5:
                this.drawOutContentStart = this.measuredWidth - rect.width();
                return;
            case 17:
                this.drawOutContentStart = (int) (((double) (this.measuredWidth - rect.width())) * 0.5d);
                return;
            default:
                return;
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        remeasure();
        setMeasuredDimension(this.measuredWidth, this.measuredHeight);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean eventConsumed = this.gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                this.startTime = System.currentTimeMillis();
                cancelFuture();
                this.previousY = event.getRawY();
                break;
            case 2:
                float dy = this.previousY - event.getRawY();
                this.previousY = event.getRawY();
                this.totalScrollY = (int) (((float) this.totalScrollY) + dy);
                if (!this.isLoop) {
                    float top = ((float) (-this.initPosition)) * this.itemHeight;
                    float bottom = ((float) ((this.adapter.getItemsCount() - 1) - this.initPosition)) * this.itemHeight;
                    if (((double) this.totalScrollY) - (((double) this.itemHeight) * 0.3d) < ((double) top)) {
                        top = ((float) this.totalScrollY) - dy;
                    } else if (((double) this.totalScrollY) + (((double) this.itemHeight) * 0.3d) > ((double) bottom)) {
                        bottom = ((float) this.totalScrollY) - dy;
                    }
                    if (((float) this.totalScrollY) >= top) {
                        if (((float) this.totalScrollY) > bottom) {
                            this.totalScrollY = (int) bottom;
                            break;
                        }
                    }
                    this.totalScrollY = (int) top;
                    break;
                }
                break;
            default:
                if (!eventConsumed) {
                    float extraOffset = ((((float) this.totalScrollY) % this.itemHeight) + this.itemHeight) % this.itemHeight;
                    this.mOffset = (int) ((((float) (((int) ((((double) (this.itemHeight / 2.0f)) + (Math.acos((double) ((((float) this.radius) - event.getY()) / ((float) this.radius))) * ((double) this.radius))) / ((double) this.itemHeight))) - (this.itemsVisible / 2))) * this.itemHeight) - extraOffset);
                    if (System.currentTimeMillis() - this.startTime <= 120) {
                        smoothScroll(ACTION.CLICK);
                        break;
                    }
                    smoothScroll(ACTION.DAGGLE);
                    break;
                }
                break;
        }
        invalidate();
        return true;
    }

    public int getItemsCount() {
        return this.adapter != null ? this.adapter.getItemsCount() : 0;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLabel(int resId) {
        this.label = this.context.getResources().getString(resId);
    }

    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        this.paintOuterText.setColor(textColorOut);
    }

    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        this.paintCenterText.setColor(textColorCenter);
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        this.paintIndicator.setColor(dividerColor);
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    public int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil((double) widths[j]);
            }
        }
        return iRet;
    }

    public int dip2px(float dipValue) {
        return (int) ((this.scale * dipValue) + 0.5f);
    }

    public float px2dip(float pxValue) {
        return (pxValue / this.scale) + 0.5f;
    }
}
