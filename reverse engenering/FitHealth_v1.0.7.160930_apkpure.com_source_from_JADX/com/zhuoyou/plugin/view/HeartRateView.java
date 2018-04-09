package com.zhuoyou.plugin.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.fithealth.running.R;
import com.tencent.open.yyb.TitleBar;
import com.umeng.analytics.C0919a;
import java.util.Arrays;

public class HeartRateView extends View {
    private float AmplitudeA = 200.0f;
    private final int OFFSET_Y = 0;
    private final int SPEED = 5;
    private volatile boolean StartFirstFrameFlag = false;
    private volatile boolean StartHeartBeatAnmiFlag = false;
    private volatile boolean StopHeartBeatAnmiFlag = false;
    private int mAnimAngle = -1;
    private Context mContext;
    private float[] mDefaultYPostion;
    private DrawFilter mDrawFilter;
    private int mFirstFrameOffset = 0;
    private Paint mHeartBeatPaint;
    private Paint mHeartBeatPathPaint;
    private int mHeartBeatWidth;
    private final int mHeartPaintWidth = 50;
    private volatile int mOffset = 0;
    private int mOffsetSpeed;
    private float[] mOriginalYPositon;
    private float mPeriodFraction = 0.0f;
    private int mRadius = 200;
    private RectF mRectF;
    private Paint mRingAnimPaint;
    private Paint mRingPaint;
    private int mTotalHeight;
    private int mTotalWidth;
    Path path = new Path();
    private int f3493x;
    private int f3494y;

    class C14381 implements Runnable {
        C14381() {
        }

        public void run() {
            while (HeartRateView.this.mFirstFrameOffset > 0) {
                HeartRateView.this.mFirstFrameOffset = HeartRateView.this.mFirstFrameOffset - 1;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HeartRateView.this.postInvalidate();
            }
            HeartRateView.this.StartFirstFrameFlag = false;
            HeartRateView.this.StartHeartBeatAnmiFlag = true;
            HeartRateView.this.startSecondFrameAnmi();
        }
    }

    class C14392 implements Runnable {
        C14392() {
        }

        public void run() {
            while (!HeartRateView.this.StopHeartBeatAnmiFlag) {
                HeartRateView.this.mOffset = HeartRateView.this.mOffset + HeartRateView.this.mOffsetSpeed;
                if (HeartRateView.this.mOffset >= HeartRateView.this.mHeartBeatWidth) {
                    HeartRateView.this.mOffset = 0;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    HeartRateView.this.postInvalidate();
                }
            }
        }
    }

    class C14403 implements Runnable {
        C14403() {
        }

        public void run() {
            while (HeartRateView.this.mAnimAngle < C0919a.f3120q && HeartRateView.this.StartHeartBeatAnmiFlag) {
                HeartRateView.this.mAnimAngle = HeartRateView.this.mAnimAngle + 1;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HeartRateView.this.postInvalidate();
            }
            HeartRateView.this.mAnimAngle = -1;
            HeartRateView.this.stopAnim();
            Log.i("lsj", "mAnimAngle = " + HeartRateView.this.mAnimAngle);
            HeartRateView.this.sendBroadcast();
        }
    }

    private void init() {
        setLayerType(1, null);
        this.mRingPaint = new Paint(1);
        if (!isInEditMode()) {
            this.mRingPaint.setColor(this.mContext.getResources().getColor(R.color.heart_default));
        }
        this.mRingPaint.setStrokeWidth(50.0f);
        this.mRingPaint.setStyle(Style.STROKE);
        this.mRingAnimPaint = new Paint(this.mRingPaint);
        this.mRingAnimPaint.setColor(-1);
        this.mDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mOffsetSpeed = (int) TypedValue.applyDimension(1, 5.0f, this.mContext.getResources().getDisplayMetrics());
        this.mHeartBeatPaint = new Paint(1);
        this.mHeartBeatPaint.setStrokeWidth(5.0f);
        if (!isInEditMode()) {
            this.mHeartBeatPaint.setColor(this.mContext.getResources().getColor(R.color.heartbeat));
        }
        this.mHeartBeatPathPaint = new Paint(this.mHeartBeatPaint);
        this.mHeartBeatPathPaint.setStrokeWidth(5.0f);
        this.mHeartBeatPathPaint.setStyle(Style.STROKE);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mTotalHeight = h;
        this.mTotalWidth = w;
        this.mHeartBeatWidth = (w - 100) - 40;
        this.mFirstFrameOffset = this.mHeartBeatWidth - 1;
        this.AmplitudeA = (float) ((this.mTotalHeight - 100) / 4);
        this.mOriginalYPositon = new float[this.mHeartBeatWidth];
        this.mDefaultYPostion = new float[this.mHeartBeatWidth];
        Arrays.fill(this.mOriginalYPositon, 0.0f);
        Arrays.fill(this.mDefaultYPostion, GroundOverlayOptions.NO_DIMENSION);
        this.mPeriodFraction = (float) ((6.283185307179586d / ((double) this.mHeartBeatWidth)) * 3.0d);
        for (int i = (this.mHeartBeatWidth / 3) * 2; i < this.mHeartBeatWidth; i++) {
            this.mOriginalYPositon[i] = (float) ((((double) this.AmplitudeA) * Math.sin((double) (this.mPeriodFraction * ((float) i)))) + 0.0d);
        }
        this.f3493x = w / 2;
        this.f3494y = h / 2;
        this.mRadius = (w / 2) - 25;
        this.mRectF = new RectF((float) (this.f3493x - this.mRadius), (float) (this.f3494y - this.mRadius), (float) (this.f3493x + this.mRadius), (float) (this.f3494y + this.mRadius));
    }

    private void resetPath() {
        this.path.reset();
        this.path.moveTo(70.0f, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[this.mOffset]);
        int interval = this.mHeartBeatWidth - this.mOffset;
        int i = this.mOffset + 1;
        int j = 70;
        while (i < this.mHeartBeatWidth) {
            this.path.lineTo((float) j, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[i]);
            i++;
            j++;
        }
        i = 0;
        j = (interval + 50) + 20;
        while (i < this.mOffset) {
            this.path.lineTo((float) j, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[i]);
            i++;
            j++;
        }
    }

    private void resetPath1() {
        int j;
        this.path.reset();
        this.path.moveTo(70.0f, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[this.mOffset]);
        int interval = this.mHeartBeatWidth - this.mOffset;
        int index = -1;
        int i = this.mOffset + 1;
        while (i < this.mHeartBeatWidth && this.mOriginalYPositon[i] == 0.0f) {
            index = i;
            i++;
        }
        if (index != -1) {
            this.path.lineTo((float) (((index - this.mOffset) + 1) + 70), (float) (this.mTotalHeight / 2));
            i = index + 1;
            j = ((index - this.mOffset) + 2) + 70;
            while (i < this.mHeartBeatWidth) {
                this.path.lineTo((float) j, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[i]);
                i++;
                j++;
            }
        } else {
            i = this.mOffset + 1;
            j = 70;
            while (i < this.mHeartBeatWidth) {
                this.path.lineTo((float) j, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[i]);
                i++;
                j++;
            }
        }
        index = -1;
        i = 0;
        while (i < this.mOffset && this.mOriginalYPositon[i] == 0.0f) {
            index = i;
            i++;
        }
        if (index != -1) {
            this.path.lineTo((float) ((this.mHeartBeatWidth - this.mOffset) + 70), (float) (this.mTotalHeight / 2));
            this.path.lineTo((float) (((interval + 50) + 20) + index), (float) (this.mTotalHeight / 2));
            i = index + 1;
            j = (((interval + 50) + 20) + index) + 1;
            while (i < this.mOffset) {
                this.path.lineTo((float) j, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[i]);
                i++;
                j++;
            }
            return;
        }
        i = 0;
        j = (interval + 50) + 20;
        while (i < this.mOffset) {
            this.path.lineTo((float) j, ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[i]);
            i++;
            j++;
        }
    }

    protected void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        canvas.setDrawFilter(this.mDrawFilter);
        for (i = 0; i < C0919a.f3120q; i += 3) {
            canvas.drawArc(this.mRectF, (float) i, 1.0f, false, this.mRingPaint);
        }
        if (this.mAnimAngle != -1) {
            for (i = -90; i < this.mAnimAngle - 90; i += 3) {
                canvas.drawArc(this.mRectF, (float) i, 1.0f, false, this.mRingAnimPaint);
            }
        }
        if (this.StartHeartBeatAnmiFlag) {
            resetPath1();
            canvas.drawPath(this.path, this.mHeartBeatPathPaint);
            canvas.drawCircle((float) ((this.mHeartBeatWidth + 20) + 50), ((float) (this.mTotalHeight / 2)) - this.mOriginalYPositon[this.mOffset], TitleBar.SHAREBTN_RIGHT_MARGIN, this.mHeartBeatPaint);
        }
        if (this.StartFirstFrameFlag) {
            i = 0;
            int j = 70;
            while (i < this.mHeartBeatWidth) {
                if (this.mDefaultYPostion[i] != GroundOverlayOptions.NO_DIMENSION) {
                    canvas.drawPoint((float) j, ((float) (this.mTotalHeight / 2)) - this.mDefaultYPostion[i], this.mHeartBeatPaint);
                }
                i++;
                j++;
            }
        }
    }

    private void startHeartBeatAnmi() {
        this.StartFirstFrameFlag = true;
        new Thread(new C14381()).start();
    }

    private void startSecondFrameAnmi() {
        new Thread(new C14392()).start();
    }

    private void startRingAnim() {
        this.StartHeartBeatAnmiFlag = true;
        this.mAnimAngle = 0;
        new Thread(new C14403()).start();
    }

    public void stopAnim() {
        Log.i("lsj", "stopAnim....");
        this.StopHeartBeatAnmiFlag = true;
        this.StartHeartBeatAnmiFlag = false;
    }

    public void startAnim() {
        Log.i("lsj", "startAnim....");
        startRingAnim();
        startHeartBeatAnmi();
    }

    public HeartRateView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public HeartRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public HeartRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void sendBroadcast() {
        this.mContext.sendBroadcast(new Intent("android.intent.action.MAIN"));
        Log.i("lsj", "sendBroadcast ok!");
    }
}
