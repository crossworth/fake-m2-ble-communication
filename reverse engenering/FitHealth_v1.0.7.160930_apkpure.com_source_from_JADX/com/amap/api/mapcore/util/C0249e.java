package com.amap.api.mapcore.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.autonavi.amap.mapcore.DPoint;
import com.autonavi.amap.mapcore.FPoint;
import com.autonavi.amap.mapcore.IPoint;
import com.autonavi.amap.mapcore.MapProjection;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.tencent.open.yyb.TitleBar;

/* compiled from: AMapDelegateImp */
class C0249e extends Handler {
    final /* synthetic */ C1592c f532a;

    C0249e(C1592c c1592c) {
        this.f532a = c1592c;
    }

    public void handleMessage(Message message) {
        if (message != null && !this.f532a.aQ.booleanValue()) {
            this.f532a.setRunLowFrame(false);
            CameraPosition cameraPosition;
            CameraUpdateFactoryDelegate cameraUpdateFactoryDelegate;
            int currX;
            int currY;
            switch (message.what) {
                case 2:
                    Log.w("amapsdk", "Key验证失败：[" + dm.f466b + "]");
                    break;
                case 10:
                    cameraPosition = (CameraPosition) message.obj;
                    if (!(cameraPosition == null || this.f532a.aa == null)) {
                        this.f532a.aa.onCameraChange(cameraPosition);
                        break;
                    }
                case 11:
                    if (this.f532a.aE != null) {
                        try {
                            this.f532a.moveCamera(this.f532a.aE);
                        } catch (Throwable th) {
                            ee.m4243a(th, "AMapDelegateImp", "onMapLoaded");
                            th.printStackTrace();
                        }
                    }
                    if (this.f532a.f4112Z != null) {
                        this.f532a.f4112Z.onMapLoaded();
                        break;
                    }
                    break;
                case 12:
                    cameraUpdateFactoryDelegate = (CameraUpdateFactoryDelegate) message.obj;
                    if (cameraUpdateFactoryDelegate != null) {
                        this.f532a.f4117e.m135a(cameraUpdateFactoryDelegate);
                        break;
                    }
                    break;
                case 13:
                    if (this.f532a.at != null && this.f532a.at.computeScrollOffset()) {
                        switch (this.f532a.at.getMode()) {
                            case 2:
                                cameraUpdateFactoryDelegate = CameraUpdateFactoryDelegate.newCamera(new IPoint(this.f532a.at.getCurrX(), this.f532a.at.getCurrY()), this.f532a.at.getCurrZ(), this.f532a.at.getCurrBearing(), this.f532a.at.getCurrTilt());
                                if (this.f532a.at.isFinished()) {
                                    cameraUpdateFactoryDelegate.isChangeFinished = true;
                                }
                                cameraUpdateFactoryDelegate.isUseAnchor = this.f532a.at.isUseAnchor();
                                this.f532a.f4117e.m135a(cameraUpdateFactoryDelegate);
                                break;
                            default:
                                currX = this.f532a.at.getCurrX() - this.f532a.au;
                                currY = this.f532a.at.getCurrY() - this.f532a.av;
                                this.f532a.au = this.f532a.at.getCurrX();
                                this.f532a.av = this.f532a.at.getCurrY();
                                FPoint fPoint = new FPoint((float) (currX + (this.f532a.m4149c() / 2)), (float) (currY + (this.f532a.m4150d() / 2)));
                                FPoint fPoint2 = new FPoint();
                                this.f532a.f4096J.win2Map((int) fPoint.f2028x, (int) fPoint.f2029y, fPoint2);
                                IPoint iPoint = new IPoint();
                                this.f532a.f4096J.map2Geo(fPoint2.f2028x, fPoint2.f2029y, iPoint);
                                cameraUpdateFactoryDelegate = CameraUpdateFactoryDelegate.changeGeoCenter(iPoint);
                                if (this.f532a.at.isFinished()) {
                                    cameraUpdateFactoryDelegate.isChangeFinished = true;
                                }
                                this.f532a.f4117e.m135a(cameraUpdateFactoryDelegate);
                                break;
                        }
                    }
                case 14:
                    if (this.f532a.f4104R != null) {
                        this.f532a.f4104R.m1000b();
                        break;
                    }
                    return;
                case 16:
                    Bitmap bitmap = (Bitmap) message.obj;
                    currY = message.arg1;
                    if (bitmap != null) {
                        Canvas canvas = new Canvas(bitmap);
                        if (this.f532a.f4102P != null) {
                            this.f532a.f4102P.onDraw(canvas);
                        }
                        if (!(this.f532a.aj == null || this.f532a.ak == null)) {
                            Bitmap drawingCache = this.f532a.aj.getDrawingCache(true);
                            if (drawingCache != null) {
                                canvas.drawBitmap(drawingCache, (float) this.f532a.aj.getLeft(), (float) this.f532a.aj.getTop(), new Paint());
                            }
                        }
                        if (this.f532a.aA != null) {
                            this.f532a.aA.onMapPrint(new BitmapDrawable(this.f532a.f4094H.getResources(), bitmap));
                        }
                        if (this.f532a.aB != null) {
                            this.f532a.aB.onMapScreenShot(bitmap);
                            this.f532a.aB.onMapScreenShot(bitmap, currY);
                        }
                    } else {
                        if (this.f532a.aA != null) {
                            this.f532a.aA.onMapPrint(null);
                        }
                        if (this.f532a.aB != null) {
                            this.f532a.aB.onMapScreenShot(null);
                            this.f532a.aB.onMapScreenShot(null, currY);
                        }
                    }
                    this.f532a.aA = null;
                    this.f532a.aB = null;
                    break;
                case 17:
                    if (!(this.f532a.aj == null || this.f532a.al == null)) {
                        this.f532a.aj.setVisibility(0);
                    }
                    try {
                        cameraPosition = this.f532a.getCameraPosition();
                        if (cameraPosition != null) {
                            if (cameraPosition.zoom < TitleBar.SHAREBTN_RIGHT_MARGIN || dg.m545a(cameraPosition.target.latitude, cameraPosition.target.longitude)) {
                                this.f532a.f4102P.setVisibility(0);
                            } else {
                                this.f532a.f4102P.setVisibility(8);
                            }
                        }
                        if (this.f532a.aw == null || !this.f532a.aN) {
                            this.f532a.m4147a(true, cameraPosition);
                        }
                        if (this.f532a.aw != null) {
                            this.f532a.aO = true;
                            this.f532a.aw.onFinish();
                            this.f532a.aO = false;
                        }
                        if (!this.f532a.aP) {
                            this.f532a.aw = null;
                            break;
                        } else {
                            this.f532a.aP = false;
                            break;
                        }
                    } catch (Throwable th2) {
                        ee.m4243a(th2, "AMapDelegateImpGLSurfaceView", "CameraUpdateFinish");
                        break;
                    }
                    break;
                case 18:
                    currX = this.f532a.m4149c();
                    int d = this.f532a.m4150d();
                    if (currX > 0 && d > 0) {
                        try {
                            CameraPosition cameraPosition2 = this.f532a.getCameraPosition();
                            MapProjection.lonlat2Geo(cameraPosition2.target.longitude, cameraPosition2.target.latitude, new IPoint());
                            MapProjection mapProjection = new MapProjection(this.f532a.f4093G);
                            mapProjection.setCameraHeaderAngle(cameraPosition2.tilt);
                            mapProjection.setMapAngle(cameraPosition2.bearing);
                            mapProjection.setMapZoomer(cameraPosition2.zoom);
                            mapProjection.recalculate();
                            DPoint dPoint = new DPoint();
                            this.f532a.m4084a(mapProjection, 0, 0, dPoint);
                            LatLng latLng = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
                            this.f532a.m4084a(mapProjection, currX, 0, dPoint);
                            LatLng latLng2 = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
                            this.f532a.m4084a(mapProjection, 0, d, dPoint);
                            LatLng latLng3 = new LatLng(dPoint.f2027y, dPoint.f2026x, false);
                            this.f532a.m4084a(mapProjection, currX, d, dPoint);
                            this.f532a.bp = LatLngBounds.builder().include(latLng3).include(new LatLng(dPoint.f2027y, dPoint.f2026x, false)).include(latLng).include(latLng2).build();
                            mapProjection.recycle();
                            break;
                        } catch (Throwable th3) {
                            break;
                        }
                    }
                    this.f532a.bp = null;
                    break;
                    break;
                case 20:
                    if (this.f532a.at.isFinished() || !(this.f532a.at.getMode() == 1 || this.f532a.f4119g == null)) {
                        this.f532a.f4119g.m220b(false);
                    }
                    if (this.f532a.f4119g != null) {
                        this.f532a.f4119g.m218a(message.arg1 != 0);
                        break;
                    }
                    break;
                case 21:
                    if (this.f532a.f4118f != null) {
                        this.f532a.f4118f.m239a(this.f532a.getZoomLevel());
                        break;
                    }
                    break;
                case 22:
                    this.f532a.m4122p();
                    break;
            }
            this.f532a.setRunLowFrame(false);
        }
    }
}
