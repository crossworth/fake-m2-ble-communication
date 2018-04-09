package com.amap.api.mapcore.util;

import android.content.Context;
import android.location.Location;
import android.os.RemoteException;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.ICircleDelegate;

/* compiled from: MyLocationOverlay */
class aj {
    private IAMapDelegate f157a;
    private Marker f158b;
    private ICircleDelegate f159c;
    private MyLocationStyle f160d;
    private LatLng f161e;
    private double f162f;
    private Context f163g;
    private as f164h;
    private int f165i = 1;
    private boolean f166j = false;
    private final String f167k = "location_map_gps_locked.png";
    private final String f168l = "location_map_gps_3d.png";
    private boolean f169m = false;

    aj(IAMapDelegate iAMapDelegate, Context context) {
        this.f163g = context;
        this.f157a = iAMapDelegate;
        this.f164h = new as(this.f163g, iAMapDelegate);
    }

    public void m187a(MyLocationStyle myLocationStyle) {
        try {
            this.f160d = myLocationStyle;
            if (this.f158b != null || this.f159c != null) {
                m181k();
                this.f164h.m210a(this.f158b);
                m180j();
            }
        } catch (Throwable th) {
            ee.m4243a(th, "MyLocationOverlay", "setMyLocationStyle");
            th.printStackTrace();
        }
    }

    public void m185a(int i) {
        this.f165i = i;
        this.f166j = false;
        switch (this.f165i) {
            case 1:
                m176f();
                return;
            case 2:
                m177g();
                return;
            case 3:
                m178h();
                return;
            default:
                return;
        }
    }

    public void m183a() {
        if (this.f165i == 3 && this.f164h != null) {
            this.f164h.m209a();
        }
    }

    private void m176f() {
        if (this.f158b != null) {
            m175c(0.0f);
            this.f164h.m211b();
            if (!this.f169m) {
                this.f158b.setIcon(BitmapDescriptorFactory.fromAsset("location_map_gps_locked.png"));
            }
            this.f158b.setFlat(false);
            m173b(0.0f);
        }
    }

    private void m177g() {
        if (this.f158b != null) {
            m175c(0.0f);
            this.f164h.m211b();
            if (!this.f169m) {
                this.f158b.setIcon(BitmapDescriptorFactory.fromAsset("location_map_gps_locked.png"));
            }
            this.f158b.setFlat(false);
            m173b(0.0f);
        }
    }

    private void m178h() {
        if (this.f158b != null) {
            this.f158b.setRotateAngle(0.0f);
            this.f164h.m209a();
            if (!this.f169m) {
                this.f158b.setIcon(BitmapDescriptorFactory.fromAsset("location_map_gps_3d.png"));
            }
            this.f158b.setFlat(true);
            try {
                this.f157a.moveCamera(CameraUpdateFactoryDelegate.zoomTo(17.0f));
                m173b(45.0f);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void m173b(float f) {
        if (this.f157a != null) {
            try {
                this.f157a.moveCamera(CameraUpdateFactoryDelegate.changeTilt(f));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void m175c(float f) {
        if (this.f157a != null) {
            try {
                this.f157a.moveCamera(CameraUpdateFactoryDelegate.changeBearing(f));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void m186a(Location location) {
        if (location != null) {
            this.f161e = new LatLng(location.getLatitude(), location.getLongitude());
            this.f162f = (double) location.getAccuracy();
            if (this.f158b == null && this.f159c == null) {
                m180j();
            }
            if (this.f158b != null) {
                this.f158b.setPosition(this.f161e);
            }
            if (this.f159c != null) {
                try {
                    this.f159c.setCenter(this.f161e);
                    if (this.f162f != -1.0d) {
                        this.f159c.setRadius(this.f162f);
                    }
                } catch (Throwable e) {
                    ee.m4243a(e, "MyLocationOverlay", "setCentAndRadius");
                    e.printStackTrace();
                }
                m179i();
                if (this.f165i != 3) {
                    m174b(location);
                }
                this.f166j = true;
            }
        }
    }

    private void m174b(Location location) {
        float bearing = location.getBearing() % 360.0f;
        if (bearing > BitmapDescriptorFactory.HUE_CYAN) {
            bearing -= 360.0f;
        } else if (bearing < -180.0f) {
            bearing += 360.0f;
        }
        if (this.f158b != null) {
            this.f158b.setRotateAngle(-bearing);
        }
    }

    private void m179i() {
        if (this.f165i != 1 || !this.f166j) {
            try {
                IPoint iPoint = new IPoint();
                MapProjection.lonlat2Geo(this.f161e.longitude, this.f161e.latitude, iPoint);
                this.f157a.animateCamera(CameraUpdateFactoryDelegate.changeGeoCenter(iPoint));
            } catch (Throwable e) {
                ee.m4243a(e, "MyLocationOverlay", "locaitonFollow");
                e.printStackTrace();
            }
        }
    }

    private void m180j() {
        if (this.f160d == null) {
            this.f160d = new MyLocationStyle();
            this.f160d.myLocationIcon(BitmapDescriptorFactory.fromAsset("location_map_gps_locked.png"));
            m182l();
            return;
        }
        this.f169m = true;
        m182l();
    }

    public void m188b() throws RemoteException {
        m181k();
        if (this.f164h != null) {
            this.f164h.m211b();
            this.f164h = null;
        }
    }

    private void m181k() {
        if (this.f159c != null) {
            try {
                this.f157a.removeGLOverlay(this.f159c.getId());
            } catch (Throwable e) {
                ee.m4243a(e, "MyLocationOverlay", "locationIconRemove");
                e.printStackTrace();
            }
            this.f159c = null;
        }
        if (this.f158b != null) {
            this.f158b.remove();
            this.f158b.destroy();
            this.f158b = null;
            this.f164h.m210a(null);
        }
    }

    private void m182l() {
        try {
            this.f159c = this.f157a.addCircle(new CircleOptions().strokeWidth(this.f160d.getStrokeWidth()).fillColor(this.f160d.getRadiusFillColor()).strokeColor(this.f160d.getStrokeColor()).center(new LatLng(0.0d, 0.0d)).zIndex(1.0f));
            if (this.f161e != null) {
                this.f159c.setCenter(this.f161e);
            }
            this.f159c.setRadius(this.f162f);
            this.f158b = this.f157a.addMarker(new MarkerOptions().visible(false).anchor(this.f160d.getAnchorU(), this.f160d.getAnchorV()).icon(this.f160d.getMyLocationIcon()).position(new LatLng(0.0d, 0.0d)));
            m185a(this.f165i);
            if (this.f161e != null) {
                this.f158b.setPosition(this.f161e);
                this.f158b.setVisible(true);
            }
            this.f164h.m210a(this.f158b);
        } catch (Throwable e) {
            ee.m4243a(e, "MyLocationOverlay", "myLocStyle");
            e.printStackTrace();
        }
    }

    public void m184a(float f) {
        if (this.f158b != null) {
            this.f158b.setRotateAngle(f);
        }
    }

    public String m189c() {
        if (this.f158b != null) {
            return this.f158b.getId();
        }
        return null;
    }

    public String m190d() throws RemoteException {
        if (this.f159c != null) {
            return this.f159c.getId();
        }
        return null;
    }

    public void m191e() {
        this.f159c = null;
        this.f158b = null;
    }
}
