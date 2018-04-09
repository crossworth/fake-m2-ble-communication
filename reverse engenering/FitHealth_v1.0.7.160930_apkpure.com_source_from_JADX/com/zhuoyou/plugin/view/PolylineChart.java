package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.media.TransportMediator;
import android.view.View;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.fithealth.running.R;
import com.tencent.open.yyb.TitleBar;
import com.zhuoyou.plugin.running.Tools;
import java.util.ArrayList;

public class PolylineChart extends View {
    private static final int BLUE = 1;
    private static final int ORANGE = 2;
    public static int mPadding;
    private static int mWidth;
    public ArrayList<Float> XList = new ArrayList();
    public ArrayList<Float> YList = new ArrayList();
    private final Context context;
    public ArrayList<Double> dataList = new ArrayList();
    private final int initOffset;
    private int mColorType;
    private final int mHeight;
    private double mIntervalNum;
    private double mMax;
    private final int mMaxHeight;
    private double mMaxValue = 0.0d;
    private double mMinValue = 0.0d;
    private final Paint mPaint;
    private final Path mPathFiller;
    private final Path mPathLine;
    private final int mWindowWidth;
    private final int minPosition;
    private final int offset = 45;

    public PolylineChart(Context paramContext, ArrayList<Double> paramArrayList, int paramInt1, int paramInt2, int paramInt3) {
        super(paramContext);
        this.context = paramContext;
        this.dataList = paramArrayList;
        if (paramInt1 == 0) {
            this.mColorType = 1;
        } else if (paramInt1 == 1) {
            this.mColorType = 2;
        }
        this.mHeight = paramInt2;
        this.mWindowWidth = paramInt3;
        this.initOffset = 18;
        mWidth = ((this.dataList.size() - 1) * Tools.dip2px(paramContext, 45.0f)) + (this.initOffset * 2);
        this.mMaxHeight = this.mHeight - (Tools.dip2px(this.context, 40.0f) * 2);
        this.minPosition = this.mHeight - Tools.dip2px(this.context, 40.0f);
        this.mMinValue = ((Double) this.dataList.get(0)).doubleValue();
        for (int i = 0; i < this.dataList.size(); i++) {
            if (this.mMaxValue < ((Double) this.dataList.get(i)).doubleValue()) {
                this.mMaxValue = ((Double) this.dataList.get(i)).doubleValue();
            }
            if (this.mMinValue > ((Double) this.dataList.get(i)).doubleValue()) {
                this.mMinValue = ((Double) this.dataList.get(i)).doubleValue();
            }
        }
        if (this.mMaxValue != this.mMinValue) {
            this.mIntervalNum = ((double) this.mMaxHeight) / (this.mMaxValue - this.mMinValue);
        } else {
            this.mIntervalNum = 0.0d;
        }
        this.mPaint = new Paint(1);
        this.mPathFiller = new Path();
        this.mPathLine = new Path();
    }

    public int getCanvasWidth() {
        return mWidth;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        float f1 = (float) this.mHeight;
        float f2 = (float) this.initOffset;
        float f3 = ((float) this.minPosition) - ((float) ((((Double) this.dataList.get(0)).doubleValue() - this.mMinValue) * this.mIntervalNum));
        this.XList.clear();
        this.YList.clear();
        this.XList.add(Float.valueOf(f2));
        this.YList.add(Float.valueOf(f3));
        this.mPathFiller.reset();
        this.mPathLine.reset();
        paramCanvas.drawColor(getResources().getColor(R.color.transparent));
        this.mPathFiller.moveTo((float) this.initOffset, f1);
        this.mPathFiller.lineTo(f2, (float) this.minPosition);
        this.mPathFiller.lineTo(f2, f3);
        this.mPathLine.moveTo(f2, f3);
        for (int i = 1; i < this.dataList.size(); i++) {
            float f4 = f2 + ((float) Tools.dip2px(this.context, TitleBar.BACKBTN_LEFT_MARGIN));
            float f5 = ((float) this.minPosition) - ((float) ((((Double) this.dataList.get(i)).doubleValue() - this.mMinValue) * this.mIntervalNum));
            this.mPathFiller.lineTo(f4, f5);
            this.mPathLine.lineTo(f4, f5);
            this.XList.add(Float.valueOf(f4));
            this.YList.add(Float.valueOf(f5));
            f2 = f4;
        }
        this.mPathFiller.lineTo(f2, (float) this.mHeight);
        if (this.mColorType == 1) {
            this.mPaint.setColor(Color.argb(51, 7, 210, 246));
        } else if (this.mColorType == 2) {
            this.mPaint.setColor(Color.argb(51, 255, FacebookRequestErrorClassification.EC_INVALID_TOKEN, 0));
        } else {
            this.mPaint.setColor(0);
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setPathEffect(null);
        this.mPaint.setStrokeWidth(1.0f);
        this.mPaint.setStyle(Style.FILL_AND_STROKE);
        paramCanvas.drawPath(this.mPathFiller, this.mPaint);
        this.mPathFiller.reset();
        if (this.mColorType == 1) {
            this.mPaint.setColor(Color.rgb(86, 198, 241));
        } else if (this.mColorType == 2) {
            this.mPaint.setColor(Color.rgb(255, TransportMediator.KEYCODE_MEDIA_PLAY, 0));
        } else {
            this.mPaint.setColor(Color.rgb(255, 156, 202));
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setPathEffect(null);
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setStyle(Style.STROKE);
        paramCanvas.drawPath(this.mPathLine, this.mPaint);
        this.mPathLine.reset();
        Paint localPaint = new Paint(1);
        localPaint.setColor(-1);
        localPaint.setAntiAlias(true);
        localPaint.setPathEffect(null);
        localPaint.setStrokeWidth(1.0f);
        localPaint.setStyle(Style.FILL_AND_STROKE);
        if (this.mColorType == 1) {
            this.mPaint.setColor(Color.rgb(88, 206, 255));
        } else if (this.mColorType == 2) {
            this.mPaint.setColor(Color.rgb(255, FacebookRequestErrorClassification.EC_INVALID_TOKEN, 0));
        } else {
            this.mPaint.setColor(Color.rgb(243, 55, 139));
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setPathEffect(null);
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setStyle(Style.STROKE);
        for (int k = 0; k < this.XList.size(); k++) {
            paramCanvas.drawCircle(((Float) this.XList.get(k)).floatValue(), ((Float) this.YList.get(k)).floatValue(), (float) Tools.dip2px(this.context, 2.0f), localPaint);
            paramCanvas.drawCircle(((Float) this.XList.get(k)).floatValue(), ((Float) this.YList.get(k)).floatValue(), (float) Tools.dip2px(this.context, 3.0f), this.mPaint);
        }
    }
}
