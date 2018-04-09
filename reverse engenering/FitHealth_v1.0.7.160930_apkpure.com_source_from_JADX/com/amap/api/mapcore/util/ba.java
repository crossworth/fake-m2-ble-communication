package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.api.mapcore.util.ah.C0198a;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;

/* compiled from: ZoomControllerView */
class ba extends LinearLayout {
    private Bitmap f219a;
    private Bitmap f220b;
    private Bitmap f221c;
    private Bitmap f222d;
    private Bitmap f223e;
    private Bitmap f224f;
    private Bitmap f225g;
    private Bitmap f226h;
    private Bitmap f227i;
    private Bitmap f228j;
    private Bitmap f229k;
    private Bitmap f230l;
    private ImageView f231m;
    private ImageView f232n;
    private IAMapDelegate f233o;

    /* compiled from: ZoomControllerView */
    class C02021 implements OnTouchListener {
        final /* synthetic */ ba f217a;

        C02021(ba baVar) {
            this.f217a = baVar;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.f217a.f233o.getZoomLevel() < this.f217a.f233o.getMaxZoomLevel() && this.f217a.f233o.isMaploaded()) {
                if (motionEvent.getAction() == 0) {
                    this.f217a.f231m.setImageBitmap(this.f217a.f223e);
                } else if (motionEvent.getAction() == 1) {
                    this.f217a.f231m.setImageBitmap(this.f217a.f219a);
                    try {
                        this.f217a.f233o.animateCamera(CameraUpdateFactoryDelegate.zoomIn());
                    } catch (Throwable e) {
                        ee.m4243a(e, "ZoomControllerView", "zoomin ontouch");
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
    }

    /* compiled from: ZoomControllerView */
    class C02032 implements OnTouchListener {
        final /* synthetic */ ba f218a;

        C02032(ba baVar) {
            this.f218a = baVar;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.f218a.f233o.getZoomLevel() > this.f218a.f233o.getMinZoomLevel() && this.f218a.f233o.isMaploaded()) {
                if (motionEvent.getAction() == 0) {
                    this.f218a.f232n.setImageBitmap(this.f218a.f224f);
                } else if (motionEvent.getAction() == 1) {
                    this.f218a.f232n.setImageBitmap(this.f218a.f221c);
                    try {
                        this.f218a.f233o.animateCamera(CameraUpdateFactoryDelegate.zoomOut());
                    } catch (Throwable e) {
                        ee.m4243a(e, "ZoomControllerView", "zoomout ontouch");
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
    }

    public void m238a() {
        try {
            removeAllViews();
            this.f219a.recycle();
            this.f220b.recycle();
            this.f221c.recycle();
            this.f222d.recycle();
            this.f223e.recycle();
            this.f224f.recycle();
            this.f219a = null;
            this.f220b = null;
            this.f221c = null;
            this.f222d = null;
            this.f223e = null;
            this.f224f = null;
            if (this.f225g != null) {
                this.f225g.recycle();
                this.f225g = null;
            }
            if (this.f226h != null) {
                this.f226h.recycle();
                this.f226h = null;
            }
            if (this.f227i != null) {
                this.f227i.recycle();
                this.f227i = null;
            }
            if (this.f228j != null) {
                this.f228j.recycle();
                this.f225g = null;
            }
            if (this.f229k != null) {
                this.f229k.recycle();
                this.f229k = null;
            }
            if (this.f230l != null) {
                this.f230l.recycle();
                this.f230l = null;
            }
            this.f231m = null;
            this.f232n = null;
        } catch (Throwable th) {
            ee.m4243a(th, "ZoomControllerView", "destory");
            th.printStackTrace();
        }
    }

    public ba(Context context) {
        super(context);
    }

    public ba(Context context, IAMapDelegate iAMapDelegate) {
        super(context);
        this.f233o = iAMapDelegate;
        try {
            this.f225g = dj.m564a(context, "zoomin_selected.png");
            this.f219a = dj.m565a(this.f225g, C0273r.f694a);
            this.f226h = dj.m564a(context, "zoomin_unselected.png");
            this.f220b = dj.m565a(this.f226h, C0273r.f694a);
            this.f227i = dj.m564a(context, "zoomout_selected.png");
            this.f221c = dj.m565a(this.f227i, C0273r.f694a);
            this.f228j = dj.m564a(context, "zoomout_unselected.png");
            this.f222d = dj.m565a(this.f228j, C0273r.f694a);
            this.f229k = dj.m564a(context, "zoomin_pressed.png");
            this.f223e = dj.m565a(this.f229k, C0273r.f694a);
            this.f230l = dj.m564a(context, "zoomout_pressed.png");
            this.f224f = dj.m565a(this.f230l, C0273r.f694a);
            this.f231m = new ImageView(context);
            this.f231m.setImageBitmap(this.f219a);
            this.f231m.setClickable(true);
            this.f232n = new ImageView(context);
            this.f232n.setImageBitmap(this.f221c);
            this.f232n.setClickable(true);
            this.f231m.setOnTouchListener(new C02021(this));
            this.f232n.setOnTouchListener(new C02032(this));
            this.f231m.setPadding(0, 0, 20, -2);
            this.f232n.setPadding(0, 0, 20, 20);
            setOrientation(1);
            addView(this.f231m);
            addView(this.f232n);
        } catch (Throwable th) {
            ee.m4243a(th, "ZoomControllerView", "create");
            th.printStackTrace();
        }
    }

    public void m239a(float f) {
        try {
            if (f < this.f233o.getMaxZoomLevel() && f > this.f233o.getMinZoomLevel()) {
                this.f231m.setImageBitmap(this.f219a);
                this.f232n.setImageBitmap(this.f221c);
            } else if (f == this.f233o.getMinZoomLevel()) {
                this.f232n.setImageBitmap(this.f222d);
                this.f231m.setImageBitmap(this.f219a);
            } else if (f == this.f233o.getMaxZoomLevel()) {
                this.f231m.setImageBitmap(this.f220b);
                this.f232n.setImageBitmap(this.f221c);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "ZoomControllerView", "setZoomBitmap");
            th.printStackTrace();
        }
    }

    public void m240a(int i) {
        try {
            C0198a c0198a = (C0198a) getLayoutParams();
            if (i == 1) {
                c0198a.f155d = 16;
            } else if (i == 2) {
                c0198a.f155d = 80;
            }
            setLayoutParams(c0198a);
        } catch (Throwable th) {
            ee.m4243a(th, "ZoomControllerView", "setZoomPosition");
            th.printStackTrace();
        }
    }

    public void m241a(boolean z) {
        if (z) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
    }
}
