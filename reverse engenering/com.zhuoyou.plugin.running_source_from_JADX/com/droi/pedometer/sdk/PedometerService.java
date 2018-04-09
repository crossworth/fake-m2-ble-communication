package com.droi.pedometer.sdk;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.database.SportHelper;

public class PedometerService extends Service implements SensorEventListener, IPedListener {
    private static final int DELAY = 20000;
    private static final int SLEEP_TIMES = 1500;
    private static final String TAG = "PedometerService";
    private static final int TIMES = 1;
    public static boolean ifmove = true;
    private int count_num = 0;
    private Handler handler = new Handler();
    private boolean ifHaveStepSensor;
    private Runnable ifReMoveThread = new C07501();
    private PedBinder mBinder;
    private ICallback mCallback;
    private Context mCtx = TheApp.getInstance();
    private PedProcessor mPedProcessor;
    private PowerManager mPowerManager;
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private WakeLock mWakeLock = null;
    private float mX = 0.0f;
    private float mY = 0.0f;
    private float mZ = 0.0f;
    private int not_move_num = 0;
    private int state = 0;
    private int step_more;
    private int step_sensor;
    private int steps = 0;

    class C07501 implements Runnable {
        C07501() {
        }

        public void run() {
            PedometerService.this.count_num = 0;
        }
    }

    interface ICallback {
        void onStateChanged(int i);

        void onStepDetected(int i);
    }

    class PedBinder extends Binder {
        PedBinder() {
        }

        public Service getService() {
            return PedometerService.this;
        }
    }

    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "---PedService onBind---");
        return this.mBinder;
    }

    public void onCreate() {
        super.onCreate();
        this.mBinder = new PedBinder();
        this.mPowerManager = (PowerManager) getSystemService("power");
        this.mWakeLock = this.mPowerManager.newWakeLock(1, PedometerService.class.getName());
        this.mWakeLock.acquire();
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.mSensor = this.mSensorManager.getDefaultSensor(19);
        if (this.mSensor == null) {
            this.mSensor = this.mSensorManager.getDefaultSensor(1);
            this.ifHaveStepSensor = false;
        } else {
            this.mWakeLock.release();
            this.mWakeLock = null;
            this.ifHaveStepSensor = true;
        }
        this.mSensorManager.registerListener(this, this.mSensor, 27000);
        Log.d(TAG, "++ onCreate()++");
        if (!this.ifHaveStepSensor) {
            this.mPedProcessor = new PedProcessor();
            this.mPedProcessor.registerListener(this);
            this.mPedProcessor.open();
        }
        Log.i(TAG, "---PedService onCreate--- ifHaveStepSensor:" + this.ifHaveStepSensor);
    }

    public void onStepDetected(int count, int state) {
        Log.i(TAG, "onStepDetected by ped:" + count);
        SportHelper.updateTodayStepByPed(count);
    }

    public void onStateChanged(int state) {
    }

    public void onCadenceChanged(float newCadence) {
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.ifHaveStepSensor) {
            this.handler.removeCallbacks(this.ifReMoveThread);
            if (this.step_more == 0) {
                this.step_more = (int) event.values[0];
                return;
            }
            int count = ((int) event.values[0]) - this.step_more;
            this.count_num += count;
            if (this.count_num >= 10) {
                SportHelper.updateTodayStepByPed(this.count_num);
                this.count_num = Integer.MIN_VALUE;
            } else if (this.count_num < 0) {
                SportHelper.updateTodayStepByPed(count);
            }
            this.step_more = (int) event.values[0];
            this.handler.postDelayed(this.ifReMoveThread, 5000);
            return;
        }
        juggle_move(event);
        if (ifmove) {
            this.mPedProcessor.detectStep(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    public void reset() {
        this.steps = 0;
        if (this.mPedProcessor.isOpen()) {
            this.mPedProcessor.close();
        }
        this.mPedProcessor.open();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, 1, startId);
    }

    public void onDestroy() {
        Log.i(TAG, "---PedService onDestroy---");
        super.onDestroy();
        Log.d(TAG, "++ onDestroy()++");
        if (this.mWakeLock != null) {
            Log.d(TAG, "wakeLock release");
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        if (this.mPedProcessor != null) {
            this.mPedProcessor.unregisterListener(this);
            this.mPedProcessor.close();
            this.mPedProcessor = null;
        }
        this.mSensorManager.unregisterListener(this);
    }

    private void juggle_move(SensorEvent event) {
        if (this.mX == 0.0f && this.mY == 0.0f && this.mZ == 0.0f) {
            this.mX = event.values[0];
            this.mY = event.values[1];
            this.mZ = event.values[2];
        } else if (Math.abs(this.mX - event.values[0]) > 0.5f || Math.abs(this.mY - event.values[1]) > 0.5f || Math.abs(this.mZ - event.values[2]) > 0.5f) {
            ifmove = true;
            this.not_move_num = 0;
            this.mX = event.values[0];
            this.mY = event.values[1];
            this.mZ = event.values[2];
        } else if (this.not_move_num >= SLEEP_TIMES) {
            ifmove = false;
            if (!this.mPowerManager.isScreenOn()) {
                stopSelf();
            }
        } else {
            this.not_move_num++;
        }
    }
}
