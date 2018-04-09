package com.baidu.location.p008c;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

class C0402f implements SensorEventListener {
    final /* synthetic */ C0401e f553a;

    C0402f(C0401e c0401e) {
        this.f553a = c0401e;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr;
        switch (sensorEvent.sensor.getType()) {
            case 1:
                fArr = (float[]) sensorEvent.values.clone();
                this.f553a.f539m = (float[]) fArr.clone();
                fArr = this.f553a.m600a(fArr[0], fArr[1], fArr[2]);
                if (C0401e.m595a(this.f553a) >= 20) {
                    double d = (double) ((fArr[2] * fArr[2]) + ((fArr[0] * fArr[0]) + (fArr[1] * fArr[1])));
                    if (this.f553a.f536j == 0) {
                        if (d > 4.0d) {
                            this.f553a.f536j = 1;
                            return;
                        }
                        return;
                    } else if (d < 0.009999999776482582d) {
                        this.f553a.f536j = 0;
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case 3:
                fArr = (float[]) sensorEvent.values.clone();
                this.f553a.f525L[this.f553a.f524K] = (double) fArr[0];
                this.f553a.f524K = this.f553a.f524K + 1;
                if (this.f553a.f524K == this.f553a.f523J) {
                    this.f553a.f524K = 0;
                }
                if (C0401e.m611g(this.f553a) >= 20) {
                    this.f553a.f526M = this.f553a.m610f();
                    if (!this.f553a.f526M) {
                        this.f553a.f530d.unregisterListener(this.f553a.f528b, this.f553a.f534h);
                    }
                    this.f553a.f540n[0] = this.f553a.m592a(this.f553a.f540n[0], (double) fArr[0], 0.7d);
                    this.f553a.f540n[1] = (double) fArr[1];
                    this.f553a.f540n[2] = (double) fArr[2];
                    return;
                }
                return;
            default:
                return;
        }
    }
}
