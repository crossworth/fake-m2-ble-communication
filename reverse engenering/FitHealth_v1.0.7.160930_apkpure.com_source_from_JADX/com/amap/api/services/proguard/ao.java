package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IRouteSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.FromAndTo;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkRouteResult;

/* compiled from: RouteSearchCore */
public class ao implements IRouteSearch {
    private OnRouteSearchListener f4331a;
    private Context f4332b;
    private Handler f4333c = C0407q.m1654a();

    public ao(Context context) {
        this.f4332b = context.getApplicationContext();
    }

    public void setRouteSearchListener(OnRouteSearchListener onRouteSearchListener) {
        this.f4331a = onRouteSearchListener;
    }

    public WalkRouteResult calculateWalkRoute(WalkRouteQuery walkRouteQuery) throws AMapException {
        try {
            C0394o.m1652a(this.f4332b);
            if (walkRouteQuery == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            } else if (m4414a(walkRouteQuery.getFromAndTo())) {
                WalkRouteQuery clone = walkRouteQuery.clone();
                WalkRouteResult walkRouteResult = (WalkRouteResult) new ac(this.f4332b, clone).m4358a();
                if (walkRouteResult != null) {
                    walkRouteResult.setWalkQuery(clone);
                }
                return walkRouteResult;
            } else {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "RouteSearch", "calculateWalkRoute");
            throw e;
        }
    }

    public void calculateWalkRouteAsyn(final WalkRouteQuery walkRouteQuery) {
        new Thread(this) {
            final /* synthetic */ ao f1297b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                obtainMessage.what = 102;
                obtainMessage.arg1 = 1;
                Bundle bundle = new Bundle();
                Parcelable parcelable = null;
                try {
                    parcelable = this.f1297b.calculateWalkRoute(walkRouteQuery);
                    bundle.putInt("errorCode", 1000);
                    obtainMessage.obj = this.f1297b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1297b.f4333c.sendMessage(obtainMessage);
                } catch (AMapException e) {
                    bundle.putInt("errorCode", e.getErrorCode());
                    obtainMessage.obj = this.f1297b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1297b.f4333c.sendMessage(obtainMessage);
                } catch (Throwable th) {
                    obtainMessage.obj = this.f1297b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1297b.f4333c.sendMessage(obtainMessage);
                }
            }
        }.start();
    }

    public BusRouteResult calculateBusRoute(BusRouteQuery busRouteQuery) throws AMapException {
        try {
            C0394o.m1652a(this.f4332b);
            if (busRouteQuery == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            } else if (m4414a(busRouteQuery.getFromAndTo())) {
                BusRouteQuery clone = busRouteQuery.clone();
                BusRouteResult busRouteResult = (BusRouteResult) new C2045c(this.f4332b, clone).m4358a();
                if (busRouteResult != null) {
                    busRouteResult.setBusQuery(clone);
                }
                return busRouteResult;
            } else {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "RouteSearch", "calculateBusRoute");
            throw e;
        }
    }

    public void calculateBusRouteAsyn(final BusRouteQuery busRouteQuery) {
        new Thread(this) {
            final /* synthetic */ ao f1299b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                obtainMessage.what = 100;
                obtainMessage.arg1 = 1;
                Bundle bundle = new Bundle();
                Parcelable parcelable = null;
                try {
                    parcelable = this.f1299b.calculateBusRoute(busRouteQuery);
                    bundle.putInt("errorCode", 1000);
                    obtainMessage.obj = this.f1299b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1299b.f4333c.sendMessage(obtainMessage);
                } catch (AMapException e) {
                    bundle.putInt("errorCode", e.getErrorCode());
                    obtainMessage.obj = this.f1299b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1299b.f4333c.sendMessage(obtainMessage);
                } catch (Throwable th) {
                    obtainMessage.obj = this.f1299b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1299b.f4333c.sendMessage(obtainMessage);
                }
            }
        }.start();
    }

    public DriveRouteResult calculateDriveRoute(DriveRouteQuery driveRouteQuery) throws AMapException {
        try {
            C0394o.m1652a(this.f4332b);
            if (driveRouteQuery == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            } else if (m4414a(driveRouteQuery.getFromAndTo())) {
                DriveRouteQuery clone = driveRouteQuery.clone();
                DriveRouteResult driveRouteResult = (DriveRouteResult) new C2049k(this.f4332b, clone).m4358a();
                if (driveRouteResult != null) {
                    driveRouteResult.setDriveQuery(clone);
                }
                return driveRouteResult;
            } else {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
        } catch (Throwable e) {
            C0390i.m1594a(e, "RouteSearch", "calculateDriveRoute");
            throw e;
        }
    }

    public void calculateDriveRouteAsyn(final DriveRouteQuery driveRouteQuery) {
        new Thread(this) {
            final /* synthetic */ ao f1301b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                obtainMessage.what = 101;
                obtainMessage.arg1 = 1;
                Bundle bundle = new Bundle();
                Parcelable parcelable = null;
                try {
                    parcelable = this.f1301b.calculateDriveRoute(driveRouteQuery);
                    bundle.putInt("errorCode", 1000);
                    obtainMessage.obj = this.f1301b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1301b.f4333c.sendMessage(obtainMessage);
                } catch (AMapException e) {
                    bundle.putInt("errorCode", e.getErrorCode());
                    obtainMessage.obj = this.f1301b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1301b.f4333c.sendMessage(obtainMessage);
                } catch (Throwable th) {
                    obtainMessage.obj = this.f1301b.f4331a;
                    bundle.putParcelable("result", parcelable);
                    obtainMessage.setData(bundle);
                    this.f1301b.f4333c.sendMessage(obtainMessage);
                }
            }
        }.start();
    }

    private boolean m4414a(FromAndTo fromAndTo) {
        if (fromAndTo == null || fromAndTo.getFrom() == null || fromAndTo.getTo() == null) {
            return false;
        }
        return true;
    }
}
