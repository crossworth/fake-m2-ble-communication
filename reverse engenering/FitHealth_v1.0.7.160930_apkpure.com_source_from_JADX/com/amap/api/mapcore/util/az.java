package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.view.View;
import com.amap.api.mapcore.util.C0273r.C0272a;
import java.io.InputStream;

/* compiled from: WaterMarkerView */
class az extends View {
    int f206a = 10;
    private Bitmap f207b;
    private Bitmap f208c;
    private Bitmap f209d;
    private Bitmap f210e;
    private Paint f211f = new Paint();
    private boolean f212g = false;
    private int f213h = 0;
    private C1592c f214i;
    private int f215j = 0;

    public void m226a() {
        try {
            if (this.f207b != null) {
                this.f207b.recycle();
            }
            if (this.f208c != null) {
                this.f208c.recycle();
            }
            this.f207b = null;
            this.f208c = null;
            if (this.f209d != null) {
                this.f209d.recycle();
                this.f209d = null;
            }
            if (this.f210e != null) {
                this.f210e.recycle();
                this.f210e = null;
            }
            this.f211f = null;
        } catch (Throwable th) {
            ee.m4243a(th, "WaterMarkerView", "destory");
            th.printStackTrace();
        }
    }

    public az(Context context) {
        super(context);
    }

    public az(Context context, C1592c c1592c) {
        super(context);
        this.f214i = c1592c;
        try {
            InputStream open;
            if (C0273r.f700g == C0272a.ALIBABA) {
                open = dh.m547a(context).open("apl.data");
            } else {
                open = dh.m547a(context).open("ap.data");
            }
            this.f209d = BitmapFactory.decodeStream(open);
            this.f207b = dj.m565a(this.f209d, C0273r.f694a);
            open.close();
            if (C0273r.f700g == C0272a.ALIBABA) {
                open = dh.m547a(context).open("apl1.data");
            } else {
                open = dh.m547a(context).open("ap1.data");
            }
            this.f210e = BitmapFactory.decodeStream(open);
            this.f208c = dj.m565a(this.f210e, C0273r.f694a);
            open.close();
            this.f213h = this.f208c.getHeight();
            this.f211f.setAntiAlias(true);
            this.f211f.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.f211f.setStyle(Style.STROKE);
        } catch (Throwable th) {
            ee.m4243a(th, "WaterMarkerView", "create");
            th.printStackTrace();
        }
    }

    public Bitmap m229b() {
        if (this.f212g) {
            return this.f208c;
        }
        return this.f207b;
    }

    public void m228a(boolean z) {
        try {
            this.f212g = z;
            if (z) {
                this.f211f.setColor(-1);
            } else {
                this.f211f.setColor(ViewCompat.MEASURED_STATE_MASK);
            }
            invalidate();
        } catch (Throwable th) {
            ee.m4243a(th, "WaterMarkerView", "changeBitmap");
            th.printStackTrace();
        }
    }

    public Point m230c() {
        return new Point(this.f206a, (getHeight() - this.f213h) - 10);
    }

    public void m227a(int i) {
        this.f215j = i;
    }

    public void onDraw(Canvas canvas) {
        try {
            if (this.f208c != null) {
                int width = this.f208c.getWidth();
                if (this.f215j == 1) {
                    this.f206a = (this.f214i.m4149c() - width) / 2;
                } else if (this.f215j == 2) {
                    this.f206a = (this.f214i.m4149c() - width) - 10;
                } else {
                    this.f206a = 10;
                }
                if (C0273r.f700g == C0272a.ALIBABA) {
                    canvas.drawBitmap(m229b(), (float) (this.f206a + 15), (float) ((getHeight() - this.f213h) - 8), this.f211f);
                } else {
                    canvas.drawBitmap(m229b(), (float) this.f206a, (float) ((getHeight() - this.f213h) - 8), this.f211f);
                }
            }
        } catch (Throwable th) {
            ee.m4243a(th, "WaterMarkerView", "onDraw");
            th.printStackTrace();
        }
    }
}
