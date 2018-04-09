package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnPolylineClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0482k;
import com.baidu.platform.comapi.map.C0616D;
import java.util.Iterator;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONException;
import org.json.JSONObject;

class C0483b implements C0482k {
    final /* synthetic */ BaiduMap f1412a;

    C0483b(BaiduMap baiduMap) {
        this.f1412a = baiduMap;
    }

    public void mo1770a() {
    }

    public void mo1771a(Bitmap bitmap) {
        if (this.f1412a.f1040z != null) {
            this.f1412a.f1040z.onSnapshotReady(bitmap);
        }
    }

    public void mo1772a(MotionEvent motionEvent) {
        if (this.f1412a.f1030p != null) {
            this.f1412a.f1030p.onTouch(motionEvent);
        }
    }

    public void mo1773a(GeoPoint geoPoint) {
        if (this.f1412a.f1031q != null) {
            this.f1412a.f1031q.onMapClick(CoordUtil.mc2ll(geoPoint));
        }
    }

    public void mo1774a(C0616D c0616d) {
        if (this.f1412a.f1007I != null) {
            this.f1412a.f1007I.setVisibility(4);
        }
        if (this.f1412a.f1029o != null) {
            this.f1412a.f1029o.onMapStatusChangeStart(MapStatus.m1145a(c0616d));
        }
    }

    public void mo1775a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONArray("dataset").optJSONObject(0);
            GeoPoint b = this.f1412a.f1023i.m1988b(jSONObject.optInt("px"), jSONObject.optInt("py"));
            int optInt = optJSONObject.optInt("ty");
            if (optInt == 17) {
                if (this.f1412a.f1031q != null) {
                    MapPoi mapPoi = new MapPoi();
                    mapPoi.m1144a(optJSONObject);
                    this.f1412a.f1031q.onMapPoiClick(mapPoi);
                }
            } else if (optInt == 18) {
                if (this.f1412a.f1039y != null) {
                    this.f1412a.f1039y.onMyLocationClick();
                } else {
                    mo1773a(b);
                }
            } else if (optInt == 19) {
                if (this.f1412a.f1023i != null) {
                    C0616D D = this.f1412a.f1023i.m1953D();
                    D.f1965c = 0;
                    D.f1964b = 0;
                    this.f1412a.f1023i.m1975a(D, 300);
                }
            } else if (optInt == 90909) {
                String optString = optJSONObject.optString("marker_id");
                if (this.f1412a.f1005G == null || !optString.equals(this.f1412a.f1006H.p)) {
                    for (Overlay overlay : this.f1412a.f1025k) {
                        if ((overlay instanceof Marker) && overlay.f976p.equals(optString)) {
                            if (this.f1412a.f1036v.isEmpty()) {
                                mo1773a(b);
                            } else {
                                Iterator it = this.f1412a.f1036v.iterator();
                                while (it.hasNext()) {
                                    ((OnMarkerClickListener) it.next()).onMarkerClick((Marker) overlay);
                                }
                                return;
                            }
                        }
                    }
                    return;
                }
                OnInfoWindowClickListener onInfoWindowClickListener = this.f1412a.f1005G.f1130d;
                if (onInfoWindowClickListener != null) {
                    onInfoWindowClickListener.onInfoWindowClick();
                } else {
                    mo1773a(b);
                }
            } else if (optInt == 90910) {
                String optString2 = optJSONObject.optString("polyline_id");
                for (Overlay overlay2 : this.f1412a.f1025k) {
                    if ((overlay2 instanceof Polyline) && overlay2.f976p.equals(optString2)) {
                        if (this.f1412a.f1037w.isEmpty()) {
                            mo1773a(b);
                        } else {
                            Iterator it2 = this.f1412a.f1037w.iterator();
                            while (it2.hasNext()) {
                                ((OnPolylineClickListener) it2.next()).onPolylineClick((Polyline) overlay2);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void mo1776a(GL10 gl10, C0616D c0616d) {
        if (this.f1412a.f999A != null) {
            this.f1412a.f999A.onMapDrawFrame(gl10, MapStatus.m1145a(c0616d));
        }
    }

    public void mo1777a(boolean z) {
        if (this.f1412a.f1000B != null) {
            this.f1412a.f1000B.onBaseIndoorMapMode(z, this.f1412a.getFocusedBaseIndoorMapInfo());
        }
    }

    public void mo1778b() {
        this.f1412a.f1020f = new Projection(this.f1412a.f1023i);
        this.f1412a.f1014P = true;
        if (this.f1412a.f1032r != null) {
            this.f1412a.f1032r.onMapLoaded();
        }
    }

    public void mo1779b(GeoPoint geoPoint) {
        if (this.f1412a.f1034t != null) {
            this.f1412a.f1034t.onMapDoubleClick(CoordUtil.mc2ll(geoPoint));
        }
    }

    public void mo1780b(C0616D c0616d) {
        if (this.f1412a.f1029o != null) {
            this.f1412a.f1029o.onMapStatusChange(MapStatus.m1145a(c0616d));
        }
    }

    public boolean mo1781b(String str) {
        try {
            JSONObject optJSONObject = new JSONObject(str).optJSONArray("dataset").optJSONObject(0);
            if (optJSONObject.optInt("ty") == 90909) {
                String optString = optJSONObject.optString("marker_id");
                if (this.f1412a.f1006H == null || !optString.equals(this.f1412a.f1006H.p)) {
                    for (Overlay overlay : this.f1412a.f1025k) {
                        if ((overlay instanceof Marker) && overlay.f976p.equals(optString)) {
                            Marker marker = (Marker) overlay;
                            if (marker.f1212f) {
                                this.f1412a.f1008J = marker;
                                Point toScreenLocation = this.f1412a.f1020f.toScreenLocation(this.f1412a.f1008J.f1207a);
                                this.f1412a.f1008J.setPosition(this.f1412a.f1020f.fromScreenLocation(new Point(toScreenLocation.x, toScreenLocation.y - 60)));
                                if (this.f1412a.f1038x != null) {
                                    this.f1412a.f1038x.onMarkerDragStart(this.f1412a.f1008J);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void mo1782c() {
        if (this.f1412a.f1033s != null) {
            this.f1412a.f1033s.onMapRenderFinished();
        }
    }

    public void mo1783c(GeoPoint geoPoint) {
        if (this.f1412a.f1035u != null) {
            this.f1412a.f1035u.onMapLongClick(CoordUtil.mc2ll(geoPoint));
        }
    }

    public void mo1784c(C0616D c0616d) {
        if (this.f1412a.f1007I != null) {
            this.f1412a.f1007I.setVisibility(0);
        }
        if (this.f1412a.f1029o != null) {
            this.f1412a.f1029o.onMapStatusChangeFinish(MapStatus.m1145a(c0616d));
        }
    }

    public void mo1785d() {
        this.f1412a.f1003E.lock();
        try {
            if (this.f1412a.f1002D != null) {
                this.f1412a.f1002D.m1141a();
            }
            this.f1412a.f1003E.unlock();
        } catch (Throwable th) {
            this.f1412a.f1003E.unlock();
        }
    }

    public void mo1786d(GeoPoint geoPoint) {
        if (this.f1412a.f1008J != null && this.f1412a.f1008J.f1212f) {
            Point toScreenLocation = this.f1412a.f1020f.toScreenLocation(CoordUtil.mc2ll(geoPoint));
            this.f1412a.f1008J.setPosition(this.f1412a.f1020f.fromScreenLocation(new Point(toScreenLocation.x, toScreenLocation.y - 60)));
            if (this.f1412a.f1038x != null && this.f1412a.f1008J.f1212f) {
                this.f1412a.f1038x.onMarkerDrag(this.f1412a.f1008J);
            }
        }
    }

    public void mo1787e() {
        this.f1412a.f1003E.lock();
        try {
            if (this.f1412a.f1002D != null) {
                this.f1412a.f1002D.m1141a();
                this.f1412a.f1023i.m2021n();
            }
            this.f1412a.f1003E.unlock();
        } catch (Throwable th) {
            this.f1412a.f1003E.unlock();
        }
    }

    public void mo1788e(GeoPoint geoPoint) {
        if (this.f1412a.f1008J != null && this.f1412a.f1008J.f1212f) {
            Point toScreenLocation = this.f1412a.f1020f.toScreenLocation(CoordUtil.mc2ll(geoPoint));
            this.f1412a.f1008J.setPosition(this.f1412a.f1020f.fromScreenLocation(new Point(toScreenLocation.x, toScreenLocation.y - 60)));
            if (this.f1412a.f1038x != null && this.f1412a.f1008J.f1212f) {
                this.f1412a.f1038x.onMarkerDragEnd(this.f1412a.f1008J);
            }
            this.f1412a.f1008J = null;
        }
    }

    public void mo1789f() {
        this.f1412a.f1023i.m1994b(false);
        this.f1412a.f1003E.lock();
        try {
            if (this.f1412a.f1002D != null) {
                this.f1412a.m1099a(this.f1412a.f1002D);
            }
            this.f1412a.f1003E.unlock();
        } catch (Throwable th) {
            this.f1412a.f1003E.unlock();
        }
    }
}
