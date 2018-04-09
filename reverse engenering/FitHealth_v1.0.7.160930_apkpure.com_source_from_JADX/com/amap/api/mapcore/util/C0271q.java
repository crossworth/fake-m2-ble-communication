package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.amap.api.maps.model.CameraPosition;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;

/* compiled from: CompassView */
class C0271q extends LinearLayout {
    Bitmap f684a;
    Bitmap f685b;
    Bitmap f686c;
    ImageView f687d;
    IAMapDelegate f688e;

    /* compiled from: CompassView */
    class C02701 implements OnTouchListener {
        final /* synthetic */ C0271q f683a;

        C02701(C0271q c0271q) {
            this.f683a = c0271q;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                if (this.f683a.f688e.isMaploaded()) {
                    if (motionEvent.getAction() == 0) {
                        this.f683a.f687d.setImageBitmap(this.f683a.f685b);
                    } else if (motionEvent.getAction() == 1) {
                        this.f683a.f687d.setImageBitmap(this.f683a.f684a);
                        CameraPosition cameraPosition = this.f683a.f688e.getCameraPosition();
                        this.f683a.f688e.animateCamera(CameraUpdateFactoryDelegate.newCameraPosition(new CameraPosition(cameraPosition.target, cameraPosition.zoom, 0.0f, 0.0f)));
                    }
                }
            } catch (Throwable th) {
                ee.m4243a(th, "CompassView", "onTouch");
                th.printStackTrace();
            }
            return false;
        }
    }

    public void m998a() {
        try {
            removeAllViews();
            if (this.f684a != null) {
                this.f684a.recycle();
            }
            if (this.f685b != null) {
                this.f685b.recycle();
            }
            if (this.f686c != null) {
                this.f686c.recycle();
            }
            this.f686c = null;
            this.f684a = null;
            this.f685b = null;
        } catch (Throwable th) {
            ee.m4243a(th, "CompassView", "destroy");
            th.printStackTrace();
        }
    }

    public C0271q(Context context) {
        super(context);
    }

    public C0271q(Context context, ad adVar, IAMapDelegate iAMapDelegate) {
        super(context);
        this.f688e = iAMapDelegate;
        try {
            this.f686c = dj.m564a(context, "maps_dav_compass_needle_large.png");
            this.f685b = dj.m565a(this.f686c, C0273r.f694a * 0.8f);
            this.f686c = dj.m565a(this.f686c, C0273r.f694a * 0.7f);
            this.f684a = Bitmap.createBitmap(this.f685b.getWidth(), this.f685b.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(this.f684a);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            canvas.drawBitmap(this.f686c, ((float) (this.f685b.getWidth() - this.f686c.getWidth())) / 2.0f, ((float) (this.f685b.getHeight() - this.f686c.getHeight())) / 2.0f, paint);
            this.f687d = new ImageView(context);
            this.f687d.setScaleType(ScaleType.MATRIX);
            this.f687d.setImageBitmap(this.f684a);
            this.f687d.setClickable(true);
            m1000b();
            this.f687d.setOnTouchListener(new C02701(this));
            addView(this.f687d);
        } catch (Throwable th) {
            ee.m4243a(th, "CompassView", "create");
            th.printStackTrace();
        }
    }

    public void m1000b() {
        try {
            MapProjection mapProjection = this.f688e.getMapProjection();
            float mapAngle = mapProjection.getMapAngle();
            float cameraHeaderAngle = mapProjection.getCameraHeaderAngle();
            Matrix matrix = new Matrix();
            matrix.postRotate(-mapAngle, ((float) this.f687d.getDrawable().getBounds().width()) / 2.0f, ((float) this.f687d.getDrawable().getBounds().height()) / 2.0f);
            matrix.postScale(1.0f, (float) Math.cos((((double) cameraHeaderAngle) * 3.141592653589793d) / 180.0d), ((float) this.f687d.getDrawable().getBounds().width()) / 2.0f, ((float) this.f687d.getDrawable().getBounds().height()) / 2.0f);
            this.f687d.setImageMatrix(matrix);
        } catch (Throwable th) {
            ee.m4243a(th, "CompassView", "invalidateAngle");
            th.printStackTrace();
        }
    }

    public void m999a(boolean z) {
        if (z) {
            setVisibility(0);
            m1000b();
            return;
        }
        setVisibility(8);
    }
}
