package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.api.maps.model.LatLng;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;

/* compiled from: LocationView */
class aa extends LinearLayout {
    Bitmap f125a;
    Bitmap f126b;
    Bitmap f127c;
    Bitmap f128d;
    Bitmap f129e;
    Bitmap f130f;
    ImageView f131g;
    IAMapDelegate f132h;
    boolean f133i = false;

    /* compiled from: LocationView */
    class C01961 implements OnTouchListener {
        final /* synthetic */ aa f124a;

        C01961(aa aaVar) {
            this.f124a = aaVar;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.f124a.f133i) {
                if (motionEvent.getAction() == 0) {
                    this.f124a.f131g.setImageBitmap(this.f124a.f126b);
                } else if (motionEvent.getAction() == 1) {
                    try {
                        this.f124a.f131g.setImageBitmap(this.f124a.f125a);
                        this.f124a.f132h.setMyLocationEnabled(true);
                        Location myLocation = this.f124a.f132h.getMyLocation();
                        if (myLocation != null) {
                            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                            this.f124a.f132h.showMyLocationOverlay(myLocation);
                            this.f124a.f132h.moveCamera(CameraUpdateFactoryDelegate.newLatLngZoom(latLng, this.f124a.f132h.getZoomLevel()));
                        }
                    } catch (Throwable th) {
                        ee.m4243a(th, "LocationView", "onTouch");
                        th.printStackTrace();
                    }
                }
            }
            return false;
        }
    }

    public void m129a() {
        try {
            removeAllViews();
            if (this.f125a != null) {
                this.f125a.recycle();
            }
            if (this.f126b != null) {
                this.f126b.recycle();
            }
            if (this.f126b != null) {
                this.f127c.recycle();
            }
            this.f125a = null;
            this.f126b = null;
            this.f127c = null;
            if (this.f128d != null) {
                this.f128d.recycle();
                this.f128d = null;
            }
            if (this.f129e != null) {
                this.f129e.recycle();
                this.f129e = null;
            }
            if (this.f130f != null) {
                this.f130f.recycle();
                this.f130f = null;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "LocationView", "destroy");
            th.printStackTrace();
        }
    }

    public aa(Context context) {
        super(context);
    }

    public aa(Context context, ad adVar, IAMapDelegate iAMapDelegate) {
        super(context);
        this.f132h = iAMapDelegate;
        try {
            this.f128d = dj.m564a(context, "location_selected.png");
            this.f125a = dj.m565a(this.f128d, C0273r.f694a);
            this.f129e = dj.m564a(context, "location_pressed.png");
            this.f126b = dj.m565a(this.f129e, C0273r.f694a);
            this.f130f = dj.m564a(context, "location_unselected.png");
            this.f127c = dj.m565a(this.f130f, C0273r.f694a);
            this.f131g = new ImageView(context);
            this.f131g.setImageBitmap(this.f125a);
            this.f131g.setClickable(true);
            this.f131g.setPadding(0, 20, 20, 0);
            this.f131g.setOnTouchListener(new C01961(this));
            addView(this.f131g);
        } catch (Throwable th) {
            ee.m4243a(th, "LocationView", "create");
            th.printStackTrace();
        }
    }

    public void m130a(boolean z) {
        this.f133i = z;
        if (z) {
            try {
                this.f131g.setImageBitmap(this.f125a);
            } catch (Throwable th) {
                ee.m4243a(th, "LocationView", "showSelect");
                th.printStackTrace();
                return;
            }
        }
        this.f131g.setImageBitmap(this.f127c);
        this.f131g.invalidate();
    }
}
