package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.view.View;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.tencent.open.yyb.TitleBar;

/* compiled from: ScaleView */
class ar extends View {
    private String f172a = "";
    private int f173b = 0;
    private IAMapDelegate f174c;
    private Paint f175d;
    private Paint f176e;
    private Rect f177f;
    private final int[] f178g = new int[]{10000000, 5000000, 2000000, 1000000, 500000, 200000, 100000, 50000, 30000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5};

    public void m203a() {
        this.f175d = null;
        this.f176e = null;
        this.f177f = null;
        this.f172a = null;
    }

    public ar(Context context) {
        super(context);
    }

    public ar(Context context, IAMapDelegate iAMapDelegate) {
        super(context);
        this.f174c = iAMapDelegate;
        this.f175d = new Paint();
        this.f177f = new Rect();
        this.f175d.setAntiAlias(true);
        this.f175d.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f175d.setStrokeWidth(2.0f * C0273r.f694a);
        this.f175d.setStyle(Style.STROKE);
        this.f176e = new Paint();
        this.f176e.setAntiAlias(true);
        this.f176e.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.f176e.setTextSize(TitleBar.BACKBTN_LEFT_MARGIN * C0273r.f694a);
    }

    protected void onDraw(Canvas canvas) {
        if (this.f172a != null && !this.f172a.equals("") && this.f173b != 0) {
            Point waterMarkerPositon = this.f174c.getWaterMarkerPositon();
            if (waterMarkerPositon != null) {
                this.f176e.getTextBounds(this.f172a, 0, this.f172a.length(), this.f177f);
                int i = waterMarkerPositon.x;
                int height = (waterMarkerPositon.y - this.f177f.height()) + 5;
                canvas.drawText(this.f172a, (float) i, (float) height, this.f176e);
                int height2 = height + (this.f177f.height() - 5);
                canvas.drawLine((float) i, (float) (height2 - 2), (float) i, (float) (height2 + 2), this.f175d);
                canvas.drawLine((float) i, (float) height2, (float) (this.f173b + i), (float) height2, this.f175d);
                canvas.drawLine((float) (this.f173b + i), (float) (height2 - 2), (float) (this.f173b + i), (float) (height2 + 2), this.f175d);
            }
        }
    }

    public void m205a(String str) {
        this.f172a = str;
    }

    public void m204a(int i) {
        this.f173b = i;
    }

    public void m206a(boolean z) {
        if (z) {
            setVisibility(0);
            m207b();
            return;
        }
        m205a("");
        m204a(0);
        setVisibility(8);
    }

    void m207b() {
        if (this.f174c != null) {
            try {
                CameraPosition cameraPosition = this.f174c.getCameraPosition();
                if (cameraPosition != null) {
                    LatLng latLng = cameraPosition.target;
                    float zoomLevel = this.f174c.getZoomLevel();
                    double cos = (double) ((float) ((((Math.cos((latLng.latitude * 3.141592653589793d) / 180.0d) * 2.0d) * 3.141592653589793d) * 6378137.0d) / (256.0d * Math.pow(2.0d, (double) zoomLevel))));
                    int mapZoomScale = (int) (((double) this.f178g[(int) zoomLevel]) / (((double) this.f174c.getMapZoomScale()) * cos));
                    String b = dj.m588b(this.f178g[(int) zoomLevel]);
                    m204a(mapZoomScale);
                    m205a(b);
                    invalidate();
                }
            } catch (Throwable th) {
                ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "changeScaleState");
                th.printStackTrace();
            }
        }
    }
}
