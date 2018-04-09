package com.amap.api.mapcore.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.RemoteException;
import android.view.WindowManager;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.autonavi.amap.mapcore.interfaces.CameraUpdateFactoryDelegate;
import com.autonavi.amap.mapcore.interfaces.IAMapDelegate;

/* compiled from: SensorEventHelper */
public class as implements SensorEventListener {
    private SensorManager f179a;
    private Sensor f180b;
    private long f181c = 0;
    private final int f182d = 100;
    private float f183e;
    private Context f184f;
    private IAMapDelegate f185g;
    private Marker f186h;

    public as(Context context, IAMapDelegate iAMapDelegate) {
        this.f184f = context;
        this.f185g = iAMapDelegate;
        this.f179a = (SensorManager) context.getSystemService("sensor");
        this.f180b = this.f179a.getDefaultSensor(3);
    }

    public void m209a() {
        this.f179a.registerListener(this, this.f180b, 3);
    }

    public void m211b() {
        this.f179a.unregisterListener(this, this.f180b);
    }

    public void m210a(Marker marker) {
        this.f186h = marker;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (System.currentTimeMillis() - this.f181c >= 100 && this.f185g.getCameraAnimator().isFinished()) {
            switch (sensorEvent.sensor.getType()) {
                case 3:
                    float a = (sensorEvent.values[0] + ((float) m208a(this.f184f))) % 360.0f;
                    if (a > BitmapDescriptorFactory.HUE_CYAN) {
                        a -= 360.0f;
                    } else if (a < -180.0f) {
                        a += 360.0f;
                    }
                    if (Math.abs(this.f183e - a) >= 3.0f) {
                        if (Float.isNaN(a)) {
                            a = 0.0f;
                        }
                        this.f183e = a;
                        if (this.f186h != null) {
                            try {
                                this.f185g.moveCamera(CameraUpdateFactoryDelegate.changeBearing(this.f183e));
                                this.f186h.setRotateAngle(-this.f183e);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        this.f181c = System.currentTimeMillis();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public static int m208a(Context context) {
        switch (((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return -90;
            default:
                return 0;
        }
    }
}
