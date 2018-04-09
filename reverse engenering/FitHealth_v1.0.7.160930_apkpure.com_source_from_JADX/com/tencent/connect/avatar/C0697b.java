package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.tencent.open.yyb.TitleBar;

/* compiled from: ProGuard */
public class C0697b extends ImageView {
    final String f2416a = "TouchView";
    public boolean f2417b = false;
    private Matrix f2418c = new Matrix();
    private Matrix f2419d = new Matrix();
    private int f2420e = 0;
    private float f2421f = 1.0f;
    private float f2422g = 1.0f;
    private Bitmap f2423h;
    private boolean f2424i = false;
    private float f2425j;
    private float f2426k;
    private PointF f2427l = new PointF();
    private PointF f2428m = new PointF();
    private float f2429n = 1.0f;
    private float f2430o = 0.0f;
    private Rect f2431p = new Rect();

    /* compiled from: ProGuard */
    class C06961 implements Runnable {
        final /* synthetic */ C0697b f2415a;

        /* compiled from: ProGuard */
        class C06951 implements Runnable {
            final /* synthetic */ C06961 f2414a;

            C06951(C06961 c06961) {
                this.f2414a = c06961;
            }

            public void run() {
                this.f2414a.f2415a.clearAnimation();
                this.f2414a.f2415a.m2370b();
            }
        }

        C06961(C0697b c0697b) {
            this.f2415a = c0697b;
        }

        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.f2415a.post(new C06951(this));
            this.f2415a.f2424i = false;
        }
    }

    public C0697b(Context context) {
        super(context);
        getDrawingRect(this.f2431p);
        m2366a();
    }

    public C0697b(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getDrawingRect(this.f2431p);
        m2366a();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.f2423h = bitmap;
        if (bitmap != null) {
            this.f2423h = bitmap;
        }
    }

    private float m2365a(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return FloatMath.sqrt((x * x) + (y * y));
    }

    private void m2366a() {
    }

    public void m2372a(Rect rect) {
        this.f2431p = rect;
        if (this.f2423h != null) {
            m2371c();
        }
    }

    private void m2367a(PointF pointF) {
        float f = 1.0f;
        if (this.f2423h != null) {
            float[] fArr = new float[9];
            this.f2418c.getValues(fArr);
            float f2 = fArr[2];
            float f3 = fArr[5];
            float f4 = fArr[0];
            float width = ((float) this.f2423h.getWidth()) * f4;
            float height = ((float) this.f2423h.getHeight()) * f4;
            f4 = ((float) this.f2431p.left) - f2;
            if (f4 <= 1.0f) {
                f4 = 1.0f;
            }
            f2 = (f2 + width) - ((float) this.f2431p.right);
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            width = ((f4 * ((float) this.f2431p.width())) / (f2 + f4)) + ((float) this.f2431p.left);
            f2 = ((float) this.f2431p.top) - f3;
            f4 = (f3 + height) - ((float) this.f2431p.bottom);
            if (f2 <= 1.0f) {
                f2 = 1.0f;
            }
            if (f4 > 1.0f) {
                f = f4;
            }
            pointF.set(width, ((((float) this.f2431p.height()) * f2) / (f2 + f)) + ((float) this.f2431p.top));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.f2424i) {
            switch (motionEvent.getAction() & 255) {
                case 0:
                    this.f2418c.set(getImageMatrix());
                    this.f2419d.set(this.f2418c);
                    this.f2427l.set(motionEvent.getX(), motionEvent.getY());
                    this.f2420e = 1;
                    break;
                case 1:
                case 6:
                    m2370b();
                    this.f2420e = 0;
                    break;
                case 2:
                    if (this.f2420e != 1) {
                        if (this.f2420e == 2) {
                            this.f2418c.set(this.f2418c);
                            float a = m2365a(motionEvent);
                            if (a > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                                this.f2418c.set(this.f2419d);
                                a /= this.f2429n;
                                this.f2418c.postScale(a, a, this.f2428m.x, this.f2428m.y);
                            }
                            setImageMatrix(this.f2418c);
                            break;
                        }
                    }
                    this.f2418c.set(this.f2419d);
                    this.f2418c.postTranslate(motionEvent.getX() - this.f2427l.x, motionEvent.getY() - this.f2427l.y);
                    setImageMatrix(this.f2418c);
                    break;
                    break;
                case 5:
                    this.f2429n = m2365a(motionEvent);
                    if (this.f2429n > TitleBar.SHAREBTN_RIGHT_MARGIN) {
                        this.f2419d.set(this.f2418c);
                        m2367a(this.f2428m);
                        this.f2420e = 2;
                        break;
                    }
                    break;
            }
            this.f2417b = true;
        }
        return true;
    }

    private void m2370b() {
        if (this.f2423h != null) {
            float width = (float) this.f2431p.width();
            float height = (float) this.f2431p.height();
            float[] fArr = new float[9];
            this.f2418c.getValues(fArr);
            float f = fArr[2];
            float f2 = fArr[5];
            float f3 = fArr[0];
            Animation animation = null;
            if (f3 > this.f2421f) {
                this.f2430o = this.f2421f / f3;
                this.f2418c.postScale(this.f2430o, this.f2430o, this.f2428m.x, this.f2428m.y);
                setImageMatrix(this.f2418c);
                animation = new ScaleAnimation(1.0f / this.f2430o, 1.0f, 1.0f / this.f2430o, 1.0f, this.f2428m.x, this.f2428m.y);
            } else if (f3 < this.f2422g) {
                this.f2430o = this.f2422g / f3;
                this.f2418c.postScale(this.f2430o, this.f2430o, this.f2428m.x, this.f2428m.y);
                animation = new ScaleAnimation(1.0f, this.f2430o, 1.0f, this.f2430o, this.f2428m.x, this.f2428m.y);
            } else {
                Object obj = null;
                float width2 = ((float) this.f2423h.getWidth()) * f3;
                f3 *= (float) this.f2423h.getHeight();
                float f4 = ((float) this.f2431p.left) - f;
                float f5 = ((float) this.f2431p.top) - f2;
                if (f4 < 0.0f) {
                    f = (float) this.f2431p.left;
                    obj = 1;
                }
                if (f5 < 0.0f) {
                    f2 = (float) this.f2431p.top;
                    obj = 1;
                }
                f5 = f3 - f5;
                if (width2 - f4 < width) {
                    f = ((float) this.f2431p.left) - (width2 - width);
                    obj = 1;
                }
                if (f5 < height) {
                    f2 = ((float) this.f2431p.top) - (f3 - height);
                    obj = 1;
                }
                if (obj != null) {
                    float f6 = fArr[2] - f;
                    width = fArr[5] - f2;
                    fArr[2] = f;
                    fArr[5] = f2;
                    this.f2418c.setValues(fArr);
                    setImageMatrix(this.f2418c);
                    animation = new TranslateAnimation(f6, 0.0f, width, 0.0f);
                } else {
                    setImageMatrix(this.f2418c);
                }
            }
            if (animation != null) {
                this.f2424i = true;
                animation.setDuration(300);
                startAnimation(animation);
                new Thread(new C06961(this)).start();
            }
        }
    }

    private void m2371c() {
        if (this.f2423h != null) {
            float[] fArr = new float[9];
            this.f2418c.getValues(fArr);
            float max = Math.max(((float) this.f2431p.width()) / ((float) this.f2423h.getWidth()), ((float) this.f2431p.height()) / ((float) this.f2423h.getHeight()));
            this.f2425j = ((float) this.f2431p.left) - (((((float) this.f2423h.getWidth()) * max) - ((float) this.f2431p.width())) / 2.0f);
            this.f2426k = ((float) this.f2431p.top) - (((((float) this.f2423h.getHeight()) * max) - ((float) this.f2431p.height())) / 2.0f);
            fArr[2] = this.f2425j;
            fArr[5] = this.f2426k;
            fArr[4] = max;
            fArr[0] = max;
            this.f2418c.setValues(fArr);
            this.f2421f = Math.min(2048.0f / ((float) this.f2423h.getWidth()), 2048.0f / ((float) this.f2423h.getHeight()));
            this.f2422g = max;
            if (this.f2421f < this.f2422g) {
                this.f2421f = this.f2422g;
            }
            setImageMatrix(this.f2418c);
        }
    }
}
