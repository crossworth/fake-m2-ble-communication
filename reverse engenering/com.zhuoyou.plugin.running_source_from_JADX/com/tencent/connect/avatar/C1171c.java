package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.tencent.open.yyb.TitleBar;

/* compiled from: ProGuard */
public class C1171c extends ImageView {
    final String f3631a = "TouchView";
    public boolean f3632b = false;
    private Matrix f3633c = new Matrix();
    private Matrix f3634d = new Matrix();
    private int f3635e = 0;
    private float f3636f = 1.0f;
    private float f3637g = 1.0f;
    private Bitmap f3638h;
    private boolean f3639i = false;
    private float f3640j;
    private float f3641k;
    private PointF f3642l = new PointF();
    private PointF f3643m = new PointF();
    private float f3644n = 1.0f;
    private float f3645o = 0.0f;
    private Rect f3646p = new Rect();

    /* compiled from: ProGuard */
    class C11701 implements Runnable {
        final /* synthetic */ C1171c f3630a;

        /* compiled from: ProGuard */
        class C11691 implements Runnable {
            final /* synthetic */ C11701 f3629a;

            C11691(C11701 c11701) {
                this.f3629a = c11701;
            }

            public void run() {
                this.f3629a.f3630a.clearAnimation();
                this.f3629a.f3630a.m3449b();
            }
        }

        C11701(C1171c c1171c) {
            this.f3630a = c1171c;
        }

        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.f3630a.post(new C11691(this));
            this.f3630a.f3639i = false;
        }
    }

    public C1171c(Context context) {
        super(context);
        getDrawingRect(this.f3646p);
        m3445a();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.f3638h = bitmap;
        if (bitmap != null) {
            this.f3638h = bitmap;
        }
    }

    private float m3444a(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private void m3445a() {
    }

    public void m3451a(Rect rect) {
        this.f3646p = rect;
        if (this.f3638h != null) {
            m3450c();
        }
    }

    private void m3446a(PointF pointF) {
        float f = 1.0f;
        if (this.f3638h != null) {
            float[] fArr = new float[9];
            this.f3633c.getValues(fArr);
            float f2 = fArr[2];
            float f3 = fArr[5];
            float f4 = fArr[0];
            float width = ((float) this.f3638h.getWidth()) * f4;
            float height = ((float) this.f3638h.getHeight()) * f4;
            f4 = ((float) this.f3646p.left) - f2;
            if (f4 <= 1.0f) {
                f4 = 1.0f;
            }
            f2 = (f2 + width) - ((float) this.f3646p.right);
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            width = ((f4 * ((float) this.f3646p.width())) / (f2 + f4)) + ((float) this.f3646p.left);
            f2 = ((float) this.f3646p.top) - f3;
            f4 = (f3 + height) - ((float) this.f3646p.bottom);
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            if (f4 > 1.0f) {
                f = f4;
            }
            pointF.set(width, ((((float) this.f3646p.height()) * f2) / (f2 + f)) + ((float) this.f3646p.top));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.f3639i) {
            switch (motionEvent.getAction() & 255) {
                case 0:
                    this.f3633c.set(getImageMatrix());
                    this.f3634d.set(this.f3633c);
                    this.f3642l.set(motionEvent.getX(), motionEvent.getY());
                    this.f3635e = 1;
                    break;
                case 1:
                case 6:
                    m3449b();
                    this.f3635e = 0;
                    break;
                case 2:
                    if (this.f3635e != 1) {
                        if (this.f3635e == 2) {
                            this.f3633c.set(this.f3633c);
                            float a = m3444a(motionEvent);
                            if (a > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                                this.f3633c.set(this.f3634d);
                                a /= this.f3644n;
                                this.f3633c.postScale(a, a, this.f3643m.x, this.f3643m.y);
                            }
                            setImageMatrix(this.f3633c);
                            break;
                        }
                    }
                    this.f3633c.set(this.f3634d);
                    this.f3633c.postTranslate(motionEvent.getX() - this.f3642l.x, motionEvent.getY() - this.f3642l.y);
                    setImageMatrix(this.f3633c);
                    break;
                    break;
                case 5:
                    this.f3644n = m3444a(motionEvent);
                    if (this.f3644n > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                        this.f3634d.set(this.f3633c);
                        m3446a(this.f3643m);
                        this.f3635e = 2;
                        break;
                    }
                    break;
            }
            this.f3632b = true;
        }
        return true;
    }

    private void m3449b() {
        if (this.f3638h != null) {
            float width = (float) this.f3646p.width();
            float height = (float) this.f3646p.height();
            float[] fArr = new float[9];
            this.f3633c.getValues(fArr);
            float f = fArr[2];
            float f2 = fArr[5];
            float f3 = fArr[0];
            Animation animation = null;
            if (f3 > this.f3636f) {
                this.f3645o = this.f3636f / f3;
                this.f3633c.postScale(this.f3645o, this.f3645o, this.f3643m.x, this.f3643m.y);
                setImageMatrix(this.f3633c);
                animation = new ScaleAnimation(1.0f / this.f3645o, 1.0f, 1.0f / this.f3645o, 1.0f, this.f3643m.x, this.f3643m.y);
            } else if (f3 < this.f3637g) {
                this.f3645o = this.f3637g / f3;
                this.f3633c.postScale(this.f3645o, this.f3645o, this.f3643m.x, this.f3643m.y);
                animation = new ScaleAnimation(1.0f, this.f3645o, 1.0f, this.f3645o, this.f3643m.x, this.f3643m.y);
            } else {
                Object obj = null;
                float width2 = ((float) this.f3638h.getWidth()) * f3;
                f3 *= (float) this.f3638h.getHeight();
                float f4 = ((float) this.f3646p.left) - f;
                float f5 = ((float) this.f3646p.top) - f2;
                if (f4 < 0.0f) {
                    f = (float) this.f3646p.left;
                    obj = 1;
                }
                if (f5 < 0.0f) {
                    f2 = (float) this.f3646p.top;
                    obj = 1;
                }
                f5 = f3 - f5;
                if (width2 - f4 < width) {
                    f = ((float) this.f3646p.left) - (width2 - width);
                    obj = 1;
                }
                if (f5 < height) {
                    f2 = ((float) this.f3646p.top) - (f3 - height);
                    obj = 1;
                }
                if (obj != null) {
                    float f6 = fArr[2] - f;
                    width = fArr[5] - f2;
                    fArr[2] = f;
                    fArr[5] = f2;
                    this.f3633c.setValues(fArr);
                    setImageMatrix(this.f3633c);
                    animation = new TranslateAnimation(f6, 0.0f, width, 0.0f);
                } else {
                    setImageMatrix(this.f3633c);
                }
            }
            if (animation != null) {
                this.f3639i = true;
                animation.setDuration(300);
                startAnimation(animation);
                new Thread(new C11701(this)).start();
            }
        }
    }

    private void m3450c() {
        if (this.f3638h != null) {
            float[] fArr = new float[9];
            this.f3633c.getValues(fArr);
            float max = Math.max(((float) this.f3646p.width()) / ((float) this.f3638h.getWidth()), ((float) this.f3646p.height()) / ((float) this.f3638h.getHeight()));
            this.f3640j = ((float) this.f3646p.left) - (((((float) this.f3638h.getWidth()) * max) - ((float) this.f3646p.width())) / 2.0f);
            this.f3641k = ((float) this.f3646p.top) - (((((float) this.f3638h.getHeight()) * max) - ((float) this.f3646p.height())) / 2.0f);
            fArr[2] = this.f3640j;
            fArr[5] = this.f3641k;
            fArr[4] = max;
            fArr[0] = max;
            this.f3633c.setValues(fArr);
            this.f3636f = Math.min(2048.0f / ((float) this.f3638h.getWidth()), 2048.0f / ((float) this.f3638h.getHeight()));
            this.f3637g = max;
            if (this.f3636f < this.f3637g) {
                this.f3636f = this.f3637g;
            }
            setImageMatrix(this.f3633c);
        }
    }
}
