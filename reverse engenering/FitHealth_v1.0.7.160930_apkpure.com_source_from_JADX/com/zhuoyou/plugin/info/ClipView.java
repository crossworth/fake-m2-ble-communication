package com.zhuoyou.plugin.info;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region.Op;
import android.util.AttributeSet;
import android.view.View;

public class ClipView extends View {
    public ClipView(Context context) {
        super(context);
    }

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onDraw(Canvas canvas) {
        canvas.save();
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        paint.setColor(-1442840576);
        Path path = new Path();
        path.reset();
        path.addCircle((float) (width / 2), (float) (height / 2), (float) (width / 2), Direction.CW);
        canvas.clipPath(path, Op.XOR);
        canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, paint);
        canvas.restore();
    }
}
