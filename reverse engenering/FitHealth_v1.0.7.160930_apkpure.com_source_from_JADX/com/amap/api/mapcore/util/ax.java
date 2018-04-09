package com.amap.api.mapcore.util;

import android.os.Handler;
import android.os.RemoteException;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;
import com.autonavi.amap.mapcore.interfaces.IUiSettingsDelegate;

/* compiled from: UiSettingsDelegateImp */
class ax implements IUiSettingsDelegate {
    final Handler f4026a = new ay(this);
    private IAMapDelegate f4027b;
    private boolean f4028c = true;
    private boolean f4029d = true;
    private boolean f4030e = true;
    private boolean f4031f = false;
    private boolean f4032g = true;
    private boolean f4033h = true;
    private boolean f4034i = true;
    private boolean f4035j = false;
    private int f4036k = 0;
    private int f4037l = 1;
    private boolean f4038m = true;

    ax(IAMapDelegate iAMapDelegate) {
        this.f4027b = iAMapDelegate;
    }

    public boolean isIndoorSwitchEnabled() throws RemoteException {
        return this.f4038m;
    }

    public void setIndoorSwitchEnabled(boolean z) throws RemoteException {
        this.f4038m = z;
        this.f4026a.obtainMessage(4).sendToTarget();
    }

    public void setScaleControlsEnabled(boolean z) throws RemoteException {
        this.f4035j = z;
        this.f4026a.obtainMessage(1).sendToTarget();
    }

    public void setZoomControlsEnabled(boolean z) throws RemoteException {
        this.f4033h = z;
        this.f4026a.obtainMessage(0).sendToTarget();
    }

    public void setCompassEnabled(boolean z) throws RemoteException {
        this.f4034i = z;
        this.f4026a.obtainMessage(2).sendToTarget();
    }

    public void setMyLocationButtonEnabled(boolean z) throws RemoteException {
        this.f4031f = z;
        this.f4026a.obtainMessage(3).sendToTarget();
    }

    public void setScrollGesturesEnabled(boolean z) throws RemoteException {
        this.f4029d = z;
    }

    public void setZoomGesturesEnabled(boolean z) throws RemoteException {
        this.f4032g = z;
    }

    public void setTiltGesturesEnabled(boolean z) throws RemoteException {
        this.f4030e = z;
    }

    public void setRotateGesturesEnabled(boolean z) throws RemoteException {
        this.f4028c = z;
    }

    public void setAllGesturesEnabled(boolean z) throws RemoteException {
        setRotateGesturesEnabled(z);
        setTiltGesturesEnabled(z);
        setZoomGesturesEnabled(z);
        setScrollGesturesEnabled(z);
    }

    public void setLogoPosition(int i) throws RemoteException {
        this.f4036k = i;
        this.f4027b.setLogoPosition(i);
    }

    public void setZoomPosition(int i) throws RemoteException {
        this.f4037l = i;
        this.f4027b.setZoomPosition(i);
    }

    public boolean isScaleControlsEnabled() throws RemoteException {
        return this.f4035j;
    }

    public boolean isZoomControlsEnabled() throws RemoteException {
        return this.f4033h;
    }

    public boolean isCompassEnabled() throws RemoteException {
        return this.f4034i;
    }

    public boolean isMyLocationButtonEnabled() throws RemoteException {
        return this.f4031f;
    }

    public boolean isScrollGesturesEnabled() throws RemoteException {
        return this.f4029d;
    }

    public boolean isZoomGesturesEnabled() throws RemoteException {
        return this.f4032g;
    }

    public boolean isTiltGesturesEnabled() throws RemoteException {
        return this.f4030e;
    }

    public boolean isRotateGesturesEnabled() throws RemoteException {
        return this.f4028c;
    }

    public int getLogoPosition() throws RemoteException {
        return this.f4036k;
    }

    public int getZoomPosition() throws RemoteException {
        return this.f4037l;
    }
}
