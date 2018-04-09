package com.zhuoyou.plugin.running.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.C1680R;
import java.util.ArrayList;
import java.util.List;

public class HeartAnimView extends View {
    private static float START_Y = 200.0f;
    public final float f5009T;
    private int colorLine;
    private int colorPoint;
    private float deltaX;
    private ValueAnimator mAnimator;
    private int mLength;
    private List<Point> mList;
    private float mScale;
    private float mTime;
    private Paint paintLine;
    private Paint paintPoint;
    private Path path;
    private float pointSize;
    private float stokeWidth;
    private float viwHeight;
    private float viwWidth;

    class C19361 implements AnimatorUpdateListener {
        C19361() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            HeartAnimView.this.mTime = ((Float) animation.getAnimatedValue()).floatValue();
            HeartAnimView.this.postInvalidate();
        }
    }

    class C19372 implements AnimatorUpdateListener {
        C19372() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            HeartAnimView.this.deltaX = ((Float) animation.getAnimatedValue()).floatValue();
            HeartAnimView.this.postInvalidate();
        }
    }

    class C19383 implements AnimatorListener {
        C19383() {
        }

        public void onAnimationStart(Animator arg0) {
        }

        public void onAnimationRepeat(Animator arg0) {
        }

        public void onAnimationEnd(Animator arg0) {
            HeartAnimView.this.mAnimator.start();
        }

        public void onAnimationCancel(Animator arg0) {
        }
    }

    public HeartAnimView(Context context) {
        this(context, null);
    }

    public HeartAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        parseAttributes(context.obtainStyledAttributes(attrs, C1680R.styleable.HeartAnimView));
    }

    public HeartAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.paintLine = new Paint();
        this.paintPoint = new Paint();
        this.path = new Path();
        this.f5009T = 196.0f;
        this.mTime = 0.0f;
        this.mList = new ArrayList();
        this.mLength = -1;
        this.deltaX = 0.0f;
        parseAttributes(context.obtainStyledAttributes(attrs, C1680R.styleable.HeartAnimView));
    }

    private void startAnim() {
        this.mAnimator = ValueAnimator.ofFloat(new float[]{0.0f, (this.mScale * 196.0f) - 1.0f});
        this.mAnimator.setDuration(1500);
        this.mAnimator.setRepeatCount(-1);
        this.mAnimator.setRepeatMode(1);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.addUpdateListener(new C19361());
        ValueAnimator deltaAnimator = ValueAnimator.ofFloat(new float[]{this.viwWidth * 1.5f, 0.0f});
        deltaAnimator.setDuration((long) ((int) (((this.viwWidth * 1.5f) / (this.mScale * 196.0f)) * 1500.0f)));
        deltaAnimator.setInterpolator(new LinearInterpolator());
        deltaAnimator.addUpdateListener(new C19372());
        deltaAnimator.addListener(new C19383());
        deltaAnimator.start();
    }

    private void parseAttributes(TypedArray a) {
        this.stokeWidth = a.getDimension(0, 2.0f);
        this.pointSize = a.getDimension(1, 4.0f);
        this.colorLine = a.getColor(2, -1);
        this.colorPoint = a.getColor(3, -1);
        a.recycle();
    }

    private void setupPoints() {
        this.paintLine.setColor(this.colorLine);
        this.paintLine.setStyle(Style.STROKE);
        this.paintLine.setStrokeWidth(this.stokeWidth);
        this.paintLine.setAntiAlias(true);
        this.paintPoint.setColor(this.colorPoint);
        this.paintPoint.setStyle(Style.FILL);
        this.paintPoint.setAntiAlias(true);
    }

    private void setupBounds() {
        this.mScale = getContext().getResources().getDisplayMetrics().density;
        for (int i = 0; i < 20; i++) {
            this.mList.add(new Point());
        }
        START_Y = this.viwHeight / 2.0f;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viwWidth = (float) w;
        this.viwHeight = (float) h;
        setupPoints();
        setupBounds();
        startAnim();
    }

    protected void onDraw(Canvas canvas) {
        float y = 0.0f;
        super.onDraw(canvas);
        this.path.reset();
        List<Point> list = getPoint(this.mTime, this.viwWidth + this.mTime);
        this.path.moveTo(this.deltaX - ((this.mScale * 196.0f) / 2.0f), START_Y);
        for (int i = 0; i < this.mLength % 19; i++) {
            this.path.lineTo(this.deltaX + (((float) ((Point) list.get(i)).x) - this.mTime), ((float) (-((Point) list.get(i)).y)) + START_Y);
        }
        canvas.drawPath(this.path, this.paintLine);
        if (this.deltaX > 0.0f) {
            if (this.deltaX <= this.viwWidth) {
                y = -m4560f((this.viwWidth - this.deltaX) % (this.mScale * 196.0f));
            }
            canvas.drawCircle(this.viwWidth - this.pointSize, START_Y + y, this.pointSize, this.paintPoint);
            return;
        }
        canvas.drawCircle(this.viwWidth - this.pointSize, ((float) (-((Point) list.get((this.mLength - 1) % 19)).y)) + START_Y, this.pointSize, this.paintPoint);
    }

    private float m4560f(float x) {
        if (x >= 0.0f && x <= this.mScale * TitleBar.BACKBTN_LEFT_MARGIN) {
            return -1.75f * x;
        }
        if (x >= this.mScale * TitleBar.BACKBTN_LEFT_MARGIN && x <= this.mScale * 40.0f) {
            return (6.125f * x) - (157.5f * this.mScale);
        }
        if (x >= this.mScale * 40.0f && x <= this.mScale * 60.0f) {
            return (-5.125f * x) + (292.5f * this.mScale);
        }
        if (x < this.mScale * 60.0f || x > 64.0f * this.mScale) {
            return 0.0f;
        }
        return (3.75f * x) - (240.0f * this.mScale);
    }

    private List<Point> getPoint(float startX, float endX) {
        this.mLength = 0;
        ((Point) this.mList.get(this.mLength % 19)).set((int) startX, (int) m4560f(startX));
        this.mLength++;
        getPoint(startX, endX, this.mList);
        ((Point) this.mList.get(this.mLength % 19)).set((int) endX, (int) m4560f(endX % ((float) ((int) (196.0f * this.mScale)))));
        this.mLength++;
        return this.mList;
    }

    private void getPoint(float startX, float endX, List<Point> list) {
        if (startX < endX && this.mLength <= 19) {
            ((Point) list.get(this.mLength % 19)).set((int) startX, 0);
            this.mLength++;
            ((Point) list.get(this.mLength % 19)).set((int) ((startX - (startX % (this.mScale * 196.0f))) + (TitleBar.BACKBTN_LEFT_MARGIN * this.mScale)), (int) (-35.0f * this.mScale));
            this.mLength++;
            ((Point) list.get(this.mLength % 19)).set((int) ((startX - (startX % (this.mScale * 196.0f))) + (40.0f * this.mScale)), (int) (87.5f * this.mScale));
            this.mLength++;
            ((Point) list.get(this.mLength % 19)).set((int) ((startX - (startX % (this.mScale * 196.0f))) + (60.0f * this.mScale)), (int) (-15.0f * this.mScale));
            this.mLength++;
            ((Point) list.get(this.mLength % 19)).set((int) ((startX - (startX % (this.mScale * 196.0f))) + (64.0f * this.mScale)), 0);
            this.mLength++;
            getPoint((float) ((int) (((this.mScale * 196.0f) - (startX % (this.mScale * 196.0f))) + startX)), endX, list);
        }
    }
}
