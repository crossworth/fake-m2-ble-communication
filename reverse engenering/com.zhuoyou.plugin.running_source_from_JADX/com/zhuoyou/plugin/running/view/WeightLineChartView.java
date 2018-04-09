package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.zhuoyou.plugin.running.tools.Tools;
import lecho.lib.hellocharts.view.LineChartView;

public class WeightLineChartView extends LineChartView {
    private static final int COLOR_LINE = -1;
    private int line;
    private Paint linePaint;
    private int viewWidth;

    public WeightLineChartView(Context context) {
        this(context, null, 0);
        init(context);
    }

    public WeightLineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public WeightLineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.line = 70;
        init(context);
    }

    public void setLine(int line) {
        this.line = line;
    }

    protected void onDraw(Canvas canvas) {
        float lineY = getChartComputator().computeRawY((float) this.line);
        canvas.drawLine((float) Tools.dip2px(40.0f), lineY, (float) (this.viewWidth - Tools.dip2px(50.0f)), lineY, this.linePaint);
        canvas.drawText(this.line + "㎏", (float) (this.viewWidth - Tools.dip2px(42.0f)), ((float) (Tools.dip2px(12.0f) / 2)) + lineY, this.linePaint);
        super.onDraw(canvas);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.viewWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    private void init(Context context) {
        this.linePaint = new Paint();
        this.linePaint.setColor(-1);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStrokeWidth(1.0f);
        this.linePaint.setTextSize((float) Tools.dip2px(12.0f));
    }
}
