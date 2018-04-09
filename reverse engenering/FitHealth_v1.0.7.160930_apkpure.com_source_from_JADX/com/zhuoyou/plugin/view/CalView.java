package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.fithealth.running.R;

public class CalView extends View {
    private int height;
    private boolean isShowBack;
    private boolean isShowCircle;
    private Context mContext;
    private Paint paint;
    private String text;
    private int textColor;

    public CalView(Context context) {
        super(context);
    }

    public CalView(Context context, String text, boolean isShowCircle, boolean isShowBack, int textColor, int height) {
        super(context);
        this.mContext = context;
        this.paint = new Paint();
        this.isShowBack = isShowBack;
        this.isShowCircle = isShowCircle;
        this.text = text;
        this.textColor = textColor;
        this.height = height;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawBigBack(canvas);
        onDrawText(canvas);
        onDrawCircle(canvas);
    }

    public void onDrawBigBack(Canvas canvas) {
        if (this.isShowBack) {
            this.paint.setColor(Color.parseColor("#dff7eb"));
        } else {
            this.paint.setColor(Color.parseColor("#ffffffff"));
        }
        canvas.drawRect(new Rect(0, 0, this.height, this.height), this.paint);
    }

    public void onDrawText(Canvas canvas) {
        Log.i("zoujian", this.height + "");
        this.paint.setTextSize((float) (((double) this.height) / 3.5d));
        float width = this.paint.measureText(this.text);
        Bitmap bitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.cal_today_bg);
        if (this.textColor == 1) {
            canvas.drawBitmap(bitmap, (float) (this.height / 3), (float) (this.height / 4), this.paint);
            this.paint.setColor(Color.parseColor("#FFFFFFFF"));
        } else if (this.textColor == 2) {
            this.paint.setColor(Color.parseColor("#616b75"));
        } else if (this.textColor == 3) {
            this.paint.setColor(Color.parseColor("#c7cacd"));
        }
        canvas.drawText(this.text, (((float) (this.height / 2)) - (width / 2.0f)) + 2.0f, (float) ((this.height / 2) + 5), this.paint);
    }

    public void onDrawCircle(Canvas canvas) {
        float width = this.paint.measureText(this.text);
        Bitmap cricleBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.mark_bg);
        if (this.isShowCircle) {
            canvas.drawBitmap(cricleBitmap, ((float) (this.height / 2)) - (width / 3.0f), (float) ((this.height - (this.height / 3)) + 5), this.paint);
        }
    }
}
