package com.amap.api.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import com.amap.api.location.core.C0188c;
import com.amap.api.location.core.C0189d;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class LocationManagerProxy {
    public static final String GPS_PROVIDER = "gps";
    public static final String KEY_LOCATION_CHANGED = "location";
    public static final String KEY_PROVIDER_ENABLED = "providerEnabled";
    public static final String KEY_PROXIMITY_ENTERING = "entering";
    public static final String KEY_STATUS_CHANGED = "status";
    public static final String NETWORK_PROVIDER = "network";
    public static final int WEATHER_TYPE_FORECAST = 2;
    public static final int WEATHER_TYPE_LIVE = 1;
    private static LocationManagerProxy f39b = null;
    private LocationManager f40a = null;
    private C0182a f41c = null;
    private Context f42d;
    private C1584f f43e;
    private C1582b f44f;
    private ArrayList<PendingIntent> f45g = new ArrayList();
    private Hashtable<String, LocationProviderProxy> f46h = new Hashtable();
    private Vector<C0195g> f47i = new Vector();
    private Vector<C0195g> f48j = new Vector();
    private C1581a f49k = new C1581a(this);

    class C1581a implements AMapLocationListener {
        final /* synthetic */ LocationManagerProxy f3928a;

        C1581a(LocationManagerProxy locationManagerProxy) {
            this.f3928a = locationManagerProxy;
        }

        public void onLocationChanged(Location location) {
            int i = 0;
            int i2;
            C0195g c0195g;
            if (location != null) {
                try {
                    AMapLocation aMapLocation = new AMapLocation(location);
                    i2 = 0;
                    while (this.f3928a.f47i != null && i2 < this.f3928a.f47i.size()) {
                        c0195g = (C0195g) this.f3928a.f47i.get(i2);
                        if (!(c0195g == null || c0195g.f122b == null)) {
                            c0195g.f122b.onLocationChanged(aMapLocation);
                        }
                        if (!(c0195g == null || c0195g.f121a != -1 || this.f3928a.f48j == null)) {
                            this.f3928a.f48j.add(c0195g);
                        }
                        i2++;
                    }
                    if (this.f3928a.f48j != null && this.f3928a.f48j.size() > 0 && this.f3928a.f47i != null) {
                        while (i < this.f3928a.f48j.size()) {
                            this.f3928a.f47i.remove(this.f3928a.f48j.get(i));
                            i++;
                        }
                        this.f3928a.f48j.clear();
                        if (this.f3928a.f47i.size() == 0 && this.f3928a.f40a != null && this.f3928a.f49k != null) {
                            this.f3928a.f40a.removeUpdates(this.f3928a.f49k);
                            return;
                        }
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    th.printStackTrace();
                    return;
                }
            }
            i2 = 0;
            while (this.f3928a.f47i != null && i2 < this.f3928a.f47i.size()) {
                c0195g = (C0195g) this.f3928a.f47i.get(i2);
                if (!(c0195g == null || c0195g.f121a != -1 || this.f3928a.f48j == null)) {
                    this.f3928a.f48j.add(c0195g);
                }
                i2++;
            }
            if (this.f3928a.f48j != null && this.f3928a.f48j.size() > 0 && this.f3928a.f47i != null) {
                for (int i3 = 0; i3 < this.f3928a.f48j.size(); i3++) {
                    this.f3928a.f47i.remove(this.f3928a.f48j.get(i3));
                }
                this.f3928a.f48j.clear();
                if (this.f3928a.f47i.size() == 0 && this.f3928a.f40a != null && this.f3928a.f49k != null) {
                    this.f3928a.f40a.removeUpdates(this.f3928a.f49k);
                }
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onProviderDisabled(String str) {
        }

        public void onLocationChanged(AMapLocation aMapLocation) {
        }
    }

    class C1582b implements AMapLocationListener {
        final /* synthetic */ LocationManagerProxy f3929a;

        C1582b(LocationManagerProxy locationManagerProxy) {
            this.f3929a = locationManagerProxy;
        }

        public void onLocationChanged(Location location) {
            try {
                if (this.f3929a.f45g != null && this.f3929a.f45g.size() > 0) {
                    Iterator it = this.f3929a.f45g.iterator();
                    while (it.hasNext()) {
                        PendingIntent pendingIntent = (PendingIntent) it.next();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("location", location);
                        intent.putExtras(bundle);
                        pendingIntent.send(this.f3929a.f42d, 0, intent);
                    }
                }
            } catch (CanceledException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onProviderDisabled(String str) {
        }

        public void onLocationChanged(AMapLocation aMapLocation) {
            try {
                if (this.f3929a.f45g != null && this.f3929a.f45g.size() > 0) {
                    Iterator it = this.f3929a.f45g.iterator();
                    while (it.hasNext()) {
                        PendingIntent pendingIntent = (PendingIntent) it.next();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("location", aMapLocation);
                        intent.putExtras(bundle);
                        pendingIntent.send(this.f3929a.f42d, 0, intent);
                    }
                }
            } catch (CanceledException e) {
                e.printStackTrace();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static void m32a() {
        f39b = null;
    }

    private LocationManagerProxy(Context context) {
        m33a(context);
    }

    private LocationManagerProxy(Activity activity) {
        m33a(activity.getApplicationContext());
    }

    public static synchronized LocationManagerProxy getInstance(Activity activity) {
        LocationManagerProxy locationManagerProxy;
        synchronized (LocationManagerProxy.class) {
            try {
                if (f39b == null) {
                    f39b = new LocationManagerProxy(activity);
                }
                locationManagerProxy = f39b;
            } catch (Throwable th) {
                th.printStackTrace();
                locationManagerProxy = null;
            }
        }
        return locationManagerProxy;
    }

    public static synchronized LocationManagerProxy getInstance(Context context) {
        LocationManagerProxy locationManagerProxy;
        synchronized (LocationManagerProxy.class) {
            try {
                if (f39b == null) {
                    f39b = new LocationManagerProxy(context);
                }
                locationManagerProxy = f39b;
            } catch (Throwable th) {
                th.printStackTrace();
                locationManagerProxy = null;
            }
        }
        return locationManagerProxy;
    }

    private void m33a(Context context) {
        try {
            this.f42d = context;
            C0188c.m84a(context);
            this.f40a = (LocationManager) context.getSystemService("location");
            this.f41c = new C0182a(context.getApplicationContext(), this.f40a);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void addProximityAlert(double d, double d2, float f, long j, PendingIntent pendingIntent) {
        try {
            if (this.f41c.f61f) {
                this.f40a.addProximityAlert(d, d2, f, j, pendingIntent);
            }
            this.f41c.m53a(d, d2, f, j, pendingIntent);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void addGeoFenceAlert(double d, double d2, float f, long j, PendingIntent pendingIntent) {
        try {
            if (this.f41c != null) {
                this.f41c.m60b(d, d2, f, j, pendingIntent);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent) {
        try {
            if (this.f41c != null) {
                this.f41c.m61b(pendingIntent);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeProximityAlert(PendingIntent pendingIntent) {
        try {
            if (!(this.f41c == null || !this.f41c.f61f || this.f40a == null)) {
                this.f40a.removeProximityAlert(pendingIntent);
            }
            if (this.f41c != null) {
                this.f41c.m56a(pendingIntent);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public AMapLocation getLastKnownLocation(String str) {
        try {
            if (this.f41c == null) {
                return null;
            }
            if (LocationProviderProxy.AMapNetwork.equals(str)) {
                return this.f41c.m52a();
            }
            if (this.f40a == null) {
                return null;
            }
            Location lastKnownLocation = this.f40a.getLastKnownLocation(str);
            if (lastKnownLocation != null) {
                return new AMapLocation(lastKnownLocation);
            }
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void setGpsEnable(boolean z) {
        try {
            if (this.f41c != null) {
                this.f41c.m58a(z);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void m34a(String str, long j, float f, AMapLocationListener aMapLocationListener, boolean z) {
        try {
            String str2;
            if (this.f41c == null) {
                this.f41c = new C0182a(this.f42d.getApplicationContext(), this.f40a);
            }
            if (str == null) {
                str2 = LocationProviderProxy.AMapNetwork;
            } else {
                str2 = str;
            }
            if (LocationProviderProxy.AMapNetwork.equals(str2)) {
                this.f41c.m55a(j, f, aMapLocationListener, LocationProviderProxy.AMapNetwork, z);
            } else if ("gps".equals(str2)) {
                this.f41c.m55a(j, f, aMapLocationListener, "gps", z);
            } else {
                Looper mainLooper = this.f42d.getMainLooper();
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                this.f47i.add(new C0195g(j, f, aMapLocationListener, str2, false));
                this.f40a.requestLocationUpdates(str2, j, f, this.f49k, mainLooper);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Deprecated
    public void requestLocationUpdates(String str, long j, float f, AMapLocationListener aMapLocationListener) {
        m34a(str, j, f, aMapLocationListener, false);
    }

    public void requestLocationData(String str, long j, float f, AMapLocationListener aMapLocationListener) {
        m34a(str, j, f, aMapLocationListener, true);
    }

    public void removeUpdates(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener != null) {
            try {
                if (this.f41c != null) {
                    this.f41c.m57a(aMapLocationListener);
                }
                this.f40a.removeUpdates(aMapLocationListener);
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        if (this.f47i != null && this.f47i.size() > 0) {
            int size = this.f47i.size();
            int i = 0;
            while (i < size) {
                int i2;
                C0195g c0195g = (C0195g) this.f47i.get(i);
                if (aMapLocationListener.equals(c0195g.f122b)) {
                    this.f47i.remove(c0195g);
                    i2 = i - 1;
                    i = size - 1;
                } else {
                    i2 = i;
                    i = size;
                }
                size = i;
                i = i2 + 1;
            }
            if (this.f47i.size() == 0 && this.f49k != null) {
                this.f40a.removeUpdates(this.f49k);
            }
        }
    }

    public void requestLocationUpdates(String str, long j, float f, PendingIntent pendingIntent) {
        try {
            if (LocationProviderProxy.AMapNetwork.equals(str)) {
                if (this.f43e == null) {
                    this.f43e = new C1584f(this);
                }
                if (this.f44f == null) {
                    this.f44f = new C1582b(this);
                }
                this.f43e.m3968a(this.f44f, j, f, str);
                this.f45g.add(pendingIntent);
                return;
            }
            this.f45g.add(pendingIntent);
            this.f40a.requestLocationUpdates(str, j, f, pendingIntent);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void removeUpdates(PendingIntent pendingIntent) {
        try {
            if (this.f43e != null) {
                this.f45g.remove(pendingIntent);
                if (this.f45g.size() == 0) {
                    this.f43e.m3967a();
                }
            }
            this.f43e = null;
            this.f40a.removeUpdates(pendingIntent);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public List<String> getAllProviders() {
        try {
            List<String> allProviders = this.f40a.getAllProviders();
            if (allProviders == null) {
                allProviders = new ArrayList();
                allProviders.add(LocationProviderProxy.AMapNetwork);
                allProviders.addAll(this.f40a.getAllProviders());
                return allProviders;
            } else if (allProviders.contains(LocationProviderProxy.AMapNetwork)) {
                return allProviders;
            } else {
                allProviders.add(LocationProviderProxy.AMapNetwork);
                return allProviders;
            }
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public List<String> getProviders(boolean z) {
        try {
            List<String> providers = this.f40a.getProviders(z);
            if (!isProviderEnabled(LocationProviderProxy.AMapNetwork)) {
                return providers;
            }
            if (providers == null || providers.size() == 0) {
                providers = new ArrayList();
            }
            providers.add(LocationProviderProxy.AMapNetwork);
            return providers;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public List<String> getProviders(Criteria criteria, boolean z) {
        try {
            List<String> providers = this.f40a.getProviders(criteria, z);
            if (providers == null || providers.size() == 0) {
                providers = new ArrayList();
            }
            if (!LocationProviderProxy.AMapNetwork.equals(getBestProvider(criteria, z))) {
                return providers;
            }
            providers.add(LocationProviderProxy.AMapNetwork);
            return providers;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public String getBestProvider(Criteria criteria, boolean z) {
        try {
            String str = LocationProviderProxy.AMapNetwork;
            if (criteria == null) {
                return str;
            }
            if (!getProvider(LocationProviderProxy.AMapNetwork).meetsCriteria(criteria)) {
                str = this.f40a.getBestProvider(criteria, z);
            }
            if (!z || C0189d.m108a(this.f42d)) {
                return str;
            }
            return this.f40a.getBestProvider(criteria, z);
        } catch (Throwable th) {
            th.printStackTrace();
            return "gps";
        }
    }

    public boolean isProviderEnabled(String str) {
        try {
            if (LocationProviderProxy.AMapNetwork.equals(str)) {
                return C0189d.m108a(this.f42d);
            }
            return this.f40a.isProviderEnabled(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public LocationProviderProxy getProvider(String str) {
        if (str == null) {
            try {
                throw new IllegalArgumentException("name不能为空！");
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        } else if (this.f46h.containsKey(str)) {
            return (LocationProviderProxy) this.f46h.get(str);
        } else {
            LocationProviderProxy a = LocationProviderProxy.m41a(this.f40a, str);
            this.f46h.put(str, a);
            return a;
        }
    }

    public GpsStatus getGpsStatus(GpsStatus gpsStatus) {
        GpsStatus gpsStatus2 = null;
        try {
            if (this.f40a != null) {
                gpsStatus2 = this.f40a.getGpsStatus(gpsStatus);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return gpsStatus2;
    }

    public void removeGpsStatusListener(Listener listener) {
        try {
            if (this.f40a != null) {
                this.f40a.removeGpsStatusListener(listener);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean addGpsStatusListener(Listener listener) {
        try {
            if (this.f40a != null) {
                return this.f40a.addGpsStatusListener(listener);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return false;
    }

    public void addTestProvider(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, int i2) {
        try {
            if (this.f40a != null) {
                this.f40a.addTestProvider(str, z, z2, z3, z4, z5, z6, z7, i, i2);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setTestProviderEnabled(String str, boolean z) {
        try {
            if (this.f40a != null) {
                this.f40a.setTestProviderEnabled(str, z);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setTestProviderLocation(String str, Location location) {
        try {
            if (this.f40a != null) {
                this.f40a.setTestProviderLocation(str, location);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setTestProviderStatus(String str, int i, Bundle bundle, long j) {
        try {
            if (this.f40a != null) {
                this.f40a.setTestProviderStatus(str, i, bundle, j);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void clearTestProviderEnabled(String str) {
        try {
            if (this.f40a != null) {
                this.f40a.clearTestProviderEnabled(str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void clearTestProviderLocation(String str) {
        try {
            if (this.f40a != null) {
                this.f40a.clearTestProviderLocation(str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void clearTestProviderStatus(String str) {
        try {
            if (this.f40a != null) {
                this.f40a.clearTestProviderStatus(str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void requestWeatherUpdates(int i, AMapLocalWeatherListener aMapLocalWeatherListener) {
        try {
            this.f41c.m54a(i, aMapLocalWeatherListener);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Deprecated
    public void destory() {
        try {
            destroy();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (this.f41c != null) {
                this.f41c.m59b();
            }
            if (this.f46h != null) {
                this.f46h.clear();
            }
            if (this.f47i != null) {
                this.f47i.clear();
            }
            if (this.f40a != null) {
                if (this.f49k != null) {
                    this.f40a.removeUpdates(this.f49k);
                }
                if (this.f45g != null) {
                    for (int i = 0; i < this.f45g.size(); i++) {
                        PendingIntent pendingIntent = (PendingIntent) this.f45g.get(i);
                        if (pendingIntent != null) {
                            this.f40a.removeUpdates(pendingIntent);
                        }
                    }
                }
            }
            if (this.f45g != null) {
                this.f45g.clear();
            }
            this.f41c = null;
            m32a();
            this.f49k = null;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String getVersion() {
        try {
            return "V1.3.0";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
