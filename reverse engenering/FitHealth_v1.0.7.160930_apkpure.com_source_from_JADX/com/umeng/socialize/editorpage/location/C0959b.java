package com.umeng.socialize.editorpage.location;

import android.location.Location;
import android.os.AsyncTask;
import com.amap.api.location.LocationManagerProxy;
import com.umeng.socialize.utils.Log;

/* compiled from: GetLocationTask */
public class C0959b extends AsyncTask<Void, Void, Location> {
    private static final String f3304b = "Location";
    private C1811a f3305a;

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m3218a((Void[]) objArr);
    }

    public C0959b(C1811a c1811a) {
        this.f3305a = c1811a;
    }

    protected Location m3218a(Void... voidArr) {
        int i = 0;
        while (i < 20) {
            try {
                if (isCancelled()) {
                    return null;
                }
                Location a = m3217a();
                if (a != null) {
                    return a;
                }
                Thread.sleep(500);
                i++;
            } catch (InterruptedException e) {
                return null;
            }
        }
        return null;
    }

    private Location m3217a() {
        Location b = this.f3305a.mo2174b();
        if (b != null) {
            return b;
        }
        Log.m3248d("Location", "Fetch gps info from default failed,then fetch form network..");
        this.f3305a.m4996a(LocationManagerProxy.NETWORK_PROVIDER);
        b = this.f3305a.mo2174b();
        this.f3305a.m4996a(null);
        return b;
    }
}
