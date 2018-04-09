package com.baidu.location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.Timer;
import java.util.TimerTask;

public class aa implements C1619j, an {
    public static final float fK = 0.01f;
    private static final int fP = 20;
    private static final float fU = 0.8f;
    public static final float fV = 4.0f;
    private int fG;
    private int fH;
    private final long fI;
    private float[] fJ;
    public SensorEventListener fL;
    Timer fM;
    private int fN;
    private Sensor fO;
    private float[] fQ;
    private volatile int fR;
    private int fS;
    private double[] fT;
    private int fW;
    private SensorManager fX;
    private boolean fY;

    class C04981 implements SensorEventListener {
        final /* synthetic */ aa f2140a;

        C04981(aa aaVar) {
            this.f2140a = aaVar;
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()) {
                case 1:
                    float[] fArr = (float[]) sensorEvent.values.clone();
                    this.f2140a.fQ = (float[]) fArr.clone();
                    fArr = this.f2140a.m5855if(fArr[0], fArr[1], fArr[2]);
                    if (aa.m5850do(this.f2140a) >= 20) {
                        double d = (double) ((fArr[2] * fArr[2]) + ((fArr[0] * fArr[0]) + (fArr[1] * fArr[1])));
                        if (this.f2140a.fR == 0) {
                            if (d > 4.0d) {
                                this.f2140a.fR = 1;
                                return;
                            }
                            return;
                        } else if (d < 0.009999999776482582d) {
                            this.f2140a.fR = 0;
                            return;
                        } else {
                            return;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    class C04992 extends TimerTask {
        final /* synthetic */ aa f2141a;

        C04992(aa aaVar) {
            this.f2141a = aaVar;
        }

        public void run() {
            this.f2141a.a1();
        }
    }

    public aa(Context context) {
        this(context, 0);
    }

    private aa(Context context, int i) {
        this.fI = 30;
        this.fR = 0;
        this.fH = 1;
        this.fJ = new float[3];
        this.fQ = new float[]{0.0f, 0.0f, 0.0f};
        this.fS = 31;
        this.fT = new double[this.fS];
        this.fW = 0;
        this.fL = new C04981(this);
        try {
            this.fX = (SensorManager) context.getSystemService("sensor");
            this.fN = i;
            this.fO = this.fX.getDefaultSensor(1);
        } catch (Exception e) {
        }
    }

    private void a1() {
        Object obj = new float[3];
        System.arraycopy(this.fQ, 0, obj, 0, 3);
        this.fT[this.fW] = Math.sqrt((double) ((obj[2] * obj[2]) + ((obj[0] * obj[0]) + (obj[1] * obj[1]))));
        this.fW++;
        if (this.fW == this.fS) {
            this.fW = 0;
            double d = m5852if(this.fT);
            if (this.fR != 0 || d >= 0.3d) {
                m5858try(1);
                this.fR = 1;
                return;
            }
            m5858try(0);
            this.fR = 0;
        }
    }

    static /* synthetic */ int m5850do(aa aaVar) {
        int i = aaVar.fG + 1;
        aaVar.fG = i;
        return i;
    }

    private double m5852if(double[] dArr) {
        int i = 0;
        double d = 0.0d;
        double d2 = 0.0d;
        for (double d3 : dArr) {
            d2 += d3;
        }
        d2 /= (double) r6;
        while (i < r6) {
            d += (dArr[i] - d2) * (dArr[i] - d2);
            i++;
        }
        return d / ((double) (r6 - 1));
    }

    private float[] m5855if(float f, float f2, float f3) {
        float[] fArr = new float[]{(this.fJ[0] * fU) + (0.19999999f * f), (this.fJ[1] * fU) + (0.19999999f * f2), (this.fJ[2] * fU) + (0.19999999f * f3)};
        fArr[0] = f - this.fJ[0];
        fArr[1] = f2 - this.fJ[1];
        fArr[2] = f3 - this.fJ[2];
        return fArr;
    }

    private synchronized void m5858try(int i) {
        this.fH |= i;
    }

    public synchronized int a0() {
        return this.fG < 20 ? 1 : this.fH;
    }

    public void a2() {
        if (!this.fY && this.fO != null) {
            try {
                this.fX.registerListener(this.fL, this.fO, this.fN);
            } catch (Exception e) {
            }
            this.fM = new Timer("UpdateData", false);
            this.fM.schedule(new C04992(this), 500, 30);
            this.fY = true;
        }
    }

    public void a3() {
        if (this.fY) {
            try {
                this.fX.unregisterListener(this.fL);
            } catch (Exception e) {
            }
            this.fM.cancel();
            this.fM.purge();
            this.fM = null;
            this.fY = false;
        }
    }

    public synchronized void aZ() {
        this.fH = 0;
    }
}
