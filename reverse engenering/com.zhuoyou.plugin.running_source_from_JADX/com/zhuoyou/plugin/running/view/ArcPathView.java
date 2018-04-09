package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.util.AttributeSet;
import android.view.View;
import com.zhuoyou.plugin.running.C1680R;

public class ArcPathView extends View {
    private Paint arcPaint = new Paint();
    private Path path = new Path();
    private float viewHeight;
    private float viewWdith;

    public ArcPathView(Context context) {
        super(context);
        setupPaints();
    }

    public ArcPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaints();
    }

    public ArcPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupPaints();
    }

    private void setupPaints() {
        this.arcPaint.setColor(getResources().getColor(C1680R.color.main_style_background));
        this.arcPaint.setStyle(Style.FILL);
        this.arcPaint.setAntiAlias(true);
    }

    private void setupBounds() {
        float startY = this.viewHeight;
        float controlX = this.viewWdith / 2.0f;
        float controlY = -this.viewHeight;
        float endX = this.viewWdith;
        float endY = this.viewHeight;
        this.path.moveTo(0.0f, startY);
        this.path.quadTo(controlX, controlY, endX, endY);
        this.path.setFillType(FillType.WINDING);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWdith = (float) w;
        this.viewHeight = (float) h;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        setupBounds();
        canvas.drawPath(this.path, this.arcPaint);
        canvas.restore();
        this.path.reset();
    }
}
