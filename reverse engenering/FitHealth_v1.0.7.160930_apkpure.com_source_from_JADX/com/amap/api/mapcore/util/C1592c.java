package com.amap.api.mapcore.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.api.mapcore.indoor.IndoorBuilding;
import com.amap.api.mapcore.util.C0284u.C0281a;
import com.amap.api.mapcore.util.C0284u.C0282b;
import com.amap.api.mapcore.util.C0284u.C0283c;
import com.amap.api.mapcore.util.C0296z.C0295a;
import com.amap.api.mapcore.util.ah.C0198a;
import com.amap.api.mapcore.util.bd.C0204a;
import com.amap.api.mapcore.util.be.C0205a;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnCacheRemoveListener;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnIndoorBuildingActiveListener;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMapScreenShotListener;
import com.amap.api.maps.AMap.OnMapTouchListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.AMap.OnMarkerDragListener;
import com.amap.api.maps.AMap.OnMyLocationChangeListener;
import com.amap.api.maps.AMap.OnPOIClickListener;
import com.amap.api.maps.AMap.OnPolylineClickListener;
import com.amap.api.maps.AMap.onMapPrintScreenListener;
import com.amap.api.maps.CustomRenderer;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.LatLngBounds.Builder;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.MyTrafficStyle;
import com.amap.api.maps.model.NavigateArrowOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.Text;
import com.amap.api.maps.model.TextOptions;
import com.amap.api.maps.model.TileOverlay;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.services.core.AMapException;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapCore;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.SelectedMapPoi;
import com.autonavi.amap.mapcore.VMapDataCache;
import com.autonavi.amap.mapcore.interfaces.CameraAnimator;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate.Type;
import com.autonavi.amap.mapcore.interfaces.GLOverlay;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IArcDelegate;
import com.autonavi.amap.mapcore.interfaces.ICircleDelegate;
import com.autonavi.amap.mapcore.interfaces.IGLSurfaceView;
import com.autonavi.amap.mapcore.interfaces.IGroundOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IMarkerDelegate;
import com.autonavi.amap.mapcore.interfaces.INavigateArrowDelegate;
import com.autonavi.amap.mapcore.interfaces.IOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IPolygonDelegate;
import com.autonavi.amap.mapcore.interfaces.IPolylineDelegate;
import com.autonavi.amap.mapcore.interfaces.IProjectionDelegate;
import com.autonavi.amap.mapcore.interfaces.ITileOverlayDelegate;
import com.autonavi.amap.mapcore.interfaces.IUiSettingsDelegate;
import com.facebook.login.widget.ToolTipPopup;
import com.tencent.open.yyb.TitleBar;
import com.umeng.analytics.C0919a;
import com.umeng.socialize.common.SocializeConstants;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import p031u.aly.C1507j;

/* compiled from: AMapDelegateImp */
public class C1592c implements Renderer, IAMapDelegate {
    private static final double aG = Math.log(2.0d);
    private CopyOnWriteArrayList<Integer> f4087A;
    private CopyOnWriteArrayList<Integer> f4088B;
    private C0283c f4089C;
    private C0281a f4090D;
    private C0282b f4091E;
    private int f4092F;
    private MapCore f4093G;
    private Context f4094H;
    private C1965a f4095I;
    private MapProjection f4096J;
    private GestureDetector f4097K;
    private ScaleGestureDetector f4098L;
    private be f4099M;
    private SurfaceHolder f4100N;
    private ah f4101O;
    private az f4102P;
    private aa f4103Q;
    private C0271q f4104R;
    private ar f4105S;
    private C0269o f4106T;
    private C0296z f4107U;
    private OnMyLocationChangeListener f4108V;
    private OnMarkerClickListener f4109W;
    private OnPolylineClickListener f4110X;
    private OnMarkerDragListener f4111Y;
    private OnMapLoadedListener f4112Z;
    float f4113a;
    private onMapPrintScreenListener aA;
    private OnMapScreenShotListener aB;
    private Handler aC;
    private IndoorBuilding aD;
    private CameraUpdateFactoryDelegate aE;
    private Timer aF;
    private boolean aH;
    private boolean aI;
    private boolean aJ;
    private boolean aK;
    private boolean aL;
    private boolean aM;
    private boolean aN;
    private boolean aO;
    private boolean aP;
    private Boolean aQ;
    private boolean aR;
    private boolean aS;
    private boolean aT;
    private Handler aU;
    private int aV;
    private C0275s aW;
    private boolean aX;
    private boolean aY;
    private volatile boolean aZ;
    private OnCameraChangeListener aa;
    private OnMapClickListener ab;
    private OnMapTouchListener ac;
    private OnPOIClickListener ad;
    private OnMapLongClickListener ae;
    private OnInfoWindowClickListener af;
    private OnIndoorBuildingActiveListener ag;
    private InfoWindowAdapter ah;
    private InfoWindowAdapter ai;
    private View aj;
    private IMarkerDelegate ak;
    private ap al;
    private IProjectionDelegate am;
    private IUiSettingsDelegate an;
    private LocationSource ao;
    private Rect ap;
    private C1606m aq;
    private bd ar;
    private aj as;
    private CameraAnimator at;
    private int au;
    private int av;
    private CancelableCallback aw;
    private int ax;
    private Drawable ay;
    private Location az;
    float f4114b;
    private volatile boolean ba;
    private Handler bb;
    private Runnable bc;
    private volatile boolean bd;
    private boolean be;
    private boolean bf;
    private boolean bg;
    private Marker bh;
    private IMarkerDelegate bi;
    private boolean bj;
    private boolean bk;
    private boolean bl;
    private int bm;
    private boolean bn;
    private Thread bo;
    private LatLngBounds bp;
    private boolean bq;
    private boolean br;
    private int bs;
    private int bt;
    private Handler bu;
    private Runnable bv;
    private Runnable bw;
    private C0218a bx;
    float f4115c;
    public ae f4116d;
    ad f4117e;
    ba f4118f;
    aw f4119g;
    C0286v f4120h;
    C0284u f4121i;
    IGLSurfaceView f4122j;
    Runnable f4123k;
    final Handler f4124l;
    CustomRenderer f4125m;
    private int f4126n;
    private int f4127o;
    private int f4128p;
    private Bitmap f4129q;
    private Bitmap f4130r;
    private int f4131s;
    private int f4132t;
    private boolean f4133u;
    private boolean f4134v;
    private boolean f4135w;
    private boolean f4136x;
    private MyTrafficStyle f4137y;
    private float f4138z;

    /* compiled from: AMapDelegateImp */
    class C02177 implements Runnable {
        final /* synthetic */ C1592c f331a;

        C02177(C1592c c1592c) {
            this.f331a = c1592c;
        }

        public void run() {
            try {
                this.f331a.showInfoWindow(this.f331a.ak);
            } catch (Throwable th) {
                ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "showInfoWindow postDelayed");
                th.printStackTrace();
            }
        }
    }

    /* compiled from: AMapDelegateImp */
    private static abstract class C0218a implements Runnable {
        boolean f332b;
        boolean f333c;
        C0281a f334d;
        C0283c f335e;
        C0282b f336f;

        private C0218a() {
            this.f332b = false;
            this.f333c = false;
        }

        public void run() {
            this.f332b = false;
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C0222c implements OnDoubleTapListener {
        final /* synthetic */ C1592c f343a;

        private C0222c(C1592c c1592c) {
            this.f343a = c1592c;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onDoubleTap(android.view.MotionEvent r6) {
            /*
            r5 = this;
            r4 = 1;
            r0 = r5.f343a;	 Catch:{ RemoteException -> 0x000e }
            r0 = r0.an;	 Catch:{ RemoteException -> 0x000e }
            r0 = r0.isZoomGesturesEnabled();	 Catch:{ RemoteException -> 0x000e }
            if (r0 != 0) goto L_0x0012;
        L_0x000d:
            return r4;
        L_0x000e:
            r0 = move-exception;
            r0.printStackTrace();
        L_0x0012:
            r0 = r5.f343a;
            r0 = r0.bm;
            if (r0 > r4) goto L_0x000d;
        L_0x001a:
            r0 = r5.f343a;
            r0.bl = r4;
            r0 = r5.f343a;
            r0 = r0.f4096J;
            r0 = r0.getMapZoomer();
            r1 = r5.f343a;
            r1 = r1.getMaxZoomLevel();
            r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1));
            if (r0 == 0) goto L_0x000d;
        L_0x0033:
            r0 = r6.getX();
            r1 = r6.getY();
            r0 = (int) r0;
            r1 = (int) r1;
            r2 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
            r3 = new android.graphics.Point;
            r3.<init>(r0, r1);
            r0 = com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate.zoomBy(r2, r3);
            r1 = r5.f343a;	 Catch:{ RemoteException -> 0x004e }
            r1.animateCamera(r0);	 Catch:{ RemoteException -> 0x004e }
            goto L_0x000d;
        L_0x004e:
            r0 = move-exception;
            r1 = "AMapDelegateImpGLSurfaceView";
            r2 = "onDoubleTap";
            com.amap.api.mapcore.util.ee.m4243a(r0, r1, r2);
            r0.printStackTrace();
            goto L_0x000d;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.c.c.onDoubleTap(android.view.MotionEvent):boolean");
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }

        public boolean onSingleTapConfirmed(final MotionEvent motionEvent) {
            this.f343a.bj = false;
            if (this.f343a.bn) {
                this.f343a.bn = false;
            } else {
                if (this.f343a.aj != null) {
                    if (this.f343a.f4116d.m149a(new Rect(this.f343a.aj.getLeft(), this.f343a.aj.getTop(), this.f343a.aj.getRight(), this.f343a.aj.getBottom()), (int) motionEvent.getX(), (int) motionEvent.getY())) {
                        if (this.f343a.af != null) {
                            IMarkerDelegate e = this.f343a.f4116d.m159e();
                            if (e.isVisible()) {
                                this.f343a.af.onInfoWindowClick(new Marker(e));
                            }
                        }
                    }
                }
                try {
                    if (this.f343a.f4116d.m154b(motionEvent)) {
                        final IMarkerDelegate e2 = this.f343a.f4116d.m159e();
                        if (e2 != null && e2.isVisible()) {
                            Marker marker = new Marker(e2);
                            if (this.f343a.f4109W != null) {
                                if (this.f343a.f4109W.onMarkerClick(marker) || this.f343a.f4116d.m151b() <= 0) {
                                    this.f343a.f4116d.m157d(e2);
                                } else {
                                    this.f343a.aC.postDelayed(new Runnable(this) {
                                        final /* synthetic */ C0222c f338b;

                                        public void run() {
                                            try {
                                                this.f338b.f343a.showInfoWindow(e2);
                                            } catch (Throwable th) {
                                                ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "onSingleTapUp showInfoWindow");
                                                th.printStackTrace();
                                            }
                                        }
                                    }, 20);
                                    if (!e2.isViewMode()) {
                                        LatLng realPosition = e2.getRealPosition();
                                        if (realPosition != null) {
                                            IPoint iPoint = new IPoint();
                                            this.f343a.latlon2Geo(realPosition.latitude, realPosition.longitude, iPoint);
                                            this.f343a.moveCamera(CameraUpdateFactoryDelegate.changeGeoCenter(iPoint));
                                        }
                                    }
                                }
                            }
                            this.f343a.f4116d.m157d(e2);
                        }
                    } else {
                        DPoint dPoint;
                        if (this.f343a.ab != null) {
                            dPoint = new DPoint();
                            this.f343a.getPixel2LatLng((int) motionEvent.getX(), (int) motionEvent.getY(), dPoint);
                            this.f343a.ab.onMapClick(new LatLng(dPoint.f2027y, dPoint.f2026x));
                        }
                        if (this.f343a.f4110X != null) {
                            dPoint = new DPoint();
                            this.f343a.getPixel2LatLng((int) motionEvent.getX(), (int) motionEvent.getY(), dPoint);
                            LatLng latLng = new LatLng(dPoint.f2027y, dPoint.f2026x);
                            if (latLng != null) {
                                IOverlayDelegate a = this.f343a.f4120h.m1026a(latLng);
                                if (a != null) {
                                    this.f343a.f4110X.onPolylineClick(new Polyline((IPolylineDelegate) a));
                                }
                            }
                        }
                        this.f343a.queueEvent(new Runnable(this) {
                            final /* synthetic */ C0222c f342b;

                            public void run() {
                                final Poi a = this.f342b.f343a.m4077a((int) motionEvent.getX(), (int) motionEvent.getY(), 25);
                                if (this.f342b.f343a.ad != null && a != null) {
                                    this.f342b.f343a.f4124l.post(new Runnable(this) {
                                        final /* synthetic */ C02212 f340b;

                                        public void run() {
                                            this.f340b.f342b.f343a.ad.onPOIClick(a);
                                        }
                                    });
                                }
                            }
                        });
                    }
                } catch (Throwable e3) {
                    ee.m4243a(e3, "AMapDelegateImpGLSurfaceView", "onSingleTapUp moveCamera");
                    e3.printStackTrace();
                } catch (Throwable e32) {
                    ee.m4243a(e32, "AMapDelegateImpGLSurfaceView", "onSingleTapUp");
                    e32.printStackTrace();
                }
            }
            return true;
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C0223d implements OnGestureListener {
        FPoint f344a;
        IPoint f345b;
        IPoint f346c;
        CameraUpdateFactoryDelegate f347d;
        final /* synthetic */ C1592c f348e;

        private C0223d(C1592c c1592c) {
            this.f348e = c1592c;
            this.f344a = new FPoint();
            this.f345b = new IPoint();
            this.f346c = new IPoint();
            this.f347d = CameraUpdateFactoryDelegate.changeGeoCenter(this.f346c);
        }

        public boolean onDown(MotionEvent motionEvent) {
            this.f348e.bj = false;
            if (!this.f348e.bl) {
                try {
                    this.f348e.stopAnimation();
                } catch (Throwable e) {
                    ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "onDown");
                    e.printStackTrace();
                }
            }
            this.f348e.bl = false;
            this.f348e.bm = 0;
            this.f344a.f2028x = motionEvent.getX();
            this.f344a.f2029y = motionEvent.getY();
            this.f348e.getPixel2Geo((int) this.f344a.f2028x, (int) this.f344a.f2029y, this.f345b);
            return true;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onFling(android.view.MotionEvent r11, android.view.MotionEvent r12, float r13, float r14) {
            /*
            r10 = this;
            r4 = 0;
            r9 = 1;
            r0 = r10.f348e;
            r0.bj = r4;
            r0 = r10.f348e;	 Catch:{ RemoteException -> 0x0014 }
            r0 = r0.an;	 Catch:{ RemoteException -> 0x0014 }
            r0 = r0.isScrollGesturesEnabled();	 Catch:{ RemoteException -> 0x0014 }
            if (r0 != 0) goto L_0x001f;
        L_0x0013:
            return r9;
        L_0x0014:
            r0 = move-exception;
            r1 = "AMapDelegateImpGLSurfaceView";
            r2 = "onFling";
            com.amap.api.mapcore.util.ee.m4243a(r0, r1, r2);
            r0.printStackTrace();
        L_0x001f:
            r0 = r10.f348e;
            r0 = r0.ar;
            r0 = r0.m265a();
            if (r0 != 0) goto L_0x0013;
        L_0x002b:
            r0 = r11.getEventTime();
            r2 = r10.f348e;
            r2 = r2.ar;
            r2 = r2.m267b();
            r0 = r0 - r2;
            r2 = 30;
            r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r0 < 0) goto L_0x0013;
        L_0x0040:
            r0 = r10.f348e;
            r0 = r0.getMapWidth();
            r1 = r10.f348e;
            r1 = r1.getMapHeight();
            r6 = r0 * 2;
            r8 = r1 * 2;
            r2 = r10.f348e;
            r0 = r0 / 2;
            r2.au = r0;
            r0 = r10.f348e;
            r1 = r1 / 2;
            r0.av = r1;
            r0 = r10.f348e;
            r1 = 0;
            r0.aw = r1;
            r0 = r10.f348e;
            r0 = r0.aj;
            if (r0 == 0) goto L_0x0096;
        L_0x006c:
            r0 = r10.f348e;
            r0 = r0.ak;
            if (r0 == 0) goto L_0x0096;
        L_0x0074:
            r0 = r10.f348e;
            r0 = r0.ak;
            r0 = r0.isViewMode();
            if (r0 != 0) goto L_0x0096;
        L_0x0080:
            r0 = r10.f348e;
            r0.aS = r4;
            r0 = r10.f348e;
            r0 = r0.al;
            if (r0 == 0) goto L_0x0096;
        L_0x008d:
            r0 = r10.f348e;
            r0 = r0.al;
            r0.setVisible(r9);
        L_0x0096:
            r0 = r10.f348e;
            r0 = r0.at;
            r1 = r10.f348e;
            r1 = r1.au;
            r2 = r10.f348e;
            r2 = r2.av;
            r3 = -r13;
            r3 = (int) r3;
            r3 = r3 * 3;
            r3 = r3 / 5;
            r4 = -r14;
            r4 = (int) r4;
            r4 = r4 * 3;
            r4 = r4 / 5;
            r5 = -r6;
            r7 = -r8;
            r0.fling(r1, r2, r3, r4, r5, r6, r7, r8);
            r0 = r10.f348e;
            r0 = r0.f4119g;
            if (r0 == 0) goto L_0x0013;
        L_0x00bf:
            r0 = r10.f348e;
            r0 = r0.f4119g;
            r0.m220b(r9);
            goto L_0x0013;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.api.mapcore.util.c.d.onFling(android.view.MotionEvent, android.view.MotionEvent, float, float):boolean");
        }

        public void onLongPress(MotionEvent motionEvent) {
            this.f348e.bj = false;
            this.f348e.bi = this.f348e.f4116d.m143a(motionEvent);
            if (this.f348e.f4111Y != null && this.f348e.bi != null && this.f348e.bi.isDraggable()) {
                this.f348e.bh = new Marker(this.f348e.bi);
                LatLng position = this.f348e.bh.getPosition();
                LatLng realPosition = this.f348e.bi.getRealPosition();
                IPoint iPoint = new IPoint();
                this.f348e.getLatLng2Pixel(realPosition.latitude, realPosition.longitude, iPoint);
                iPoint.f2031y -= 60;
                DPoint dPoint = new DPoint();
                this.f348e.getPixel2LatLng(iPoint.f2030x, iPoint.f2031y, dPoint);
                this.f348e.bh.setPosition(new LatLng((position.latitude + dPoint.f2027y) - realPosition.latitude, (dPoint.f2026x + position.longitude) - realPosition.longitude));
                this.f348e.f4116d.m157d(this.f348e.bi);
                this.f348e.f4111Y.onMarkerDragStart(this.f348e.bh);
                this.f348e.bg = true;
            } else if (this.f348e.ae != null) {
                DPoint dPoint2 = new DPoint();
                this.f348e.getPixel2LatLng((int) motionEvent.getX(), (int) motionEvent.getY(), dPoint2);
                this.f348e.ae.onMapLongClick(new LatLng(dPoint2.f2027y, dPoint2.f2026x));
                this.f348e.bn = true;
            }
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            this.f348e.bj = true;
            if ((!this.f348e.at.isFinished() && this.f348e.at.getMode() == 1) || this.f348e.ar.m265a() || motionEvent2.getEventTime() - this.f348e.ar.m267b() < 30) {
                this.f348e.bj = false;
            } else if (motionEvent2.getPointerCount() >= 2) {
                this.f348e.bj = false;
            } else {
                try {
                    if (!this.f348e.an.isScrollGesturesEnabled()) {
                        this.f348e.bj = false;
                    } else if (this.f348e.bm > 1) {
                        this.f348e.bj = false;
                    } else {
                        if (!(this.f348e.aj == null || this.f348e.ak == null || this.f348e.ak.isViewMode() || this.f348e.al == null)) {
                            this.f348e.al.setVisible(true);
                        }
                        IPoint iPoint = new IPoint();
                        this.f348e.getPixel2Geo((int) motionEvent2.getX(), (int) motionEvent2.getY(), iPoint);
                        int i = this.f345b.f2030x - iPoint.f2030x;
                        int i2 = this.f345b.f2031y - iPoint.f2031y;
                        IPoint iPoint2 = new IPoint();
                        this.f348e.f4096J.getGeoCenter(iPoint2);
                        this.f346c.f2030x = i + iPoint2.f2030x;
                        this.f346c.f2031y = i2 + iPoint2.f2031y;
                        this.f347d.geoPoint = this.f346c;
                        this.f348e.f4117e.m135a(this.f347d);
                    }
                } catch (Throwable th) {
                    ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "onScroll");
                    th.printStackTrace();
                }
            }
            return true;
        }

        public void onShowPress(MotionEvent motionEvent) {
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C0224g implements OnScaleGestureListener {
        CameraUpdateFactoryDelegate f349a;
        final /* synthetic */ C1592c f350b;
        private float f351c;
        private IPoint f352d;

        private C0224g(C1592c c1592c) {
            this.f350b = c1592c;
            this.f351c = 0.0f;
            this.f352d = new IPoint();
            this.f349a = CameraUpdateFactoryDelegate.newInstance();
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            if (!this.f350b.be) {
                float scaleFactor = scaleGestureDetector.getScaleFactor();
                if (this.f350b.bf || ((double) scaleFactor) > 1.08d || ((double) scaleFactor) < 0.92d) {
                    this.f350b.bf = true;
                    scaleFactor = (float) (Math.log((double) scaleFactor) / C1592c.aG);
                    this.f349a.zoom = dj.m557a(scaleFactor + this.f351c);
                    this.f350b.f4117e.m135a(this.f349a);
                }
            }
            return false;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            try {
                if (!this.f350b.an.isZoomGesturesEnabled() || this.f350b.bm < 2) {
                    return false;
                }
            } catch (Throwable e) {
                ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "onScaleBegin");
                e.printStackTrace();
            }
            this.f350b.bm = 2;
            if (this.f350b.be) {
                return false;
            }
            if (this.f350b.br) {
                this.f349a.isUseAnchor = this.f350b.br;
                this.f349a.nowType = Type.changeGeoCenterZoom;
                this.f350b.getPixel2Geo(this.f350b.bs, this.f350b.bt, this.f352d);
                this.f349a.geoPoint = this.f352d;
            } else {
                this.f349a.nowType = Type.zoomTo;
            }
            this.f350b.bf = false;
            this.f351c = this.f350b.f4096J.getMapZoomer();
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            this.f351c = 0.0f;
            if (this.f350b.bf) {
                this.f350b.bf = false;
                CameraUpdateFactoryDelegate newInstance = CameraUpdateFactoryDelegate.newInstance();
                newInstance.isChangeFinished = true;
                this.f350b.f4117e.m135a(newInstance);
            }
            this.f350b.m4130t();
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C0225h extends TimerTask {
        C1592c f353a;
        final /* synthetic */ C1592c f354b;

        public C0225h(C1592c c1592c, C1592c c1592c2) {
            this.f354b = c1592c;
            this.f353a = c1592c2;
        }

        public void run() {
            if (!this.f354b.aZ || this.f354b.ba || !this.f354b.f4120h.m1035d()) {
                this.f354b.f4122j.requestRender();
            } else if (!this.f354b.f4116d.m158d()) {
                this.f354b.f4122j.requestRender();
            }
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C0226i implements Runnable {
        final /* synthetic */ C1592c f355a;
        private Context f356b;
        private OnCacheRemoveListener f357c;

        public C0226i(C1592c c1592c, Context context, OnCacheRemoveListener onCacheRemoveListener) {
            this.f355a = c1592c;
            this.f356b = context;
            this.f357c = onCacheRemoveListener;
        }

        public void run() {
            Throwable th;
            boolean z;
            Throwable th2;
            boolean z2;
            try {
                boolean z3;
                Context applicationContext = this.f356b.getApplicationContext();
                String b = dj.m589b(applicationContext);
                String a = dj.m570a(applicationContext);
                boolean z4 = this.f355a.m4087a(new File(b));
                if (z4) {
                    try {
                        if (this.f355a.m4087a(new File(a))) {
                            z3 = true;
                            this.f355a.f4093G.setParameter(2601, 1, 0, 0, 0);
                            if (this.f357c != null) {
                                this.f357c.onRemoveCacheFinish(z3);
                            }
                        }
                    } catch (Throwable th3) {
                        th2 = th3;
                        z2 = z4;
                        this.f355a.f4093G.setParameter(2601, 1, 0, 0, 0);
                        if (this.f357c != null) {
                            this.f357c.onRemoveCacheFinish(z2);
                        }
                        throw th2;
                    }
                }
                z3 = false;
                try {
                    this.f355a.f4093G.setParameter(2601, 1, 0, 0, 0);
                    if (this.f357c != null) {
                        this.f357c.onRemoveCacheFinish(z3);
                    }
                } catch (Throwable th32) {
                    th32.printStackTrace();
                }
            } catch (Throwable th4) {
                th2 = th4;
                z2 = true;
                this.f355a.f4093G.setParameter(2601, 1, 0, 0, 0);
                if (this.f357c != null) {
                    this.f357c.onRemoveCacheFinish(z2);
                }
                throw th2;
            }
        }

        public boolean equals(Object obj) {
            return obj instanceof C0226i;
        }
    }

    /* compiled from: AMapDelegateImp */
    class C15889 implements InfoWindowAdapter {
        final /* synthetic */ C1592c f4069a;

        C15889(C1592c c1592c) {
            this.f4069a = c1592c;
        }

        public View getInfoWindow(Marker marker) {
            return null;
        }

        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C1589b implements C0204a {
        Float f4070a;
        Float f4071b;
        IPoint f4072c;
        float f4073d;
        CameraUpdateFactoryDelegate f4074e;
        final /* synthetic */ C1592c f4075f;
        private float f4076g;
        private float f4077h;
        private float f4078i;
        private float f4079j;
        private float f4080k;

        private C1589b(C1592c c1592c) {
            this.f4075f = c1592c;
            this.f4070a = null;
            this.f4071b = null;
            this.f4072c = new IPoint();
            this.f4073d = 0.0f;
            this.f4074e = CameraUpdateFactoryDelegate.newInstance();
        }

        public void mo1485a(float f, float f2, float f3, float f4, float f5) {
            this.f4076g = f2;
            this.f4078i = f3;
            this.f4077h = f4;
            this.f4079j = f5;
            this.f4080k = (this.f4079j - this.f4078i) / (this.f4077h - this.f4076g);
            this.f4070a = null;
            this.f4071b = null;
            if (this.f4075f.br) {
                this.f4074e.nowType = Type.changeGeoCenterZoomTiltBearing;
                this.f4075f.getPixel2Geo(this.f4075f.bs, this.f4075f.bt, this.f4072c);
                this.f4074e.geoPoint = this.f4072c;
                this.f4074e.isUseAnchor = this.f4075f.br;
            } else {
                this.f4074e.nowType = Type.changeTilt;
            }
            this.f4074e.zoom = this.f4075f.f4096J.getMapZoomer();
            this.f4074e.bearing = this.f4075f.f4096J.getMapAngle();
        }

        public boolean mo1486a(MotionEvent motionEvent, float f, float f2, float f3, float f4) {
            try {
                if (!this.f4075f.an.isTiltGesturesEnabled()) {
                    return true;
                }
                if (this.f4075f.bf || this.f4075f.bk) {
                    return true;
                }
                if (this.f4071b == null) {
                    this.f4071b = Float.valueOf(f4);
                }
                if (this.f4070a == null) {
                    this.f4070a = Float.valueOf(f2);
                }
                float f5 = this.f4078i - f2;
                float f6 = this.f4079j - f4;
                float f7 = this.f4076g - f;
                float f8 = this.f4077h - f3;
                if (((double) Math.abs(this.f4080k - ((f4 - f2) / (f3 - f)))) >= 0.2d || (((f5 <= 0.0f || f6 <= 0.0f) && (f5 >= 0.0f || f6 >= 0.0f)) || ((f7 < 0.0f || f8 < 0.0f) && (f7 > 0.0f || f8 > 0.0f)))) {
                    return false;
                }
                f6 = (this.f4070a.floatValue() - f2) / 4.0f;
                this.f4075f.be = true;
                f5 = this.f4075f.f4096J.getCameraHeaderAngle();
                if (f5 > 45.0f) {
                    f5 = 45.0f;
                }
                this.f4073d = f5 - f6;
                this.f4074e.tilt = this.f4073d;
                this.f4075f.f4117e.m135a(this.f4074e);
                this.f4070a = Float.valueOf(f2);
                this.f4071b = Float.valueOf(f4);
                return true;
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        }

        public void mo1484a() {
            if (!this.f4075f.bf) {
                try {
                    if (!this.f4075f.an.isZoomGesturesEnabled()) {
                        return;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    this.f4075f.animateCamera(CameraUpdateFactoryDelegate.zoomOut());
                } catch (Throwable e2) {
                    ee.m4243a(e2, "AMapDelegateImpGLSurfaceView", "onMultiTouchSingleTap");
                    e2.printStackTrace();
                }
            }
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C1590e implements C0295a {
        final /* synthetic */ C1592c f4081a;

        private C1590e(C1592c c1592c) {
            this.f4081a = c1592c;
        }

        public void mo1487a(int i) {
            if (this.f4081a.aD != null) {
                this.f4081a.aD.activeFloorIndex = this.f4081a.aD.floor_indexs[i];
                this.f4081a.aD.activeFloorName = this.f4081a.aD.floor_names[i];
                try {
                    this.f4081a.setIndoorBuildingInfo(this.f4081a.aD);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* compiled from: AMapDelegateImp */
    private class C1591f implements C0205a {
        float f4082a;
        float f4083b;
        IPoint f4084c;
        CameraUpdateFactoryDelegate f4085d;
        final /* synthetic */ C1592c f4086e;

        private C1591f(C1592c c1592c) {
            this.f4086e = c1592c;
            this.f4082a = 0.0f;
            this.f4083b = 0.0f;
            this.f4084c = new IPoint();
            this.f4085d = CameraUpdateFactoryDelegate.newInstance();
        }

        public boolean mo1488a(be beVar) {
            if (this.f4086e.be) {
                return false;
            }
            float b = beVar.m5689b();
            this.f4082a += b;
            if (!this.f4086e.bk && Math.abs(this.f4082a) <= BitmapDescriptorFactory.HUE_ORANGE && Math.abs(this.f4082a) <= 350.0f) {
                return true;
            }
            this.f4086e.bk = true;
            this.f4083b = b + this.f4086e.f4096J.getMapAngle();
            this.f4085d.bearing = this.f4083b;
            this.f4086e.f4117e.m135a(this.f4085d);
            this.f4082a = 0.0f;
            return true;
        }

        public boolean mo1489b(be beVar) {
            try {
                if (!this.f4086e.an.isRotateGesturesEnabled()) {
                    return false;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (this.f4086e.br) {
                this.f4085d.isUseAnchor = this.f4086e.br;
                this.f4085d.nowType = Type.changeBearingGeoCenter;
                this.f4086e.getPixel2Geo(this.f4086e.bs, this.f4086e.bt, this.f4084c);
                this.f4085d.geoPoint = this.f4084c;
            } else {
                this.f4085d.nowType = Type.changeBearing;
            }
            this.f4086e.bk = false;
            this.f4082a = 0.0f;
            this.f4086e.bm = 2;
            if (this.f4086e.be || ((float) this.f4086e.m4149c()) / 8.0f >= beVar.m4010c()) {
                return false;
            }
            return true;
        }

        public void mo1490c(be beVar) {
            this.f4082a = 0.0f;
            if (this.f4086e.bk) {
                this.f4086e.bk = false;
                CameraUpdateFactoryDelegate newInstance = CameraUpdateFactoryDelegate.newInstance();
                newInstance.isChangeFinished = true;
                this.f4086e.f4117e.m135a(newInstance);
            }
            this.f4086e.m4130t();
        }
    }

    public /* synthetic */ IMarkerDelegate addMarker4Imp(MarkerOptions markerOptions) throws RemoteException {
        return m4138a(markerOptions);
    }

    public MapCore getMapCore() {
        return this.f4093G;
    }

    public int getLineTextureID() {
        return this.f4126n;
    }

    public MapProjection getMapProjection() {
        if (this.f4096J == null) {
            this.f4096J = this.f4093G.getMapstate();
        }
        return this.f4096J;
    }

    public void m4145a(GL10 gl10) {
        int i = 0;
        if (!this.aT) {
            int[] iArr = new int[500];
            this.f4087A.clear();
            gl10.glGenTextures(500, iArr, 0);
            while (i < iArr.length) {
                this.f4087A.add(Integer.valueOf(iArr[i]));
                i++;
            }
            this.aT = true;
        }
    }

    public C1592c(IGLSurfaceView iGLSurfaceView, Context context) {
        this(iGLSurfaceView, context, null);
        this.f4094H = context;
    }

    private C1592c(IGLSurfaceView iGLSurfaceView, Context context, AttributeSet attributeSet) {
        this.f4126n = -1;
        this.f4127o = -1;
        this.f4128p = 40;
        this.f4129q = null;
        this.f4130r = null;
        this.f4131s = 221010267;
        this.f4132t = 101697799;
        this.f4113a = TitleBar.SHAREBTN_RIGHT_MARGIN;
        this.f4114b = 0.0f;
        this.f4115c = 0.0f;
        this.f4133u = false;
        this.f4134v = true;
        this.f4135w = true;
        this.f4136x = false;
        this.f4137y = null;
        this.f4138z = 1.0f;
        this.f4087A = new CopyOnWriteArrayList();
        this.f4088B = new CopyOnWriteArrayList();
        this.f4117e = new ad(this);
        this.f4089C = C0283c.DAY;
        this.f4090D = C0281a.NORAML;
        this.f4091E = C0282b.NORMAL;
        this.f4092F = 1;
        this.f4095I = null;
        this.f4100N = null;
        this.ap = new Rect();
        this.au = 0;
        this.av = 0;
        this.aw = null;
        this.ax = 0;
        this.ay = null;
        this.aA = null;
        this.aB = null;
        this.aC = new Handler();
        this.aD = null;
        this.aE = null;
        this.aH = true;
        this.aI = false;
        this.aJ = false;
        this.aK = false;
        this.aL = false;
        this.aM = true;
        this.aN = false;
        this.aO = false;
        this.aP = false;
        this.aQ = Boolean.valueOf(false);
        this.aR = false;
        this.aS = true;
        this.aT = false;
        this.aU = new Handler();
        this.f4120h = null;
        this.f4121i = null;
        this.f4122j = null;
        this.aV = 0;
        this.aW = new C0275s();
        this.aZ = false;
        this.ba = false;
        this.bb = new Handler();
        this.bc = new C0268j(this);
        this.bd = false;
        this.be = false;
        this.bf = false;
        this.bg = false;
        this.bh = null;
        this.bi = null;
        this.bj = false;
        this.bk = false;
        this.bl = false;
        this.bm = 0;
        this.bn = false;
        this.bo = new C0239d(this);
        this.bp = null;
        this.f4124l = new C0249e(this);
        this.bq = false;
        this.br = false;
        this.bu = new C0253f(this);
        this.bv = new C0265g(this);
        this.bw = new C0267h(this);
        this.bx = new C1603i(this);
        C0273r.f696c = dl.m604c(context);
        this.f4122j = iGLSurfaceView;
        this.f4094H = context;
        this.an = new ax(this);
        this.f4093G = new MapCore(this.f4094H);
        this.f4095I = new C1965a(this);
        this.f4093G.setMapCallback(this.f4095I);
        iGLSurfaceView.setRenderer(this);
        m4111l();
        this.f4121i = new C0284u(this, context);
        this.am = new aq(this);
        this.aq = new C1606m(this);
        this.f4097K = new GestureDetector(context, new C0223d());
        this.f4097K.setOnDoubleTapListener(new C0222c());
        this.f4097K.setIsLongpressEnabled(true);
        this.f4098L = new ScaleGestureDetector(context, new C0224g());
        this.f4099M = new be(context, new C1591f());
        this.ar = new bd(context, new C1589b());
        this.f4101O = new ah(this, context, this) {
            final /* synthetic */ C1592c f4068a;

            protected void mo1481a() {
                super.mo1481a();
                this.f4068a.aC.removeCallbacks(this.f4068a.bw);
                this.f4068a.aC.post(this.f4068a.bv);
            }
        };
        this.f4120h = new C0286v(this);
        this.f4102P = new az(this.f4094H, this);
        this.f4105S = new ar(this.f4094H, this);
        this.f4106T = new C0269o(this.f4094H);
        this.f4107U = new C0296z(this.f4094H);
        this.f4119g = new aw(this.f4094H, this);
        this.f4118f = new ba(this.f4094H, this);
        this.f4103Q = new aa(this.f4094H, this.f4117e, this);
        this.f4104R = new C0271q(this.f4094H, this.f4117e, this);
        this.f4116d = new ae(this.f4094H, attributeSet, this);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.f4101O.addView((View) this.f4122j, 0, layoutParams);
        this.f4101O.addView(this.f4106T, 1, layoutParams);
        this.f4101O.addView(this.f4116d, new C0198a(layoutParams));
        this.f4101O.addView(this.f4102P, layoutParams);
        this.f4101O.addView(this.f4105S, layoutParams);
        this.f4101O.addView(this.f4119g, layoutParams);
        this.f4101O.addView(this.f4107U, new LayoutParams(-2, -2));
        this.f4107U.m1064a(new C1590e());
        this.f4101O.addView(this.f4118f, new C0198a(-2, -2, new FPoint(0.0f, 0.0f), 0, 0, 83));
        this.f4101O.addView(this.f4103Q, new C0198a(-2, -2, new FPoint(0.0f, 0.0f), 0, 0, 83));
        try {
            if (!this.an.isMyLocationButtonEnabled()) {
                this.f4103Q.setVisibility(8);
            }
        } catch (Throwable e) {
            ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "locationView gone");
            e.printStackTrace();
        }
        this.f4101O.addView(this.f4104R, new C0198a(-2, -2, new FPoint(0.0f, 0.0f), 0, 0, 51));
        this.f4104R.setVisibility(8);
        this.at = new CameraAnimator(context);
        this.as = new aj(this, context);
        this.ai = new C15889(this);
        this.ah = this.ai;
    }

    public void addOverlay(GLOverlay gLOverlay) {
        gLOverlay.setMap(this);
        this.aW.m1003a(gLOverlay);
    }

    public void removeOverlay(GLOverlay gLOverlay) {
        this.aW.m1005b(gLOverlay);
    }

    public void clearGLOverlay() {
        this.aW.m1002a();
    }

    public void setOnMyLocationChangeListener(OnMyLocationChangeListener onMyLocationChangeListener) {
        this.f4108V = onMyLocationChangeListener;
    }

    public void onActivityResume() {
        this.aY = false;
    }

    public void onActivityPause() {
        this.aY = true;
    }

    public void onResume() {
        if (this.aV != 1) {
            this.aV = 1;
            this.aX = false;
            if (!this.aI) {
                queueEvent(new Runnable(this) {
                    final /* synthetic */ C1592c f311a;

                    {
                        this.f311a = r1;
                    }

                    public void run() {
                        this.f311a.m4111l();
                        this.f311a.m4122p();
                        if (this.f311a.f4095I != null) {
                            this.f311a.f4095I.onResume(this.f311a.f4093G);
                            this.f311a.setRunLowFrame(false);
                        }
                        if (this.f311a.f4119g != null) {
                            this.f311a.f4119g.m223d();
                        }
                        if (this.f311a.as != null) {
                            this.f311a.as.m183a();
                        }
                    }
                });
            }
            if (this.f4122j instanceof C1604k) {
                ((C1604k) this.f4122j).onResume();
            } else {
                ((C1605l) this.f4122j).onResume();
            }
        }
    }

    public void onPause() {
        if (this.aV == 1) {
            this.aV = -1;
            this.aX = true;
            this.aL = false;
            if (this.f4106T != null) {
                this.f4106T.m997a(true);
            }
            if (this.f4095I != null) {
                this.f4095I.destoryMap(this.f4093G);
            }
            m4124q();
            IPoint iPoint = new IPoint();
            this.f4096J.recalculate();
            this.f4096J.getGeoCenter(iPoint);
            this.f4131s = iPoint.f2030x;
            this.f4132t = iPoint.f2031y;
            this.f4113a = this.f4096J.getMapZoomer();
            this.f4115c = this.f4096J.getMapAngle();
            this.f4114b = this.f4096J.getCameraHeaderAngle();
            if (this.f4122j instanceof C1604k) {
                ((C1604k) this.f4122j).onPause();
            } else {
                ((C1605l) this.f4122j).onPause();
            }
            m4115m();
        }
    }

    private void m4111l() {
        if (!this.aI) {
            this.f4093G.newMap();
            this.f4095I.onResume(this.f4093G);
            this.f4096J = this.f4093G.getMapstate();
            this.f4096J.setGeoCenter(this.f4131s, this.f4132t);
            this.f4096J.setMapAngle(this.f4115c);
            this.f4096J.setMapZoomer(this.f4113a);
            this.f4096J.setCameraHeaderAngle(this.f4114b);
            this.f4093G.setMapstate(this.f4096J);
            this.aI = true;
            m4118n();
            this.f4122j.setRenderMode(0);
        }
    }

    private void m4115m() {
        queueEvent(new Runnable(this) {
            final /* synthetic */ C1592c f312a;

            {
                this.f312a = r1;
            }

            public void run() {
                if (this.f312a.aI) {
                    this.f312a.f4089C = C0283c.DAY;
                    this.f312a.f4090D = C0281a.NORAML;
                    this.f312a.f4091E = C0282b.NORMAL;
                    try {
                        this.f312a.f4093G.destroy();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    VMapDataCache.getInstance().reset();
                    this.f312a.aI = false;
                }
            }
        });
    }

    private void m4118n() {
        try {
            setIndoorEnabled(this.f4133u);
            set3DBuildingEnabled(this.f4134v);
            setMapTextEnable(this.f4135w);
            setTrafficEnabled(this.f4136x);
            setMyTrafficStyle(this.f4137y);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setMyLocationStyle(MyLocationStyle myLocationStyle) {
        if (this.as != null) {
            this.as.m187a(myLocationStyle);
        }
    }

    public void setMyLocationType(int i) {
        if (this.as != null) {
            this.as.m185a(i);
        }
    }

    public void setMyLocationRotateAngle(float f) throws RemoteException {
        if (this.as != null) {
            this.as.m184a(f);
        }
    }

    public void showMyLocationOverlay(Location location) throws RemoteException {
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            try {
                if (!this.aH || this.ao == null) {
                    this.as.m188b();
                    this.as = null;
                    return;
                }
                if (this.as == null || this.az == null) {
                    if (this.as == null) {
                        this.as = new aj(this, this.f4094H);
                    }
                    moveCamera(CameraUpdateFactoryDelegate.newLatLngZoom(latLng, this.f4096J.getMapZoomer()));
                }
                this.as.m186a(location);
                if (!(this.f4108V == null || (this.az != null && this.az.getBearing() == location.getBearing() && this.az.getAccuracy() == location.getAccuracy() && this.az.getLatitude() == location.getLatitude() && this.az.getLongitude() == location.getLongitude()))) {
                    this.f4108V.onMyLocationChange(location);
                }
                this.az = new Location(location);
                setRunLowFrame(false);
            } catch (Throwable e) {
                ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "showMyLocationOverlay");
                e.printStackTrace();
            }
        }
    }

    public void showZoomControlsEnabled(boolean z) {
        if (this.f4118f != null) {
            this.f4118f.m241a(z);
        }
    }

    public void showIndoorSwitchControlsEnabled(boolean z) {
        if (this.f4107U != null && z && m4120o()) {
            this.f4107U.m1066a(true);
        }
    }

    private boolean m4120o() {
        if (!(this.f4096J.getMapZoomer() < 17.0f || this.aD == null || this.aD.geoCenter == null)) {
            IPoint iPoint = new IPoint();
            m4140a(this.aD.geoCenter.f2030x, this.aD.geoCenter.f2031y, iPoint);
            if (this.ap.contains(iPoint.f2030x, iPoint.f2031y)) {
                return true;
            }
        }
        return false;
    }

    public void destroy() {
        this.aQ = Boolean.valueOf(true);
        try {
            m4124q();
            if (this.f4130r != null) {
                this.f4130r.recycle();
                this.f4130r = null;
            }
            if (this.f4129q != null) {
                this.f4129q.recycle();
                this.f4129q = null;
            }
            if (!(this.f4124l == null || this.f4123k == null)) {
                this.f4124l.removeCallbacks(this.f4123k);
                this.f4123k = null;
            }
            if (this.bb != null) {
                this.bb.removeCallbacks(this.bc);
            }
            if (this.f4118f != null) {
                this.f4118f.m238a();
            }
            if (this.f4105S != null) {
                this.f4105S.m203a();
            }
            if (this.f4102P != null) {
                this.f4102P.m226a();
            }
            if (this.f4103Q != null) {
                this.f4103Q.m129a();
            }
            if (this.f4104R != null) {
                this.f4104R.m998a();
            }
            if (this.f4119g != null) {
                this.f4119g.m219b();
                this.f4119g.m224e();
            }
            if (this.f4120h != null) {
                this.f4120h.m1027a();
            }
            if (this.f4116d != null) {
                this.f4116d.m161f();
            }
            if (this.f4107U != null) {
                this.f4107U.m1068b();
            }
            if (this.bo != null) {
                this.bo.interrupt();
                this.bo = null;
            }
            if (this.f4095I != null) {
                this.f4095I.OnMapDestory(this.f4093G);
                this.f4093G.setMapCallback(null);
                this.f4095I = null;
            }
            hiddenInfoWindowShown();
            dj.m578a(this.ay);
            if (this.f4087A != null) {
                this.f4087A.clear();
            }
            if (this.f4088B != null) {
                this.f4088B.clear();
            }
            if (this.f4093G != null) {
                queueEvent(new Runnable(this) {
                    final /* synthetic */ C1592c f313a;

                    {
                        this.f313a = r1;
                    }

                    public void run() {
                        try {
                            this.f313a.f4093G.destroy();
                            this.f313a.f4093G = null;
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
                Thread.sleep(200);
            }
            if (this.f4101O != null) {
                this.f4101O.removeAllViews();
                this.f4101O = null;
            }
            this.ao = null;
            this.ab = null;
            this.f4137y = null;
            ee.m4244b();
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "destroy");
            th.printStackTrace();
        }
    }

    public void showMyLocationButtonEnabled(boolean z) {
        if (this.f4103Q != null) {
            if (z) {
                this.f4103Q.setVisibility(0);
            } else {
                this.f4103Q.setVisibility(8);
            }
        }
    }

    public void showCompassEnabled(boolean z) {
        if (this.f4104R != null) {
            this.f4104R.m999a(z);
        }
    }

    void m4139a() {
        this.f4124l.obtainMessage(14).sendToTarget();
    }

    public void showScaleEnabled(boolean z) {
        if (this.f4105S != null) {
            this.f4105S.m206a(z);
        }
    }

    void m4148b() {
        this.f4124l.post(new Runnable(this) {
            final /* synthetic */ C1592c f314a;

            {
                this.f314a = r1;
            }

            public void run() {
                this.f314a.f4105S.m207b();
            }
        });
    }

    public boolean removeGLOverlay(String str) throws RemoteException {
        setRunLowFrame(false);
        return this.f4120h.m1034c(str);
    }

    public synchronized void setRunLowFrame(boolean z) {
        if (!z) {
            this.ba = false;
            this.bb.removeCallbacks(this.bc);
            this.aZ = false;
        } else if (!(this.aZ || this.ba)) {
            this.ba = true;
            this.bb.postDelayed(this.bc, ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
        }
    }

    public void onDrawFrame(GL10 gl10) {
        int i = 1;
        try {
            if (this.aI) {
                gl10.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                gl10.glClear(16640);
                this.f4093G.setGL(gl10);
                this.f4093G.drawFrame(gl10);
                m4145a(gl10);
                this.f4119g.m217a(gl10);
                this.f4120h.m1030a(gl10, false, this.ax);
                this.f4116d.m148a(gl10);
                this.aW.m1004a(gl10);
                if (this.al != null) {
                    this.al.m3985a(gl10);
                }
                if (this.aR) {
                    if (!this.f4093G.canStopRenderMap()) {
                        i = 0;
                    }
                    Message obtainMessage = this.f4124l.obtainMessage(16, C1592c.m4068a(0, 0, m4149c(), m4150d(), gl10));
                    obtainMessage.arg1 = i;
                    obtainMessage.sendToTarget();
                    this.aR = false;
                }
                if (!this.at.isFinished()) {
                    this.f4124l.sendEmptyMessage(13);
                }
                if (this.f4106T != null) {
                    i = this.f4106T.getVisibility();
                    C0269o c0269o = this.f4106T;
                    if (i != 8) {
                        if (!this.aJ) {
                            this.f4124l.sendEmptyMessage(11);
                            this.aJ = true;
                        }
                        this.aL = true;
                        this.f4124l.post(new Runnable(this) {
                            final /* synthetic */ C1592c f315a;

                            {
                                this.f315a = r1;
                            }

                            public void run() {
                                if (!this.f315a.aX) {
                                    try {
                                        this.f315a.setMapType(this.f315a.f4092F);
                                        if (this.f315a.aD != null) {
                                            this.f315a.setIndoorBuildingInfo(this.f315a.aD);
                                        }
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                    this.f315a.f4106T.m997a(false);
                                }
                            }
                        });
                        return;
                    }
                    return;
                }
                return;
            }
            gl10.glClearColor(0.9453125f, 0.93359f, 0.9101f, 1.0f);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public Rect getRect() {
        return this.ap;
    }

    public int getMapWidth() {
        return this.ap.width();
    }

    public int getMapHeight() {
        return this.ap.height();
    }

    public int m4149c() {
        return this.f4122j.getWidth();
    }

    public int m4150d() {
        return this.f4122j.getHeight();
    }

    public void setMyTrafficStyle(MyTrafficStyle myTrafficStyle) {
        if (this.aI && myTrafficStyle != null) {
            this.f4137y = myTrafficStyle;
            this.f4093G.setParameter(AMapException.CODE_AMAP_CLIENT_USERID_ILLEGAL, 1, 1, 1, 1);
            this.f4093G.setParameter(AMapException.CODE_AMAP_CLIENT_NEARBY_NULL_RESULT, myTrafficStyle.getSmoothColor(), myTrafficStyle.getSlowColor(), myTrafficStyle.getCongestedColor(), myTrafficStyle.getSeriousCongestedColor());
        }
    }

    private synchronized void m4122p() {
        if (this.aF != null) {
            m4124q();
        }
        if (this.aF == null) {
            this.aF = new Timer();
        }
        this.aF.schedule(new C0225h(this, this), 0, (long) (1000 / this.f4128p));
    }

    private synchronized void m4124q() {
        if (this.aF != null) {
            this.aF.cancel();
            this.aF = null;
        }
    }

    private synchronized void m4125r() {
        try {
            if (!this.bd) {
                this.f4121i.m1014a();
                this.f4121i.m1015a(true);
                this.f4121i.m1017b(true);
                this.f4121i.m1021e(true);
                this.f4121i.m1020d(true);
                this.f4121i.m1019c(true);
                this.bd = true;
            }
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "setInternaltexture");
            th.printStackTrace();
        }
    }

    public int getImaginaryLineTextureID() {
        return this.f4127o;
    }

    public void redrawInfoWindow() {
        try {
            if (this.aS && this.aj != null && this.ak != null) {
                C0198a c0198a = (C0198a) this.aj.getLayoutParams();
                if (c0198a != null) {
                    this.ak.getRect();
                    int realInfoWindowOffsetX = this.ak.getRealInfoWindowOffsetX() + this.ak.getInfoWindowOffsetX();
                    int realInfoWindowOffsetY = (this.ak.getRealInfoWindowOffsetY() + this.ak.getInfoWindowOffsetY()) + 2;
                    c0198a.f152a = this.ak.getMapPosition();
                    c0198a.f153b = realInfoWindowOffsetX;
                    c0198a.f154c = realInfoWindowOffsetY;
                    if (this.al != null) {
                        this.al.m3984a(this.ak.getMapPosition());
                        this.al.setInfoWindowOffset(realInfoWindowOffsetX, realInfoWindowOffsetY);
                    }
                }
                this.f4101O.onLayout(false, 0, 0, 0, 0);
                setRunLowFrame(false);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "redrawInfoWindow");
            th.printStackTrace();
        }
    }

    public void setZOrderOnTop(boolean z) {
        this.f4122j.setZOrderOnTop(z);
    }

    public CameraPosition getCameraPosition() throws RemoteException {
        return getCameraPositionPrj(this.br);
    }

    public float getMaxZoomLevel() {
        return C0273r.f699f;
    }

    public float getMinZoomLevel() {
        return 3.0f;
    }

    public void moveCamera(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) throws RemoteException {
        if (cameraUpdateFactoryDelegate.nowType == Type.newLatLngBounds) {
            boolean z = m4149c() > 0 && m4150d() > 0;
            cu.m450a(z, (Object) "the map must have a size");
        }
        if ((this.aX || this.aY) && this.f4117e.m138d() > 0) {
            CameraUpdateFactoryDelegate newInstance = CameraUpdateFactoryDelegate.newInstance();
            newInstance.nowType = Type.changeGeoCenterZoomTiltBearing;
            newInstance.geoPoint = new IPoint(this.f4131s, this.f4132t);
            newInstance.zoom = this.f4113a;
            newInstance.bearing = this.f4115c;
            newInstance.tilt = this.f4114b;
            this.f4117e.m135a(cameraUpdateFactoryDelegate);
            while (this.f4117e.m138d() > 0) {
                CameraUpdateFactoryDelegate c = this.f4117e.m137c();
                if (c != null) {
                    if (c.cameraPosition != null) {
                        float f;
                        CameraPosition cameraPosition = c.cameraPosition;
                        if (cameraPosition.target != null) {
                            IPoint iPoint = new IPoint();
                            MapProjection.lonlat2Geo(cameraPosition.target.longitude, cameraPosition.target.latitude, iPoint);
                            newInstance.geoPoint = iPoint;
                        }
                        newInstance.zoom = cameraPosition.zoom == 0.0f ? newInstance.zoom : cameraPosition.zoom;
                        newInstance.bearing = cameraPosition.bearing == 0.0f ? newInstance.bearing : cameraPosition.bearing;
                        if (cameraPosition.tilt == 0.0f) {
                            f = newInstance.tilt;
                        } else {
                            f = cameraPosition.tilt;
                        }
                        newInstance.tilt = f;
                    } else if (c.nowType.equals(Type.zoomIn)) {
                        newInstance.zoom += 1.0f;
                    } else if (c.nowType.equals(Type.zoomOut)) {
                        newInstance.zoom -= 1.0f;
                    } else if (c.nowType.equals(Type.zoomBy)) {
                        newInstance.zoom += newInstance.amount;
                    } else {
                        int i;
                        newInstance.geoPoint = c.geoPoint == null ? newInstance.geoPoint : c.geoPoint;
                        newInstance.zoom = c.zoom == 0.0f ? newInstance.zoom : c.zoom;
                        newInstance.bearing = c.bearing == 0.0f ? newInstance.bearing : c.bearing;
                        newInstance.tilt = c.tilt == 0.0f ? newInstance.tilt : c.tilt;
                        newInstance.xPixel = c.xPixel == 0.0f ? newInstance.xPixel : c.xPixel;
                        newInstance.yPixel = c.yPixel == 0.0f ? newInstance.yPixel : c.yPixel;
                        newInstance.width = c.width == 0 ? newInstance.width : c.width;
                        if (c.height == 0) {
                            i = newInstance.height;
                        } else {
                            i = c.height;
                        }
                        newInstance.height = i;
                    }
                    newInstance.zoom = dj.m557a(newInstance.zoom);
                    newInstance.tilt = dj.m558a(newInstance.tilt, newInstance.zoom);
                }
            }
            cameraUpdateFactoryDelegate = newInstance;
        }
        stopAnimation();
        cameraUpdateFactoryDelegate.isChangeFinished = true;
        cameraUpdateFactoryDelegate.isUseAnchor = this.br;
        this.f4117e.m135a(cameraUpdateFactoryDelegate);
    }

    public void animateCamera(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate) throws RemoteException {
        animateCameraWithCallback(cameraUpdateFactoryDelegate, null);
    }

    public void animateCameraWithCallback(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate, CancelableCallback cancelableCallback) throws RemoteException {
        animateCameraWithDurationAndCallback(cameraUpdateFactoryDelegate, 250, cancelableCallback);
    }

    public void animateCameraWithDurationAndCallback(CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate, long j, CancelableCallback cancelableCallback) throws RemoteException {
        if (this.aX || this.aY) {
            moveCamera(cameraUpdateFactoryDelegate);
            return;
        }
        if (cameraUpdateFactoryDelegate.nowType == Type.newLatLngBounds) {
            boolean z = m4149c() > 0 && m4150d() > 0;
            cu.m450a(z, (Object) "the map must have a size");
        }
        if (!this.at.isFinished()) {
            this.at.forceFinished(true);
            if (this.aw != null) {
                this.aw.onCancel();
            }
        }
        this.at.setUseAnchor(this.br);
        this.aw = cancelableCallback;
        if (this.aO) {
            this.aP = true;
        }
        this.aN = false;
        IPoint iPoint;
        if (cameraUpdateFactoryDelegate.nowType == Type.scrollBy) {
            if (cameraUpdateFactoryDelegate.xPixel == 0.0f && cameraUpdateFactoryDelegate.yPixel == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            this.at.setUseAnchor(false);
            iPoint = new IPoint();
            this.f4096J.getGeoCenter(iPoint);
            IPoint iPoint2 = new IPoint();
            getPixel2Geo((m4149c() / 2) + ((int) cameraUpdateFactoryDelegate.xPixel), (m4150d() / 2) + ((int) cameraUpdateFactoryDelegate.yPixel), iPoint2);
            this.at.setInterpolator(new AccelerateDecelerateInterpolator());
            this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, this.f4096J.getMapZoomer(), this.f4096J.getMapAngle(), this.f4096J.getCameraHeaderAngle(), iPoint2.f2030x - iPoint.f2030x, iPoint2.f2031y - iPoint.f2031y, 0.0f, 0.0f, 0.0f, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.zoomIn) {
            r6 = this.f4096J.getMapZoomer();
            r11 = dj.m557a(1.0f + r6) - r6;
            if (r11 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            this.at.setInterpolator(new AccelerateInterpolator());
            this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, r6, this.f4096J.getMapAngle(), this.f4096J.getCameraHeaderAngle(), 0, 0, r11, 0.0f, 0.0f, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.zoomOut) {
            r6 = this.f4096J.getMapZoomer();
            r11 = dj.m557a(r6 - 1.0f) - r6;
            if (r11 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            this.at.setInterpolator(new AccelerateInterpolator());
            this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, r6, this.f4096J.getMapAngle(), this.f4096J.getCameraHeaderAngle(), 0, 0, r11, 0.0f, 0.0f, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.zoomTo) {
            r6 = this.f4096J.getMapZoomer();
            r11 = dj.m557a(cameraUpdateFactoryDelegate.zoom) - r6;
            if (r11 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            this.at.setInterpolator(new AccelerateInterpolator());
            this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, r6, this.f4096J.getMapAngle(), this.f4096J.getCameraHeaderAngle(), 0, 0, r11, 0.0f, 0.0f, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.zoomBy) {
            this.at.setUseAnchor(false);
            float f = cameraUpdateFactoryDelegate.amount;
            r6 = this.f4096J.getMapZoomer();
            r11 = dj.m557a(r6 + f) - r6;
            if (r11 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            Point point = cameraUpdateFactoryDelegate.focus;
            IPoint iPoint3 = new IPoint();
            this.f4096J.getGeoCenter(iPoint3);
            r9 = 0;
            r10 = 0;
            IPoint iPoint4 = new IPoint();
            int i;
            if (point != null) {
                getPixel2Geo(point.x, point.y, iPoint4);
                r3 = iPoint3.f2030x - iPoint4.f2030x;
                i = iPoint3.f2031y - iPoint4.f2031y;
                r9 = (int) ((((double) r3) / Math.pow(2.0d, (double) f)) - ((double) r3));
                r10 = (int) ((((double) i) / Math.pow(2.0d, (double) f)) - ((double) i));
            } else if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint4);
                r3 = iPoint3.f2030x - iPoint4.f2030x;
                i = iPoint3.f2031y - iPoint4.f2031y;
                r9 = (int) ((((double) r3) / Math.pow(2.0d, (double) f)) - ((double) r3));
                r10 = (int) ((((double) i) / Math.pow(2.0d, (double) f)) - ((double) i));
            }
            this.at.setInterpolator(new AccelerateInterpolator());
            this.at.startChangeCamera(iPoint3.f2030x, iPoint3.f2031y, r6, this.f4096J.getMapAngle(), this.f4096J.getCameraHeaderAngle(), r9, r10, r11, 0.0f, 0.0f, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.newCameraPosition) {
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            r3 = new IPoint();
            CameraPosition cameraPosition = cameraUpdateFactoryDelegate.cameraPosition;
            MapProjection.lonlat2Geo(cameraPosition.target.longitude, cameraPosition.target.latitude, r3);
            r6 = this.f4096J.getMapZoomer();
            r9 = r3.f2030x - iPoint.f2030x;
            r10 = r3.f2031y - iPoint.f2031y;
            r11 = dj.m557a(cameraPosition.zoom) - r6;
            r7 = this.f4096J.getMapAngle();
            r12 = (cameraPosition.bearing % 360.0f) - (r7 % 360.0f);
            if (Math.abs(r12) >= BitmapDescriptorFactory.HUE_CYAN) {
                r12 -= Math.signum(r12) * 360.0f;
            }
            r8 = this.f4096J.getCameraHeaderAngle();
            r13 = dj.m558a(cameraPosition.tilt, cameraPosition.zoom) - r8;
            if (r9 == 0 && r10 == 0 && r11 == 0.0f && r12 == 0.0f && r13 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            } else {
                this.at.setInterpolator(new AccelerateInterpolator());
                this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, r6, r7, r8, r9, r10, r11, r12, r13, j);
            }
        } else if (cameraUpdateFactoryDelegate.nowType == Type.changeBearing) {
            r7 = this.f4096J.getMapAngle();
            r12 = (cameraUpdateFactoryDelegate.bearing % 360.0f) - (r7 % 360.0f);
            if (Math.abs(r12) >= BitmapDescriptorFactory.HUE_CYAN) {
                r12 -= Math.signum(r12) * 360.0f;
            }
            if (r12 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            this.at.setInterpolator(new AccelerateInterpolator());
            this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, this.f4096J.getMapZoomer(), r7, this.f4096J.getCameraHeaderAngle(), 0, 0, 0.0f, r12, 0.0f, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.changeTilt) {
            r8 = this.f4096J.getCameraHeaderAngle();
            r13 = cameraUpdateFactoryDelegate.tilt - r8;
            if (r13 == 0.0f) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            this.at.setInterpolator(new AccelerateInterpolator());
            this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, this.f4096J.getMapZoomer(), this.f4096J.getMapAngle(), r8, 0, 0, 0.0f, 0.0f, r13, j);
        } else if (cameraUpdateFactoryDelegate.nowType == Type.changeCenter) {
            iPoint = new IPoint();
            if (this.br) {
                getPixel2Geo(this.bs, this.bt, iPoint);
            } else {
                this.f4096J.getGeoCenter(iPoint);
            }
            r9 = cameraUpdateFactoryDelegate.geoPoint.f2030x - iPoint.f2030x;
            r10 = cameraUpdateFactoryDelegate.geoPoint.f2031y - iPoint.f2031y;
            if (r9 == 0 && r10 == 0) {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            } else {
                this.at.setInterpolator(new AccelerateDecelerateInterpolator());
                this.at.startChangeCamera(iPoint.f2030x, iPoint.f2031y, this.f4096J.getMapZoomer(), this.f4096J.getMapAngle(), this.f4096J.getCameraHeaderAngle(), r9, r10, 0.0f, 0.0f, 0.0f, j);
            }
        } else if (cameraUpdateFactoryDelegate.nowType == Type.newLatLngBounds || cameraUpdateFactoryDelegate.nowType == Type.newLatLngBoundsWithSize) {
            int i2;
            this.at.setUseAnchor(false);
            if (cameraUpdateFactoryDelegate.nowType == Type.newLatLngBounds) {
                r3 = m4149c();
                r9 = m4150d();
                i2 = r3;
            } else {
                r3 = cameraUpdateFactoryDelegate.width;
                r9 = cameraUpdateFactoryDelegate.height;
                i2 = r3;
            }
            float mapAngle = this.f4096J.getMapAngle() % 360.0f;
            float cameraHeaderAngle = this.f4096J.getCameraHeaderAngle();
            r12 = -mapAngle;
            if (Math.abs(r12) >= BitmapDescriptorFactory.HUE_CYAN) {
                r12 -= Math.signum(r12) * 360.0f;
            }
            r13 = -cameraHeaderAngle;
            LatLngBounds latLngBounds = cameraUpdateFactoryDelegate.bounds;
            int i3 = cameraUpdateFactoryDelegate.padding;
            IPoint iPoint5 = new IPoint();
            this.f4096J.getGeoCenter(iPoint5);
            float mapZoomer = this.f4096J.getMapZoomer();
            this.at.setInterpolator(new AccelerateInterpolator());
            iPoint = new IPoint();
            r3 = new IPoint();
            MapProjection.lonlat2Geo(latLngBounds.northeast.longitude, latLngBounds.northeast.latitude, iPoint);
            MapProjection.lonlat2Geo(latLngBounds.southwest.longitude, latLngBounds.southwest.latitude, r3);
            r10 = iPoint.f2030x - r3.f2030x;
            int i4 = r3.f2031y - iPoint.f2031y;
            if (r10 > 0 || i4 > 0) {
                int i5 = (iPoint.f2030x + r3.f2030x) / 2;
                int i6 = (iPoint.f2031y + r3.f2031y) / 2;
                IPoint iPoint6 = new IPoint();
                getLatLng2Pixel((latLngBounds.northeast.latitude + latLngBounds.southwest.latitude) / 2.0d, (latLngBounds.northeast.longitude + latLngBounds.southwest.longitude) / 2.0d, iPoint6);
                int i7;
                if ((!this.ap.contains(iPoint6.f2030x, iPoint6.f2031y) ? 1 : null) == null) {
                    r3 = i2 - (i3 * 2);
                    i7 = r9 - (i3 * 2);
                    if (r3 <= 0) {
                        r3 = 1;
                    }
                    if (i7 <= 0) {
                        i7 = 1;
                    }
                    r11 = dj.m557a((float) ((int) (Math.min(Math.log(((double) this.f4096J.getMapLenWithWin(r3)) / ((double) this.f4096J.getMapLenWithGeo(r10))) / Math.log(2.0d), Math.log(((double) this.f4096J.getMapLenWithWin(i7)) / ((double) this.f4096J.getMapLenWithGeo(i4))) / Math.log(2.0d)) + ((double) mapZoomer)))) - mapZoomer;
                    r9 = i5 - iPoint5.f2030x;
                    r10 = i6 - iPoint5.f2031y;
                    if (r9 == 0 && r10 == 0 && r11 == 0.0f) {
                        this.f4124l.obtainMessage(17).sendToTarget();
                        return;
                    } else {
                        this.at.setInterpolator(new DecelerateInterpolator());
                        this.at.startChangeCamera(iPoint5.f2030x, iPoint5.f2031y, mapZoomer, mapAngle, cameraHeaderAngle, r9, r10, r11, r12, r13, j);
                    }
                } else {
                    final CancelableCallback cancelableCallback2 = this.aw;
                    final LatLngBounds latLngBounds2 = latLngBounds;
                    final int i8 = i2;
                    final int i9 = r9;
                    final int i10 = i3;
                    final long j2 = j;
                    this.aw = new CancelableCallback(this) {
                        final /* synthetic */ C1592c f4067g;

                        public void onFinish() {
                            try {
                                this.f4067g.animateCameraWithDurationAndCallback(CameraUpdateFactoryDelegate.newLatLngBoundsWithSize(latLngBounds2, i8, i9, i10), j2, cancelableCallback2);
                            } catch (Throwable th) {
                                th.printStackTrace();
                            }
                        }

                        public void onCancel() {
                            if (cancelableCallback2 != null) {
                                cancelableCallback2.onCancel();
                            }
                        }
                    };
                    i4 = ((iPoint5.f2030x + i5) / 2) - iPoint5.f2030x;
                    r10 = ((iPoint5.f2031y + i6) / 2) - iPoint5.f2031y;
                    i7 = (int) dj.m556a((double) (((float) getMapWidth()) / 2.0f), (double) (((float) getMapHeight()) / 2.0f), (double) Math.abs(i5 - iPoint5.f2030x), (double) Math.abs(i6 - iPoint5.f2031y));
                    r11 = i7 == 0 ? 0.0f : ((float) i7) - mapZoomer;
                    if (r11 >= 0.0f) {
                        r11 = 0.0f;
                    }
                    this.aN = true;
                    this.at.startChangeCamera(iPoint5.f2030x, iPoint5.f2031y, mapZoomer, mapAngle, cameraHeaderAngle, i4, r10, r11, r12 / 2.0f, r13 / 2.0f, j / 2);
                }
            } else {
                this.f4124l.obtainMessage(17).sendToTarget();
                return;
            }
        } else {
            cameraUpdateFactoryDelegate.isChangeFinished = true;
            this.f4117e.m135a(cameraUpdateFactoryDelegate);
        }
        setRunLowFrame(false);
    }

    public void stopAnimation() throws RemoteException {
        if (!this.at.isFinished()) {
            this.at.forceFinished(true);
            m4147a(true, null);
            if (this.aw != null) {
                this.aw.onCancel();
            }
            if (!(this.aj == null || this.al == null)) {
                this.aj.setVisibility(0);
            }
            this.aw = null;
        }
        setRunLowFrame(false);
    }

    public IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException {
        if (polylineOptions == null) {
            return null;
        }
        IOverlayDelegate aoVar = new ao(this.f4120h);
        aoVar.setColor(polylineOptions.getColor());
        aoVar.setGeodesic(polylineOptions.isGeodesic());
        aoVar.setDottedLine(polylineOptions.isDottedLine());
        aoVar.setPoints(polylineOptions.getPoints());
        aoVar.setVisible(polylineOptions.isVisible());
        aoVar.setWidth(polylineOptions.getWidth());
        aoVar.setZIndex(polylineOptions.getZIndex());
        aoVar.m5674a(polylineOptions.isUseTexture());
        if (polylineOptions.getColorValues() != null) {
            aoVar.setColorValues(polylineOptions.getColorValues());
            aoVar.useGradient(polylineOptions.isUseGradient());
        }
        if (polylineOptions.getCustomTexture() != null) {
            aoVar.m5670a(polylineOptions.getCustomTexture());
        }
        if (polylineOptions.getCustomTextureList() != null) {
            aoVar.setCustomTextureList(polylineOptions.getCustomTextureList());
            aoVar.setCustemTextureIndex(polylineOptions.getCustomTextureIndex());
        }
        this.f4120h.m1028a(aoVar);
        setRunLowFrame(false);
        return aoVar;
    }

    public INavigateArrowDelegate addNavigateArrow(NavigateArrowOptions navigateArrowOptions) throws RemoteException {
        if (navigateArrowOptions == null) {
            return null;
        }
        IOverlayDelegate akVar = new ak(this);
        akVar.setTopColor(navigateArrowOptions.getTopColor());
        akVar.setPoints(navigateArrowOptions.getPoints());
        akVar.setVisible(navigateArrowOptions.isVisible());
        akVar.setWidth(navigateArrowOptions.getWidth());
        akVar.setZIndex(navigateArrowOptions.getZIndex());
        this.f4120h.m1028a(akVar);
        setRunLowFrame(false);
        return akVar;
    }

    public IPolygonDelegate addPolygon(PolygonOptions polygonOptions) throws RemoteException {
        if (polygonOptions == null) {
            return null;
        }
        IOverlayDelegate anVar = new an(this);
        anVar.setFillColor(polygonOptions.getFillColor());
        anVar.setPoints(polygonOptions.getPoints());
        anVar.setVisible(polygonOptions.isVisible());
        anVar.setStrokeWidth(polygonOptions.getStrokeWidth());
        anVar.setZIndex(polygonOptions.getZIndex());
        anVar.setStrokeColor(polygonOptions.getStrokeColor());
        this.f4120h.m1028a(anVar);
        setRunLowFrame(false);
        return anVar;
    }

    public ICircleDelegate addCircle(CircleOptions circleOptions) throws RemoteException {
        if (circleOptions == null) {
            return null;
        }
        IOverlayDelegate c1969p = new C1969p(this);
        c1969p.setFillColor(circleOptions.getFillColor());
        c1969p.setCenter(circleOptions.getCenter());
        c1969p.setVisible(circleOptions.isVisible());
        c1969p.setStrokeWidth(circleOptions.getStrokeWidth());
        c1969p.setZIndex(circleOptions.getZIndex());
        c1969p.setStrokeColor(circleOptions.getStrokeColor());
        c1969p.setRadius(circleOptions.getRadius());
        this.f4120h.m1028a(c1969p);
        setRunLowFrame(false);
        return c1969p;
    }

    public IArcDelegate addArc(ArcOptions arcOptions) throws RemoteException {
        if (arcOptions == null) {
            return null;
        }
        IOverlayDelegate c1968n = new C1968n(this);
        c1968n.setStrokeColor(arcOptions.getStrokeColor());
        c1968n.setStart(arcOptions.getStart());
        c1968n.setPassed(arcOptions.getPassed());
        c1968n.setEnd(arcOptions.getEnd());
        c1968n.setVisible(arcOptions.isVisible());
        c1968n.setStrokeWidth(arcOptions.getStrokeWidth());
        c1968n.setZIndex(arcOptions.getZIndex());
        this.f4120h.m1028a(c1968n);
        setRunLowFrame(false);
        return c1968n;
    }

    public IGroundOverlayDelegate addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException {
        if (groundOverlayOptions == null) {
            return null;
        }
        IOverlayDelegate c1970x = new C1970x(this);
        c1970x.setAnchor(groundOverlayOptions.getAnchorU(), groundOverlayOptions.getAnchorV());
        c1970x.setDimensions(groundOverlayOptions.getWidth(), groundOverlayOptions.getHeight());
        c1970x.setImage(groundOverlayOptions.getImage());
        c1970x.setPosition(groundOverlayOptions.getLocation());
        c1970x.setPositionFromBounds(groundOverlayOptions.getBounds());
        c1970x.setBearing(groundOverlayOptions.getBearing());
        c1970x.setTransparency(groundOverlayOptions.getTransparency());
        c1970x.setVisible(groundOverlayOptions.isVisible());
        c1970x.setZIndex(groundOverlayOptions.getZIndex());
        this.f4120h.m1028a(c1970x);
        setRunLowFrame(false);
        return c1970x;
    }

    public Marker addMarker(MarkerOptions markerOptions) throws RemoteException {
        if (markerOptions == null) {
            return null;
        }
        IMarkerDelegate aiVar = new ai(markerOptions, this.f4116d);
        this.f4116d.m152b(aiVar);
        setRunLowFrame(false);
        return new Marker(aiVar);
    }

    public Text addText(TextOptions textOptions) throws RemoteException {
        if (textOptions == null) {
            return null;
        }
        IMarkerDelegate atVar = new at(textOptions, this.f4116d);
        this.f4116d.m152b(atVar);
        setRunLowFrame(false);
        return new Text(atVar);
    }

    public ArrayList<Marker> addMarkers(ArrayList<MarkerOptions> arrayList, boolean z) throws RemoteException {
        int i = 0;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        ArrayList<Marker> arrayList2 = new ArrayList();
        try {
            MarkerOptions markerOptions;
            if (arrayList.size() == 1) {
                markerOptions = (MarkerOptions) arrayList.get(0);
                if (markerOptions != null) {
                    arrayList2.add(addMarker(markerOptions));
                    if (z && markerOptions.getPosition() != null) {
                        moveCamera(CameraUpdateFactoryDelegate.newLatLngZoom(markerOptions.getPosition(), 18.0f));
                    }
                    return arrayList2;
                }
            }
            final Builder builder = LatLngBounds.builder();
            int i2 = 0;
            while (i2 < arrayList.size()) {
                int i3;
                markerOptions = (MarkerOptions) arrayList.get(i2);
                if (arrayList.get(i2) != null) {
                    arrayList2.add(addMarker(markerOptions));
                    if (markerOptions.getPosition() != null) {
                        builder.include(markerOptions.getPosition());
                        i3 = i + 1;
                        i2++;
                        i = i3;
                    }
                }
                i3 = i;
                i2++;
                i = i3;
            }
            if (z && i > 0) {
                if (this.aJ) {
                    this.f4124l.postDelayed(new Runnable(this) {
                        final /* synthetic */ C1592c f317b;

                        public void run() {
                            try {
                                this.f317b.moveCamera(CameraUpdateFactoryDelegate.newLatLngBounds(builder.build(), 50));
                            } catch (Throwable th) {
                            }
                        }
                    }, 50);
                } else {
                    this.aE = CameraUpdateFactoryDelegate.newLatLngBounds(builder.build(), 50);
                }
            }
            return arrayList2;
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "addMarkers");
            th.printStackTrace();
            return arrayList2;
        }
    }

    public ai m4138a(MarkerOptions markerOptions) throws RemoteException {
        if (markerOptions == null) {
            return null;
        }
        IMarkerDelegate aiVar = new ai(markerOptions, this.f4116d);
        this.f4116d.m152b(aiVar);
        setRunLowFrame(false);
        return aiVar;
    }

    public TileOverlay addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException {
        if (tileOverlayOptions == null || tileOverlayOptions.getTileProvider() == null) {
            return null;
        }
        ITileOverlayDelegate avVar = new av(tileOverlayOptions, this.f4119g);
        this.f4119g.m216a(avVar);
        setRunLowFrame(false);
        return new TileOverlay(avVar);
    }

    public void clear() throws RemoteException {
        try {
            clear(false);
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "clear");
            Log.d("amapApi", "AMapDelegateImpGLSurfaceView clear erro" + th.getMessage());
            th.printStackTrace();
        }
    }

    public void clear(boolean z) throws RemoteException {
        String str = null;
        try {
            String c;
            hiddenInfoWindowShown();
            if (this.as != null) {
                if (z) {
                    c = this.as.m189c();
                    str = this.as.m190d();
                    this.f4120h.m1032b(str);
                    this.f4119g.m219b();
                    this.f4116d.m153b(c);
                    setRunLowFrame(false);
                }
                this.as.m191e();
            }
            c = null;
            this.f4120h.m1032b(str);
            this.f4119g.m219b();
            this.f4116d.m153b(c);
            setRunLowFrame(false);
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "clear");
            Log.d("amapApi", "AMapDelegateImpGLSurfaceView clear erro" + th.getMessage());
            th.printStackTrace();
        }
    }

    public boolean removeMarker(String str) {
        IMarkerDelegate a;
        try {
            a = this.f4116d.m144a(str);
        } catch (Throwable e) {
            ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "removeMarker");
            e.printStackTrace();
            a = null;
        }
        if (a == null) {
            return false;
        }
        setRunLowFrame(false);
        return this.f4116d.m156c(a);
    }

    public int getMapType() throws RemoteException {
        return this.f4092F;
    }

    public void setMapType(int i) throws RemoteException {
        this.f4092F = i;
        if (this.aJ) {
            if (i == 1) {
                try {
                    m4142a(C0281a.NORAML, C0283c.DAY);
                } catch (Throwable th) {
                    ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "setMaptype");
                    th.printStackTrace();
                    return;
                }
            } else if (i == 2) {
                m4142a(C0281a.SATELLITE, C0283c.DAY);
            } else if (i == 3) {
                m4143a(C0281a.NORAML, C0283c.NIGHT, C0282b.NAVI_CAR);
            } else if (i == 4) {
                m4143a(C0281a.NORAML, C0283c.DAY, C0282b.NAVI_CAR);
            } else {
                this.f4092F = 1;
            }
            setRunLowFrame(false);
        }
    }

    public void m4142a(C0281a c0281a, C0283c c0283c) {
        m4143a(c0281a, c0283c, C0282b.NORMAL);
    }

    public void m4143a(C0281a c0281a, C0283c c0283c, C0282b c0282b) {
        if (this.f4089C != c0283c || this.f4090D != c0281a || this.f4091E != c0282b) {
            if (this.aK) {
                final C0283c c0283c2 = this.f4089C;
                final C0281a c0281a2 = this.f4090D;
                C0282b c0282b2 = this.f4091E;
                if (this.bd && this.aI) {
                    final C0283c c0283c3 = c0283c;
                    final C0281a c0281a3 = c0281a;
                    final C0282b c0282b3 = c0282b;
                    queueEvent(new Runnable(this) {
                        final /* synthetic */ C1592c f324f;

                        /* compiled from: AMapDelegateImp */
                        class C02121 implements Runnable {
                            final /* synthetic */ C02133 f318a;

                            C02121(C02133 c02133) {
                                this.f318a = c02133;
                            }

                            public void run() {
                                this.f318a.f324f.m4131u();
                            }
                        }

                        public void run() {
                            String b = this.f324f.f4121i.m1016b();
                            String c = this.f324f.f4121i.m1018c();
                            this.f324f.f4089C = c0283c3;
                            this.f324f.f4090D = c0281a3;
                            this.f324f.f4091E = c0282b3;
                            String b2 = this.f324f.f4121i.m1016b();
                            String c2 = this.f324f.f4121i.m1018c();
                            if (this.f324f.f4090D == C0281a.SATELLITE || this.f324f.f4089C == C0283c.NIGHT || c0283c2 == C0283c.NIGHT || c0281a2 == C0281a.SATELLITE) {
                                this.f324f.f4124l.post(new C02121(this));
                            }
                            this.f324f.f4093G.setParameter(2501, 0, 0, 0, 0);
                            if (!b.equals(b2)) {
                                this.f324f.f4121i.m1014a();
                            }
                            if (this.f324f.f4090D == C0281a.SATELLITE || c0281a2 == C0281a.SATELLITE) {
                                this.f324f.f4093G.setParameter(2011, this.f324f.f4090D == C0281a.SATELLITE ? 1 : 0, 0, 0, 0);
                            }
                            if (this.f324f.f4089C == C0283c.NIGHT || c0283c2 == C0283c.NIGHT) {
                                int i;
                                MapCore g = this.f324f.f4093G;
                                if (this.f324f.f4089C == C0283c.NIGHT) {
                                    i = 1;
                                } else {
                                    i = 0;
                                }
                                g.setParameter(2401, i, 0, 0, 0);
                                this.f324f.f4121i.m1020d(true);
                                this.f324f.f4121i.m1019c(true);
                            }
                            if (!c.equals(c2)) {
                                this.f324f.f4121i.m1015a(true);
                            }
                            this.f324f.f4121i.m1017b(true);
                            if (this.f324f.f4091E != null) {
                                this.f324f.f4093G.setParameter(2013, this.f324f.f4090D.ordinal(), this.f324f.f4089C.ordinal(), this.f324f.f4091E.ordinal(), 0);
                            }
                            this.f324f.f4093G.setParameter(2501, 1, 1, 0, 0);
                        }
                    });
                    return;
                }
                this.bx.f334d = c0281a;
                this.bx.f335e = c0283c;
                this.bx.f332b = true;
                return;
            }
            this.f4089C = c0283c;
            this.f4090D = c0281a;
            this.f4091E = c0282b;
        }
    }

    public boolean isTrafficEnabled() throws RemoteException {
        return this.f4136x;
    }

    public void setTrafficEnabled(boolean z) throws RemoteException {
        this.f4136x = z;
        setRunLowFrame(false);
        this.f4117e.m134a(new ac(2).m131a(z));
    }

    public void setMapTextEnable(final boolean z) throws RemoteException {
        this.f4135w = z;
        setRunLowFrame(false);
        queueEvent(new Runnable(this) {
            final /* synthetic */ C1592c f326b;

            public void run() {
                if (this.f326b.f4093G != null) {
                    int i;
                    MapCore g = this.f326b.f4093G;
                    if (z) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    g.setParameter(1024, i, 0, 0, 0);
                }
            }
        });
    }

    public boolean isIndoorEnabled() throws RemoteException {
        return this.f4133u;
    }

    public void setIndoorEnabled(final boolean z) throws RemoteException {
        this.f4133u = z;
        setRunLowFrame(false);
        if (z) {
            this.f4093G.setParameter(1026, 1, 0, 0, 0);
        } else {
            this.f4093G.setParameter(1026, 0, 0, 0, 0);
            C0273r.f699f = 19.0f;
            if (this.an.isZoomControlsEnabled()) {
                this.f4124l.sendEmptyMessage(21);
            }
        }
        if (this.an.isIndoorSwitchEnabled()) {
            this.f4124l.post(new Runnable(this) {
                final /* synthetic */ C1592c f328b;

                public void run() {
                    if (z) {
                        this.f328b.showIndoorSwitchControlsEnabled(true);
                    } else {
                        this.f328b.f4107U.m1066a(false);
                    }
                }
            });
        }
    }

    public void set3DBuildingEnabled(final boolean z) throws RemoteException {
        this.f4134v = z;
        setRunLowFrame(false);
        queueEvent(new Runnable(this) {
            final /* synthetic */ C1592c f330b;

            public void run() {
                if (this.f330b.f4093G != null) {
                    int i;
                    MapCore g = this.f330b.f4093G;
                    if (z) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    g.setParameter(1021, i, 0, 0, 0);
                }
            }
        });
    }

    public boolean isMyLocationEnabled() throws RemoteException {
        return this.aH;
    }

    public void setMyLocationEnabled(boolean z) throws RemoteException {
        try {
            if (this.ao == null) {
                this.f4103Q.m130a(false);
            } else if (z) {
                this.ao.activate(this.aq);
                this.f4103Q.m130a(true);
                if (this.as == null) {
                    this.as = new aj(this, this.f4094H);
                }
            } else {
                if (this.as != null) {
                    this.as.m188b();
                    this.as = null;
                }
                this.az = null;
                this.ao.deactivate();
            }
            if (!z) {
                this.an.setMyLocationButtonEnabled(z);
            }
            this.aH = z;
            setRunLowFrame(false);
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "setMyLocationEnabled");
            th.printStackTrace();
        }
    }

    public Location getMyLocation() throws RemoteException {
        if (this.ao != null) {
            return this.aq.f4223a;
        }
        return null;
    }

    public void setLocationSource(LocationSource locationSource) throws RemoteException {
        try {
            this.ao = locationSource;
            if (locationSource != null) {
                this.f4103Q.m130a(true);
            } else {
                this.f4103Q.m130a(false);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "setLocationSource");
            th.printStackTrace();
        }
    }

    public IUiSettingsDelegate getUiSettings() throws RemoteException {
        return this.an;
    }

    public IProjectionDelegate getProjection() throws RemoteException {
        return this.am;
    }

    public void setOnCameraChangeListener(OnCameraChangeListener onCameraChangeListener) throws RemoteException {
        this.aa = onCameraChangeListener;
    }

    void m4144a(CameraPosition cameraPosition) {
        Message message = new Message();
        message.what = 10;
        message.obj = cameraPosition;
        this.f4124l.sendMessage(message);
    }

    public OnCameraChangeListener getOnCameraChangeListener() throws RemoteException {
        return this.aa;
    }

    public void setOnMapClickListener(OnMapClickListener onMapClickListener) throws RemoteException {
        this.ab = onMapClickListener;
    }

    public void setOnMapTouchListener(OnMapTouchListener onMapTouchListener) throws RemoteException {
        this.ac = onMapTouchListener;
    }

    public void setOnPOIClickListener(OnPOIClickListener onPOIClickListener) throws RemoteException {
        this.ad = onPOIClickListener;
    }

    public void setOnMapLongClickListener(OnMapLongClickListener onMapLongClickListener) throws RemoteException {
        this.ae = onMapLongClickListener;
    }

    public void setOnMarkerClickListener(OnMarkerClickListener onMarkerClickListener) throws RemoteException {
        this.f4109W = onMarkerClickListener;
    }

    public void setOnPolylineClickListener(OnPolylineClickListener onPolylineClickListener) throws RemoteException {
        this.f4110X = onPolylineClickListener;
    }

    public void setOnMarkerDragListener(OnMarkerDragListener onMarkerDragListener) throws RemoteException {
        this.f4111Y = onMarkerDragListener;
    }

    public void setOnMaploadedListener(OnMapLoadedListener onMapLoadedListener) throws RemoteException {
        this.f4112Z = onMapLoadedListener;
    }

    public void setOnInfoWindowClickListener(OnInfoWindowClickListener onInfoWindowClickListener) throws RemoteException {
        this.af = onInfoWindowClickListener;
    }

    public void setOnIndoorBuildingActiveListener(OnIndoorBuildingActiveListener onIndoorBuildingActiveListener) throws RemoteException {
        this.ag = onIndoorBuildingActiveListener;
    }

    public void setInfoWindowAdapter(InfoWindowAdapter infoWindowAdapter) throws RemoteException {
        if (infoWindowAdapter == null) {
            this.ah = this.ai;
        } else {
            this.ah = infoWindowAdapter;
        }
    }

    public View getView() throws RemoteException {
        return this.f4101O;
    }

    public float checkZoomLevel(float f) throws RemoteException {
        return dj.m557a(f);
    }

    public float toMapLenWithWin(int i) {
        if (this.aI) {
            return this.f4096J.getMapLenWithWin(i);
        }
        return 0.0f;
    }

    public void getPixel2LatLng(int i, int i2, DPoint dPoint) {
        m4084a(this.f4096J, i, i2, dPoint);
    }

    private void m4084a(MapProjection mapProjection, int i, int i2, DPoint dPoint) {
        if (this.aI) {
            FPoint fPoint = new FPoint();
            mapProjection.win2Map(i, i2, fPoint);
            IPoint iPoint = new IPoint();
            mapProjection.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
            MapProjection.geo2LonLat(iPoint.f2030x, iPoint.f2031y, dPoint);
        }
    }

    public void getPixel2Geo(int i, int i2, IPoint iPoint) {
        if (this.aI) {
            FPoint fPoint = new FPoint();
            this.f4096J.win2Map(i, i2, fPoint);
            this.f4096J.map2Geo(fPoint.f2028x, fPoint.f2029y, iPoint);
        }
    }

    public void m4140a(int i, int i2, IPoint iPoint) {
        if (this.aI) {
            FPoint fPoint = new FPoint();
            this.f4096J.geo2Map(i, i2, fPoint);
            this.f4096J.map2Win(fPoint.f2028x, fPoint.f2029y, iPoint);
        }
    }

    public void getLatLng2Map(double d, double d2, FPoint fPoint) {
        if (this.aI) {
            IPoint iPoint = new IPoint();
            MapProjection.lonlat2Geo(d2, d, iPoint);
            this.f4096J.geo2Map(iPoint.f2030x, iPoint.f2031y, fPoint);
        }
    }

    public void latlon2Geo(double d, double d2, IPoint iPoint) {
        MapProjection.lonlat2Geo(d2, d, iPoint);
    }

    public void pixel2Map(int i, int i2, FPoint fPoint) {
        if (this.aI) {
            this.f4096J.win2Map(i, i2, fPoint);
        }
    }

    public void geo2Map(int i, int i2, FPoint fPoint) {
        if (this.aI) {
            this.f4096J.geo2Map(i2, i, fPoint);
        }
    }

    public void map2Geo(float f, float f2, IPoint iPoint) {
        if (this.aI) {
            this.f4096J.map2Geo(f, f2, iPoint);
        }
    }

    public void geo2Latlng(int i, int i2, DPoint dPoint) {
        MapProjection.geo2LonLat(i, i2, dPoint);
    }

    public void getLatLng2Pixel(double d, double d2, IPoint iPoint) {
        if (this.aI) {
            MapProjection mapProjection = new MapProjection(this.f4093G);
            mapProjection.recalculate();
            IPoint iPoint2 = new IPoint();
            FPoint fPoint = new FPoint();
            MapProjection.lonlat2Geo(d2, d, iPoint2);
            mapProjection.geo2Map(iPoint2.f2030x, iPoint2.f2031y, fPoint);
            mapProjection.map2Win(fPoint.f2028x, fPoint.f2029y, iPoint);
            mapProjection.recycle();
        }
    }

    private LatLng m4128s() {
        if (!this.aI) {
            return null;
        }
        DPoint dPoint = new DPoint();
        IPoint iPoint = new IPoint();
        this.f4096J.getGeoCenter(iPoint);
        MapProjection.geo2LonLat(iPoint.f2030x, iPoint.f2031y, dPoint);
        return new LatLng(dPoint.f2027y, dPoint.f2026x, false);
    }

    public CameraPosition getCameraPositionPrj(boolean z) {
        if (!this.aI) {
            return null;
        }
        LatLng latLng;
        if (z) {
            DPoint dPoint = new DPoint();
            getPixel2LatLng(this.bs, this.bt, dPoint);
            latLng = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
        } else {
            latLng = m4128s();
        }
        return CameraPosition.builder().target(latLng).bearing(this.f4096J.getMapAngle()).tilt(this.f4096J.getCameraHeaderAngle()).zoom(this.f4096J.getMapZoomer()).build();
    }

    private void m4130t() {
        if (this.bn) {
            this.bn = false;
        }
        if (this.bj) {
            this.bj = false;
            CameraUpdateFactoryDelegate newInstance = CameraUpdateFactoryDelegate.newInstance();
            newInstance.isChangeFinished = true;
            this.f4117e.m135a(newInstance);
        }
        if (this.be) {
            this.be = false;
            newInstance = CameraUpdateFactoryDelegate.newInstance();
            newInstance.isChangeFinished = true;
            this.f4117e.m135a(newInstance);
        }
        this.bf = false;
        this.bg = false;
        if (this.f4111Y != null && this.bh != null) {
            this.f4111Y.onMarkerDragEnd(this.bh);
            this.bh = null;
        }
    }

    private void m4082a(MotionEvent motionEvent) throws RemoteException {
        if (this.bg && this.bh != null) {
            int x = (int) motionEvent.getX();
            int y = (int) (motionEvent.getY() - BitmapDescriptorFactory.HUE_YELLOW);
            LatLng realPosition = this.bi.getRealPosition();
            LatLng position = this.bi.getPosition();
            DPoint dPoint = new DPoint();
            getPixel2LatLng(x, y, dPoint);
            this.bh.setPosition(new LatLng((position.latitude + dPoint.f2027y) - realPosition.latitude, (dPoint.f2026x + position.longitude) - realPosition.longitude));
            this.f4111Y.onMarkerDrag(this.bh);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.aJ) {
            return false;
        }
        setRunLowFrame(false);
        if (motionEvent.getAction() == 261) {
            this.bm = motionEvent.getPointerCount();
        }
        this.f4097K.onTouchEvent(motionEvent);
        this.ar.m266a(motionEvent);
        this.f4098L.onTouchEvent(motionEvent);
        this.f4099M.m257a(motionEvent);
        if (motionEvent.getAction() == 2) {
            try {
                m4082a(motionEvent);
            } catch (Throwable e) {
                ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "onDragMarker");
                e.printStackTrace();
            }
        }
        if (motionEvent.getAction() == 1) {
            m4130t();
        }
        setRunLowFrame(false);
        if (this.ac != null) {
            this.bu.removeMessages(1);
            Message obtainMessage = this.bu.obtainMessage();
            obtainMessage.what = 1;
            obtainMessage.obj = MotionEvent.obtain(motionEvent);
            obtainMessage.sendToTarget();
        }
        return true;
    }

    public void showInfoWindow(IMarkerDelegate iMarkerDelegate) throws RemoteException {
        int i = -2;
        if (iMarkerDelegate != null) {
            hiddenInfoWindowShown();
            if ((iMarkerDelegate.getTitle() != null || iMarkerDelegate.getSnippet() != null) && this.ah != null) {
                this.ak = iMarkerDelegate;
                if (this.aJ) {
                    int i2;
                    Marker marker = new Marker(iMarkerDelegate);
                    this.aj = this.ah.getInfoWindow(marker);
                    try {
                        if (this.ay == null) {
                            this.ay = al.m194a(this.f4094H, "infowindow_bg.9.png");
                        }
                    } catch (Throwable th) {
                        ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "showInfoWindow decodeDrawableFromAsset");
                        th.printStackTrace();
                    }
                    if (this.aj == null) {
                        this.aj = this.ah.getInfoContents(marker);
                    }
                    View linearLayout = new LinearLayout(this.f4094H);
                    if (this.aj != null) {
                        if (this.aj.getBackground() == null) {
                            this.aj.setBackgroundDrawable(this.ay);
                        }
                        linearLayout.addView(this.aj);
                    } else {
                        linearLayout.setBackgroundDrawable(this.ay);
                        View textView = new TextView(this.f4094H);
                        textView.setText(iMarkerDelegate.getTitle());
                        textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                        View textView2 = new TextView(this.f4094H);
                        textView2.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                        textView2.setText(iMarkerDelegate.getSnippet());
                        linearLayout.setOrientation(1);
                        linearLayout.addView(textView);
                        linearLayout.addView(textView2);
                    }
                    this.aj = linearLayout;
                    LayoutParams layoutParams = this.aj.getLayoutParams();
                    this.aj.setDrawingCacheEnabled(true);
                    this.aj.setDrawingCacheQuality(0);
                    iMarkerDelegate.getRect();
                    int realInfoWindowOffsetX = iMarkerDelegate.getRealInfoWindowOffsetX() + iMarkerDelegate.getInfoWindowOffsetX();
                    int realInfoWindowOffsetY = (iMarkerDelegate.getRealInfoWindowOffsetY() + iMarkerDelegate.getInfoWindowOffsetY()) + 2;
                    if (layoutParams != null) {
                        i2 = layoutParams.width;
                        i = layoutParams.height;
                    } else {
                        i2 = -2;
                    }
                    layoutParams = new C0198a(i2, i, iMarkerDelegate.getMapPosition(), realInfoWindowOffsetX, realInfoWindowOffsetY, 81);
                    Bitmap a;
                    BitmapDescriptor fromBitmap;
                    if (this.al == null) {
                        a = dj.m567a(this.aj);
                        fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                        a.recycle();
                        this.al = new ap(this, new MarkerOptions().icon(fromBitmap), this) {
                            final /* synthetic */ C1592c f5356a;

                            public void mo3005a() {
                                this.f5356a.aC.removeCallbacks(this.f5356a.bv);
                                this.f5356a.aC.post(this.f5356a.bw);
                            }
                        };
                        this.al.m3984a(iMarkerDelegate.getMapPosition());
                        this.al.setInfoWindowOffset(realInfoWindowOffsetX, realInfoWindowOffsetY);
                    } else {
                        this.al.m3984a(iMarkerDelegate.getMapPosition());
                        this.al.setInfoWindowOffset(realInfoWindowOffsetX, realInfoWindowOffsetY);
                        a = dj.m567a(this.aj);
                        fromBitmap = BitmapDescriptorFactory.fromBitmap(a);
                        a.recycle();
                        this.al.setIcon(fromBitmap);
                    }
                    this.f4101O.addView(this.aj, layoutParams);
                    iMarkerDelegate.setInfoWindowShown(true);
                    return;
                }
                this.aC.postDelayed(new C02177(this), 100);
            }
        }
    }

    public boolean isInfoWindowShown(IMarkerDelegate iMarkerDelegate) {
        try {
            if (!(this.ak == null || this.aj == null)) {
                return this.ak.getId().equals(iMarkerDelegate.getId());
            }
        } catch (Throwable e) {
            ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "isInfoWindowShown");
            e.printStackTrace();
        }
        return false;
    }

    public void hiddenInfoWindowShown() {
        if (this.aj != null) {
            this.aj.clearFocus();
            this.f4101O.removeView(this.aj);
            dj.m578a(this.aj.getBackground());
            dj.m578a(this.ay);
            if (this.al != null) {
                this.al.setVisible(false);
            }
            this.aj = null;
        }
        this.ak = null;
    }

    public float getZoomLevel() {
        return this.f4096J.getMapZoomer();
    }

    void m4151e() {
        this.f4124l.obtainMessage(18).sendToTarget();
    }

    public LatLngBounds getMapBounds() {
        return this.bp;
    }

    public LatLngBounds getMapBounds(LatLng latLng, float f) {
        int c = m4149c();
        int d = m4150d();
        if (c <= 0 || d <= 0) {
            return null;
        }
        IPoint iPoint = new IPoint();
        MapProjection.lonlat2Geo(latLng.longitude, latLng.latitude, iPoint);
        MapProjection mapProjection = new MapProjection(this.f4093G);
        mapProjection.setCameraHeaderAngle(0.0f);
        mapProjection.setMapAngle(0.0f);
        mapProjection.setGeoCenter(iPoint.f2030x, iPoint.f2031y);
        mapProjection.setMapZoomer(f);
        mapProjection.recalculate();
        DPoint dPoint = new DPoint();
        m4084a(mapProjection, 0, 0, dPoint);
        LatLng latLng2 = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
        m4084a(mapProjection, c, d, dPoint);
        LatLng latLng3 = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
        mapProjection.recycle();
        return LatLngBounds.builder().include(latLng3).include(latLng2).build();
    }

    public Point getWaterMarkerPositon() {
        if (this.f4102P == null) {
            return null;
        }
        return this.f4102P.m230c();
    }

    public static Bitmap m4068a(int i, int i2, int i3, int i4, GL10 gl10) {
        try {
            int[] iArr = new int[(i3 * i4)];
            int[] iArr2 = new int[(i3 * i4)];
            Buffer wrap = IntBuffer.wrap(iArr);
            wrap.position(0);
            gl10.glReadPixels(i, i2, i3, i4, 6408, 5121, wrap);
            for (int i5 = 0; i5 < i4; i5++) {
                for (int i6 = 0; i6 < i3; i6++) {
                    int i7 = iArr[(i5 * i3) + i6];
                    iArr2[(((i4 - i5) - 1) * i3) + i6] = ((i7 & -16711936) | ((i7 << 16) & 16711680)) | ((i7 >> 16) & 255);
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Config.ARGB_8888);
            createBitmap.setPixels(iArr2, 0, i3, 0, 0, i3, i4);
            return createBitmap;
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "SavePixels");
            th.printStackTrace();
            return null;
        }
    }

    public void getMapPrintScreen(onMapPrintScreenListener com_amap_api_maps_AMap_onMapPrintScreenListener) {
        this.aA = com_amap_api_maps_AMap_onMapPrintScreenListener;
        this.aR = true;
        setRunLowFrame(false);
    }

    public void getMapScreenShot(OnMapScreenShotListener onMapScreenShotListener) {
        this.aB = onMapScreenShotListener;
        this.aR = true;
        setRunLowFrame(false);
    }

    public int getLogoPosition() {
        try {
            return this.an.getLogoPosition();
        } catch (Throwable e) {
            ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "getLogoPosition");
            e.printStackTrace();
            return 0;
        }
    }

    public void setLogoPosition(int i) {
        if (this.f4102P != null) {
            this.f4102P.m227a(i);
            this.f4102P.invalidate();
            if (this.f4105S.getVisibility() == 0) {
                this.f4105S.invalidate();
            }
        }
    }

    public void setZoomPosition(int i) {
        if (this.f4118f != null) {
            this.f4118f.m240a(i);
        }
    }

    public float getScalePerPixel() {
        try {
            LatLng latLng = getCameraPosition().target;
            float f = this.f4113a;
            if (this.aI) {
                f = this.f4096J.getMapZoomer();
            }
            return (float) ((((Math.cos((latLng.latitude * 3.141592653589793d) / 180.0d) * 2.0d) * 3.141592653589793d) * 6378137.0d) / (Math.pow(2.0d, (double) f) * 256.0d));
        } catch (Throwable th) {
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "getScalePerPixel");
            th.printStackTrace();
            return 0.0f;
        }
    }

    void m4146a(boolean z) {
        int i;
        Handler handler = this.f4124l;
        if (z) {
            i = 1;
        } else {
            i = 0;
        }
        handler.obtainMessage(20, i, 0).sendToTarget();
    }

    protected void m4147a(boolean z, CameraPosition cameraPosition) {
        if (this.aa != null && this.at.isFinished() && this.f4122j.isEnabled()) {
            if (cameraPosition == null) {
                try {
                    cameraPosition = getCameraPosition();
                } catch (Throwable e) {
                    ee.m4243a(e, "AMapDelegateImpGLSurfaceView", "cameraChangeFinish");
                    e.printStackTrace();
                }
            }
            this.aa.onCameraChangeFinish(cameraPosition);
        }
    }

    public void deleteTexsureId(int i) {
        if (this.f4088B.contains(Integer.valueOf(i))) {
            this.f4087A.add(Integer.valueOf(i));
            this.f4088B.remove(this.f4088B.indexOf(Integer.valueOf(i)));
        }
    }

    public int getTexsureId() {
        Integer valueOf = Integer.valueOf(0);
        if (this.f4087A.size() > 0) {
            valueOf = (Integer) this.f4087A.get(0);
            this.f4087A.remove(0);
            this.f4088B.add(valueOf);
        }
        return valueOf.intValue();
    }

    public List<Marker> getMapScreenMarkers() {
        boolean z = m4149c() > 0 && m4150d() > 0;
        cu.m450a(z, (Object) "地图未初始化完成！");
        return this.f4116d.m163g();
    }

    public void changeGLOverlayIndex() {
        this.f4120h.m1031b();
    }

    public boolean isAdreno() {
        return this.aM;
    }

    public void callRunDestroy() {
        this.bq = true;
    }

    public boolean isNeedRunDestroy() {
        return this.bq;
    }

    public void runDestroy() {
        if (this.f4116d != null) {
            this.f4116d.m164h();
        }
        this.bq = false;
    }

    public void setCenterToPixel(int i, int i2) {
        if (this.f4095I != null) {
            this.br = true;
            this.f4095I.m5642a(i, i2);
            this.bs = i;
            this.bt = i2;
        }
    }

    public boolean isUseAnchor() {
        return this.br;
    }

    public void setMapTextZIndex(int i) {
        this.ax = i;
    }

    public int getMapTextZIndex() {
        return this.ax;
    }

    public boolean isMaploaded() {
        return this.aJ;
    }

    public int getAnchorX() {
        return this.bs;
    }

    public int getAnchorY() {
        return this.bt;
    }

    public CameraAnimator getCameraAnimator() {
        return this.at;
    }

    public void setLoadOfflineData(final boolean z) throws RemoteException {
        queueEvent(new Runnable(this) {
            final /* synthetic */ C1592c f307b;

            public void run() {
                int i;
                MapCore g = this.f307b.f4093G;
                if (z) {
                    i = 1;
                } else {
                    i = 0;
                }
                g.setParameter(2601, i, 0, 0, 0);
            }
        });
    }

    public void removecache() {
        removecache(null);
    }

    public void removecache(OnCacheRemoveListener onCacheRemoveListener) {
        if (this.aU != null) {
            try {
                this.f4093G.setParameter(2601, 0, 0, 0, 0);
                Runnable c0226i = new C0226i(this, this.f4094H, onCacheRemoveListener);
                this.aU.removeCallbacks(c0226i);
                this.aU.post(c0226i);
            } catch (Throwable th) {
                ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "removecache");
                th.printStackTrace();
            }
        }
    }

    private boolean m4087a(File file) throws IOException, Exception {
        if (file == null || !file.exists()) {
            return false;
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    if (!listFiles[i].delete()) {
                        return false;
                    }
                } else if (!m4087a(listFiles[i])) {
                    return false;
                } else {
                    listFiles[i].delete();
                }
            }
        }
        return true;
    }

    public void m4152f() {
        if (this.f4120h != null) {
            this.f4120h.m1033c();
        }
        if (this.f4116d != null) {
            this.f4116d.m155c();
        }
        if (this.f4125m != null) {
            this.f4125m.OnMapReferencechanged();
        }
    }

    public void setVisibilityEx(int i) {
        this.f4122j.setVisibility(i);
    }

    public void m4141a(IndoorBuilding indoorBuilding) throws RemoteException {
        if (!this.f4133u) {
            return;
        }
        if (indoorBuilding == null) {
            if (!m4120o()) {
                if (this.ag != null) {
                    this.ag.OnIndoorBuilding(indoorBuilding);
                }
                if (this.aD != null) {
                    this.aD.geoCenter = null;
                }
                if (this.f4107U.m1070d()) {
                    this.f4124l.post(new Runnable(this) {
                        final /* synthetic */ C1592c f308a;

                        {
                            this.f308a = r1;
                        }

                        public void run() {
                            this.f308a.f4107U.setVisibility(8);
                        }
                    });
                }
                C0273r.f699f = 19.0f;
                if (this.an.isZoomControlsEnabled()) {
                    this.f4124l.sendEmptyMessage(21);
                }
            }
        } else if (this.aD == null || !this.aD.poiid.equals(indoorBuilding.poiid) || !this.f4107U.m1070d()) {
            if (this.aD == null || !this.aD.poiid.equals(indoorBuilding.poiid) || this.aD.geoCenter == null) {
                this.aD = indoorBuilding;
                this.aD.geoCenter = new IPoint();
                this.f4096J.getGeoCenter(this.aD.geoCenter);
            }
            if (this.ag != null) {
                this.ag.OnIndoorBuilding(indoorBuilding);
            }
            C0273r.f699f = TitleBar.BACKBTN_LEFT_MARGIN;
            if (this.an.isZoomControlsEnabled()) {
                this.f4124l.sendEmptyMessage(21);
            }
            if (this.an.isIndoorSwitchEnabled() && !this.f4107U.m1070d()) {
                this.an.setIndoorSwitchEnabled(true);
                this.f4124l.post(new Runnable(this) {
                    final /* synthetic */ C1592c f309a;

                    {
                        this.f309a = r1;
                    }

                    public void run() {
                        try {
                            this.f309a.f4107U.m1067a(this.f309a.aD.floor_names);
                            this.f309a.f4107U.m1065a(this.f309a.aD.activeFloorName);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
            } else if (!this.an.isIndoorSwitchEnabled() && this.f4107U.m1070d()) {
                this.an.setIndoorSwitchEnabled(false);
            }
        }
    }

    public void setIndoorBuildingInfo(IndoorBuilding indoorBuilding) throws RemoteException {
        if (indoorBuilding != null && indoorBuilding.activeFloorName != null && indoorBuilding.poiid != null) {
            this.aD = indoorBuilding;
            setRunLowFrame(false);
            queueEvent(new Runnable(this) {
                final /* synthetic */ C1592c f310a;

                {
                    this.f310a = r1;
                }

                public void run() {
                    this.f310a.f4093G.setIndoorBuildingToBeActive(this.f310a.aD.activeFloorName, this.f310a.aD.activeFloorIndex, this.f310a.aD.poiid);
                }
            });
        }
    }

    private Poi m4077a(int i, int i2, int i3) {
        if (!this.aJ) {
            return null;
        }
        try {
            SelectedMapPoi GetSelectedMapPoi = this.f4093G.GetSelectedMapPoi(i, i2, i3);
            if (GetSelectedMapPoi == null) {
                return null;
            }
            DPoint dPoint = new DPoint();
            MapProjection.geo2LonLat(GetSelectedMapPoi.mapx, GetSelectedMapPoi.mapy, dPoint);
            return new Poi(GetSelectedMapPoi.name, new LatLng(dPoint.f2027y, dPoint.f2026x, false), GetSelectedMapPoi.poiid);
        } catch (Throwable th) {
            return null;
        }
    }

    public void setCustomRenderer(CustomRenderer customRenderer) {
        this.f4125m = customRenderer;
    }

    public Context m4153g() {
        return this.f4094H;
    }

    public void queueEvent(Runnable runnable) {
        if (this.f4122j != null) {
            this.f4122j.queueEvent(runnable);
        }
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        try {
            if (!this.aI) {
                m4111l();
            }
            this.bd = false;
            this.f4093G.setGL(gl10);
            m4125r();
            this.f4093G.surfaceCreate(gl10);
            if (this.f4129q == null || this.f4129q.isRecycled()) {
                this.f4129q = dj.m564a(this.f4094H, "lineTexture.png");
            }
            if (this.f4130r == null || this.f4130r.isRecycled()) {
                this.f4130r = dj.m564a(this.f4094H, "lineDashTexture.png");
            }
            this.aT = false;
            this.f4126n = dj.m561a(gl10, this.f4129q);
            this.f4127o = dj.m562a(gl10, this.f4130r, true);
            this.f4129q = null;
            this.f4116d.m166j();
            this.f4120h.m1036e();
            this.f4119g.m225f();
            if (this.al != null) {
                this.al.reLoadTexture();
            }
            m4122p();
            setRunLowFrame(false);
            if (!this.aK) {
                this.bo.setName("AuthThread");
                this.bo.start();
                this.aK = true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (this.f4125m != null) {
            this.f4125m.onSurfaceCreated(gl10, eGLConfig);
        }
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        int i3 = 120;
        int i4 = 50;
        int i5 = 1;
        this.ap = new Rect(0, 0, i, i2);
        try {
            this.f4093G.setGL(gl10);
            this.f4093G.surfaceChange(gl10, i, i2);
            int i6 = this.f4094H.getResources().getDisplayMetrics().densityDpi;
            float f = this.f4094H.getResources().getDisplayMetrics().density;
            int i7 = 100;
            if (i6 > 120) {
                if (i6 <= C1507j.f3829b) {
                    if (Math.max(i, i2) <= 480) {
                        i6 = 120;
                    } else {
                        i6 = 100;
                        i3 = C1507j.f3829b;
                    }
                    i4 = i3;
                    i7 = i6;
                } else if (i6 <= SocializeConstants.MASK_USER_CENTER_HIDE_AREA) {
                    if (Math.min(i, i2) >= 1000) {
                        i7 = 60;
                        i4 = 200;
                        i5 = 2;
                    } else {
                        i7 = 70;
                        i4 = 150;
                        i5 = 2;
                    }
                } else if (i6 <= 320) {
                    i5 = 3;
                    i7 = 50;
                    i4 = 180;
                } else if (i6 <= 480) {
                    i5 = 3;
                    i7 = 50;
                    i4 = 300;
                } else {
                    i7 = 40;
                    i4 = C0919a.f3120q;
                    i5 = 4;
                }
            }
            this.f4093G.setParameter(2051, i7, i4, (int) (f * 100.0f), i5);
            this.f4138z = ((float) i7) / 100.0f;
            this.f4093G.setParameter(1001, 0, 0, 0, 0);
            this.f4093G.setParameter(1023, 1, 0, 0, 0);
            setRunLowFrame(false);
            if (this.f4125m != null) {
                this.f4125m.onSurfaceChanged(gl10, i, i2);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public float getMapZoomScale() {
        return this.f4138z;
    }

    public void reloadMap() {
        this.aJ = false;
        onPause();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onResume();
    }

    public C0283c m4154h() {
        return this.f4089C;
    }

    public C0281a m4155i() {
        return this.f4090D;
    }

    public C0282b m4156j() {
        return this.f4091E;
    }

    private void m4131u() {
        if (this.f4090D == C0281a.SATELLITE || this.f4089C == C0283c.NIGHT) {
            this.f4102P.m228a(true);
        } else {
            this.f4102P.m228a(false);
        }
    }

    public void setRenderFps(int i) {
        try {
            this.f4128p = Math.max(10, Math.min(i, 40));
            this.f4124l.sendEmptyMessage(22);
        } catch (Throwable th) {
        }
    }

    public boolean isDrawOnce() {
        return this.aL;
    }
}
