package com.zhuoyou.plugin.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class HeartRateAnimationView extends View {
    private static final int START_Y = 200;
    private static final int mRdius = 2;
    public final int f3492T;
    private ValueAnimator deltaAnimator;
    private int deltaX;
    private ValueAnimator mAnimator;
    private int mLength;
    private List<Point> mList;
    private Paint mPaint;
    private Path mPath;
    private float mScale;
    private int mTime;
    private int screenWidth;

    class C14351 implements AnimatorUpdateListener {
        C14351() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            HeartRateAnimationView.this.mTime = ((Integer) animation.getAnimatedValue()).intValue();
            HeartRateAnimationView.this.postInvalidate();
        }
    }

    class C14362 implements AnimatorUpdateListener {
        C14362() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            HeartRateAnimationView.this.deltaX = ((Integer) animation.getAnimatedValue()).intValue();
            HeartRateAnimationView.this.postInvalidate();
        }
    }

    class C14373 implements AnimatorListener {
        C14373() {
        }

        public void onAnimationStart(Animator arg0) {
        }

        public void onAnimationRepeat(Animator arg0) {
        }

        public void onAnimationEnd(Animator arg0) {
            HeartRateAnimationView.this.mAnimator.start();
        }

        public void onAnimationCancel(Animator arg0) {
        }
    }

    public HeartRateAnimationView(Context context) {
        this(context, null);
    }

    public HeartRateAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartRateAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f3492T = 196;
        this.mTime = 0;
        this.mList = new ArrayList();
        this.mLength = -1;
        this.deltaX = 0;
        init(context);
    }

    public void init(Context context) {
        this.mScale = context.getResources().getDisplayMetrics().density;
        this.screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(-1);
        this.mPaint.setStrokeWidth(this.mScale * 1.5f);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mPath = new Path();
        this.mAnimator = ValueAnimator.ofInt(new int[]{0, (int) ((this.mScale * 196.0f) - 1.0f)});
        this.mAnimator.setDuration(1500);
        this.mAnimator.setRepeatCount(-1);
        this.mAnimator.setRepeatMode(1);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.addUpdateListener(new C14351());
        this.deltaAnimator = ValueAnimator.ofInt(new int[]{(int) (((float) this.screenWidth) * 1.5f), 0});
        this.deltaAnimator.setDuration((long) ((int) (((((float) this.screenWidth) * 1.5f) / (this.mScale * 196.0f)) * 1500.0f)));
        this.deltaAnimator.setInterpolator(new LinearInterpolator());
        this.deltaAnimator.addUpdateListener(new C14362());
        this.deltaAnimator.addListener(new C14373());
        this.deltaAnimator.start();
        for (int i = 0; i < 20; i++) {
            this.mList.add(new Point());
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPath.reset();
        List<Point> list = getPoint(this.mTime + 0, this.screenWidth + this.mTime);
        this.mPath.moveTo(((float) this.deltaX) - ((this.mScale * 196.0f) / 2.0f), this.mScale * 200.0f);
        for (int i = 0; i < this.mLength; i++) {
            this.mPath.lineTo((float) ((((Point) list.get(i)).x - this.mTime) + this.deltaX), ((float) (-((Point) list.get(i)).y)) + (this.mScale * 200.0f));
        }
        this.mPaint.setStyle(Style.STROKE);
        canvas.drawPath(this.mPath, this.mPaint);
        this.mPaint.setStyle(Style.FILL);
        if (this.deltaX > 0) {
            canvas.drawCircle(((float) this.screenWidth) - (this.mScale * 2.0f), (this.mScale * 200.0f) + (this.deltaX > this.screenWidth ? 0.0f : -m3377f((int) (((float) (this.screenWidth - this.deltaX)) % (this.mScale * 196.0f)))), this.mScale * 4.0f, this.mPaint);
        } else {
            canvas.drawCircle(((float) this.screenWidth) - (this.mScale * 2.0f), ((float) (-((Point) list.get(this.mLength - 1)).y)) + (this.mScale * 200.0f), this.mScale * 4.0f, this.mPaint);
        }
    }

    private float m3377f(int x) {
        if (x >= 0 && ((float) x) <= this.mScale * TitleBar.BACKBTN_LEFT_MARGIN) {
            return -1.75f * ((float) x);
        }
        if (((float) x) >= this.mScale * TitleBar.BACKBTN_LEFT_MARGIN && ((float) x) <= this.mScale * 40.0f) {
            return (6.125f * ((float) x)) - (157.5f * this.mScale);
        }
        if (((float) x) >= this.mScale * 40.0f && ((float) x) <= this.mScale * BitmapDescriptorFactory.HUE_YELLOW) {
            return (-5.125f * ((float) x)) + (292.5f * this.mScale);
        }
        if (((float) x) < this.mScale * BitmapDescriptorFactory.HUE_YELLOW || ((float) x) > 64.0f * this.mScale) {
            return 0.0f;
        }
        return (3.75f * ((float) x)) - (BitmapDescriptorFactory.HUE_BLUE * this.mScale);
    }

    private List<Point> getPoint(int startX, int endX) {
        this.mLength = 0;
        ((Point) this.mList.get(this.mLength)).set(startX, (int) m3377f(startX));
        this.mLength++;
        getPoint(startX, endX, this.mList);
        ((Point) this.mList.get(this.mLength)).set(endX, (int) m3377f(endX % ((int) (196.0f * this.mScale))));
        this.mLength++;
        return this.mList;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void getPoint(int r12, int r13, java.util.List<android.graphics.Point> r14) {
        /*
        r11 = this;
        r10 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r9 = 1114636288; // 0x42700000 float:60.0 double:5.507034975E-315;
        r8 = 1109393408; // 0x42200000 float:40.0 double:5.481131706E-315;
        r7 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r6 = 1128529920; // 0x43440000 float:196.0 double:5.57567864E-315;
        if (r12 < r13) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r1 = -1;
        r0 = -1;
        if (r12 < 0) goto L_0x0017;
    L_0x0011:
        r2 = (float) r12;
        r3 = r11.mScale;
        r3 = r3 * r6;
        r2 = r2 % r3;
        r0 = (int) r2;
    L_0x0017:
        if (r0 > 0) goto L_0x00c8;
    L_0x0019:
        r1 = 0;
    L_0x001a:
        switch(r1) {
            case 0: goto L_0x001e;
            case 1: goto L_0x0030;
            case 2: goto L_0x0053;
            case 3: goto L_0x0076;
            case 4: goto L_0x0099;
            case 5: goto L_0x0099;
            default: goto L_0x001d;
        };
    L_0x001d:
        goto L_0x000c;
    L_0x001e:
        r2 = r11.mLength;
        r2 = r14.get(r2);
        r2 = (android.graphics.Point) r2;
        r3 = 0;
        r2.set(r12, r3);
        r2 = r11.mLength;
        r2 = r2 + 1;
        r11.mLength = r2;
    L_0x0030:
        r2 = r11.mLength;
        r2 = r14.get(r2);
        r2 = (android.graphics.Point) r2;
        r3 = (float) r12;
        r4 = (float) r12;
        r5 = r11.mScale;
        r5 = r5 * r6;
        r4 = r4 % r5;
        r3 = r3 - r4;
        r4 = r11.mScale;
        r4 = r4 * r7;
        r3 = r3 + r4;
        r3 = (int) r3;
        r4 = -1039400960; // 0xffffffffc20c0000 float:-35.0 double:NaN;
        r5 = r11.mScale;
        r4 = r4 * r5;
        r4 = (int) r4;
        r2.set(r3, r4);
        r2 = r11.mLength;
        r2 = r2 + 1;
        r11.mLength = r2;
    L_0x0053:
        r2 = r11.mLength;
        r2 = r14.get(r2);
        r2 = (android.graphics.Point) r2;
        r3 = (float) r12;
        r4 = (float) r12;
        r5 = r11.mScale;
        r5 = r5 * r6;
        r4 = r4 % r5;
        r3 = r3 - r4;
        r4 = r11.mScale;
        r4 = r4 * r8;
        r3 = r3 + r4;
        r3 = (int) r3;
        r4 = 1118765056; // 0x42af0000 float:87.5 double:5.5274338E-315;
        r5 = r11.mScale;
        r4 = r4 * r5;
        r4 = (int) r4;
        r2.set(r3, r4);
        r2 = r11.mLength;
        r2 = r2 + 1;
        r11.mLength = r2;
    L_0x0076:
        r2 = r11.mLength;
        r2 = r14.get(r2);
        r2 = (android.graphics.Point) r2;
        r3 = (float) r12;
        r4 = (float) r12;
        r5 = r11.mScale;
        r5 = r5 * r6;
        r4 = r4 % r5;
        r3 = r3 - r4;
        r4 = r11.mScale;
        r4 = r4 * r9;
        r3 = r3 + r4;
        r3 = (int) r3;
        r4 = -1049624576; // 0xffffffffc1700000 float:-15.0 double:NaN;
        r5 = r11.mScale;
        r4 = r4 * r5;
        r4 = (int) r4;
        r2.set(r3, r4);
        r2 = r11.mLength;
        r2 = r2 + 1;
        r11.mLength = r2;
    L_0x0099:
        r2 = r11.mLength;
        r2 = r14.get(r2);
        r2 = (android.graphics.Point) r2;
        r3 = (float) r12;
        r4 = (float) r12;
        r5 = r11.mScale;
        r5 = r5 * r6;
        r4 = r4 % r5;
        r3 = r3 - r4;
        r4 = r11.mScale;
        r4 = r4 * r10;
        r3 = r3 + r4;
        r3 = (int) r3;
        r4 = 0;
        r2.set(r3, r4);
        r2 = r11.mLength;
        r2 = r2 + 1;
        r11.mLength = r2;
        r2 = r11.mScale;
        r2 = r2 * r6;
        r3 = (float) r12;
        r4 = r11.mScale;
        r4 = r4 * r6;
        r3 = r3 % r4;
        r2 = r2 - r3;
        r3 = (float) r12;
        r2 = r2 + r3;
        r2 = (int) r2;
        r11.getPoint(r2, r13, r14);
        goto L_0x000c;
    L_0x00c8:
        if (r0 <= 0) goto L_0x00d5;
    L_0x00ca:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r7;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 > 0) goto L_0x00d5;
    L_0x00d2:
        r1 = 1;
        goto L_0x001a;
    L_0x00d5:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r7;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 < 0) goto L_0x00e8;
    L_0x00dd:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r8;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 > 0) goto L_0x00e8;
    L_0x00e5:
        r1 = 2;
        goto L_0x001a;
    L_0x00e8:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r8;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 < 0) goto L_0x00fb;
    L_0x00f0:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r9;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 > 0) goto L_0x00fb;
    L_0x00f8:
        r1 = 3;
        goto L_0x001a;
    L_0x00fb:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r9;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 < 0) goto L_0x010e;
    L_0x0103:
        r2 = (float) r0;
        r3 = r11.mScale;
        r3 = r3 * r10;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 > 0) goto L_0x010e;
    L_0x010b:
        r1 = 4;
        goto L_0x001a;
    L_0x010e:
        r1 = 5;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhuoyou.plugin.view.HeartRateAnimationView.getPoint(int, int, java.util.List):void");
    }
}
