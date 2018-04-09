package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.autonavi.amap.mapcore.interfaces.CameraAnimator;
import com.zhuoyou.plugin.running.SleepBean;
import com.zhuoyou.plugin.running.SleepItem;

public class BarChartSleep extends View {
    private int HEIGHT;
    private int WIDTH;
    private int deepColor = Color.rgb(34, 214, CameraAnimator.DEFAULT_DURATION);
    private int deep_Height;
    private Handler handler;
    private SleepItem item;
    private int lightColor = Color.rgb(0, 169, 255);
    private int light_Height;
    private int touchX;
    private int touchY;

    public BarChartSleep(Context context, SleepItem item, Handler handler) {
        super(context);
        this.item = item;
        this.handler = handler;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        if (this.item != null) {
            this.WIDTH = getWidth();
            this.HEIGHT = getHeight();
            this.deep_Height = (this.HEIGHT / 5) * 2;
            this.light_Height = this.HEIGHT / 2;
            double perPx = ((double) this.WIDTH) / ((double) this.item.getmSleepT());
            Paint paint = new Paint();
            for (int i = 0; i < this.item.getData().size(); i++) {
                SleepBean mBean = (SleepBean) this.item.getData().get(i);
                Rect mRect = new Rect();
                float left = (float) (((double) diffSecond(this.item.getmStartT(), mBean.getStartTime())) * perPx);
                float top = (float) this.light_Height;
                float right = (float) (((double) left) + (((double) diffSecond(mBean.getStartTime(), mBean.getEndTime())) * perPx));
                float bottom = (float) this.HEIGHT;
                if (mBean.isDeep()) {
                    top = (float) this.deep_Height;
                    paint.setColor(this.deepColor);
                } else {
                    paint.setColor(this.lightColor);
                }
                mRect.set((int) left, (int) top, (int) right, (int) bottom);
                Log.i("hello", "Rect:left:" + left + ",top:" + top + ",right:" + right + ",bottom:" + bottom);
                paint.setTextSize(35.0f);
                if (mRect.contains(this.touchX, this.touchY)) {
                    paint.setColor(Color.rgb(255, 98, 0));
                    Message msg = this.handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = mBean;
                    this.handler.sendMessage(msg);
                }
                paramCanvas.drawRect(mRect, paint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.touchX = (int) event.getX();
                this.touchY = (int) event.getY();
                invalidate();
                break;
            case 1:
                this.touchX = -1;
                this.touchY = -1;
                this.handler.sendEmptyMessage(2);
                invalidate();
                break;
        }
        return true;
    }

    private int diffSecond(String baseTime, String time) {
        int diffValue = (((Integer.valueOf(time.split(":")[0]).intValue() * 60) * 60) + (Integer.valueOf(time.split(":")[1]).intValue() * 60)) - (((Integer.valueOf(baseTime.split(":")[0]).intValue() * 60) * 60) + (Integer.valueOf(baseTime.split(":")[1]).intValue() * 60));
        return diffValue >= 0 ? diffValue : diffValue + 86400;
    }
}
