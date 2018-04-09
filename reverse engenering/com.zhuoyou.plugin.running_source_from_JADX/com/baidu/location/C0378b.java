package com.baidu.location;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

class C0378b implements ServiceConnection {
    final /* synthetic */ LocationClient f391a;

    C0378b(LocationClient locationClient) {
        this.f391a = locationClient;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f391a.mServer = new Messenger(iBinder);
        if (this.f391a.mServer != null) {
            this.f391a.mIsStarted = true;
            Log.d("baidu_location_client", "baidu location connected ...");
            if (this.f391a.isStop) {
                this.f391a.mHandler.obtainMessage(2).sendToTarget();
                return;
            }
            try {
                Message obtain = Message.obtain(null, 11);
                obtain.replyTo = this.f391a.mMessenger;
                obtain.setData(this.f391a.getOptionBundle());
                this.f391a.mServer.send(obtain);
                this.f391a.mIsStarted = true;
                if (this.f391a.mOption != null) {
                    if (this.f391a.firstConnected.booleanValue()) {
                        this.f391a.mHandler.obtainMessage(4).sendToTarget();
                    } else {
                        this.f391a.mHandler.obtainMessage(4).sendToTarget();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.f391a.mServer = null;
        this.f391a.mIsStarted = false;
    }
}
