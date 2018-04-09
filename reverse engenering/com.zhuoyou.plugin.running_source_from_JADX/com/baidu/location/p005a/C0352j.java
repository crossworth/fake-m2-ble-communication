package com.baidu.location.p005a;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.baidu.location.C0455f;
import com.baidu.location.p013g.C0457a;

public class C0352j implements SensorEventListener {
    private static C0352j f271d;
    private float[] f272a;
    private float[] f273b;
    private SensorManager f274c;
    private float f275e;
    private double f276f = Double.MIN_VALUE;
    private boolean f277g = false;
    private boolean f278h = false;
    private boolean f279i = false;
    private float f280j = 0.0f;
    private long f281k = 0;
    private boolean f282l = false;
    private long f283m = 0;

    private C0352j() {
        try {
            if (this.f274c == null) {
                this.f274c = (SensorManager) C0455f.getServiceContext().getSystemService("sensor");
            }
            if (this.f274c.getDefaultSensor(6) != null) {
                this.f279i = true;
            }
        } catch (Exception e) {
            this.f279i = false;
        }
    }

    public static synchronized C0352j m308a() {
        C0352j c0352j;
        synchronized (C0352j.class) {
            if (f271d == null) {
                f271d = new C0352j();
            }
            c0352j = f271d;
        }
        return c0352j;
    }

    private void m310k() {
        if (this.f274c != null) {
            Sensor defaultSensor = this.f274c.getDefaultSensor(6);
            if (defaultSensor != null) {
                this.f274c.registerListener(f271d, defaultSensor, 3);
            }
            C0457a.m968a().postDelayed(new C0353k(this), 2000);
        }
    }

    public void m311a(boolean z) {
        this.f277g = z;
    }

    public synchronized void m312b() {
        if (!this.f282l) {
            if (this.f277g || this.f278h) {
                if (this.f274c == null) {
                    this.f274c = (SensorManager) C0455f.getServiceContext().getSystemService("sensor");
                }
                if (this.f274c != null) {
                    Sensor defaultSensor = this.f274c.getDefaultSensor(11);
                    if (defaultSensor != null && this.f277g) {
                        this.f274c.registerListener(this, defaultSensor, 3);
                    }
                    defaultSensor = this.f274c.getDefaultSensor(6);
                    if (defaultSensor != null && this.f278h) {
                        this.f274c.registerListener(this, defaultSensor, 3);
                    }
                }
                this.f282l = true;
            }
        }
    }

    public void m313b(boolean z) {
    }

    public synchronized void m314c() {
        if (this.f282l) {
            if (this.f274c != null) {
                this.f274c.unregisterListener(this);
                this.f274c = null;
            }
            this.f282l = false;
            this.f280j = 0.0f;
        }
    }

    public void m315d() {
        if (!this.f278h && this.f279i && System.currentTimeMillis() - this.f283m > 60000) {
            this.f283m = System.currentTimeMillis();
            m310k();
        }
    }

    public float m316e() {
        return (!this.f279i || this.f281k <= 0 || Math.abs(System.currentTimeMillis() - this.f281k) >= 5000 || this.f280j <= 0.0f) ? 0.0f : this.f280j;
    }

    public boolean m317f() {
        return this.f277g;
    }

    public boolean m318g() {
        return this.f278h;
    }

    public float m319h() {
        return this.f275e;
    }

    public double m320i() {
        return this.f276f;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @SuppressLint({"NewApi"})
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case 6:
                try {
                    this.f273b = (float[]) sensorEvent.values.clone();
                    this.f280j = this.f273b[0];
                    this.f281k = System.currentTimeMillis();
                    this.f276f = (double) SensorManager.getAltitude(1013.25f, this.f273b[0]);
                    return;
                } catch (Exception e) {
                    return;
                }
            case 11:
                this.f272a = (float[]) sensorEvent.values.clone();
                if (this.f272a != null) {
                    float[] fArr = new float[9];
                    try {
                        SensorManager.getRotationMatrixFromVector(fArr, this.f272a);
                        float[] fArr2 = new float[3];
                        SensorManager.getOrientation(fArr, fArr2);
                        this.f275e = (float) Math.toDegrees((double) fArr2[0]);
                        this.f275e = (float) Math.floor(this.f275e >= 0.0f ? (double) this.f275e : (double) (this.f275e + 360.0f));
                        return;
                    } catch (Exception e2) {
                        this.f275e = 0.0f;
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }
}
