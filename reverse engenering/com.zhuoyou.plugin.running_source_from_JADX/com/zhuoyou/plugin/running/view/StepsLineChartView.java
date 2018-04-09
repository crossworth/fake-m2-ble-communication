package com.zhuoyou.plugin.running.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.Tools;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.view.LineChartView;

public class StepsLineChartView extends LineChartView {
    private static final int COLOR_LABEL = -15817730;
    private static final int COLOR_LINE = -2130706433;
    private static final int COLOR_TEXT = -1;
    private Bitmap bitmap;
    private Bitmap bitmap1;
    private int line = 10000;
    private Paint linePaint = new Paint();
    private Path linePath = new Path();
    private Rect rect = new Rect();
    private Paint textPaint = new Paint();
    private int viewWidth;

    public StepsLineChartView(Context context) {
        super(context);
        init();
    }

    public StepsLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepsLineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setLine(int line) {
        this.line = line;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lineY = getChartComputator().computeRawY((float) this.line);
        this.linePath.moveTo((float) getPaddingLeft(), lineY);
        this.linePath.lineTo((float) (this.viewWidth - getPaddingRight()), lineY);
        canvas.drawPath(this.linePath, this.linePaint);
        this.linePath.reset();
        this.textPaint.setColor(-1);
        canvas.drawText((this.line / 1000) + "k", TitleBar.SHAREBTN_RIGHT_MARGIN, lineY - TitleBar.SHAREBTN_RIGHT_MARGIN, this.textPaint);
        this.textPaint.setColor(COLOR_LABEL);
        SelectedValue value = getSelectedValue();
        if (value.isSet()) {
            PointValue point = (PointValue) ((Line) getLineChartData().getLines().get(value.getFirstIndex())).getValues().get(value.getSecondIndex());
            String text = String.valueOf((int) point.getY());
            this.textPaint.getTextBounds(text, 0, text.length(), this.rect);
            float labelX = getChartComputator().computeRawX(point.getX());
            float labelY = getChartComputator().computeRawY(point.getY());
            float space = (float) Tools.dip2px(8.0f);
            float textX = labelX - ((float) (this.rect.width() / 2));
            float bitmapX = labelX - ((float) (this.bitmap.getWidth() / 2));
            float textY;
            float bitmapY;
            if (labelY < ((float) this.bitmap.getHeight()) + space) {
                textY = ((((float) ((this.bitmap.getHeight() - this.rect.height()) / 2)) + labelY) + ((float) this.rect.height())) + space;
                bitmapY = labelY + space;
                canvas.drawBitmap(this.bitmap1, bitmapX, bitmapY, this.textPaint);
                canvas.drawText(text, textX, textY, this.textPaint);
                return;
            }
            textY = (labelY - ((float) ((this.bitmap.getHeight() - this.rect.height()) / 2))) - space;
            bitmapY = (labelY - ((float) this.bitmap.getHeight())) - space;
            canvas.drawBitmap(this.bitmap, bitmapX, bitmapY, this.textPaint);
            canvas.drawText(text, textX, textY, this.textPaint);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.viewWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    private void init() {
        this.bitmap = BitmapFactory.decodeResource(getResources(), C1680R.drawable.ic_label);
        this.bitmap1 = BitmapFactory.decodeResource(getResources(), C1680R.drawable.ic_label1);
        this.linePaint.setColor(COLOR_LINE);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStyle(Style.STROKE);
        this.linePaint.setStrokeWidth(2.0f);
        this.linePaint.setPathEffect(new DashPathEffect(new float[]{TitleBar.SHAREBTN_RIGHT_MARGIN, TitleBar.SHAREBTN_RIGHT_MARGIN}, 1.0f));
        this.textPaint.setColor(-1);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize((float) Tools.dip2px(12.0f));
    }
}
