package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;

public class BarChart extends View {
    private static final int BLUE = 1;
    private static final int ORANGE = 2;
    public ArrayList<Double> dataList = new ArrayList();
    private final int height;
    private final int initOffset;
    private int mColorType;
    private final double mIntervalNum;
    private final int mMaxHeight;
    private double mMaxValue = 0.0d;
    private final Paint mPaint;
    private final Path mPathLine;
    private final int mWidth;
    private final int mWindowWidth;
    private final int offsetPx;

    public BarChart(Context paramContext, ArrayList<Double> paramArrayList, int paramInt1, int paramInt2, int paramInt3) {
        super(paramContext);
        this.dataList = paramArrayList;
        if (paramInt1 == 0) {
            this.mColorType = 1;
        } else if (paramInt1 == 1) {
            this.mColorType = 2;
        }
        this.height = paramInt2;
        this.mWindowWidth = paramInt3;
        this.offsetPx = Tools.dip2px(getContext(), 22.0f);
        this.initOffset = (this.mWindowWidth / 2) - Tools.dip2px(paramContext, 23.0f);
        this.mWidth = (this.dataList.size() * Tools.dip2px(paramContext, 22.0f)) + (this.initOffset * 2);
        this.mMaxHeight = this.height - Tools.dip2px(paramContext, 22.0f);
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.mMaxValue < ((Double) this.dataList.get(i)).doubleValue()) {
                this.mMaxValue = ((Double) this.dataList.get(i)).doubleValue();
            }
        }
        this.mIntervalNum = ((double) this.mMaxHeight) / this.mMaxValue;
        this.mPaint = new Paint(1);
        this.mPathLine = new Path();
    }

    public int getCanvasWidth() {
        return this.mWidth;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        paramCanvas.drawColor(getResources().getColor(R.color.transparent));
        this.mPathLine.reset();
        this.mPaint.setPathEffect(null);
        this.mPaint.setStrokeWidth(2.0f);
        this.mPaint.setStyle(Style.FILL);
        if (this.mColorType == 1) {
            this.mPaint.setColor(Color.rgb(122, 227, 255));
        } else if (this.mColorType == 2) {
            this.mPaint.setColor(Color.rgb(255, FacebookRequestErrorClassification.EC_INVALID_TOKEN, 0));
        }
        float f1 = (float) this.initOffset;
        float f2 = (float) this.height;
        this.mPathLine.moveTo(f1, f2);
        for (int i = 0; i < this.dataList.size(); i++) {
            float f3 = ((float) this.height) - ((float) (((Double) this.dataList.get(i)).doubleValue() * this.mIntervalNum));
            this.mPathLine.lineTo(f1, f3);
            float f4 = f1 + ((float) this.offsetPx);
            this.mPathLine.lineTo(f4, f3);
            f1 = f4;
            this.mPathLine.lineTo(f1, (float) this.height);
            f2 = (float) this.height;
            paramCanvas.drawPath(this.mPathLine, this.mPaint);
            this.mPathLine.reset();
            this.mPathLine.moveTo(f1, f2);
        }
        this.mPathLine.moveTo(((float) this.initOffset) + f1, f2);
        paramCanvas.drawPath(this.mPathLine, this.mPaint);
    }
}
