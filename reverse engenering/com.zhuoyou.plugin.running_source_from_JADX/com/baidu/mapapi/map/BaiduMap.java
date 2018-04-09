package com.baidu.mapapi.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo.SwitchFloorError;
import com.baidu.mapapi.map.MapStatus.Builder;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.Overlay.C0477a;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.C0613C;
import com.baidu.platform.comapi.map.C0616D;
import com.baidu.platform.comapi.map.C0618E;
import com.baidu.platform.comapi.map.C0633e;
import com.baidu.platform.comapi.map.C0636h;
import com.baidu.platform.comapi.map.C0638i;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.microedition.khronos.opengles.GL10;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.HttpResponseCode;

public class BaiduMap {
    public static final int MAP_TYPE_NONE = 3;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    private static final String f998e = BaiduMap.class.getSimpleName();
    private OnMapDrawFrameCallback f999A;
    private OnBaseIndoorMapListener f1000B;
    private TileOverlay f1001C;
    private HeatMap f1002D;
    private Lock f1003E = new ReentrantLock();
    private Lock f1004F = new ReentrantLock();
    private InfoWindow f1005G;
    private Marker f1006H;
    private View f1007I;
    private Marker f1008J;
    private MyLocationData f1009K;
    private MyLocationConfiguration f1010L;
    private boolean f1011M;
    private boolean f1012N;
    private boolean f1013O;
    private boolean f1014P;
    private Point f1015Q;
    MapView f1016a;
    TextureMapView f1017b;
    WearMapView f1018c;
    C0613C f1019d;
    private Projection f1020f;
    private UiSettings f1021g;
    private C0638i f1022h;
    private C0633e f1023i;
    private C0618E f1024j;
    private List<Overlay> f1025k;
    private List<Marker> f1026l;
    private List<Marker> f1027m;
    private C0477a f1028n;
    private OnMapStatusChangeListener f1029o;
    private OnMapTouchListener f1030p;
    private OnMapClickListener f1031q;
    private OnMapLoadedCallback f1032r;
    private OnMapRenderCallback f1033s;
    private OnMapDoubleClickListener f1034t;
    private OnMapLongClickListener f1035u;
    private CopyOnWriteArrayList<OnMarkerClickListener> f1036v = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<OnPolylineClickListener> f1037w = new CopyOnWriteArrayList();
    private OnMarkerDragListener f1038x;
    private OnMyLocationClickListener f1039y;
    private SnapshotReadyCallback f1040z;

    public interface OnBaseIndoorMapListener {
        void onBaseIndoorMapMode(boolean z, MapBaseIndoorMapInfo mapBaseIndoorMapInfo);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);

        boolean onMapPoiClick(MapPoi mapPoi);
    }

    public interface OnMapDoubleClickListener {
        void onMapDoubleClick(LatLng latLng);
    }

    public interface OnMapDrawFrameCallback {
        void onMapDrawFrame(GL10 gl10, MapStatus mapStatus);
    }

    public interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMapRenderCallback {
        void onMapRenderFinished();
    }

    public interface OnMapStatusChangeListener {
        void onMapStatusChange(MapStatus mapStatus);

        void onMapStatusChangeFinish(MapStatus mapStatus);

        void onMapStatusChangeStart(MapStatus mapStatus);
    }

    public interface OnMapTouchListener {
        void onTouch(MotionEvent motionEvent);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationClickListener {
        boolean onMyLocationClick();
    }

    public interface OnPolylineClickListener {
        boolean onPolylineClick(Polyline polyline);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    BaiduMap(C0618E c0618e) {
        this.f1024j = c0618e;
        this.f1023i = this.f1024j.m1915b();
        this.f1019d = C0613C.TextureView;
        m1074c();
    }

    BaiduMap(C0638i c0638i) {
        this.f1022h = c0638i;
        this.f1023i = this.f1022h.m2046a();
        this.f1019d = C0613C.GLSurfaceView;
        m1074c();
    }

    private Point m1065a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int i = 0;
        int i2 = 0;
        for (String replaceAll : str.replaceAll("^\\{", "").replaceAll("\\}$", "").split(",")) {
            String[] split = replaceAll.replaceAll("\"", "").split(":");
            if ("x".equals(split[0])) {
                i2 = Integer.valueOf(split[1]).intValue();
            }
            if ("y".equals(split[0])) {
                i = Integer.valueOf(split[1]).intValue();
            }
        }
        return new Point(i2, i);
    }

    private C0616D m1068a(MapStatusUpdate mapStatusUpdate) {
        if (this.f1023i == null) {
            return null;
        }
        return mapStatusUpdate.m1150a(this.f1023i, getMapStatus()).m1148b(this.f1023i.m1953D());
    }

    private final void m1070a(MyLocationData myLocationData, MyLocationConfiguration myLocationConfiguration) {
        if (myLocationData != null && myLocationConfiguration != null && isMyLocationEnabled()) {
            Bundle bundle;
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            LatLng latLng = new LatLng(myLocationData.latitude, myLocationData.longitude);
            GeoPoint ll2mc = CoordUtil.ll2mc(latLng);
            try {
                jSONObject.put("type", 0);
                jSONObject2.put("ptx", ll2mc.getLongitudeE6());
                jSONObject2.put("pty", ll2mc.getLatitudeE6());
                jSONObject2.put("radius", (double) ((float) CoordUtil.getMCDistanceByOneLatLngAndRadius(latLng, (int) myLocationData.accuracy)));
                float f = myLocationData.direction;
                if (myLocationConfiguration.enableDirection) {
                    f = myLocationData.direction % 360.0f;
                    if (f > 180.0f) {
                        f -= 360.0f;
                    } else if (f < -180.0f) {
                        f += 360.0f;
                    }
                } else {
                    f = -1.0f;
                }
                jSONObject2.put("direction", (double) f);
                jSONObject2.put("iconarrownor", "NormalLocArrow");
                jSONObject2.put("iconarrownorid", 28);
                jSONObject2.put("iconarrowfoc", "FocusLocArrow");
                jSONObject2.put("iconarrowfocid", 29);
                jSONObject2.put("lineid", myLocationConfiguration.accuracyCircleStrokeColor);
                jSONObject2.put("areaid", myLocationConfiguration.accuracyCircleFillColor);
                jSONArray.put(jSONObject2);
                jSONObject.put("data", jSONArray);
                if (myLocationConfiguration.locationMode == LocationMode.COMPASS) {
                    jSONObject3.put("ptx", ll2mc.getLongitudeE6());
                    jSONObject3.put("pty", ll2mc.getLatitudeE6());
                    jSONObject3.put("radius", 0);
                    jSONObject3.put("direction", 0);
                    jSONObject3.put("iconarrownor", "direction_wheel");
                    jSONObject3.put("iconarrownorid", 54);
                    jSONObject3.put("iconarrowfoc", "direction_wheel");
                    jSONObject3.put("iconarrowfocid", 54);
                    jSONArray.put(jSONObject3);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (myLocationConfiguration.customMarker == null) {
                bundle = null;
            } else {
                List<BitmapDescriptor> arrayList = new ArrayList();
                arrayList.add(myLocationConfiguration.customMarker);
                Bundle bundle2 = new Bundle();
                ArrayList arrayList2 = new ArrayList();
                for (BitmapDescriptor bitmapDescriptor : arrayList) {
                    ParcelItem parcelItem = new ParcelItem();
                    Bundle bundle3 = new Bundle();
                    Bitmap bitmap = bitmapDescriptor.f1053a;
                    Buffer allocate = ByteBuffer.allocate((bitmap.getWidth() * bitmap.getHeight()) * 4);
                    bitmap.copyPixelsToBuffer(allocate);
                    bundle3.putByteArray("imgdata", allocate.array());
                    bundle3.putInt("imgindex", bitmapDescriptor.hashCode());
                    bundle3.putInt("imgH", bitmap.getHeight());
                    bundle3.putInt("imgW", bitmap.getWidth());
                    parcelItem.setBundle(bundle3);
                    arrayList2.add(parcelItem);
                }
                if (arrayList2.size() > 0) {
                    Parcelable[] parcelableArr = new ParcelItem[arrayList2.size()];
                    for (int i = 0; i < arrayList2.size(); i++) {
                        parcelableArr[i] = (ParcelItem) arrayList2.get(i);
                    }
                    bundle2.putParcelableArray("icondata", parcelableArr);
                }
                bundle = bundle2;
            }
            if (this.f1023i != null) {
                this.f1023i.m1980a(jSONObject.toString(), bundle);
            }
            switch (myLocationConfiguration.locationMode) {
                case COMPASS:
                    animateMapStatus(MapStatusUpdateFactory.newMapStatus(new Builder().rotate(myLocationData.direction).overlook(-45.0f).target(new LatLng(myLocationData.latitude, myLocationData.longitude)).targetScreen(getMapStatus().targetScreen).zoom(getMapStatus().zoom).build()));
                    return;
                case FOLLOWING:
                    animateMapStatus(MapStatusUpdateFactory.newMapStatus(new Builder().target(new LatLng(myLocationData.latitude, myLocationData.longitude)).zoom(getMapStatus().zoom).rotate(getMapStatus().rotate).overlook(getMapStatus().overlook).targetScreen(getMapStatus().targetScreen).build()));
                    return;
                case NORMAL:
                    return;
                default:
                    return;
            }
        }
    }

    private void m1074c() {
        this.f1025k = new CopyOnWriteArrayList();
        this.f1026l = new CopyOnWriteArrayList();
        this.f1027m = new CopyOnWriteArrayList();
        this.f1015Q = new Point((int) (SysOSUtil.getDensity() * 40.0f), (int) (SysOSUtil.getDensity() * 40.0f));
        this.f1021g = new UiSettings(this.f1023i);
        this.f1028n = new C0481a(this);
        this.f1023i.m1978a(new C0483b(this));
        this.f1023i.m1979a(new C0485c(this));
        this.f1023i.m1976a(new C0487d(this));
        this.f1011M = this.f1023i.m1951B();
        this.f1012N = this.f1023i.m1952C();
    }

    void m1098a() {
        if (this.f1023i != null) {
            this.f1023i.m2031s();
        }
    }

    void m1099a(HeatMap heatMap) {
        this.f1003E.lock();
        try {
            if (!(this.f1002D == null || this.f1023i == null || heatMap != this.f1002D)) {
                this.f1002D.m1142b();
                this.f1002D.m1143c();
                this.f1002D.f1114a = null;
                this.f1023i.m2021n();
                this.f1002D = null;
                this.f1023i.m2017l(false);
            }
            this.f1003E.unlock();
        } catch (Throwable th) {
            this.f1003E.unlock();
        }
    }

    void m1100a(TileOverlay tileOverlay) {
        this.f1004F.lock();
        if (tileOverlay != null) {
            try {
                if (this.f1001C == tileOverlay) {
                    tileOverlay.m1216b();
                    tileOverlay.f1356a = null;
                    if (this.f1023i != null) {
                        this.f1023i.m1996c(false);
                    }
                }
            } catch (Throwable th) {
                this.f1001C = null;
                this.f1004F.unlock();
            }
        }
        this.f1001C = null;
        this.f1004F.unlock();
    }

    public void addHeatMap(HeatMap heatMap) {
        if (heatMap != null) {
            this.f1003E.lock();
            try {
                if (heatMap != this.f1002D) {
                    if (this.f1002D != null) {
                        this.f1002D.m1142b();
                        this.f1002D.m1143c();
                        this.f1002D.f1114a = null;
                        this.f1023i.m2021n();
                    }
                    this.f1002D = heatMap;
                    this.f1002D.f1114a = this;
                    this.f1023i.m2017l(true);
                    this.f1003E.unlock();
                }
            } finally {
                this.f1003E.unlock();
            }
        }
    }

    public final Overlay addOverlay(OverlayOptions overlayOptions) {
        if (overlayOptions == null) {
            return null;
        }
        Overlay a = overlayOptions.mo1760a();
        a.listener = this.f1028n;
        if (a instanceof Marker) {
            Marker marker = (Marker) a;
            if (!(marker.f1220n == null || marker.f1220n.size() == 0)) {
                this.f1026l.add(marker);
                if (this.f1023i != null) {
                    this.f1023i.m1994b(true);
                }
            }
            this.f1027m.add(marker);
        }
        Bundle bundle = new Bundle();
        a.mo1759a(bundle);
        if (this.f1023i != null) {
            this.f1023i.m1990b(bundle);
        }
        this.f1025k.add(a);
        return a;
    }

    public final List<Overlay> addOverlays(List<OverlayOptions> list) {
        if (list == null) {
            return null;
        }
        List<Overlay> arrayList = new ArrayList();
        Bundle[] bundleArr = new Bundle[list.size()];
        int i = 0;
        for (OverlayOptions overlayOptions : list) {
            if (overlayOptions != null) {
                Bundle bundle = new Bundle();
                Overlay a = overlayOptions.mo1760a();
                a.listener = this.f1028n;
                if (a instanceof Marker) {
                    Marker marker = (Marker) a;
                    if (!(marker.f1220n == null || marker.f1220n.size() == 0)) {
                        this.f1026l.add(marker);
                        if (this.f1023i != null) {
                            this.f1023i.m1994b(true);
                        }
                    }
                    this.f1027m.add(marker);
                }
                this.f1025k.add(a);
                arrayList.add(a);
                a.mo1759a(bundle);
                bundleArr[i] = bundle;
                i++;
            }
        }
        i = bundleArr.length / HttpResponseCode.BAD_REQUEST;
        int i2 = 0;
        while (i2 < i + 1) {
            List arrayList2 = new ArrayList();
            int i3 = 0;
            while (i3 < HttpResponseCode.BAD_REQUEST && (i2 * HttpResponseCode.BAD_REQUEST) + i3 < bundleArr.length) {
                if (bundleArr[(i2 * HttpResponseCode.BAD_REQUEST) + i3] != null) {
                    arrayList2.add(bundleArr[(i2 * HttpResponseCode.BAD_REQUEST) + i3]);
                }
                i3++;
            }
            if (this.f1023i != null) {
                this.f1023i.m1981a(arrayList2);
            }
            i2++;
        }
        return arrayList;
    }

    public TileOverlay addTileLayer(TileOverlayOptions tileOverlayOptions) {
        if (tileOverlayOptions == null) {
            return null;
        }
        if (this.f1001C != null) {
            this.f1001C.m1216b();
            this.f1001C.f1356a = null;
        }
        if (this.f1023i == null || !this.f1023i.m1985a(tileOverlayOptions.m1218a())) {
            return null;
        }
        TileOverlay a = tileOverlayOptions.m1219a(this);
        this.f1001C = a;
        return a;
    }

    public final void animateMapStatus(MapStatusUpdate mapStatusUpdate) {
        animateMapStatus(mapStatusUpdate, 300);
    }

    public final void animateMapStatus(MapStatusUpdate mapStatusUpdate, int i) {
        if (mapStatusUpdate != null && i > 0) {
            C0616D a = m1068a(mapStatusUpdate);
            if (this.f1023i == null) {
                return;
            }
            if (this.f1014P) {
                this.f1023i.m1975a(a, i);
            } else {
                this.f1023i.m1974a(a);
            }
        }
    }

    boolean m1101b() {
        return this.f1023i == null ? false : this.f1023i.m2002d();
    }

    public final void clear() {
        this.f1025k.clear();
        this.f1026l.clear();
        this.f1027m.clear();
        if (this.f1023i != null) {
            this.f1023i.m1994b(false);
            this.f1023i.m2019m();
        }
        hideInfoWindow();
    }

    public final Point getCompassPosition() {
        return this.f1023i != null ? m1065a(this.f1023i.m2008g()) : null;
    }

    public MapBaseIndoorMapInfo getFocusedBaseIndoorMapInfo() {
        return this.f1023i.m2023o();
    }

    public final MyLocationConfiguration getLocationConfigeration() {
        return this.f1010L;
    }

    public final MyLocationData getLocationData() {
        return this.f1009K;
    }

    public final MapStatus getMapStatus() {
        return this.f1023i == null ? null : MapStatus.m1145a(this.f1023i.m1953D());
    }

    public final LatLngBounds getMapStatusLimit() {
        return this.f1023i == null ? null : this.f1023i.m1954E();
    }

    public final int getMapType() {
        return this.f1023i == null ? 1 : !this.f1023i.m2016k() ? 3 : this.f1023i.m2014j() ? 2 : 1;
    }

    public List<Marker> getMarkersInBounds(LatLngBounds latLngBounds) {
        if (getMapStatus() == null) {
            return null;
        }
        List<Marker> arrayList = new ArrayList();
        if (this.f1027m.size() == 0) {
            return null;
        }
        for (Marker marker : this.f1027m) {
            if (latLngBounds.contains(marker.getPosition())) {
                arrayList.add(marker);
            }
        }
        return arrayList;
    }

    public final float getMaxZoomLevel() {
        return this.f1023i == null ? 0.0f : this.f1023i.f2047a;
    }

    public final float getMinZoomLevel() {
        return this.f1023i == null ? 0.0f : this.f1023i.f2048b;
    }

    public final Projection getProjection() {
        return this.f1020f;
    }

    public final UiSettings getUiSettings() {
        return this.f1021g;
    }

    public void hideInfoWindow() {
        if (this.f1005G != null) {
            if (this.f1005G.f1128b != null) {
                switch (this.f1019d) {
                    case TextureView:
                        if (this.f1017b != null) {
                            this.f1017b.removeView(this.f1007I);
                            break;
                        }
                        break;
                    case GLSurfaceView:
                        if (this.f1022h != null) {
                            this.f1016a.removeView(this.f1007I);
                            break;
                        }
                        break;
                }
                this.f1007I = null;
            }
            this.f1005G = null;
            this.f1006H.remove();
            this.f1006H = null;
        }
    }

    public final boolean isBaiduHeatMapEnabled() {
        return this.f1023i == null ? false : this.f1023i.m2011h();
    }

    public boolean isBaseIndoorMapMode() {
        return this.f1023i.m2026p();
    }

    public final boolean isBuildingsEnabled() {
        return this.f1023i == null ? false : this.f1023i.m2018l();
    }

    public final boolean isMyLocationEnabled() {
        return this.f1023i == null ? false : this.f1023i.m2030r();
    }

    public final boolean isSupportBaiduHeatMap() {
        return this.f1023i == null ? false : this.f1023i.m2013i();
    }

    public final boolean isTrafficEnabled() {
        return this.f1023i == null ? false : this.f1023i.m2007f();
    }

    public final void removeMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        if (this.f1036v.contains(onMarkerClickListener)) {
            this.f1036v.remove(onMarkerClickListener);
        }
    }

    public final void setBaiduHeatMapEnabled(boolean z) {
        if (this.f1023i != null) {
            this.f1023i.m2005e(z);
        }
    }

    public final void setBuildingsEnabled(boolean z) {
        if (this.f1023i != null) {
            this.f1023i.m2009g(z);
        }
    }

    public void setCompassIcon(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("compass's icon can not be null");
        }
        this.f1023i.m1970a(bitmap);
    }

    public void setCompassPosition(Point point) {
        if (this.f1023i.m1984a(point)) {
            this.f1015Q = point;
        }
    }

    public final void setIndoorEnable(boolean z) {
        if (this.f1023i != null) {
            this.f1013O = z;
            this.f1023i.m2012i(z);
        }
        if (this.f1000B != null && !z) {
            this.f1000B.onBaseIndoorMapMode(false, null);
        }
    }

    public final void setMapStatus(MapStatusUpdate mapStatusUpdate) {
        if (mapStatusUpdate != null) {
            C0616D a = m1068a(mapStatusUpdate);
            if (this.f1023i != null) {
                this.f1023i.m1974a(a);
                if (this.f1029o != null) {
                    this.f1029o.onMapStatusChange(getMapStatus());
                }
            }
        }
    }

    public final void setMapStatusLimits(LatLngBounds latLngBounds) {
        if (this.f1023i != null) {
            this.f1023i.m1972a(latLngBounds);
            setMapStatus(MapStatusUpdateFactory.newLatLngBounds(latLngBounds));
        }
    }

    public final void setMapType(int i) {
        if (this.f1023i != null) {
            switch (i) {
                case 1:
                    this.f1023i.m1982a(false);
                    this.f1023i.m2027q(this.f1011M);
                    this.f1023i.m2029r(this.f1012N);
                    this.f1023i.m2001d(true);
                    this.f1023i.m2012i(this.f1013O);
                    break;
                case 2:
                    this.f1023i.m1982a(true);
                    this.f1023i.m2027q(this.f1011M);
                    this.f1023i.m2029r(this.f1012N);
                    this.f1023i.m2001d(true);
                    break;
                case 3:
                    if (this.f1023i.m1951B()) {
                        this.f1023i.m2027q(false);
                    }
                    if (this.f1023i.m1952C()) {
                        this.f1023i.m2029r(false);
                    }
                    this.f1023i.m2001d(false);
                    this.f1023i.m2012i(false);
                    break;
            }
            if (this.f1022h != null) {
                this.f1022h.m2047a(i);
            }
        }
    }

    public final void setMaxAndMinZoomLevel(float f, float f2) {
        if (f <= 21.0f && f2 >= 3.0f && f >= f2 && this.f1023i != null) {
            this.f1023i.m1968a(f, f2);
        }
    }

    public final void setMyLocationConfigeration(MyLocationConfiguration myLocationConfiguration) {
        this.f1010L = myLocationConfiguration;
        m1070a(this.f1009K, this.f1010L);
    }

    public final void setMyLocationData(MyLocationData myLocationData) {
        this.f1009K = myLocationData;
        if (this.f1010L == null) {
            this.f1010L = new MyLocationConfiguration(LocationMode.NORMAL, false, null);
        }
        m1070a(myLocationData, this.f1010L);
    }

    public final void setMyLocationEnabled(boolean z) {
        if (this.f1023i != null) {
            this.f1023i.m2015k(z);
        }
    }

    public final void setOnBaseIndoorMapListener(OnBaseIndoorMapListener onBaseIndoorMapListener) {
        this.f1000B = onBaseIndoorMapListener;
    }

    public final void setOnMapClickListener(OnMapClickListener onMapClickListener) {
        this.f1031q = onMapClickListener;
    }

    public final void setOnMapDoubleClickListener(OnMapDoubleClickListener onMapDoubleClickListener) {
        this.f1034t = onMapDoubleClickListener;
    }

    public final void setOnMapDrawFrameCallback(OnMapDrawFrameCallback onMapDrawFrameCallback) {
        this.f999A = onMapDrawFrameCallback;
    }

    public void setOnMapLoadedCallback(OnMapLoadedCallback onMapLoadedCallback) {
        this.f1032r = onMapLoadedCallback;
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener onMapLongClickListener) {
        this.f1035u = onMapLongClickListener;
    }

    public void setOnMapRenderCallbadk(OnMapRenderCallback onMapRenderCallback) {
        this.f1033s = onMapRenderCallback;
    }

    public final void setOnMapStatusChangeListener(OnMapStatusChangeListener onMapStatusChangeListener) {
        this.f1029o = onMapStatusChangeListener;
    }

    public final void setOnMapTouchListener(OnMapTouchListener onMapTouchListener) {
        this.f1030p = onMapTouchListener;
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) {
        if (onMarkerClickListener != null && !this.f1036v.contains(onMarkerClickListener)) {
            this.f1036v.add(onMarkerClickListener);
        }
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener onMarkerDragListener) {
        this.f1038x = onMarkerDragListener;
    }

    public final void setOnMyLocationClickListener(OnMyLocationClickListener onMyLocationClickListener) {
        this.f1039y = onMyLocationClickListener;
    }

    public final void setOnPolylineClickListener(OnPolylineClickListener onPolylineClickListener) {
        if (onPolylineClickListener != null) {
            this.f1037w.add(onPolylineClickListener);
        }
    }

    @Deprecated
    public final void setPadding(int i, int i2, int i3, int i4) {
        if (i >= 0 && i2 >= 0 && i3 >= 0 && i4 >= 0 && this.f1023i != null) {
            this.f1023i.m1953D();
            float width;
            float height;
            MapStatusUpdate newMapStatus;
            switch (this.f1019d) {
                case TextureView:
                    if (this.f1017b != null) {
                        width = ((float) ((this.f1017b.getWidth() - i) - i3)) / ((float) this.f1017b.getWidth());
                        height = ((float) ((this.f1017b.getHeight() - i2) - i4)) / ((float) this.f1017b.getHeight());
                        newMapStatus = MapStatusUpdateFactory.newMapStatus(new Builder().targetScreen(new Point(((this.f1017b.getWidth() + i) - i3) / 2, ((this.f1017b.getHeight() + i2) - i4) / 2)).build());
                        this.f1023i.m1984a(new Point((int) ((width * ((float) this.f1015Q.x)) + ((float) i)), (int) ((height * ((float) this.f1015Q.y)) + ((float) i2))));
                        setMapStatus(newMapStatus);
                        this.f1017b.setPadding(i, i2, i3, i4);
                        this.f1017b.invalidate();
                        return;
                    }
                    return;
                case GLSurfaceView:
                    if (this.f1016a != null) {
                        width = ((float) ((this.f1016a.getWidth() - i) - i3)) / ((float) this.f1016a.getWidth());
                        height = ((float) ((this.f1016a.getHeight() - i2) - i4)) / ((float) this.f1016a.getHeight());
                        newMapStatus = MapStatusUpdateFactory.newMapStatus(new Builder().targetScreen(new Point(((this.f1016a.getWidth() + i) - i3) / 2, ((this.f1016a.getHeight() + i2) - i4) / 2)).build());
                        this.f1023i.m1984a(new Point((int) ((width * ((float) this.f1015Q.x)) + ((float) i)), (int) ((height * ((float) this.f1015Q.y)) + ((float) i2))));
                        setMapStatus(newMapStatus);
                        this.f1016a.setPadding(i, i2, i3, i4);
                        this.f1016a.invalidate();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public final void setTrafficEnabled(boolean z) {
        if (this.f1023i != null) {
            this.f1023i.m2006f(z);
        }
    }

    public final void setViewPadding(int i, int i2, int i3, int i4) {
        if (i >= 0 && i2 >= 0 && i3 >= 0 && i4 >= 0 && this.f1023i != null) {
            float f;
            switch (this.f1019d) {
                case TextureView:
                    if (this.f1017b != null) {
                        f = (float) i;
                        f = (float) i2;
                        this.f1023i.m1984a(new Point((int) (((((float) ((this.f1017b.getWidth() - i) - i3)) / ((float) this.f1017b.getWidth())) * ((float) this.f1015Q.x)) + f), (int) (((((float) ((this.f1017b.getHeight() - i2) - i4)) / ((float) this.f1017b.getHeight())) * ((float) this.f1015Q.y)) + f)));
                        this.f1017b.setPadding(i, i2, i3, i4);
                        this.f1017b.invalidate();
                        return;
                    }
                    return;
                case GLSurfaceView:
                    if (this.f1016a != null) {
                        f = (float) i;
                        f = (float) i2;
                        this.f1023i.m1984a(new Point((int) (((((float) ((this.f1016a.getWidth() - i) - i3)) / ((float) this.f1016a.getWidth())) * ((float) this.f1015Q.x)) + f), (int) (((((float) ((this.f1016a.getHeight() - i2) - i4)) / ((float) this.f1016a.getHeight())) * ((float) this.f1015Q.y)) + f)));
                        this.f1016a.setPadding(i, i2, i3, i4);
                        this.f1016a.invalidate();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void showInfoWindow(InfoWindow infoWindow) {
        if (infoWindow != null) {
            hideInfoWindow();
            if (infoWindow.f1128b != null) {
                this.f1007I = infoWindow.f1128b;
                this.f1007I.destroyDrawingCache();
                LayoutParams build = new MapViewLayoutParams.Builder().layoutMode(ELayoutMode.mapMode).position(infoWindow.f1129c).yOffset(infoWindow.f1131e).build();
                switch (this.f1019d) {
                    case TextureView:
                        if (this.f1017b != null) {
                            this.f1017b.addView(this.f1007I, build);
                            break;
                        }
                        break;
                    case GLSurfaceView:
                        if (this.f1022h != null) {
                            this.f1016a.addView(this.f1007I, build);
                            break;
                        }
                        break;
                }
            }
            this.f1005G = infoWindow;
            Overlay a = new MarkerOptions().perspective(false).icon(infoWindow.f1128b != null ? BitmapDescriptorFactory.fromView(infoWindow.f1128b) : infoWindow.f1127a).position(infoWindow.f1129c).zIndex(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED).m1168a(infoWindow.f1131e).mo1760a();
            a.listener = this.f1028n;
            a.f977q = C0636h.popup;
            Bundle bundle = new Bundle();
            a.mo1759a(bundle);
            if (this.f1023i != null) {
                this.f1023i.m1990b(bundle);
            }
            this.f1025k.add(a);
            this.f1006H = (Marker) a;
        }
    }

    public final void showMapIndoorPoi(boolean z) {
        if (this.f1023i != null) {
            this.f1023i.m2029r(z);
            this.f1012N = z;
        }
    }

    public final void showMapPoi(boolean z) {
        if (this.f1023i != null) {
            this.f1023i.m2027q(z);
            this.f1011M = z;
        }
    }

    public final void snapshot(SnapshotReadyCallback snapshotReadyCallback) {
        this.f1040z = snapshotReadyCallback;
        switch (this.f1019d) {
            case TextureView:
                if (this.f1024j != null) {
                    this.f1024j.m1914a("anything", null);
                    return;
                }
                return;
            case GLSurfaceView:
                if (this.f1022h != null) {
                    this.f1022h.m2048a("anything", null);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public final void snapshotScope(Rect rect, SnapshotReadyCallback snapshotReadyCallback) {
        this.f1040z = snapshotReadyCallback;
        switch (this.f1019d) {
            case TextureView:
                if (this.f1024j != null) {
                    this.f1024j.m1914a("anything", rect);
                    return;
                }
                return;
            case GLSurfaceView:
                if (this.f1022h != null) {
                    this.f1022h.m2048a("anything", rect);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public SwitchFloorError switchBaseIndoorMapFloor(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return SwitchFloorError.FLOOR_INFO_ERROR;
        }
        MapBaseIndoorMapInfo focusedBaseIndoorMapInfo = getFocusedBaseIndoorMapInfo();
        if (!str2.equals(focusedBaseIndoorMapInfo.f1135a)) {
            return SwitchFloorError.FOCUSED_ID_ERROR;
        }
        List floors = focusedBaseIndoorMapInfo.getFloors();
        return (floors == null || !floors.contains(str)) ? SwitchFloorError.FLOOR_OVERLFLOW : this.f1023i.m1987a(str, str2) ? SwitchFloorError.SWITCH_OK : SwitchFloorError.SWITCH_ERROR;
    }
}
