package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.tencent.open.yyb.TitleBar;

public class HeartRateBarView extends View {
    private Context mContext;

    public HeartRateBarView(Context context) {
        super(context);
        this.mContext = context;
    }

    public HeartRateBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public HeartRateBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint painta = new Paint();
        painta.setAntiAlias(true);
        painta.setColor(Color.rgb(166, 145, 83));
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, 160.0f, TitleBar.SHAREBTN_RIGHT_MARGIN), 5.0f, 5.0f, painta);
        Paint paintb = new Paint();
        paintb.setAntiAlias(true);
        paintb.setColor(Color.rgb(255, 159, 128));
        canvas.drawRoundRect(new RectF(160.0f, 0.0f, 320.0f, TitleBar.SHAREBTN_RIGHT_MARGIN), 5.0f, 5.0f, paintb);
        Paint paintc = new Paint();
        paintc.setAntiAlias(true);
        paintc.setColor(Color.rgb(255, 191, 128));
        canvas.drawRoundRect(new RectF(320.0f, 0.0f, 480.0f, TitleBar.SHAREBTN_RIGHT_MARGIN), 5.0f, 5.0f, paintc);
        Paint paintd = new Paint();
        paintd.setAntiAlias(true);
        paintd.setColor(Color.rgb(76, 57, 38));
        canvas.drawRoundRect(new RectF(480.0f, 0.0f, 640.0f, TitleBar.SHAREBTN_RIGHT_MARGIN), 5.0f, 5.0f, paintd);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
