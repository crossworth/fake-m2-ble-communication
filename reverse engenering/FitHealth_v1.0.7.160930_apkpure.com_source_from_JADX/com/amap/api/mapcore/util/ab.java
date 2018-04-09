package com.amap.api.mapcore.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.model.CameraPosition;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IMapFragmentDelegate;
import com.autonavi.amap.mapcore.interfaces.IUiSettingsDelegate;
import com.umeng.socialize.common.SocializeConstants;
import p031u.aly.C1507j;

/* compiled from: MapFragmentDelegateImp */
public class ab implements IMapFragmentDelegate {
    public static volatile Context f3938a;
    public static int f3939c = 0;
    public static int f3940d = 1;
    public int f3941b = 0;
    private IAMapDelegate f3942e;
    private int f3943f = 0;
    private AMapOptions f3944g;

    public ab(int i) {
        int i2 = 0;
        if (i > 0) {
            i2 = 1;
        }
        this.f3943f = i2;
    }

    public void setContext(Context context) {
        if (context != null) {
            f3938a = context.getApplicationContext();
        }
    }

    public void setOptions(AMapOptions aMapOptions) {
        this.f3944g = aMapOptions;
    }

    public IAMapDelegate getMap() throws RemoteException {
        if (this.f3942e == null) {
            if (f3938a == null) {
                throw new NullPointerException("Context 为 null 请在地图调用之前 使用 MapsInitializer.initialize(Context paramContext) 来设置Context");
            }
            int i = f3938a.getResources().getDisplayMetrics().densityDpi;
            if (i <= 120) {
                C0273r.f694a = 0.5f;
            } else if (i <= C1507j.f3829b) {
                C0273r.f694a = 0.8f;
            } else if (i <= SocializeConstants.MASK_USER_CENTER_HIDE_AREA) {
                C0273r.f694a = 0.87f;
            } else if (i <= 320) {
                C0273r.f694a = 1.0f;
            } else if (i <= 480) {
                C0273r.f694a = 1.5f;
            } else if (i <= 640) {
                C0273r.f694a = 1.8f;
            } else {
                C0273r.f694a = 0.9f;
            }
            if (this.f3943f == f3939c) {
                this.f3942e = new C1604k(f3938a).m4302a();
            } else {
                this.f3942e = new C1605l(f3938a).m4303a();
            }
        }
        return this.f3942e;
    }

    public void onInflate(Activity activity, AMapOptions aMapOptions, Bundle bundle) throws RemoteException {
        f3938a = activity.getApplicationContext();
        this.f3944g = aMapOptions;
    }

    public void onCreate(Bundle bundle) throws RemoteException {
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws RemoteException {
        if (this.f3942e == null) {
            if (f3938a == null && layoutInflater != null) {
                f3938a = layoutInflater.getContext().getApplicationContext();
            }
            if (f3938a == null) {
                throw new NullPointerException("Context 为 null 请在地图调用之前 使用 MapsInitializer.initialize(Context paramContext) 来设置Context");
            }
            int i = f3938a.getResources().getDisplayMetrics().densityDpi;
            if (i <= 120) {
                C0273r.f694a = 0.5f;
            } else if (i <= C1507j.f3829b) {
                C0273r.f694a = 0.6f;
            } else if (i <= SocializeConstants.MASK_USER_CENTER_HIDE_AREA) {
                C0273r.f694a = 0.87f;
            } else if (i <= 320) {
                C0273r.f694a = 1.0f;
            } else if (i <= 480) {
                C0273r.f694a = 1.5f;
            } else if (i <= 640) {
                C0273r.f694a = 1.8f;
            } else {
                C0273r.f694a = 0.9f;
            }
            if (this.f3943f == f3939c) {
                this.f3942e = new C1604k(f3938a).m4302a();
            } else {
                this.f3942e = new C1605l(f3938a).m4303a();
            }
            this.f3942e.setVisibilityEx(this.f3941b);
        }
        try {
            if (this.f3944g == null && bundle != null) {
                byte[] byteArray = bundle.getByteArray("MapOptions");
                if (byteArray != null) {
                    Parcel obtain = Parcel.obtain();
                    obtain.unmarshall(byteArray, 0, byteArray.length);
                    obtain.setDataPosition(0);
                    this.f3944g = AMapOptions.CREATOR.createFromParcel(obtain);
                }
            }
            m3969a(this.f3944g);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return this.f3942e.getView();
    }

    void m3969a(AMapOptions aMapOptions) throws RemoteException {
        if (aMapOptions != null && this.f3942e != null) {
            CameraPosition camera = aMapOptions.getCamera();
            if (camera != null) {
                this.f3942e.moveCamera(CameraUpdateFactoryDelegate.newCamera(camera.target, camera.zoom, camera.bearing, camera.tilt));
            }
            IUiSettingsDelegate uiSettings = this.f3942e.getUiSettings();
            uiSettings.setRotateGesturesEnabled(aMapOptions.getRotateGesturesEnabled().booleanValue());
            uiSettings.setScrollGesturesEnabled(aMapOptions.getScrollGesturesEnabled().booleanValue());
            uiSettings.setTiltGesturesEnabled(aMapOptions.getTiltGesturesEnabled().booleanValue());
            uiSettings.setZoomControlsEnabled(aMapOptions.getZoomControlsEnabled().booleanValue());
            uiSettings.setZoomGesturesEnabled(aMapOptions.getZoomGesturesEnabled().booleanValue());
            uiSettings.setCompassEnabled(aMapOptions.getCompassEnabled().booleanValue());
            uiSettings.setScaleControlsEnabled(aMapOptions.getScaleControlsEnabled().booleanValue());
            uiSettings.setLogoPosition(aMapOptions.getLogoPosition());
            this.f3942e.setMapType(aMapOptions.getMapType());
            this.f3942e.setZOrderOnTop(aMapOptions.getZOrderOnTop().booleanValue());
        }
    }

    public void onResume() throws RemoteException {
        if (this.f3942e != null) {
            this.f3942e.onActivityResume();
        }
    }

    public void onPause() throws RemoteException {
        if (this.f3942e != null) {
            this.f3942e.onActivityPause();
        }
    }

    public void onDestroyView() throws RemoteException {
    }

    public void onDestroy() throws RemoteException {
        if (this.f3942e != null) {
            this.f3942e.clear();
            this.f3942e.destroy();
            this.f3942e = null;
        }
    }

    public void onLowMemory() throws RemoteException {
        Log.d("onLowMemory", "onLowMemory run");
    }

    public void onSaveInstanceState(Bundle bundle) throws RemoteException {
        if (this.f3942e != null) {
            if (this.f3944g == null) {
                this.f3944g = new AMapOptions();
            }
            try {
                Parcel obtain = Parcel.obtain();
                this.f3944g = this.f3944g.camera(getMap().getCameraPositionPrj(false));
                this.f3944g.writeToParcel(obtain, 0);
                bundle.putByteArray("MapOptions", obtain.marshall());
            } catch (Throwable th) {
            }
        }
    }

    public boolean isReady() throws RemoteException {
        return false;
    }

    public void setVisibility(int i) {
        this.f3941b = i;
        if (this.f3942e != null) {
            this.f3942e.setVisibilityEx(i);
        }
    }
}
