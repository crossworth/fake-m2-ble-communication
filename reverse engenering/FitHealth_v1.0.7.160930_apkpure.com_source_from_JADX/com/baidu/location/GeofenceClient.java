package com.baidu.location;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import java.util.List;

public class GeofenceClient implements an, C1619j {
    public static final String BUNDLE_FOR_GEOFENCE_ID = "geofence_id";
    private static final int ba = 1;
    private Context a6;
    private OnGeofenceTriggerListener a7;
    private ServiceConnection a8 = new C04901(this);
    private Messenger a9 = new Messenger(this.bd);
    private Messenger bb = null;
    private boolean bc = false;
    private C0491a bd = new C0491a();

    class C04901 implements ServiceConnection {
        final /* synthetic */ GeofenceClient f2100a;

        C04901(GeofenceClient geofenceClient) {
            this.f2100a = geofenceClient;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.f2100a.bb = new Messenger(iBinder);
            if (this.f2100a.bb != null) {
                this.f2100a.bc = true;
                this.f2100a.startGeofenceScann();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.f2100a.bb = null;
            this.f2100a.bc = false;
        }
    }

    public interface OnAddBDGeofencesResultListener {
        void onAddBDGeofencesResult(int i, String str);
    }

    public interface OnGeofenceTriggerListener {
        void onGeofenceTrigger(String str);
    }

    public interface OnRemoveBDGeofencesResultListener {
        void onRemoveBDGeofencesByRequestIdsResult(int i, String[] strArr);
    }

    private class C0491a extends Handler {
        final /* synthetic */ GeofenceClient f2101a;

        private C0491a(GeofenceClient geofenceClient) {
            this.f2101a = geofenceClient;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.f2101a.m5799byte();
                    return;
                case an.f2211char /*208*/:
                    Bundle data = message.getData();
                    if (data != null) {
                        this.f2101a.m5802for(data.getString("geofence_id"));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public GeofenceClient(Context context) {
        this.a6 = context;
    }

    private void m5799byte() {
        if (!this.bc) {
            try {
                this.a6.bindService(new Intent(this.a6, C1976f.class), this.a8, 1);
            } catch (Exception e) {
                this.bc = false;
            }
        }
    }

    private void m5800case() {
        try {
            Message obtain = Message.obtain(null, 207);
            obtain.replyTo = this.a9;
            this.bb.send(obtain);
        } catch (Exception e) {
        }
    }

    private void m5802for(String str) {
        if (this.a7 != null) {
            this.a7.onGeofenceTrigger(str);
        }
    }

    public void addBDGeofence(BDGeofence bDGeofence, OnAddBDGeofencesResultListener onAddBDGeofencesResultListener) throws NullPointerException, IllegalArgumentException, IllegalStateException {
        ae.m2136a((Object) bDGeofence, (Object) "geofence is null");
        if (bDGeofence != null) {
            ae.m2141if(bDGeofence instanceof ah, "BDGeofence must be created using BDGeofence.Builder");
        }
        al.m5867for(this.a6).m5881if((ah) bDGeofence, onAddBDGeofencesResultListener);
    }

    public boolean isStarted() {
        return this.bc;
    }

    public void registerGeofenceTriggerListener(OnGeofenceTriggerListener onGeofenceTriggerListener) {
        if (this.a7 == null) {
            this.a7 = onGeofenceTriggerListener;
        }
    }

    public void removeBDGeofences(List list, OnRemoveBDGeofencesResultListener onRemoveBDGeofencesResultListener) throws NullPointerException, IllegalArgumentException {
        al.m5867for(this.a6).m5882if(list, onRemoveBDGeofencesResultListener);
    }

    public void start() throws NullPointerException {
        ae.m2136a(this.a7, (Object) "OnGeofenceTriggerListener not register!");
        this.bd.obtainMessage(1).sendToTarget();
    }

    public void startGeofenceScann() {
        if (this.bc) {
            try {
                Message obtain = Message.obtain(null, 206);
                obtain.replyTo = this.a9;
                this.bb.send(obtain);
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        m5800case();
    }
}
