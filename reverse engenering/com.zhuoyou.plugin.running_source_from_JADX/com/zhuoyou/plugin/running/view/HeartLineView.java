package com.zhuoyou.plugin.running.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import com.zhuoyou.plugin.running.C1680R;

public class HeartLineView extends View {
    private int colorLine;
    private int colorPoint;
    private float controlY;
    private float endY;
    private float lineSize;
    private float offset;
    private Paint paintLine = new Paint();
    private Paint paintPoint = new Paint();
    private Path path = new Path();
    private float pointSize;
    private float startY;
    private float stokeWidth;
    private float viwWidth;

    class C19391 implements AnimatorUpdateListener {
        C19391() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            HeartLineView.this.offset = ((Float) animation.getAnimatedValue()).floatValue();
            HeartLineView.this.invalidate();
        }
    }

    public HeartLineView(Context context) {
        super(context);
    }

    public HeartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs, C1680R.styleable.HeartLineView));
    }

    public HeartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context.obtainStyledAttributes(attrs, C1680R.styleable.HeartLineView));
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
        this.offset = this.viwWidth;
        this.lineSize = this.viwWidth / 5.0f;
        this.startY = this.viwWidth / 2.0f;
        this.controlY = (this.viwWidth / 2.0f) - (this.lineSize * 2.0f);
        this.endY = this.viwWidth / 2.0f;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viwWidth = (float) w;
        setupPoints();
        setupBounds();
        startAnim();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{this.viwWidth, 0.0f});
        animator.setRepeatMode(1);
        animator.setDuration(2000);
        animator.addUpdateListener(new C19391());
        animator.start();
    }

    protected void onDraw(Canvas canvas) {
        this.path.reset();
        float startX = this.offset;
        float controlX = (this.lineSize / 2.0f) + this.offset;
        float controlY = (this.viwWidth / 2.0f) - (this.lineSize * 2.0f);
        float endX = this.lineSize + this.offset;
        this.path.moveTo(startX, this.startY);
        this.path.quadTo(controlX, controlY, endX, this.endY);
        controlX += this.lineSize;
        endX += this.lineSize;
        controlY = (this.viwWidth / 2.0f) + (this.lineSize * 2.0f);
        this.path.moveTo(startX + this.lineSize, this.startY);
        this.path.quadTo(controlX, controlY, endX, this.endY);
        canvas.drawPath(this.path, this.paintLine);
    }
}
