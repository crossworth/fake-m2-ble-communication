package com.mcube.lib.ped;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.util.Log;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.download.Util_update.TimeManager;
import com.zhuoyou.plugin.gps.ilistener.StepWatcher;
import com.zhuoyou.plugin.running.MainService;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.RunningApp;
import com.zhuoyou.plugin.running.RunningItem;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PedometerService extends Service {
    private static final int DELAY = 20000;
    private static final String FILENAME = "steps.txt";
    private static final long MeanTime = 5000000;
    private static final float NS2MS = 1.0E-6f;
    private static int PHONE_STEP = 0;
    private static final int SLEEP_TIMES = 15000;
    private static final String STEPSFILE = "/data/data/com.zhuoyou.plugin.running/files/steps.txt";
    public static final String TAG = "PedometerService";
    private static final int TIMES = 1;
    private static Runnable checkDataBaseThread = new C06425();
    public static int hadRunStep;
    public static boolean ifmove = true;
    private static int mPwrState = 0;
    public static int start_step;
    boolean AccleListenerRegisted = false;
    private int count_num = 0;
    private int currentStepCount = 0;
    private int currentSteps = 0;
    private boolean flag = false;
    private Handler handler = new C06414();
    private boolean ifHaveStepSensor = false;
    private Runnable ifMoveThread = new C06402();
    private Runnable ifReMoveThread = new C06391();
    FileInputStream input = null;
    private long last_time;
    private Sensor mAccel;
    private AccelListener mAccelListener = new AccelListener();
    private BroadcastReceiver mAliveReceiver = new C06436();
    private final IBinder mBinder = new LocalBinder();
    private ContentResolver mContentResolver;
    private PedometerController mController = new PedometerController();
    private Context mCtx = RunningApp.getInstance();
    private DateChangeReceiver mDateChangeReceiver = new DateChangeReceiver();
    private PedLibrary mLibrary = new PedLibrary();
    private ArrayList<PedometerListener> mListeners = new ArrayList();
    private PedListenerImp mPedListener = new PedListenerImp();
    private PowerManager mPowerManager;
    private Long mPrevTime = Long.valueOf(0);
    private SensorManager mSensorManager = null;
    private WakeLock mWakeLock = null;
    private float mX = 0.0f;
    private float mY = 0.0f;
    private float mZ = 0.0f;
    private int not_move_num = 0;
    PrintStream out;
    FileOutputStream output = null;
    private PedometerListener pedListener = new C17063();
    private long start_time;
    private int stepCountOffset = 0;
    private int step_more = 0;
    private int step_sensor = 0;
    private int steps = 0;
    private int times = 0;
    private int total_step;

    class C06391 implements Runnable {
        C06391() {
        }

        public void run() {
            PedometerService.this.count_num = 0;
        }
    }

    class C06402 implements Runnable {
        C06402() {
        }

        public void run() {
            PedometerService.this.stopService(new Intent(PedometerService.this.getApplicationContext(), PedometerService.class));
        }
    }

    class C06414 extends Handler {
        C06414() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.i(PedometerService.TAG, "handleMessage 1");
                PedometerService.this.getPaceGroup(PedometerService.hadRunStep);
            }
        }
    }

    static class C06425 implements Runnable {
        C06425() {
        }

        public void run() {
            Log.i(PedometerService.TAG, "checkDataBaseThread");
            MainService.getInstance().checkDataBase();
        }
    }

    class C06436 extends BroadcastReceiver {
        C06436() {
        }

        public void onReceive(Context context, Intent intent) {
            PedometerService.this.sendBroadcast(new Intent("com.zhuoyou.steps.activity.hi"));
        }
    }

    class AccelListener implements SensorEventListener {
        PedometerController mPedometerController = new PedometerController();

        AccelListener() {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onSensorChanged(android.hardware.SensorEvent r19) {
            /*
            r18 = this;
            monitor-enter(r18);
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.ifHaveStepSensor;	 Catch:{ all -> 0x0119 }
            if (r3 == 0) goto L_0x0125;
        L_0x000b:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.handler;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r6 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = r6.ifReMoveThread;	 Catch:{ all -> 0x0119 }
            r3.removeCallbacks(r6);	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.step_more;	 Catch:{ all -> 0x0119 }
            if (r3 != 0) goto L_0x0039;
        L_0x0028:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r6 = r0.values;	 Catch:{ all -> 0x0119 }
            r7 = 0;
            r6 = r6[r7];	 Catch:{ all -> 0x0119 }
            r6 = (int) r6;	 Catch:{ all -> 0x0119 }
            r3.step_more = r6;	 Catch:{ all -> 0x0119 }
            monitor-exit(r18);	 Catch:{ all -> 0x0119 }
        L_0x0038:
            return;
        L_0x0039:
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 0;
            r3 = r3[r6];	 Catch:{ all -> 0x0119 }
            r3 = (int) r3;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r6 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = r6.step_more;	 Catch:{ all -> 0x0119 }
            r2 = r3 - r6;
            r3 = 10;
            if (r2 <= r3) goto L_0x011c;
        L_0x004f:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r6 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = r6.step_sensor;	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r7 = r0.values;	 Catch:{ all -> 0x0119 }
            r8 = 0;
            r7 = r7[r8];	 Catch:{ all -> 0x0119 }
            r7 = (int) r7;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r8 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r8 = r8.step_more;	 Catch:{ all -> 0x0119 }
            r7 = r7 - r8;
            r6 = r6 + r7;
            r3.step_sensor = r6;	 Catch:{ all -> 0x0119 }
        L_0x0070:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3.count_num = r3.count_num + 1;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.count_num;	 Catch:{ all -> 0x0119 }
            r6 = 10;
            if (r3 >= r6) goto L_0x009a;
        L_0x0083:
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 0;
            r3 = r3[r6];	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r6 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = r6.step_more;	 Catch:{ all -> 0x0119 }
            r6 = (float) r6;	 Catch:{ all -> 0x0119 }
            r3 = r3 - r6;
            r6 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
            r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1));
            if (r3 < 0) goto L_0x00f2;
        L_0x009a:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.step_sensor;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r6 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = r6.stepCountOffset;	 Catch:{ all -> 0x0119 }
            r17 = r3 + r6;
            r3 = "PedometerService";
            r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0119 }
            r6.<init>();	 Catch:{ all -> 0x0119 }
            r7 = "step_sensor:";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r7 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r7 = r7.step_sensor;	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r7 = " stepCountOffset:";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r7 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r7 = r7.stepCountOffset;	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r6 = r6.toString();	 Catch:{ all -> 0x0119 }
            android.util.Log.i(r3, r6);	 Catch:{ all -> 0x0119 }
            com.mcube.lib.ped.PedometerService.hadRunStep = r17;	 Catch:{ all -> 0x0119 }
            r3 = com.zhuoyou.plugin.gps.ilistener.StepWatcher.getInstance();	 Catch:{ all -> 0x0119 }
            r0 = r17;
            r3.notifyStepCount(r0);	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r0 = r17;
            r3.saveTotalSteps(r0);	 Catch:{ all -> 0x0119 }
        L_0x00f2:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r6 = r0.values;	 Catch:{ all -> 0x0119 }
            r7 = 0;
            r6 = r6[r7];	 Catch:{ all -> 0x0119 }
            r6 = (int) r6;	 Catch:{ all -> 0x0119 }
            r3.step_more = r6;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.handler;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r6 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = r6.ifReMoveThread;	 Catch:{ all -> 0x0119 }
            r8 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
            r3.postDelayed(r6, r8);	 Catch:{ all -> 0x0119 }
        L_0x0116:
            monitor-exit(r18);	 Catch:{ all -> 0x0119 }
            goto L_0x0038;
        L_0x0119:
            r3 = move-exception;
            monitor-exit(r18);	 Catch:{ all -> 0x0119 }
            throw r3;
        L_0x011c:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3.step_sensor = r3.step_sensor + 1;	 Catch:{ all -> 0x0119 }
            goto L_0x0070;
        L_0x0125:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r3.juggle_move(r0);	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r4 = r0.timestamp;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.mPrevTime;	 Catch:{ all -> 0x0119 }
            r6 = r3.longValue();	 Catch:{ all -> 0x0119 }
            r6 = r4 - r6;
            r3 = (float) r6;	 Catch:{ all -> 0x0119 }
            r6 = 897988541; // 0x358637bd float:1.0E-6 double:4.436652885E-315;
            r3 = r3 * r6;
            r6 = 2139095039; // 0x7f7fffff float:3.4028235E38 double:1.056853372E-314;
            r13 = r3 % r6;
            r3 = "PedometerService";
            r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0119 }
            r6.<init>();	 Catch:{ all -> 0x0119 }
            r7 = "DeltaTime =";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r13);	 Catch:{ all -> 0x0119 }
            r7 = ", X=";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r7 = r0.values;	 Catch:{ all -> 0x0119 }
            r8 = 0;
            r7 = r7[r8];	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r7 = ", Y=";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r7 = r0.values;	 Catch:{ all -> 0x0119 }
            r8 = 1;
            r7 = r7[r8];	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r7 = ", Z=";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r7 = r0.values;	 Catch:{ all -> 0x0119 }
            r8 = 2;
            r7 = r7[r8];	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r7 = " ifhave:";
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r7 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r7 = r7.ifHaveStepSensor;	 Catch:{ all -> 0x0119 }
            r6 = r6.append(r7);	 Catch:{ all -> 0x0119 }
            r6 = r6.toString();	 Catch:{ all -> 0x0119 }
            android.util.Log.d(r3, r6);	 Catch:{ all -> 0x0119 }
            r3 = com.mcube.lib.ped.PedometerService.ifmove;	 Catch:{ all -> 0x0119 }
            if (r3 == 0) goto L_0x0116;
        L_0x01ab:
            r3 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
            r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1));
            if (r3 <= 0) goto L_0x0116;
        L_0x01b1:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.mPowerManager;	 Catch:{ all -> 0x0119 }
            r3 = r3.isScreenOn();	 Catch:{ all -> 0x0119 }
            if (r3 != 0) goto L_0x01f5;
        L_0x01bf:
            r3 = com.mcube.lib.ped.PedometerService.mPwrState;	 Catch:{ all -> 0x0119 }
            if (r3 != 0) goto L_0x01c9;
        L_0x01c5:
            r3 = 1;
            com.mcube.lib.ped.PedometerService.mPwrState = r3;	 Catch:{ all -> 0x0119 }
        L_0x01c9:
            r3 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
            r3 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1));
            if (r3 >= 0) goto L_0x0236;
        L_0x01cf:
            r3 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
            r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1));
            if (r3 >= 0) goto L_0x0236;
        L_0x01d5:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.mLibrary;	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r6 = r0.values;	 Catch:{ all -> 0x0119 }
            r7 = 0;
            r6 = r6[r7];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r7 = r0.values;	 Catch:{ all -> 0x0119 }
            r8 = 1;
            r7 = r7[r8];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r8 = r0.values;	 Catch:{ all -> 0x0119 }
            r9 = 2;
            r8 = r8[r9];	 Catch:{ all -> 0x0119 }
            r3.ProcessData(r4, r6, r7, r8);	 Catch:{ all -> 0x0119 }
        L_0x01f5:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.mPowerManager;	 Catch:{ all -> 0x0119 }
            r3 = r3.isScreenOn();	 Catch:{ all -> 0x0119 }
            if (r3 == 0) goto L_0x0229;
        L_0x0203:
            r3 = com.mcube.lib.ped.PedometerService.mPwrState;	 Catch:{ all -> 0x0119 }
            if (r3 != 0) goto L_0x02bd;
        L_0x0209:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.mLibrary;	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r6 = r0.values;	 Catch:{ all -> 0x0119 }
            r7 = 0;
            r6 = r6[r7];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r7 = r0.values;	 Catch:{ all -> 0x0119 }
            r8 = 1;
            r7 = r7[r8];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r8 = r0.values;	 Catch:{ all -> 0x0119 }
            r9 = 2;
            r8 = r8[r9];	 Catch:{ all -> 0x0119 }
            r3.ProcessData(r4, r6, r7, r8);	 Catch:{ all -> 0x0119 }
        L_0x0229:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r6 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x0119 }
            r3.mPrevTime = r6;	 Catch:{ all -> 0x0119 }
            goto L_0x0116;
        L_0x0236:
            r3 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
            r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1));
            if (r3 < 0) goto L_0x027b;
        L_0x023c:
            r3 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
            r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1));
            if (r3 > 0) goto L_0x027b;
        L_0x0242:
            r3 = (int) r13;	 Catch:{ all -> 0x0119 }
            r14 = r3 / 20;
            r16 = 0;
        L_0x0247:
            r0 = r16;
            if (r0 >= r14) goto L_0x0272;
        L_0x024b:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r7 = r3.mLibrary;	 Catch:{ all -> 0x0119 }
            r3 = r16 * 20;
            r8 = (long) r3;	 Catch:{ all -> 0x0119 }
            r8 = r8 + r4;
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 0;
            r10 = r3[r6];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 1;
            r11 = r3[r6];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 2;
            r12 = r3[r6];	 Catch:{ all -> 0x0119 }
            r7.ProcessData(r8, r10, r11, r12);	 Catch:{ all -> 0x0119 }
            r16 = r16 + 1;
            goto L_0x0247;
        L_0x0272:
            r3 = "PedometerService";
            r6 = "(time_tmp - mPrevTime) >=  30 ms";
            android.util.Log.d(r3, r6);	 Catch:{ all -> 0x0119 }
            goto L_0x01f5;
        L_0x027b:
            r3 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
            r3 = (r13 > r3 ? 1 : (r13 == r3 ? 0 : -1));
            if (r3 >= 0) goto L_0x01f5;
        L_0x0281:
            r6 = 15;
            java.lang.Thread.sleep(r6);	 Catch:{ InterruptedException -> 0x02b8 }
        L_0x0286:
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r7 = r3.mLibrary;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = com.mcube.lib.ped.PedometerService.this;	 Catch:{ all -> 0x0119 }
            r3 = r3.mPrevTime;	 Catch:{ all -> 0x0119 }
            r8 = r3.longValue();	 Catch:{ all -> 0x0119 }
            r10 = 5000000; // 0x4c4b40 float:7.006492E-39 double:2.470328E-317;
            r8 = r8 + r10;
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 0;
            r10 = r3[r6];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 1;
            r11 = r3[r6];	 Catch:{ all -> 0x0119 }
            r0 = r19;
            r3 = r0.values;	 Catch:{ all -> 0x0119 }
            r6 = 2;
            r12 = r3[r6];	 Catch:{ all -> 0x0119 }
            r7.ProcessData(r8, r10, r11, r12);	 Catch:{ all -> 0x0119 }
            goto L_0x01f5;
        L_0x02b8:
            r15 = move-exception;
            r15.printStackTrace();	 Catch:{ all -> 0x0119 }
            goto L_0x0286;
        L_0x02bd:
            r3 = com.mcube.lib.ped.PedometerService.mPwrState;	 Catch:{ all -> 0x0119 }
            r6 = 1;
            if (r3 != r6) goto L_0x0229;
        L_0x02c4:
            r3 = 0;
            com.mcube.lib.ped.PedometerService.mPwrState = r3;	 Catch:{ all -> 0x0119 }
            r0 = r18;
            r3 = r0.mPedometerController;	 Catch:{ all -> 0x0119 }
            r3.stopPedometer();	 Catch:{ all -> 0x0119 }
            r6 = 100;
            java.lang.Thread.sleep(r6);	 Catch:{ InterruptedException -> 0x02dd }
        L_0x02d4:
            r0 = r18;
            r3 = r0.mPedometerController;	 Catch:{ all -> 0x0119 }
            r3.startPedometer();	 Catch:{ all -> 0x0119 }
            goto L_0x0229;
        L_0x02dd:
            r15 = move-exception;
            r15.printStackTrace();	 Catch:{ all -> 0x0119 }
            goto L_0x02d4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mcube.lib.ped.PedometerService.AccelListener.onSensorChanged(android.hardware.SensorEvent):void");
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(PedometerService.TAG, "onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
        }
    }

    public class DateChangeReceiver extends BroadcastReceiver {
        private static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";
        private static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";

        public void onReceive(Context arg0, Intent arg1) {
            String action = arg1.getAction();
            if (ACTION_DATE_CHANGED.equals(action)) {
                Log.i(PedometerService.TAG, "---DATE_CHANGED!---");
                Intent phoneStepsIntent = new Intent(PedometerService.this.getApplicationContext(), PedometerService.class);
                PedometerService.this.stopService(phoneStepsIntent);
                PedometerService.this.startService(phoneStepsIntent);
            } else if (ACTION_SCREEN_OFF.equals(action) && PedometerService.this.ifHaveStepSensor && PedometerService.this.mAccel != null) {
                Log.i(PedometerService.TAG, "ACTION_SCREEN_OFF");
            }
        }
    }

    public class LocalBinder extends Binder {
        public void registerListeners(PedometerListener listener) {
            Log.d(PedometerService.TAG, "registerListeners()");
            PedometerService.this.mController.registerListeners(listener);
        }

        public void unRegisterListeners(PedometerListener listener) {
            Log.d(PedometerService.TAG, "unRegisterListeners()");
            PedometerService.this.mController.unRegisterListeners(listener);
        }

        public void startPedometer() {
            Log.d(PedometerService.TAG, "startPedometer()");
            PedometerService.this.mController.startPedometer();
        }

        public void stopPedometer() {
            Log.d(PedometerService.TAG, "stopPedometer()");
            PedometerService.this.mController.stopPedometer();
        }

        public void clearPedometerStepCount() {
            Log.d(PedometerService.TAG, "clearPedometerStepCount()");
            PedometerService.this.mController.clearPedometerStepCount();
        }
    }

    class PedometerController {
        PedometerController() {
        }

        public void registerListeners(PedometerListener listener) {
            Log.d(PedometerService.TAG, "PedometerController.registerListeners()");
            if (!PedometerService.this.mListeners.contains(listener)) {
                PedometerService.this.mListeners.add(listener);
            }
        }

        public void unRegisterListeners(PedometerListener listener) {
            Log.d(PedometerService.TAG, "PedometerController.unRegisterListeners()");
            if (PedometerService.this.mListeners.contains(listener)) {
                PedometerService.this.mListeners.remove(listener);
            }
            if (PedometerService.this.mListeners.size() == 0) {
                PedometerService.this.mSensorManager.unregisterListener(PedometerService.this.mAccelListener);
            }
        }

        public void startPedometer() {
            Log.d(PedometerService.TAG, "PedometerController.startPedometer()");
            if (PedometerService.this.mListeners.size() > 0) {
                PedometerService.this.mAccel = PedometerService.this.mSensorManager.getDefaultSensor(19);
                if (PedometerService.this.mAccel == null) {
                    PedometerService.this.mAccel = PedometerService.this.mSensorManager.getDefaultSensor(1);
                    PedometerService.this.ifHaveStepSensor = false;
                } else {
                    PedometerService.this.mWakeLock.release();
                    PedometerService.this.mWakeLock = null;
                    PedometerService.this.ifHaveStepSensor = true;
                }
                if (PedometerService.this.mAccel != null) {
                    Log.i(PedometerService.TAG, "has step counter sensor");
                    if (true == PedometerService.this.mSensorManager.registerListener(PedometerService.this.mAccelListener, PedometerService.this.mAccel, PedometerService.DELAY)) {
                        PedometerService.this.AccleListenerRegisted = true;
                    }
                }
            }
            if (PedometerService.this.mLibrary.IsOpened()) {
                PedometerService.this.mLibrary.Close();
                Log.d(PedometerService.TAG, "mLibrary.Close()");
            }
            if (PedometerService.this.mLibrary.Open(1)) {
                Log.d(PedometerService.TAG, "==================================Open PedLibrary Success");
            }
            PedometerService.this.mLibrary.registerListener(PedometerService.this.mPedListener);
            Log.d(PedometerService.TAG, "==================================mLibrary.registerListener(mPedListener)");
            PedometerService.this.mLibrary.PollVersion();
            Log.d(PedometerService.TAG, "==================================mLibrary.PollVersion()");
        }

        public void stopPedometer() {
            Log.d(PedometerService.TAG, "PedometerController.stopPedometer()");
            if (true == PedometerService.this.AccleListenerRegisted) {
                PedometerService.this.mSensorManager.unregisterListener(PedometerService.this.mAccelListener);
                PedometerService.this.AccleListenerRegisted = false;
            }
            if (PedometerService.this.mLibrary.IsOpened()) {
                PedometerService.this.mLibrary.unregisterListener(PedometerService.this.mPedListener);
                PedometerService.this.mLibrary.Close();
                PedometerService.this.stepCountOffset = PedometerService.this.stepCountOffset + PedometerService.this.currentStepCount;
                Log.d(PedometerService.TAG, "stepCountOffset=" + PedometerService.this.stepCountOffset);
            }
        }

        public void clearPedometerStepCount() {
            Log.d(PedometerService.TAG, "PedometerController.clearPedometerStepCount()");
            PedometerService.this.mPedListener.clearStepCount();
        }
    }

    class C17063 implements PedometerListener {
        C17063() {
        }

        public void onStepCount(int stepCount) {
            Log.i(PedometerService.TAG, "onStepCount:" + stepCount);
            PedometerService.hadRunStep = stepCount;
            StepWatcher.getInstance().notifyStepCount(stepCount);
            PedometerService.this.saveTotalSteps(stepCount);
        }

        public void onStateChanged(int newState) {
            StepWatcher.getInstance().notifyStateChanged(newState);
        }
    }

    class PedListenerImp implements PedListener {
        PedListenerImp() {
        }

        public void clearStepCount() {
            Log.d(PedometerService.TAG, "PedListener.clearStepCount()");
            Iterator it = PedometerService.this.mListeners.iterator();
            while (it.hasNext()) {
                ((PedometerListener) it.next()).onStepCount(0);
            }
            PedometerService.this.stepCountOffset = 0;
        }

        public void onStepDetected(int stepCount) {
            Log.d(PedometerService.TAG, "PedListener.onStepDetected()");
            PedometerService.this.currentStepCount = stepCount;
            Iterator it = PedometerService.this.mListeners.iterator();
            while (it.hasNext()) {
                ((PedometerListener) it.next()).onStepCount(PedometerService.this.currentStepCount + PedometerService.this.stepCountOffset);
            }
        }

        public void onStateChange(int state) {
            Log.d(PedometerService.TAG, "PedListener.onStateChange()");
            Iterator it = PedometerService.this.mListeners.iterator();
            while (it.hasNext()) {
                ((PedometerListener) it.next()).onStateChanged(state);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
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
        } else if (this.not_move_num >= 15000) {
            ifmove = false;
            if (!this.mPowerManager.isScreenOn()) {
                stopSelf();
            }
        } else {
            Log.i(TAG, "ifmove===" + this.not_move_num);
            this.not_move_num++;
        }
    }

    public void onCreate() {
        Log.d(TAG, "++ onCreate()++");
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.mPowerManager = (PowerManager) getSystemService("power");
        this.mWakeLock = this.mPowerManager.newWakeLock(1, PedometerService.class.getName());
        this.mWakeLock.acquire();
        this.mContentResolver = getContentResolver();
        IntentFilter intentf = new IntentFilter("android.intent.action.DATE_CHANGED");
        intentf.addAction("android.intent.action.SCREEN_OFF");
        this.mCtx.registerReceiver(this.mDateChangeReceiver, intentf);
        registerReceiver(this.mAliveReceiver, new IntentFilter("com.zhuoyou.steps.service.hello"));
        hadRunStep = 0;
        this.mController.registerListeners(this.pedListener);
        this.mController.startPedometer();
        if (new File(STEPSFILE).exists()) {
            try {
                this.input = super.openFileInput(FILENAME);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Scanner scan = new Scanner(this.input);
            while (scan.hasNextInt()) {
                this.currentSteps = scan.nextInt();
            }
            scan.close();
        }
        try {
            Log.i(TAG, "PedBackgroundService output....");
            this.output = super.openFileOutput(FILENAME, 0);
        } catch (FileNotFoundException e2) {
            Log.i(TAG, "PedBackgroundService FileNotFoundException....");
            e2.printStackTrace();
        }
        this.out = new PrintStream(this.output);
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, 1, startId);
    }

    public void onDestroy() {
        Log.d(TAG, "++ onDestroy()++");
        this.mPrevTime = Long.valueOf(0);
        mPwrState = 0;
        if (this.mAccel != null) {
            this.mSensorManager.unregisterListener(this.mAccelListener, this.mAccel);
            this.mAccel = null;
            this.step_sensor = 0;
        } else {
            this.mSensorManager.unregisterListener(this.mAccelListener);
        }
        Log.d(TAG, "mSensorManager.unregisterListener");
        Log.i("hph", "onDestroy111");
        if (this.mWakeLock != null) {
            Log.d(TAG, "wakeLock release");
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
        if (this.out != null) {
            this.out.close();
        }
        unregisterReceiver(this.mAliveReceiver);
        this.mController.unRegisterListeners(this.pedListener);
        this.mController.stopPedometer();
        this.mCtx.unregisterReceiver(this.mDateChangeReceiver);
        super.onDestroy();
    }

    private void saveTotalSteps(int step) {
        Log.i(TAG, "saveTotalSteps");
        if (step == 10) {
            readPhoneStep(Tools.getDate(0));
            this.total_step = PHONE_STEP;
        } else if (step >= 11) {
            Log.i("444", "step=" + step);
            Log.i("444", "hadRunStep=" + step);
            PersonalConfig config = Tools.getPersonalConfig();
            int meter = Tools.calcDistance(step, config.getHeight());
            int calories = Tools.calcCalories(meter, config.getWeightNum());
            String day = Tools.getDate(0);
            step += this.total_step;
            try {
                ArrayList<ContentProviderOperation> operations = new ArrayList();
                Cursor c = this.mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps", DataBaseContants.KILOMETER, DataBaseContants.CALORIES, DataBaseContants.SYNC_STATE}, "date  = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", "phone"}, null);
                if (c.getCount() <= 0 || !c.moveToFirst() || c.getLong(c.getColumnIndex("_id")) <= 0) {
                    operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", Long.valueOf(Tools.getPKL())).withValue("date", day).withValue("steps", Integer.valueOf(step)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue("type", Integer.valueOf(6)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(2)).withValue(DataBaseContants.DATA_FROM, "phone").withYieldAllowed(true).build());
                } else {
                    ContentProviderOperation op2;
                    if (c.getInt(c.getColumnIndex(DataBaseContants.SYNC_STATE)) == 0) {
                        op2 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", "phone"}).withValue("steps", Integer.valueOf(step)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withYieldAllowed(true).build();
                    } else {
                        op2 = ContentProviderOperation.newUpdate(DataBaseContants.CONTENT_URI).withSelection("date = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", "phone"}).withValue("steps", Integer.valueOf(step)).withValue(DataBaseContants.CALORIES, Integer.valueOf(calories)).withValue(DataBaseContants.KILOMETER, Integer.valueOf(meter)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(2)).withYieldAllowed(true).build();
                    }
                    operations.add(op2);
                }
                c.close();
                this.mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e2) {
                e2.printStackTrace();
            }
            this.handler.removeCallbacks(checkDataBaseThread);
            this.handler.post(checkDataBaseThread);
        }
    }

    private void getPaceGroup(int stepCount) {
        Log.i(TAG, "getPaceGroup");
        long current_time = System.currentTimeMillis();
        if (!this.flag) {
            this.flag = true;
            start_step = stepCount;
            this.last_time = current_time;
        }
        long ii = current_time - this.last_time;
        if (current_time - this.last_time > TimeManager.UNIT_MINUTE) {
            Log.i("111", "111111111111");
            if (stepCount - start_step >= 30) {
                Log.i("111", "start 22222222222");
                if (this.times == 0) {
                    this.start_time = this.last_time;
                }
                this.times++;
                Log.i("111", "times = " + this.times);
                this.steps += stepCount - start_step;
            } else if (this.times >= 10) {
                Log.i("111", "end 333333333333");
                this.steps += stepCount - start_step;
                save(this.steps, this.start_time, current_time);
                this.times = 0;
                this.steps = 0;
                this.flag = false;
            } else {
                Log.i("111", "clear 44444444444");
                this.times = 0;
                this.steps = 0;
                this.flag = false;
            }
            this.last_time = current_time;
            start_step = stepCount;
            Log.i("333", "start_step=" + start_step);
            Log.i("333", "start_step=" + start_step);
        }
    }

    private void readPhoneStep(String day) {
        Log.i(TAG, "readPhoneStep");
        Cursor c = this.mCtx.getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps"}, "date  = ? AND data_from  = ? AND statistics = ?", new String[]{day, "phone", "2"}, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            for (int y = 0; y < c.getCount(); y++) {
                PHONE_STEP = c.getInt(c.getColumnIndex("steps"));
                c.moveToNext();
            }
        }
        c.close();
    }

    private void save(int step, long start_time, long end_time) {
        Log.i(TAG, "save");
        String time = Tools.transformLongTime2StringFormat2(start_time);
        String date = time.substring(0, 10);
        String start = time.substring(11, 16);
        String end = Tools.transformLongTime2StringFormat2(end_time).substring(11, 16);
        PersonalConfig config = Tools.getPersonalConfig();
        int meter = Tools.calcDistance(step, config.getHeight());
        int calories = Tools.calcCalories(meter, config.getWeightNum());
        RunningItem runningItem = new RunningItem();
        runningItem.setDate(date);
        runningItem.setStartTime(start);
        runningItem.setEndTime(end);
        runningItem.setKilometer(meter);
        runningItem.setCalories(calories);
        runningItem.setSteps(step);
        runningItem.setmType(6);
        runningItem.setisStatistics(0);
        Intent phoneIntent = new Intent("ACTION_PHONE_STEPS");
        phoneIntent.putExtra("phone_steps", runningItem);
        phoneIntent.putExtra("total_step", hadRunStep);
        sendBroadcast(phoneIntent);
    }
}
