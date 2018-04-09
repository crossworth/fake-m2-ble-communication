package com.zhuoyou.plugin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;
import com.baidu.location.aa;
import com.fithealth.running.R;

public class ProgressButton extends Button {
    private static final int STATE_DETECTING = 1;
    private static final int STATE_NONE = -1;
    private static final int STATE_STOPED = 2;
    private Bitmap mBitmap;
    private Paint mPaint = new Paint();
    private float mPercent = 0.0f;
    private int mState = -1;
    private Context mcontext;

    public ProgressButton(Context context) {
        super(context);
        init(null, 0);
        this.mcontext = context;
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
        this.mcontext = context;
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
        this.mcontext = context;
    }

    private void init(AttributeSet attrs, int defStyle) {
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBitmap = BitmapFactory.decodeResource(this.mcontext.getResources(), R.drawable.button2_pressed);
    }

    protected void onDraw(Canvas canvas) {
        float right = this.mPercent * ((float) getWidth());
        if (this.mState != 1) {
            right = 0.0f;
        }
        canvas.save();
        canvas.clipRect(0.0f, 0.0f, right, (float) getHeight());
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mPaint);
        if (this.mState == 1) {
            setText(R.string.stop_testheart);
        } else {
            setText(R.string.test_heart);
        }
        canvas.restore();
        super.onDraw(canvas);
    }

    public boolean isDetecting() {
        return this.mState == 1;
    }

    public void update(int percent) {
        if (isDetecting()) {
            this.mPercent = ((float) percent) * aa.fK;
            if (percent >= 100) {
                this.mState = 2;
            } else {
                this.mState = 1;
            }
        } else {
            this.mPercent = 0.0f;
        }
        invalidate();
    }

    public void startDect() {
        this.mState = 1;
        invalidate();
    }

    public void stopDetect() {
        this.mState = 2;
        this.mPercent = 0.0f;
        invalidate();
    }
}
