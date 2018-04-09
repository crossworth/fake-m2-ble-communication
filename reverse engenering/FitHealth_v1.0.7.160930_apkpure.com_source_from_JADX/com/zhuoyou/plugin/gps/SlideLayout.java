package com.zhuoyou.plugin.gps;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SlideLayout extends RelativeLayout {
    public static final int MSG_LOCK_SUCESS = 291;
    private int ImageBOTTOM;
    private int ImageLEFT;
    private int ImageRIGHT;
    private int ImageTOP;
    private int ImageWIDTH;
    private int VIEW_RIGHT;
    private int imageOff;
    private int ini_image_left;
    private boolean isContain;
    private boolean isSpliding;
    private Handler mHandler = new C12831();
    private ImageView mImageView;
    private int moveX;
    private Handler sHandler;

    class C12831 extends Handler {
        C12831() {
        }

        public void handleMessage(Message msg) {
            SlideLayout.this.invalidate();
        }
    }

    class C12842 implements Runnable {
        C12842() {
        }

        public void run() {
            while (SlideLayout.this.ImageLEFT > SlideLayout.this.ini_image_left) {
                if (SlideLayout.this.isContain) {
                    SlideLayout.this.isSpliding = false;
                    return;
                }
                SlideLayout.this.isSpliding = true;
                SlideLayout.this.moveX = SlideLayout.this.moveX - 5;
                if (SlideLayout.this.moveX < SlideLayout.this.ini_image_left) {
                    SlideLayout.this.moveX = SlideLayout.this.ini_image_left;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SlideLayout.this.mHandler.sendEmptyMessage(0);
            }
        }
    }

    public SlideLayout(Context context) {
        super(context);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        this.VIEW_RIGHT = getRight();
        this.ImageTOP = this.mImageView.getTop();
        this.ImageBOTTOM = this.mImageView.getBottom();
        this.ImageRIGHT = this.mImageView.getRight();
        this.ImageLEFT = this.mImageView.getLeft();
        this.ImageWIDTH = this.ImageRIGHT - this.ImageLEFT;
        if (this.isContain && this.moveX >= this.ini_image_left && this.moveX < this.VIEW_RIGHT) {
            this.ImageLEFT = this.moveX;
            this.ImageRIGHT = this.ImageLEFT + this.ImageWIDTH;
        }
        if (this.isSpliding) {
            this.ImageLEFT = this.moveX;
            this.ImageRIGHT = this.ImageLEFT + this.ImageWIDTH;
        }
        this.mImageView.layout(this.ImageLEFT, this.ImageTOP, this.ImageRIGHT, this.ImageBOTTOM);
        returnImage();
    }

    private void isSlipFinish() {
        if (((double) (this.VIEW_RIGHT - this.ImageRIGHT)) < 0.4d * ((double) this.ImageWIDTH) && this.sHandler != null) {
            this.isContain = false;
            this.moveX = this.ini_image_left;
            this.ImageLEFT = this.ini_image_left;
            this.ImageRIGHT = this.ImageLEFT + this.ImageWIDTH;
            this.mImageView.layout(this.ImageLEFT, this.ImageTOP, this.ImageRIGHT, this.ImageBOTTOM);
            this.sHandler.sendEmptyMessage(291);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.isContain = isClickImage(event);
                break;
            case 1:
                isSlipFinish();
                this.isContain = false;
                returnImage();
                break;
            case 2:
                int curX = ((int) event.getX()) - this.imageOff;
                if (this.isContain) {
                    if (curX >= this.ini_image_left) {
                        if (this.ImageWIDTH + curX <= this.VIEW_RIGHT - this.ini_image_left) {
                            this.moveX = curX;
                            break;
                        }
                        this.moveX = (this.VIEW_RIGHT - this.ini_image_left) - this.ImageWIDTH;
                        break;
                    }
                    this.moveX = this.ini_image_left;
                    break;
                }
                break;
        }
        invalidate();
        return true;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.ini_image_left = this.mImageView.getLeft();
    }

    private boolean isClickImage(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (this.ImageLEFT >= x || x >= this.ImageRIGHT || this.ImageTOP >= y || y >= this.ImageBOTTOM) {
            this.imageOff = 0;
            return false;
        }
        this.imageOff = x - this.ImageLEFT;
        return true;
    }

    private void returnImage() {
        new Thread(new C12842()).start();
    }

    public void setMainHandler(Handler handler) {
        this.sHandler = handler;
    }
}
